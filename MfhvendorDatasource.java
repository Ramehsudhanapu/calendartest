package com.richlabz.mfhvendor.commonutilities;

import com.richlabz.mfhvendor.activity.AddfriendActivity;
import com.richlabz.mfhvendor.activity.AppliedBiddingsActivtiy;
import com.richlabz.mfhvendor.activity.AppliedCategoryActivity;
import com.richlabz.mfhvendor.activity.BidNowActivity;

import com.richlabz.mfhvendor.activity.BiddingAppliedViewDetails;
import com.richlabz.mfhvendor.activity.BiddingViewDetails;
import com.richlabz.mfhvendor.activity.DriverAppliedBidActivity;
import com.richlabz.mfhvendor.activity.DriverBidnowActivity;
import com.richlabz.mfhvendor.activity.DriverNotAppliedViewActivtiy;
import com.richlabz.mfhvendor.activity.FunctionHallSubmittedBidsActivity;
import com.richlabz.mfhvendor.activity.FunctionhalNotSubmittedBidsActivity;
import com.richlabz.mfhvendor.activity.MyBiddingOrderActiity;
import com.richlabz.mfhvendor.activity.PandithBidnowActivity;
import com.richlabz.mfhvendor.activity.PandithNotSubmittedBids;
import com.richlabz.mfhvendor.activity.PandithSubmittedBids;
import com.richlabz.mfhvendor.activity.Pandithhomefragment;
import com.richlabz.mfhvendor.activity.ProfileActivity;
import com.richlabz.mfhvendor.activity.RegisterActivtiy;
import com.richlabz.mfhvendor.activity.TravelAppliedBiddingview;
import com.richlabz.mfhvendor.activity.TravelNotAppliedBiddingActivity;
import com.richlabz.mfhvendor.activity.TravelBidNowActivity;
import com.richlabz.mfhvendor.fragment.Driverhomefragment;
import com.richlabz.mfhvendor.fragment.FragmentDrawer;
import com.richlabz.mfhvendor.fragment.Functionhallhomefragment;
import com.richlabz.mfhvendor.fragment.Homefragment;
import com.richlabz.mfhvendor.activity.LoginActivity;
import com.richlabz.mfhvendor.entities.Login;
import com.richlabz.mfhvendor.fragment.Travelhomefragment;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by RICHLABZ on 10-09-2016.
 */
public class MfhvendorDatasource {
     FunctionHallSubmittedBidsActivity functionHallSubmittedBidsActivity;
    FunctionhalNotSubmittedBidsActivity functionhalNotSubmittedBids;
    Functionhallhomefragment functionhallhomefragment;
    PandithSubmittedBids pandithSubmittedBids;
    Pandithhomefragment pandithhomefragment;
    PandithBidnowActivity pandithBidnowActivity;
    MfhvendorAPI restAPI;
    LoginActivity loginActivity;
    AppliedCategoryActivity appliedCategoryActivity;
    //    BiddingActivity biddingActivity;
    BidNowActivity bidNowActivity, bidNowActivity1;
    BiddingViewDetails biddingViewDetails;
    Homefragment homefragment, homefragment1;
    int bidvalue;
    RegisterActivtiy registerActivtiy, registerActivtiy1, registerActivtiy2;
    MyBiddingOrderActiity myBiddingOrderActiity;
    BiddingAppliedViewDetails biddingAppliedViewDetails;
    AppliedBiddingsActivtiy appliedBiddingsActivtiy;
    ProfileActivity profileActivity, UpdateProfiledatasource;
    TravelNotAppliedBiddingActivity travelNotAppliedBiddingActivity;
    Travelhomefragment travelhomefragment;
    TravelBidNowActivity travelBidNowActivity, travelBidNowActivity1, travelBidNowActivity2;
    TravelAppliedBiddingview travelAppliedBiddingview;
    DriverNotAppliedViewActivtiy driverNotAppliedViewActivtiy;
    Driverhomefragment driverhomefragment;
    DriverBidnowActivity driverBidnowActivity;
    DriverAppliedBidActivity driverAppliedBidActivity;
    FragmentDrawer fragmentDrawer;
    private int profilename;
    AddfriendActivity addfriendActivity;
    PandithNotSubmittedBids pandithNotSubmittedBids;

    public MfhvendorDatasource(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        init();

    }

