package com.richlabz.mfhvendor.commonutilities;

import com.richlabz.mfhvendor.entities.GetCategories;
import com.richlabz.mfhvendor.entities.Login;
import com.richlabz.mfhvendor.entities.MyBidResp;
import com.richlabz.mfhvendor.entities.RestResponse;
import com.richlabz.mfhvendor.entities.TravelBidding;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by RICHLABZ on 10-09-2016.
 */
public  interface MfhvendorAPI {
    @POST("/vendor/login")
    void postlogin(@Body Login login, Callback<LoginResponse> restallback);
    @POST("/vendor/catering/biddings")
    void  postBiddingDetails(@Body BiddingDetails biddingDetails, Callback<BiddingListResponse> biddinres);
    @POST("/vendor/catering/bidding/view")
    void postbidddingview(@Body Biddingview biddingview,Callback<BiddingviewResponse> response);
    @POST("/vendor/catering/biddnow")
     void postbidnow(@Body PostBidding postBidding,Callback<RestResponse> restResponseCallback);
    @POST("/vendor/biddings/catering/orders")
    void postOrderdetails(@Body PostBiddingOrder postBiddingOrder,Callback<BiddingOrderDetailsResponse> postBiddingOrderDetailsCallback);
    @GET("/home/get_categories")
    void gethomecategories(Callback<GetCategories> getCategoriesCallback);
    @POST("/Vendor/vendorRegister")
     void Signup(@Body SignUp signUp,Callback<RestResponse> restResponseCallback);
    @POST("/Vendor/mobileVerification")
     void verification(@Body Verification verification,Callback<RestResponse>restResponseCallback);
    @POST("/Vendor/getVendorBiddingDetails")
    void getBiddingapp(@Body Mybiddapp mybiddapp,Callback<MyBidResp> callback);
    @POST("/vendor/catering/bidding/view")
    void postappliedview(@Body Biddingview biddingview, Callback<BiddingviewResponse> response);
    @POST("/Vendor/myProfile")
    void gettingprofile(@Body PostProfile profile,Callback<GettingProfile> gettingProfileCallback);
    @POST("/Vendor/updateProfileDetails")
    void  postprofile(@Body UpdateProfile updateProfile,Callback<RestResponse> restResponseCallback);
    @POST("/vendor/vendorbidrplydetails")
    void postvendroreplay(@Body VendorReplay vendorReplay,Callback<VendorReplayResponse> res);
    @POST("/Vendor/dashboard")
    void postdashboard(@Body Dashboard dashboard,Callback<Dashboardresponse> res);
    @POST("/Vendor/vendornotappliedbidds")
    void notappliedbids(@Body NotAppliedbids appliedBidds, Callback<AppliedBidResponse> res);
    @POST("/Vendor/vendorappliedbidds")
    void appliedbids(@Body VendorAppliedBids vendorAppliedBids,Callback<VendorAppliedResponse> vendorAppliedResponseCallback);
    @POST("/vendornotappliedbidds")
    void postTravelBidding(@Body TravNotAppliedbids travNotAppliedbids,Callback<Travelbiddingviewlist> travelbiddingviewlistCallback);
    @POST("/vendorappliedbidds")
    void postTravelapplyBidding(@Body TravNotAppliedbids travNotAppliedbids,Callback<Travelbiddingappliedviewlist> travelbiddingviewlistCallback);
    @POST("/dashboard")
    void traveldashboard(@Body TravelDashboadrd travelDashboadrd,Callback<TraveldashboardResponse> res);
    @POST("/vendorbidding")
    void travelbiddingpost(@Body TravelVendorbidding travelVendorbidding,Callback<RestResponse> restResponseCallback);
    @GET("/vendorvehiclemodellist")
    void getVehicleModellist(Callback<GetVehicleModeList> res);
    @GET("/vendorvehicletypelist")
    void getVehicleTypelist(Callback<GetVehicleTypelist> res);
    @POST("/drivervendornotappliedbidds")
    void vendornotappliedbids(@Body DriverVendorNotAppliedBids driverVendorNotAppliedBids,Callback<DriverNotAppliedResponse> res);
    @POST("/driverdashboard")
    void driverDashBoard(@Body DriverDashBoard driverDashBoard, Callback<DriverDashBoardResponse> res);
    @POST("/rplydriverbidd")
    void driverReplayBidd(@Body DriverReplayBid driverReplayBid, Callback<RestResponse> res);
    @POST("/drivervendorappliedbidds")
    void driverappliedbids(@Body DriverAppliedBids  driverAppliedBids,Callback<DriverAppliedBidResponse> res);
    @POST("/Vendor/updateProfileDetails")
     void driverprofile_Update(@Body DriverUpdateProfile  driverUpdateProfile,Callback<RestResponse> callback);
    @POST("/vendorrefferal")
    void vendorreferal(@Body GetReferalAmount getReferalAmount,Callback<RestResponse> res);
    @POST("/pandithnotappliedbiddlist")
    void getPoojadetails(@Body PandithNotSumittedBids pandithNotSumittedBids,Callback<GetpoojadetailResponse> res);
    @POST("/rplyuserpoojabidding")
    void getReplaypoojabids(@Body ReplayPoojabids replayPoojabids,Callback<RestResponse> res);
    @POST("/pandithvendordashboard")
    void pandithdashboard(@Body PandithDashboard pandithDashboard,Callback<DriverDashBoardResponse> res);
    @POST("/pandithappliedbiddlist")
    void pandithSubmittedbids(@Body PandithSubmitedbids submitedbids,Callback<PandithSubmittedbidsRes> res);
    @POST("/fhvendordashboard")
    void getfhDashboard(@Body DriverDashBoard driverDashBoard, Callback<DriverDashBoardResponse> res);
    @POST("/fhvendornotappliedlist")
    void getFhnotSubmittedlist(@Body Functionhallnotsubmittedbids functionhallnotsubmittedbids,Callback<GetNotSubmittedFunctionhalllist> res);
    @POST("/fhvendorappliedlist")
    void getFhAppliedBidlist(@Body Functionhallnotsubmittedbids functionHallSubmitBids,Callback<GetFunctionHallSubmittedbids> res);
    @POST("/fhvendorreply")
    void getFunctionhalReplay(@Body FunctionhallReplayBids functionhallReplayBids,Callback<RestResponse> res);


}
