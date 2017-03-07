package com.richlabz.mfhvendor.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.HLMSolutions.mfhvendor.R;
import com.richlabz.mfhvendor.commonutilities.Constans;
import com.richlabz.mfhvendor.commonutilities.CurrentLocation;
import com.richlabz.mfhvendor.commonutilities.DriverUpdateProfile;
import com.richlabz.mfhvendor.commonutilities.GettingProfile;
import com.richlabz.mfhvendor.commonutilities.MfhvendorDatasource;
import com.richlabz.mfhvendor.commonutilities.PostProfile;
import com.richlabz.mfhvendor.commonutilities.Profiledetails;
import com.richlabz.mfhvendor.commonutilities.ReplayPoojabids;
import com.richlabz.mfhvendor.commonutilities.SelectImage;
import com.richlabz.mfhvendor.commonutilities.SharedPreferenceUtil;
import com.richlabz.mfhvendor.commonutilities.Utility;
import com.richlabz.mfhvendor.entities.GetCategorieslist;
import com.richlabz.mfhvendor.entities.RestResponse;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    String userChoosenTask, licencechoosetask, adhaarchoosetask, licencebackchoosetask;
    String encodedImage, licenceencodeimage, adharencodeimage, driverbackendendcode;
    String imgPath, licencepath, adhaarpath, backendlicencpath;
    @Bind(R.id.userimg)
    CircleImageView userimage;
    @Bind(R.id.licencepic)
    ImageView img_licence;
    @Bind(R.id.adhaarpic)
    ImageView img_adhaarpic;
    EditText refid;
    @Bind(R.id.Emaiid)
    EditText emailid;
    @Bind(R.id.mobilenumber)
    EditText mobilenumber;
    @Bind(R.id.age)
    EditText age;
    @Bind(R.id.exp)
    Spinner exp;
    @Bind(R.id.address)
    EditText address;
    @Bind(R.id.toolbars)
    Toolbar mtoolbar;
    @Bind(R.id.drivername)
    EditText text_name;
    MfhvendorDatasource mfhvendorDatasource, mfhupdateprofile;
    GPSTracker gps;
    private SelectImage selectImage, selecticence, selectadahar, Selectbackendlicence;
    @Bind(R.id.state)
    EditText state;
    @Bind(R.id.area)
    EditText area;
    @Bind(R.id.city)
    EditText city;
    @Bind(R.id.pincode)
    EditText pincode;
    private String vendorid;
    ProgressDialog progressDialog;
    String from, to, dateofjourney, bidid, returndates, exps, journeytype, licetype, licencesubtype, types;
    @Bind(R.id.profile)
    LinearLayout profile;
    @Bind(R.id.driverbackedlicence)
    ImageView img_backend;
    String[] agearray = {"Select Experience", "0 - 5", "5-10", "10-15", "Above15"};
    private String agevalue, adhaaimage, licenceimage, profilepicimage, licencebackimage;
    private String spinervalue;
    private String experience;
    @Bind(R.id.driverimges)
    LinearLayout driverimages;
    @Bind(R.id.driverbacendimg)
    RelativeLayout Driverbackendimg;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilefragment);
        ButterKnife.bind(this);
        setSupportActionBar(mtoolbar);
        mfhvendorDatasource = new MfhvendorDatasource(this);
        mfhupdateprofile = new MfhvendorDatasource(this);
        vendorid = SharedPreferenceUtil.getInstance(this).getVendorid();
        userId = SharedPreferenceUtil.getInstance(this).getUserid();
        if(userId.equals("1"))
        {
            driverimages.setVisibility(View.GONE);
            Driverbackendimg.setVisibility(View.GONE);
        }
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent i = getIntent();
        Bundle b = i.getExtras();
    // GETTING VALUES FROM DRIVER not applied bids
        if (b != null) {
            from = b.getString("from");
            to = b.getString("to");
            dateofjourney = b.getString("dateofjourney");
            bidid = b.getString("bidid");
            returndates = b.getString("returndate");
            exps = b.getString("exp");
            journeytype = b.getString("journeytime");
            licetype = b.getString("licecntype");
            licencesubtype = b.getString("licencesubtype");
            types = b.getString("type");
        }
        // Get Profile Api
        if (vendorid != null) {
            PostProfile postProfile = new PostProfile();
            postProfile.setVendorid(vendorid);
            mfhvendorDatasource.Getprofile(postProfile,1);
            progressDialog = ProgressDialog.show(this, "", "Please wait Loading...", true, true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    // profie pic
    @OnClick({R.id.userimg})
    protected void browseClick() {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ProfileActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    cameraIntent();
                } else if (items[item].equals("Choose from gallery")) {
                    userChoosenTask = "Choose from gallery";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    // Driving Licence  Front image
    @OnClick({R.id.licencepic}) protected void licenceimage() {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    licencechoosetask = "Take Photo";
                    licenceIntent();
                } else if (items[item].equals("Choose from gallery")) {
                    licencechoosetask = "Choose from gallery";
                    licegalleryuintent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    // Driving Licence Back Image
    @OnClick({R.id.driverbackedlicence})
    protected void backlicenceimage() {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    licencebackchoosetask = "Take Photo";
                    licenceBackIntent();
                } else if (items[item].equals("Choose from gallery")) {
                    licencebackchoosetask = "Choose from gallery";
                    licebackgalleryuintent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    // AADHAAR Image
    @OnClick({R.id.adhaarpic})
    protected void adhaarimage() {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result1 = Utility.checkPermissionadhaar(ProfileActivity.this);
                if (items[item].equals("Take Photo")) {
                    adhaarchoosetask = "Take Photo";

                    adhaarintent();
                } else if (items[item].equals("Choose from gallery")) {
                    adhaarchoosetask = "Choose from gallery";
                    adharrgalleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo")) {
                        cameraIntent();
                    } else if (userChoosenTask.equals("Choose from Library")) {
                        galleryIntent();

                    }
                }
                break;
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (licencechoosetask.equals("Take Photo")) {
                        licenceIntent();
                        break;
                    } else if (licencechoosetask.equals("Choose from Library")) {
                        licegalleryuintent();
                        break;
                    }
                }

                break;
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (adhaarchoosetask.equals("Take Photo")) {
                        adhaarintent();
                        break;
                    } else if (adhaarchoosetask.equals("Choose from Library")) {
                        adharrgalleryIntent();
                    }
                }
                break;

            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (adhaarchoosetask.equals("Take Photo")) {
                        licenceBackIntent();
                        break;
                    } else if (adhaarchoosetask.equals("Choose from Library")) {
                        licebackgalleryuintent();
                    }
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            selectImage = new SelectImage(ProfileActivity.this);
            selecticence = new SelectImage(ProfileActivity.this);
            selectadahar = new SelectImage(this);
            Selectbackendlicence=new SelectImage(this);
            if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
                imgPath = selectImage.getImagePath(data);
                userimage.setImageBitmap(BitmapFactory.decodeFile(imgPath));
            } else if (requestCode == 200 && resultCode == RESULT_OK && null != data) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imgPath = selectImage.getCameraImg(thumbnail);
                userimage.setImageBitmap(thumbnail);
            } else if (requestCode == 300 && resultCode == RESULT_OK && null != data) {
                licencepath = selecticence.getImagePathLicence(data);
                img_licence.setImageBitmap(BitmapFactory.decodeFile(licencepath));

            } else if (requestCode == 400 && resultCode == RESULT_OK && null != data) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                licencepath = selecticence.getCameraLicenceImg(thumbnail);
                img_licence.setImageBitmap(thumbnail);

            } else if (requestCode == 500 && resultCode == RESULT_OK && null != data) {
                adhaarpath = selectadahar.getImagePath(data);
                img_adhaarpic.setImageBitmap(BitmapFactory.decodeFile(adhaarpath));

            } else if (requestCode == 600 && resultCode == RESULT_OK && null != data) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                adhaarpath = selectadahar.getCameraCameraeImg(thumbnail);
                img_adhaarpic.setImageBitmap(thumbnail);

            } else if ((requestCode == 700 && resultCode == RESULT_OK && null != data)) {
                backendlicencpath =  Selectbackendlicence.getDriverbackendImagePath(data);
                img_backend.setImageBitmap(BitmapFactory.decodeFile(backendlicencpath));
            } else if ((requestCode == 800 && resultCode == RESULT_OK && null != data)) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                backendlicencpath =  Selectbackendlicence.getCameraBackendlicenceImg(thumbnail);
                img_backend.setImageBitmap(thumbnail);
            } else {
                Toast.makeText(this, R.string.pickImg, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.somethingWrong, Toast.LENGTH_LONG).show();
        }
    }

    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 100);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 200);
    }

    private void adharrgalleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 500);
    }
    private void licegalleryuintent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 300);
    }
    private void adhaarintent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 600);
    }
    private void licenceIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 400);
    }
    private void licebackgalleryuintent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 700);
    }
    private void licenceBackIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 800);
    }
    @OnClick(R.id.update) void Updateprofile() {
        if (text_name.getText().toString().length() > 0 && age.getText().toString().length() > 0 && mobilenumber.getText().length() > 0 && agevalue != null && pincode.getText().length() > 0 && state.getText().length() > 0 && city.getText().length() > 0) {
            DriverUpdateProfile driverUpdateProfile = new DriverUpdateProfile();
            driverUpdateProfile.setBusinessname(text_name.getText().toString());
            driverUpdateProfile.setMobile(mobilenumber.getText().toString().trim());
            driverUpdateProfile.setAge(age.getText().toString());
            driverUpdateProfile.setExperience(agevalue);
            driverUpdateProfile.setDriverexp(agevalue);
            driverUpdateProfile.setAddress(address.getText().toString());
            driverUpdateProfile.setArea(area.getText().toString());
            driverUpdateProfile.setState(state.getText().toString());
            driverUpdateProfile.setCity(city.getText().toString());
            driverUpdateProfile.setState(state.getText().toString());
            driverUpdateProfile.setOtherdata("0");
            driverUpdateProfile.setLandmark("0");
            driverUpdateProfile.setLatitude("0");
            driverUpdateProfile.setLongitude("0");
            driverUpdateProfile.setPincode(pincode.getText().toString());
            driverUpdateProfile.setVendorid(vendorid);
            if ((imgPath != null) || (profilepicimage != null)) {
                if (imgPath != null) {
                    encodedImage = selectImage.encodeImg(imgPath);
                    driverUpdateProfile.setPicture_data(encodedImage);
                } else {
                    driverUpdateProfile.setPicture_data("");
                }
                driverUpdateProfile.setPicture("Name.jpeg");
            }
            if ((licencepath != null) || (licenceimage != null)) {
                if (licencepath != null) {
                    licenceencodeimage = selecticence.encodeLiceImg(licencepath);
                    driverUpdateProfile.setLicenseimage_data(licenceencodeimage);
                } else {
                    driverUpdateProfile.setLicenseimage_data("");
                }
                driverUpdateProfile.setLicenseimage("Licence.jpeg");
            }
            if ((adhaarpath != null) || (adhaaimage != null)) {
                if (adhaarpath != null) {
                    adharencodeimage = selectadahar.encodeadharrImg(adhaarpath);
                    driverUpdateProfile.setAddharimage_data(adharencodeimage);
                } else {
                    driverUpdateProfile.setAddharimage_data("");
                }
                driverUpdateProfile.setAdharimage("Adhaar.jpeg");
            }
            if ((backendlicencpath != null) || (licencebackimage != null)) {
                if (backendlicencpath != null) {
                    driverbackendendcode =  Selectbackendlicence.encodeDRrivingbackendImg(backendlicencpath);
                    driverUpdateProfile.setLicensebackimg_data(driverbackendendcode);
                } else {
                    driverUpdateProfile.setLicensebackimg_data("");
                }
                driverUpdateProfile.setLicensebackimg("Drivinglicence.jpeg");
            }
              mfhupdateprofile.updateProfiel(driverUpdateProfile);
            progressDialog = ProgressDialog.show(this, "", "Please wait While Submitting your Records...", true, true);
        } else {
            Toast.makeText(this, R.string.mandatoryfiled, Toast.LENGTH_SHORT).show();
        }
    }

    public void showProfileresponse(Object o) {
        String response = ((GettingProfile) o).getCode();
        if (response.equals("200")) {
            progressDialog.dismiss();
            profile.setVisibility(View.VISIBLE);
            Profiledetails profiledetails = ((GettingProfile) o).getVendordetails();
            text_name.setText(profiledetails.getBusinessname());
            emailid.setText(profiledetails.getEmail());
            emailid.setEnabled(false);
            experience =profiledetails.getDriverexp();
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, agearray);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            exp.setAdapter(arrayAdapter);
            if (experience!= null) {
                exp.setSelection(getIndex(exp,experience));
                ///spinervalue = "1";
            }
            exp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i > 0) {
                        agevalue = arrayAdapter.getItem(i);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            mobilenumber.setText(profiledetails.getMobile());
            age.setText(profiledetails.getAge());
            address.setText(profiledetails.getAddress());
            area.setText(profiledetails.getArea());
            pincode.setText(profiledetails.getPincode());
            state.setText(profiledetails.getState());
            city.setText(profiledetails.getCity());
            adhaaimage = profiledetails.getProfilepic();
            licenceimage = profiledetails.getDriverlicense();
            profilepicimage = profiledetails.getProfilepic();
            licencebackimage = profiledetails.getLicensebackimg();
            Picasso.with(this).load(profiledetails.getProfilepic()).placeholder(R.drawable.driveimage).into(userimage);
            Picasso.with(this).load(profiledetails.getDriverlicense()).placeholder(R.drawable.newdriver).into(img_licence);
            Picasso.with(this).load(profiledetails.getDriveraadhar()).placeholder(R.drawable.adharrimage).into(img_adhaarpic);
            Picasso.with(this).load(profiledetails.getLicensebackimg()).placeholder(R.drawable.drivingbackimage).into(img_backend);
            SharedPreferenceUtil.getInstance(this).saveKey(Constans.age, profiledetails.getAge());
            SharedPreferenceUtil.getInstance(this).saveKey(Constans.address, profiledetails.getAddress());
            SharedPreferenceUtil.getInstance(this).saveKey(Constans.exp, profiledetails.getExperience());
        } else {
            profile.setVisibility(View.VISIBLE);
            progressDialog.dismiss();

        }
    }
    private int getIndex(Spinner profileapratmentspinner, String appartment_id) {
        int index = 0;
        for (int i = 0; i < profileapratmentspinner.getCount(); i++) {
            if (profileapratmentspinner.getItemAtPosition(i).equals(appartment_id)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public void updateprofileresponse(Object o) {
        String response = ((RestResponse) o).getCode();
        String message = ((RestResponse) o).getMessage();
        if (response.equals("200")) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (types != null) {
                if (types.equals("1")) {
                    Intent i = new Intent(this, DriverBidnowActivity.class);
                    i.putExtra("from", from);
                    i.putExtra("to", to);
                    i.putExtra("dateofjourney", dateofjourney);
                    i.putExtra("journeytime", journeytype);
                    i.putExtra("exp", exps);
                    i.putExtra("licecntype", licetype);
                    i.putExtra("licencesubtype", licencesubtype);
                    i.putExtra("bidid", bidid);
                    i.putExtra("returndate", returndates);
                    startActivity(i);
                } else {
                    finish();
                }
            } else
            {
                finish();
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}