    //    public MfhvendorDatasource(BiddingActivity biddingActivity) {
//        this.biddingActivity=biddingActivity;
//        init();
//
//    }
    public MfhvendorDatasource(BidNowActivity bidNowActivity) {
        this.bidNowActivity = bidNowActivity;
        this.bidNowActivity1 = bidNowActivity;
        init();

    }

    public MfhvendorDatasource(BiddingViewDetails biddingViewDetails) {
        this.biddingViewDetails = biddingViewDetails;
        init();

    }

    public MfhvendorDatasource(Homefragment homefragment) {
        this.homefragment = homefragment;
        this.homefragment1 = homefragment;
        init();

    }

    public MfhvendorDatasource(MyBiddingOrderActiity myBiddingOrderActiity) {
        this.myBiddingOrderActiity = myBiddingOrderActiity;
        init();
    }

    public MfhvendorDatasource(RegisterActivtiy registerActivtiy) {
        this.registerActivtiy = registerActivtiy;
        this.registerActivtiy1 = registerActivtiy;
        this.registerActivtiy2 = registerActivtiy;
        init();

    }

    public MfhvendorDatasource(AppliedCategoryActivity appliedCategoryActivity) {
        this.appliedCategoryActivity = appliedCategoryActivity;
        init();
    }

    public MfhvendorDatasource(BiddingAppliedViewDetails biddingAppliedViewDetails) {
        this.biddingAppliedViewDetails = biddingAppliedViewDetails;
        init();

    }

    public MfhvendorDatasource(AppliedBiddingsActivtiy appliedBiddingsActivtiy) {
        this.appliedBiddingsActivtiy = appliedBiddingsActivtiy;
        init();

    }

    public MfhvendorDatasource(ProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
        this.UpdateProfiledatasource = profileActivity;

        init();
    }

    public MfhvendorDatasource(TravelNotAppliedBiddingActivity travelNotAppliedBiddingActivity) {
        this.travelNotAppliedBiddingActivity = travelNotAppliedBiddingActivity;
        init();
    }

    public MfhvendorDatasource(Travelhomefragment travelhomefragment) {
        this.travelhomefragment = travelhomefragment;
        init();
    }

    public MfhvendorDatasource(TravelBidNowActivity travelBidNowActivity) {
        this.travelBidNowActivity = travelBidNowActivity;
        this.travelBidNowActivity1 = travelBidNowActivity;
        this.travelBidNowActivity2 = travelBidNowActivity;
        init();
    }

    public MfhvendorDatasource(TravelAppliedBiddingview travelAppliedBiddingview) {
        this.travelAppliedBiddingview = travelAppliedBiddingview;
        init();
    }

    public MfhvendorDatasource(DriverNotAppliedViewActivtiy driverNotAppliedViewActivtiy) {
        this.driverNotAppliedViewActivtiy = driverNotAppliedViewActivtiy;
        init();

    }
    // Driver DashBoadr Fragment

    public MfhvendorDatasource(Driverhomefragment driverhomefragment) {
        this.driverhomefragment = driverhomefragment;
        init();

    }
    //Driver applied bidnow activity

    public MfhvendorDatasource(DriverBidnowActivity driverBidnowActivity) {
        this.driverBidnowActivity = driverBidnowActivity;
        init();
    }

    //driver applied bids activity
    public MfhvendorDatasource(DriverAppliedBidActivity driverAppliedBidActivity) {

        this.driverAppliedBidActivity = driverAppliedBidActivity;
        init();

    }

    public MfhvendorDatasource(FragmentDrawer fragmentDrawer) {
        this.fragmentDrawer = fragmentDrawer;
        init();
    }

    public MfhvendorDatasource(AddfriendActivity addfriendActivity) {
        this.addfriendActivity = addfriendActivity;
        init();
    }

    public MfhvendorDatasource(PandithNotSubmittedBids pandithNotSubmittedBids) {
        this.pandithNotSubmittedBids = pandithNotSubmittedBids;
        init();
    }

    public MfhvendorDatasource(PandithBidnowActivity pandithBidnowActivity) {
        this.pandithBidnowActivity=pandithBidnowActivity;
        init();
    }

    public MfhvendorDatasource(Pandithhomefragment pandithhomefragment) {
        this.pandithhomefragment=pandithhomefragment;
        init();
    }

