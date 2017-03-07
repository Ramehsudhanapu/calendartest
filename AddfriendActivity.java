package com.richlabz.mfhvendor.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.HLMSolutions.mfhvendor.R;
import com.richlabz.mfhvendor.commonutilities.GetReferalAmount;
import com.richlabz.mfhvendor.commonutilities.MfhvendorDatasource;
import com.richlabz.mfhvendor.commonutilities.SharedPreferenceUtil;
import com.richlabz.mfhvendor.entities.RestResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddfriendActivity extends AppCompatActivity {
    private static final int PICK_CONTACT = 111;
    private static final int PERMISSION_REQUEST_CONTACT = 222;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    ProgressDialog pDialog;
    String userid;
    public static final int REQUEST_CODE_PICK_CONTACT = 1;
    MfhvendorDatasource mfhvendorDatasource=new MfhvendorDatasource(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referalamount);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userid = SharedPreferenceUtil.getInstance(this).getVendorid();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @OnClick(R.id.bt_submit) void getprofile()
    {
        Addfriend();
    }
    private void Addfriend() {
        askForContactPermission();
    }
    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please confirm Contacts access");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);
                }
            } else {
                getContact();
            }
        } else {
            getContact();
        }
    }

    private void getContact() {
        Intent intentContactPick = new Intent(this,ContactsPickerActivity.class);
        AddfriendActivity.this.startActivityForResult(intentContactPick,REQUEST_CODE_PICK_CONTACT);
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_PICK_CONTACT && resultCode == RESULT_OK){
            ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
            String display="";
            //  here Selected Muliple Addresss
            for(int i=0;i<selectedContacts.size();i++) {
                display += selectedContacts.get(i).phone.replace("+91", "") + ",";
            }

            GetReferalAmount referalAmount = new GetReferalAmount();
                    referalAmount.setEmail("");
                    referalAmount.setName("");
                    referalAmount.setMobilenumber( display);
                    referalAmount.setVendor_id(userid);
             mfhvendorDatasource.refererfriend(referalAmount);
        }
        }

    public static String retrieveContactRecord(Context activity, String phoneNo) {
        String contactName = null;
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNo));
            String[] projection = new String[]{ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.PHOTO_URI};
            String selection = null;
            String[] selectionArgs = null;
            String sortOrder = ContactsContract.PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
            ContentResolver cr = activity.getContentResolver();
            if (cr != null) {
                Cursor resultCur = cr.query(uri, projection, selection, selectionArgs, sortOrder);
                if (resultCur != null) {
                    while(resultCur.moveToNext()) {
                        //contactId = resultCur.getString(resultCur.getColumnIndex(ContactsContract.PhoneLookup._ID));
                        contactName = resultCur.getString(resultCur.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
                        String photoUri = resultCur.getString(resultCur.getColumnIndexOrThrow(ContactsContract.PhoneLookup.PHOTO_URI));
                        byte[] data = resultCur.getBlob(resultCur.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_URI));
                        if (data != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        }
                        break;
                    }
                    resultCur.close();
                }
            }
        } catch (Exception sfg) {
            // Log.e("Error", "Error in loadContactRecord : "+sfg.toString());
        }
        return contactName;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                }
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    public void showReferaamount(Object o) {
        String response = ((RestResponse) o).getDescription();
        String responsecode = ((RestResponse) o).getCode();
        if (responsecode.equals("200")) {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            pDialog.dismiss();
        }
    }
}
