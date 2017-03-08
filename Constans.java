package com.richlabz.smileyserve.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Richlabz on 15-06-2016.
 */
public class Constans extends AppCompatActivity {

    public static java.lang.CharSequence progressmessage = "Please wait .....";
    public static String Response_200 = "200";
    public static String email = "email";
    public static String userid = "userid";
    public static String userName = "username";
    public static String details = "mobile";
    public static String valid = "Please enter valid username minimum length is 3";
    public static SimpleDateFormat targetFormat = new SimpleDateFormat();
    public static SimpleDateFormat originalFormat = new SimpleDateFormat();
    public static String formattedDate = "";
    public static String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String MobilePattern = "[7-9][0-9]{9}";
    public static String response200 = "200";
    public static String backpressed = "Click again to exit.";
    public static String cartemptymessage = "Cart is empty!";
    public static String deleteconfirmation = "Are you sure want to delete this Item?";
    public static String deleteTitle = "Confirmation Message";
    public static String nordersfound = "No Orders Found..";
    public static String facebook = "Facebook";
    public static String publicprofile = "public_profile email";
    public static String name = "name";
    public static CharSequence alreadyhaveaccount = "Already have account?";
    public static CharSequence readtc = "I have read and agreed to ";
    public static CharSequence tc = " Terms & Conditions ";
    public static CharSequence login = " Login ";
    public static String months[] = {"Jan", "Feb", "Mar", "April", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static String cash="smileycash";
    public static String Otp="otp";
    public static String mobile="mobile";
    public static String endsubsciption="Do you want to end this subscription?";
    public static String apartmentchange="All your subscribed orders will be cancelled and amount refund to Smileycash . Are you sure want to change your apartment?";
    public static String apartid="apartid";
    public static String usesmileycash="Do you Want to Pay Amount  Using Your SmileyCash ?";
    public static String deleteALLconfirmation="Are you sure want to delete  Items?";
    public static String deleteALLconfirmationforuser="Are you sure want to exist this page?";
    public static String otpmobile="otpmobile";
    public static String weekbarmessage="Example for one month subscription : Select one month subscription period of milk, and select quantity on week bar as you like. Same week quantity will repeat to entire subscription period.";
    public static boolean haveInternet(Context ctx) {
        try {
            NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();

            if (info == null || !info.isConnected()) {
                return false;
            }
        } catch (Exception e) {
            Log.d("err", e.toString());
        }
        return true;
    }
    // here convert datecoversion
    public static String getFormattedDate(String targetPattern, String existingPattern, String existingValue) {
        formattedDate = existingValue;
        targetFormat.applyPattern(targetPattern);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        symbols.setAmPmStrings(new String[]{"AM", "PM"});
        targetFormat.setDateFormatSymbols(symbols);
        originalFormat.applyPattern(existingPattern);
        try {
            formattedDate = targetFormat.format(originalFormat
                    .parse(existingValue));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    public static void hideKeyboard(RegisterActivity registerActivity) {
        View view = registerActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) registerActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public static String getStartdate(String date)

    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        date = String.valueOf(new StringBuilder().append(year)
                .append("-").append(month + 1).append("-").append(day)
                .append(" "));
        return  date;
    }
    public static String getTomorrowdare(String date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        date = String.valueOf(new StringBuilder().append(year)
                .append("-").append(month + 1).append("-").append(day)
                .append(" "));
        return  date;
    }


    public static void IntenetSettings(final Context ctx) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder
                .setMessage("No internet connection on your device. Would you like to enable it?")
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton(" Enable Internet ",
                        new DialogInterface.OnClickListener()
                        {

                            public void onClick(DialogInterface dialog, int id)
                            {
                                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                ctx.startActivity(dialogIntent);

                            }
                        });

        alertDialogBuilder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