    public MfhvendorDatasource(PandithSubmittedBids pandithSubmittedBids) {
        this.pandithSubmittedBids=pandithSubmittedBids;
        init();
    }

    public MfhvendorDatasource(Functionhallhomefragment functionhallhomefragment) {
            this.functionhallhomefragment=functionhallhomefragment;
           init();
    }

    public MfhvendorDatasource(FunctionhalNotSubmittedBidsActivity functionhalNotSubmittedBids) {
            this.functionhalNotSubmittedBids=functionhalNotSubmittedBids;
        init();
    }

    public MfhvendorDatasource(FunctionHallSubmittedBidsActivity functionHallSubmittedBidsActivity) {
        this.functionHallSubmittedBidsActivity=functionHallSubmittedBidsActivity;
        init();
    }

    private void init() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://myfunctionhall.in/api")
                .setLog(new AndroidLog("Mfhvendor"))
                .setClient(new OkClient(new OkHttpClient()))
                .build();
        restAPI = restAdapter.create(MfhvendorAPI.class);
    }

    // LOGIN API
    public void login(Login login) {
        restAPI.postlogin(login, CallbackloginResponse);
    }

    Callback CallbackloginResponse = new Callback() {
        @Override
        public void success(Object o, Response response) {
            loginActivity.showresponse(o);
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
        }
    };

    // get the bidding list;
    public void getbiddinglist(NotAppliedbids biddingDetails) {
        restAPI.notappliedbids(biddingDetails, Callbackbiddingdetailsresponse);
    }

    Callback Callbackbiddingdetailsresponse = new Callback() {
        @Override
        public void success(Object o, Response response) {

            appliedBiddingsActivtiy.showbiddingresponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Integrate Bidnow View API ***************************/
    public void getbiddingview(Biddingview biddingview, int i) {
        bidvalue = i;
        restAPI.postbidddingview(biddingview, callbackbiddingresponse);
    }

    Callback callbackbiddingresponse = new Callback() {
        @Override
        public void success(Object o, Response response) {
            if (bidvalue == 1) {
                bidNowActivity.showbiddingResponse(o);
            }
            if (bidvalue == 2) {
                biddingViewDetails.showbiddingResponse(o);
            }
//            if(bidvalue==3)
//            {
//                biddingAppliedViewDetails.showbiddingResponse(o);
//            }

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Call post vendor Bidnow api

    public void postbidnow(PostBidding postBidding) {
        restAPI.postbidnow(postBidding, Callbackpostbidnow);
    }

    Callback Callbackpostbidnow = new Callback() {
        @Override
        public void success(Object o, Response response) {
            bidNowActivity1.showbidnowResponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /*
    ******** Getting MyVendorOrderdetaisl
     */
    public void getMyorderbiddings(PostBiddingOrder postBiddingOrder) {
        restAPI.postOrderdetails(postBiddingOrder, Callbackpostbiddingorderresponse);
    }

    Callback Callbackpostbiddingorderresponse = new Callback() {
        @Override
        public void success(Object o, Response response) {
            myBiddingOrderActiity.showMyorderResponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /*
    ** Get menulist Response
     */
    public void getMeulist() {
        restAPI.gethomecategories(Callbackhomecategories);
    }

    Callback Callbackhomecategories = new Callback() {
        @Override
        public void success(Object o, Response response) {
            registerActivtiy.showmenulistResponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /**********
     * post signup values
     */

    public void signup(SignUp signUp) {
        restAPI.Signup(signUp, Callbacksignupresponse);
    }

    Callback Callbacksignupresponse = new Callback() {
        @Override
        public void success(Object o, Response response) {
            registerActivtiy1.showRegisterResponse(o);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /*********
     * VerifyOtp code
     */
    public void verifyotp(Verification verification) {
        restAPI.verification(verification, Callbackverification);
    }

    Callback Callbackverification = new Callback() {
        @Override
        public void success(Object o, Response response) {
            registerActivtiy2.showVerificationmessage(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /************
     * apllied Biddings
     */
    public void appliedscreen(VendorAppliedBids mybiddapp) {
        restAPI.appliedbids(mybiddapp, Callbackmybiddapp);
    }

    Callback Callbackmybiddapp = new Callback() {
        @Override
        public void success(Object o, Response response) {
            appliedCategoryActivity.showBidResp(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /***************
     * Getting Profile
     */
    public void Getprofile(PostProfile postProfile, int i)

    {
        profilename = i;
        restAPI.gettingprofile(postProfile, Callbackgettingprofile);
    }

    Callback Callbackgettingprofile = new Callback() {
        @Override
        public void success(Object o, Response response) {
            if (profilename == 1) {
                profileActivity.showProfileresponse(o);
            } else {
                fragmentDrawer.showProfileResponse(o);
            }
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /********
     * View Submitted APPLIED Biddings
     */

    public void Vendorreplies(VendorReplay vendorReplay) {
        restAPI.postvendroreplay(vendorReplay, CallbackVendorreplies);
    }

    Callback CallbackVendorreplies = new Callback() {
        @Override
        public void success(Object o, Response response) {
            biddingAppliedViewDetails.showbiddingResponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    /**********
     * Get Dashboard response
     */
    public void getdashboard(Dashboard dashboard) {
        restAPI.postdashboard(dashboard, Callbackdashboardresponse);
    }

    Callback Callbackdashboardresponse = new Callback() {
        @Override
        public void success(Object o, Response response) {
            homefragment.showdashboardresponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Call GETTING  TravelBidding NOT APPLIED  VIEW api

    public void travelBiddingview(TravNotAppliedbids travNotAppliedbids) {
        restAPI.postTravelBidding(travNotAppliedbids, Callbacktravelbiddngview);
    }

    Callback Callbacktravelbiddngview = new Callback() {
        @Override
        public void success(Object TravelbiddingResponse, Response response) {
            travelNotAppliedBiddingActivity.ShowTravelBiddingResponse(TravelbiddingResponse);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };
    // CALL TRAVEL APPLIED VIEW


    public void traelappliedview(TravNotAppliedbids travNotAppliedbids) {
        restAPI.postTravelapplyBidding(travNotAppliedbids, Callbacktravelappliedview);
    }

    Callback Callbacktravelappliedview = new Callback() {
        @Override
        public void success(Object o, Response response) {

            travelAppliedBiddingview.showappliedresponse(o);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // GET pandith  not submittedBids

    public void getPoojanotsubmitbids(PandithNotSumittedBids pandithNotSubmittedBids)

    {
        restAPI.getPoojadetails(pandithNotSubmittedBids,Callbackpandithres);
    }

    Callback Callbackpandithres = new Callback() {
        @Override
        public void success(Object o, Response response) {
            pandithNotSubmittedBids.shownotpandithres(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


    //  get Travel Bid DashBoard Api

    public void getTravelDashboard(TravelDashboadrd travelDashboadrd) {
        restAPI.traveldashboard(travelDashboadrd, Callbackdashboardresponseresponse);
    }

    Callback Callbackdashboardresponseresponse = new Callback() {
        @Override
        public void success(Object o, Response response) {
            travelhomefragment.showTravelDashboardResponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // get travelbidnow  api

    public void travelbidnow(TravelVendorbidding travelVendorbidding) {
        restAPI.travelbiddingpost(travelVendorbidding, Callbacktravelbidnowcallback);
    }

    Callback Callbacktravelbidnowcallback = new Callback() {
        @Override
        public void success(Object travelbidpostresponse, Response response) {
            travelBidNowActivity.showTravelpostResponse(travelbidpostresponse);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // get Vehicle LIST

    public void getVehicletypedetails() {
        restAPI.getVehicleTypelist(Callbackvehicledetails);
    }

    Callback Callbackvehicledetails = new Callback() {
        @Override
        public void success(Object o, Response response) {

            travelBidNowActivity1.ShowVehicledetailsResponse(o);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


    // get Vehicle Model List
    public void getVehicleModelList() {
        restAPI.getVehicleModellist(Callbackvehicelmodelist);
    }

    Callback Callbackvehicelmodelist = new Callback() {
        @Override
        public void success(Object o, Response response) {
            travelBidNowActivity2.showvehiclemodelistresponse(o);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    //  Driver Not Apllied Bids

    public void driverNotAppliedBids(DriverVendorNotAppliedBids driverVendorNotAppliedBids) {
        restAPI.vendornotappliedbids(driverVendorNotAppliedBids, CallbackVendorresponse);
    }

    Callback CallbackVendorresponse = new Callback() {
        @Override
        public void success(Object o, Response response) {
            driverNotAppliedViewActivtiy.showResponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Driver Dash Board

    public void driverdashboard(DriverDashBoard driverDashBoard) {
        restAPI.driverDashBoard(driverDashBoard, Callbackddashbord);
    }

    Callback Callbackddashbord = new Callback() {
        @Override
        public void success(Object o, Response response) {
            driverhomefragment.showDashboardResponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Driver BidNOW API

    public void driverBidnow(DriverReplayBid driverReplayBid) {
        restAPI.driverReplayBidd(driverReplayBid, CallbackdriverReplay);
    }

    Callback CallbackdriverReplay = new Callback() {
        @Override
        public void success(Object o, Response response) {
            driverBidnowActivity.showBidResp(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Driver Applied view Actvity

    public void driverappliedresponse(DriverAppliedBids driverAppliedBids)

    {
        restAPI.driverappliedbids(driverAppliedBids, Callbackdriverappliedbids);
    }

    Callback Callbackdriverappliedbids = new Callback() {
        @Override
        public void success(Object o, Response response) {
            driverAppliedBidActivity.showdriverresponse(o);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Driver Update Profile

    public void updateProfiel(DriverUpdateProfile updateProfile) {
        restAPI.driverprofile_Update(updateProfile, Callbackupdateprofile);
    }

    Callback Callbackupdateprofile = new Callback() {
        @Override
        public void success(Object o, Response response) {
            UpdateProfiledatasource.updateprofileresponse(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Add Refer A Friend

    public void refererfriend(GetReferalAmount getReferalAmount) {
        restAPI.vendorreferal(getReferalAmount, CallbackReferalAmount);
    }

    Callback CallbackReferalAmount = new Callback() {
        @Override
        public void success(Object o, Response response) {
            addfriendActivity.showReferaamount(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // PANDITH Replay Bids

    public  void pandithreplaybids(ReplayPoojabids replayPoojabids)
    {
        restAPI.getReplaypoojabids(replayPoojabids,Callbackpoojabids);
    }
    Callback Callbackpoojabids=new Callback() {
        @Override
        public void success(Object o, Response response) {
            pandithBidnowActivity.showpandithres(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // get pandith dashboard

    public  void getpandithdashboard(PandithDashboard pamdithdashboard)
    {
        restAPI.pandithdashboard(pamdithdashboard,Callabckpandithdash);
    }
    Callback Callabckpandithdash=new Callback() {
        @Override
        public void success(Object o, Response response) {
            pandithhomefragment.showDashboardResponse(o);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // get PandithSubmittedBids


    public  void  getpandithsubmittedbids(PandithSubmitedbids pandithSubmitedbids)
    {
        restAPI.pandithSubmittedbids(pandithSubmitedbids,Callbackpandsubmitres);

    }
    Callback Callbackpandsubmitres=new Callback() {
        @Override
        public void success(Object o, Response response) {
            pandithSubmittedBids.shownotpandithres(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };
    // getFh Dashboard

    public void getFhDashboard(DriverDashBoard driverdashboard)
    {
        restAPI.getfhDashboard(driverdashboard, CallbackFhResponse);
    }
  Callback CallbackFhResponse=new Callback() {
    @Override
    public void success(Object o, Response response) {
        functionhallhomefragment.showDashboardResponse(o);

    }

    @Override
    public void failure(RetrofitError error) {

    }
};

    // get Functionhal Not Submitted Bids

    public void getFunctionhallnotsubnittedbids(Functionhallnotsubmittedbids functionhalNotSubmittedBids)
    {
        restAPI.getFhnotSubmittedlist(functionhalNotSubmittedBids,Callbackfunctionhalres);
    }
    Callback Callbackfunctionhalres=new Callback() {
        @Override
        public void success(Object o, Response response) {
            functionhalNotSubmittedBids.shownotpandithres(o);

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    // Get Function Hall Submitted Bids
    public  void getFunctionhalSubmit(Functionhallnotsubmittedbids functionhallnotsubmittedbids)
    {
        restAPI.getFhAppliedBidlist(functionhallnotsubmittedbids,CallbackFunctionhallresponse);
    }
    Callback CallbackFunctionhallresponse=new Callback() {
        @Override
        public void success(Object o, Response response) {
            functionHallSubmittedBidsActivity.shownotpandithres(o);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


}


