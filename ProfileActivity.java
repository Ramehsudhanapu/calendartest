package com.richlabz.smileyserve.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.richlabz.smileyserve.R;
import com.richlabz.smileyserve.datasource.SmileyserveDataSource;
import com.richlabz.smileyserve.datasource.UpdateProfile;
import com.richlabz.smileyserve.entities.Apartmentlist;
import com.richlabz.smileyserve.entities.GettingApartmentlist;
import com.richlabz.smileyserve.entities.RestResponse;
import com.richlabz.smileyserve.entities.Userdetails;
import com.richlabz.smileyserve.entities.loginRestResponse;
import com.richlabz.smileyserve.utilities.SharedPreferenceUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {
    @Bind(R.id.profilename)
    EditText editprofile;
    @Bind(R.id.profileemail)
    EditText editemail;
    @Bind(R.id.profilemobilenumber)
    EditText editmobilenumber;
    @Bind(R.id.profileflat)
    EditText ediprofielflat;
    @Bind(R.id.profileaddress)
    EditText editprofileaddress;
    String userid;
    @Bind(R.id.profileapratmentspinner)
    Spinner profileapratmentspinner;
    @Bind(R.id.errormessage)
    TextView txterrormessage;
    @Bind(R.id.ediiprofile)
    Button editbtn;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.line)
    LinearLayout spinnerline;
    ArrayAdapter<String> arrayAdapter;
    String[] Aparmentlist;
    SmileyserveDataSource smileyserveDataSource = new SmileyserveDataSource(this);
    int apartmentid;
    ProgressDialog progressDialog;
    String name, mobile;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    Apartmentlist aparmentlist;
    List<GettingApartmentlist> apartmentlists;
    private String spinervalue;
    private String aparment;
    private boolean isNoSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.EditTheme);
        setContentView(R.layout.profile_fragment);
        ButterKnife.bind(this);
        editemail.setEnabled(false);
        userid = SharedPreferenceUtil.getInstance(this).getUserid();
        //  Toast.makeText(getContext(),userid,Toast.LENGTH_SHORT).show();
        if (Constans.haveInternet(this)) {
            if (userid != null) {
                smileyserveDataSource.getprofile(userid, 1);
                progressDialog = ProgressDialog.show(this, "", Constans.progressmessage, true, false);
            }

        } else {
            Constans.IntenetSettings(this);
        }
        profileapratmentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    GettingApartmentlist gettingApartmentlist = apartmentlists.get(position);
                    apartmentid = Integer.parseInt(gettingApartmentlist.getId());
                    if (!spinervalue.equals("1")) {
                        if (!isNoSelected) {
                            showalert(view, position);
                        } else {
                            isNoSelected = false;
                        }
                    }
                    spinervalue = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    public void showresponse(Object o) {
        String response = ((loginRestResponse) o).getCode();
        if (response.equals(Constans.Response_200)) {
            progressDialog.dismiss();
            final Userdetails userdetails = ((loginRestResponse) o).getUser_result();
            aparmentlist = ((loginRestResponse) o).getApartment_result();
            apartmentlists = aparmentlist.getApartment_result();
            GettingApartmentlist gettingApartmentlist = new GettingApartmentlist();
            gettingApartmentlist.setAddress("Select Apartment");
            apartmentlists.add(0, gettingApartmentlist);
            Aparmentlist = new String[apartmentlists.size()];
            if (apartmentlists.size() > 0) {
                for (int i = 0; i < apartmentlists.size(); i++) {
                    Aparmentlist[0] = apartmentlists.get(0).getAddress();
                    Aparmentlist[i] = apartmentlists.get(i).getTitle() + ", " + apartmentlists.get(i).getLocation();
                }
                // here  check for the edit and Save Button
                name = userdetails.getName();
                mobile = userdetails.getMobile();
                SharedPreferenceUtil.getInstance(ProfileActivity.this).saveKey(Constans.mobile, mobile);
                SharedPreferenceUtil.getInstance(ProfileActivity.this).saveKey(Constans.name, name);
                editemail.setText(userdetails.getEmail());
                editemail.setEnabled(false);
                editmobilenumber.setText(userdetails.getMobile());
                editmobilenumber.setEnabled(false);
                ediprofielflat.setText(userdetails.getPlotno());
                ediprofielflat.setEnabled(false);
                if ((name != null) && (mobile != null) && (!name.matches("")) && (!mobile.matches(""))) {
                    editprofile.setText(name);
                    editprofile.setEnabled(false);
                    update.setVisibility(View.GONE);
                    editbtn.setVisibility(View.VISIBLE);
                    profileapratmentspinner.setVisibility(View.GONE);
                    spinnerline.setVisibility(View.GONE);
                    aparment = userdetails.getApartment();
                    if ((aparment != null) && (!aparment.equals(""))) {
                        editprofileaddress.setText(aparment);
                        editprofileaddress.setEnabled(false);
                    }
                    if (Aparmentlist.length > 0) {
                        arrayAdapter = new ArrayAdapter<>(this, R.layout.suggestionlayout, Aparmentlist);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        profileapratmentspinner.setAdapter(arrayAdapter);
                        if (aparment != null) {
                            profileapratmentspinner.setSelection(getIndex(profileapratmentspinner, aparment));
                            spinervalue = "1";
                            isNoSelected = false;
                        }
                    }
                    editbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editbtn.setVisibility(View.GONE);
                            update.setVisibility(View.VISIBLE);
                            editprofile.setEnabled(true);
                            editemail.setEnabled(false);
                            editprofileaddress.setVisibility(View.GONE);
                            spinnerline.setVisibility(View.VISIBLE);
                            profileapratmentspinner.setVisibility(View.VISIBLE);
                            editmobilenumber.setEnabled(true);
                            ediprofielflat.setEnabled(true);
                        }
                    });
                } else {
                    editbtn.setVisibility(View.GONE);
                    update.setVisibility(View.VISIBLE);
                    editprofile.setEnabled(true);
                    editmobilenumber.setEnabled(true);
                    ediprofielflat.setEnabled(true);
                    editprofileaddress.setVisibility(View.GONE);
                    spinnerline.setVisibility(View.VISIBLE);
                    profileapratmentspinner.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    /**
     * detete Cart Item FROM Cart
     *************/
    private void showalert(final View view, final int position) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getContext());
        alertbox.setMessage(Constans.apartmentchange);
        alertbox.setTitle(Constans.deleteTitle);
        alertbox.setCancelable(false);
        alertbox.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                GettingApartmentlist gettingApartmentlist = apartmentlists.get(position);
                if (position > 0) {
                    apartmentid = Integer.parseInt(gettingApartmentlist.getId());
                    ediprofielflat.setText("");
                }
            }

        });
        alertbox.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface daillogue, int arg1) {
                        if (aparment != null) {
                            isNoSelected = true;
                            profileapratmentspinner.setSelection(getIndex(profileapratmentspinner, aparment));
                            daillogue.dismiss();
                        }

                    }
                });
        alertbox.show();

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

    @OnClick(R.id.update) void updateprofile() {
        update.setVisibility(View.GONE);
        UpdateProfile updateProfile = new UpdateProfile();
        updateProfile.setUserid(userid);
        String name = editprofile.getText().toString().trim();
        if ((name.length() >= 3)) {
            updateProfile.setUsername(name);
            updateProfile.setUsermobile(editmobilenumber.getText().toString().trim());
            // updateProfile.setAddress(editprofileaddress.getText().toString().trim());
            updateProfile.setArea("0");
            if (apartmentid > 0) {
                updateProfile.setApartment(apartmentid);
            }
            updateProfile.setPlotno(ediprofielflat.getText().toString().trim());
            updateProfile.setCity("0");
            updateProfile.setState("0");
            smileyserveDataSource.updateprofile(updateProfile, 1);
        } else {
            txterrormessage.setVisibility(View.VISIBLE);
            txterrormessage.setText(R.string.entername);
        }
    }

    public void showUpdateResponse(Object o) {
        String response = ((RestResponse) o).getDescription();
        String responsecode = ((RestResponse) o).getCode();
        update.setVisibility(View.VISIBLE);
        if (response != null) {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        }
        if (responsecode.equals(Constans.Response_200)) {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();


        } else {
            txterrormessage.setVisibility(View.VISIBLE);
            txterrormessage.setText(response);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}

