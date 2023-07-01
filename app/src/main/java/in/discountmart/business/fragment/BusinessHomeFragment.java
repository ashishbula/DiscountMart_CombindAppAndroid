package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import in.base.network.NetworkClient;
import in.base.network.NetworkClient1;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.activity.ComplaintActivity;
import in.discountmart.business.activity.EditProfileActivity;
import in.discountmart.business.activity.Kyc_Activity;
import in.discountmart.business.activity.WebViewActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.DocumentService;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.DashboardResponse;
import in.discountmart.business.model_business.responsemodel.GeneologyResponse;
import in.discountmart.business.model_business.responsemodel.WelcomeLetterResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessHomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    Context context;
    private SliderLayout mSlider1;
    LinearLayout layoutRegistratiion;
    LinearLayout serviceMyDash;
    LinearLayout serviceTeamReport;
    LinearLayout serviceGenology;
    LinearLayout serviceSupport;
    LinearLayout serviceIncomeDash;
    LinearLayout serviceIncomeReport;
    LinearLayout serviceWalletSummery;
    LinearLayout serviceEpin;
    LinearLayout serviceBusReport;
    LinearLayout serviceVoucher;
    LinearLayout serviceRvPayout;
    LinearLayout serviceKYC;
    LinearLayout serviceWebsite;
    LinearLayout serviceUtility;
    LinearLayout serviceShopOnline;
    LinearLayout serviceProfile;
    LinearLayout serviceMyDirect;
    LinearLayout serviceMyBusiness;
    LinearLayout serviceTeamDetail;
    LinearLayout serviceIncome;
    LinearLayout serviceDailyIncome;
    LinearLayout serviceMonthlyIncome;
    LinearLayout serviceReward;
    LinearLayout serviceLevelWise;
    LinearLayout serviceIdActive;
    LinearLayout serviceWallet;
    LinearLayout serviceClub;
    LinearLayout serviceJoining;
    LinearLayout serviceWallet_Main;
    LinearLayout serviceWallet_Franchise;
    LinearLayout serviceWallet_Shop;
    LinearLayout serviceLetter;
    LinearLayout serviceChangePass;
    LinearLayout serviceDocument;
    LinearLayout serviceGlobalTree;

    ProgressDialog pDialog;
    ImageView imgBtnBusOpportunity;
    ImageView imgBtnWebsite;
    View view;
    String strRefLink="";
    String strApiKey="";

    DashboardResponse Response;

    public BusinessHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.business_fragment_home, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            mSlider1=(SliderLayout)mainView.findViewById(R.id.frag_home_slider);

            serviceKYC=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_kyc);
            serviceProfile=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_profile);
            serviceLetter=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_letter);

            serviceDailyIncome=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_daily_incentive);
            serviceMonthlyIncome=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_servic_monthly_incentive);
            serviceReward=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_reward);
            //serviceWallet_Franchise=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_franchise_wallet);

            serviceMyDirect=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_mydirect);
            serviceMyBusiness=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_mybusiness);
            serviceTeamDetail=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_team_detail);



            serviceWallet_Shop=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_shop_wallet);
            serviceWallet_Main=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_main_wallet);

            //serviceSupport=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_support);

            //serviceClub=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_club);
            //serviceJoining=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_joining);


            //serviceDocument=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_doc);
            //serviceLetter=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_letter);
            //serviceChangePass=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_chnge_pass);
           // serviceGlobalTree=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_global_tree);
            //serviceIdActive=(LinearLayout)mainView.findViewById(R.id.business_homedash_frag_service_id_active);
            //serviceBusOpp=(LinearLayout)mainView.findViewById(R.id.bs_home_frag_layout_busopp);

            //serviceShopOnline=(LinearLayout)mainView.findViewById(R.id.bus_homedash_frag_service_online_shop);
            //serviceUtility=(LinearLayout)mainView.findViewById(R.id.bus_homedash_frag_service_utility);

            intiatSlider1();

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            /*Service Business Opportunity on click*/
            serviceProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditProfileActivity.class);
                    context.startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Business Opportunity on click*/
            serviceKYC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, Kyc_Activity.class);
                    context.startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });
            /*Service Business Welcome Letter on click*/
            serviceLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(context, Kyc_Activity.class);
//                    context.startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    getWelcomeLetter();
                }
            });


            /*Service Website on click*/
           /* serviceWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, WebViewActivity.class);
                    i.putExtra("URL", ApiConstants.website);
                    i.putExtra("Type","Website");
                    i.putExtra("From","Business");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

//                    SubServiceFragment fragment = new SubServiceFragment();
//                    Bundle bundleFund=new Bundle();
//                    bundleFund.putString("Service","Website");
//                    fragment.setArguments(bundleFund);
//                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"Website",bundleFund);


                }
            });*/

            /*Service Franchise Wallet Icon on click*/
            /*erviceWallet_Franchise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Coming soon..",Toast.LENGTH_SHORT).show();
                    Intent dailyIncIntent = new Intent(context, CommonReportActivity.class);
                    dailyIncIntent.putExtra("Title", "Franchise Wallet" );
                    dailyIncIntent.putExtra("Type","F");
                    startActivity(dailyIncIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                   *//* AlertDialogUtils.showDialogWithOneButton(context, new AlertDialogButtonListener() {
                        @Override
                        public void onButtontapped(String btnText) {
                            if (btnText.equals("OK")) {

                            }
                        }
                    },"Alert Dialog", "This field services is under work in progress !","OK");*//*

                }
            });*/

            /*Service Main Wallet Icon on click*/
            serviceWallet_Main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Coming soon..",Toast.LENGTH_SHORT).show();
                    Intent dailyIncIntent = new Intent(context, CommonReportActivity.class);
                    dailyIncIntent.putExtra("Title", "Main Wallet" );
                    dailyIncIntent.putExtra("Type","M");
                    startActivity(dailyIncIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });


            //Service News on click
            serviceMyDirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MyDirectFragment fragment = new MyDirectFragment();
                    //Bundle bundleTeam=new Bundle();
                    //bundleTeam.putString("Service","TeamReport");
                    //fragment.setArguments(bundleTeam);

                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"My Direct",null);


                }
            });

            //Service Profile on click
            serviceTeamDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //LevelWiseDirectFragment fragment=new LevelWiseDirectFragment();
                    //Bundle bundle=new Bundle();
                    //bundle.putString("From","Business");
                    //fragment.setArguments(bundle);
                    ((BusinessDashboardActivity)context).replaceFragment_business(new LevelWiseDirectFragment(),"Dashboard",null);
                }
            });

            /*Service Shopping Wallet Icon on click*/
            serviceWallet_Shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Coming soon..",Toast.LENGTH_SHORT).show();
                    Intent dailyIncIntent = new Intent(context, CommonReportActivity.class);
                    dailyIncIntent.putExtra("Title", "Shop Wallet" );
                    dailyIncIntent.putExtra("Type","R");
                    startActivity(dailyIncIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            //Service Profile on click
            /*serviceChangePass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //LevelWiseDirectFragment fragment=new LevelWiseDirectFragment();
                    //Bundle bundle=new Bundle();
                    //bundle.putString("From","Business");
                    //fragment.setArguments(bundle);
                    ((BusinessDashboardActivity)context).replaceFragment(new ChangepasswordFragment(),"Dashboard",null);
                }
            });*/

            serviceDailyIncome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Coming soon..",Toast.LENGTH_SHORT).show();
                    Intent dailyIncIntent = new Intent(context, CommonReportActivity.class);
                    dailyIncIntent.putExtra("Title", "Daily Income" );
                    dailyIncIntent.putExtra("Type","Daily");
                    startActivity(dailyIncIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });
            serviceMonthlyIncome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Coming soon..",Toast.LENGTH_SHORT).show();
                    Intent dailyIncIntent = new Intent(context, CommonReportActivity.class);
                    dailyIncIntent.putExtra("Title", "Monthly Income" );
                    dailyIncIntent.putExtra("Type","Monthly");
                    //startActivity(dailyIncIntent);
                    //getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });


           /* serviceWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*Intent mainIntent = new Intent(context, CommonReportActivity.class);
                    mainIntent.putExtra("Title", "Income Wallet Report");// Main Wallet
                    mainIntent.putExtra("Type","Wallet_M");
                    startActivity(mainIntent);
                   getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*//*

                    SubServiceFragment fragmentW = new SubServiceFragment();
                    // fragment = new WalletReportFragment();
                    String strTitle="Main Wallet Report";
                    Bundle walletBundle=new Bundle();
                    walletBundle.putString("From","Frag");
                    walletBundle.putString("Service","Wallet");
                    fragmentW.setArguments(walletBundle);
                    ((BusinessDashboardActivity)context).replaceFragment(fragmentW,"Wallet Report",walletBundle);
                }*//**//*
            });*/



            /*Service Geneology on click*/
           /* serviceGenology.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   *//* WebviewFragment fragment=new WebviewFragment();
                    Bundle bundle1=new Bundle();
                    fragment = new WebviewFragment();
                    bundle1.putString("Title","Tree View");
                    bundle1.putString("Type","memtree");
                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"Mydirect",bundle1);*//*
                    getGeneologyDetail();
                }
            });*/


            /*ServiceMy Direct on click*/
           /* serviceIncomeDash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubServiceFragment fragment = new SubServiceFragment();
                    Bundle bundleFund=new Bundle();
                    bundleFund.putString("Service","MyIncome");
                    fragment.setArguments(bundleFund);
                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"MyIncome",bundleFund);
                }
            });*/

            /*Service Level Wise direct on click*/
            /*serviceIncomeReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent dailyIntent=new Intent(context, CommonReportActivity.class);
                    dailyIntent.putExtra("Type","Daily");
                    dailyIntent.putExtra("Title","Daily Payout Detail");
                    startActivity(dailyIntent);
                    //((BusinessDashboardActivity)context).replaceFragment(new DailyIncentiveFragment(),"Income Report",null);
                }
            });*/

            /*Service Wallet Summery on click*/
            /*serviceWalletSummery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubServiceFragment fragment = new SubServiceFragment();
                    Bundle bundleFund=new Bundle();
                    bundleFund.putString("Service","Wallet");
                    fragment.setArguments(bundleFund);
                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"Wallet",bundleFund);
                }
            });*/



            /*Service Epin on click*/
           /* serviceEpin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((BusinessDashboardActivity)context).replaceFragment(new E_PinFragment(),"Epin",null);
                }
            });*/

            /*Service Business Report on click*/
           /* serviceBusReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubServiceFragment fragment = new SubServiceFragment();
                    Bundle bundleFund=new Bundle();
                    bundleFund.putString("Service","BusinessReport");
                    fragment.setArguments(bundleFund);
                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"Business",bundleFund);
                }
            });*/


            /*Service My Voucher on click*/
           /* serviceVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubServiceFragment fragment = new SubServiceFragment();
                    Bundle bundleFund=new Bundle();
                    bundleFund.putString("Service","Voucher");
                    fragment.setArguments(bundleFund);
                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"DownlinePurchase",bundleFund);
                }
            });*/



            /*Service Website on click*/
            /*serviceUtility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, BusinessDashboardActivity.class);
//                    i.putExtra("URL", ApiConstants.Utility_Url);
//                    i.putExtra("Type","Website");
//                    i.putExtra("From","Business");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

                    getActivity().finish();
//                    SubServiceFragment fragment = new SubServiceFragment();
//                    Bundle bundleFund=new Bundle();
//                    bundleFund.putString("Service","Website");
//                    fragment.setArguments(bundleFund);
//                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"Website",bundleFund);


                }
            });*/
            /*Service Website on click*/
           /* serviceShopOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, MainActivity_Brand.class);
                    //i.putExtra("URL", ApiConstants.Brand_Shop_Url);
                    //i.putExtra("Type","Website");
                    //i.putExtra("From","Business");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                    getActivity().finish();
//                    SubServiceFragment fragment = new SubServiceFragment();
//                    Bundle bundleFund=new Bundle();
//                    bundleFund.putString("Service","Website");
//                    fragment.setArguments(bundleFund);
//                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"Website",bundleFund);


                }
            });*/

            /*Service Website on click*/
          /*  serviceIdActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent activationIntent = new Intent(context, IDActivationActivity.class);
                    startActivity(activationIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                    //getActivity().finish();
//                    SubServiceFragment fragment = new SubServiceFragment();
//                    Bundle bundleFund=new Bundle();
//                    bundleFund.putString("Service","Website");
//                    fragment.setArguments(bundleFund);
//                    ((BusinessDashboardActivity)context).replaceFragment(fragment,"Website",bundleFund);


                }
            });*/

          /*  serviceClub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(context, ComplaintActivity.class);
//                    context.startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    *//*AlertDialogUtils.showDialogWithOneButton(context, new AlertDialogButtonListener() {
                        @Override
                        public void onButtontapped(String btnText) {
                            if (btnText.equals("OK")) {


                            }
                        }
                    },"Alert Dialog", "This field services is under work in progress !","OK");*//*

                    Intent rewardIncIntent = new Intent(context, CommonReportActivity.class);
                    rewardIncIntent.putExtra("Title", "My Rewards");
                    rewardIncIntent.putExtra("Type","MyReward");
                    getActivity().startActivity(rewardIncIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

            /*Service Business Opportunity on click*/
           /* serviceJoining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, RegistrationActivity.class);
                    context.startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/


            /*Service Business Opportunity on click*/
            serviceSupport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ComplaintActivity.class);
                    context.startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Business Opportunity on click*/
            /*serviceDocument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getWelcomeLetter();
                }
            });*/

            /*Service Business Opportunity on click*/
           /* serviceGlobalTree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   getGeneologyDetail("Global Pool");
                }
            });*/

            /*Service Business Opportunity on click*/
            /*serviceMyDirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getGeneologyDetail("Generation View");
                }
            });*/



        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;


    }

    /*Slider 1*/
    public void intiatSlider1(){
        // ArrayList<String> file_maps=new ArrayList<>();
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Discount Mart",R.mipmap.business_slider_1);
        /*file_maps.put("Shopping",R.mipmap.business_banner_2);
        file_maps.put("Utility",R.mipmap.business_banner_3);
        file_maps.put("Goldwing",R.mipmap.business_banner_4);
        file_maps.put("Shop",R.mipmap.business_banner_5);*/

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(context);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(BusinessHomeFragment.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider1.addSlider(textSliderView);
        }
        mSlider1.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider1.setCustomAnimation(new DescriptionAnimation());
        // mSlider.setCustomAnimation(new D);
        mSlider1.setDuration(4000);
        mSlider1.addOnPageChangeListener(BusinessHomeFragment.this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*Geneology  API*/
    private void getGeneologyDetail(String type){

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        if(type.contains("Geneology"))
            baseRequest.setReqtype(ApiConstants.REQUEST_GENEOLOGY);
        else  if(type.contains("Generation View"))
            baseRequest.setReqtype(ApiConstants.REQUEST_SPONSER_GENEOLOGY);
        else  if(type.contains("Global Pool"))
            baseRequest.setReqtype(ApiConstants.REQUEST_POOL_TREE);

        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<GeneologyResponse> geneologyResponseCall=
                NetworkClient.getInstance(context).create(MyTeamService.class).fetchGeneology(baseRequest,strApiKey);

        geneologyResponseCall.enqueue(new Callback<GeneologyResponse>() {
            @Override
            public void onResponse(Call<GeneologyResponse> call, Response<GeneologyResponse> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    GeneologyResponse geneologyResponse=new GeneologyResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){
                        Intent i = new Intent(context, WebViewActivity.class);
                        i.putExtra("URL", geneologyResponse.getUrl());
                        i.putExtra("Type",type);
                        i.putExtra("From","Business");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                    else{
                        String toast= geneologyResponse.getResponse();
                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GeneologyResponse> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();

            }
        });

    }

    private void getWelcomeLetter(){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        baseRequest.setReqtype(ApiConstants.REQUEST_WELCOME_LETTER);

        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<WelcomeLetterResponse> geneologyResponseCall=
                NetworkClient.getInstance(context).create(DocumentService.class).fetchWelcomeLetter(baseRequest,strApiKey);

        geneologyResponseCall.enqueue(new Callback<WelcomeLetterResponse>() {
            @Override
            public void onResponse(Call<WelcomeLetterResponse> call, Response<WelcomeLetterResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    WelcomeLetterResponse geneologyResponse=new WelcomeLetterResponse();
                    geneologyResponse=response.body();
                    if (geneologyResponse.getResponse().equals("OK")){
                        Intent i = new Intent(context, WebViewActivity.class);
                        i.putExtra("URL", geneologyResponse.getUrl());
                        i.putExtra("Type","Welcome Letter");
                        i.putExtra("From","Welcome Letter");
                        startActivity(i);
                       getActivity(). overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                    else{
                        Toast.makeText(context, geneologyResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WelcomeLetterResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /*Get Address Detail Api Request and REsponse*/
    private void getDashboardDetail(){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request=new BaseRequest();
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_DASHBOARD);
        request.setPasswd(SharedPrefrence_Business.getPassword(context));
        request.setUserid(SharedPrefrence_Business.getUserID(context));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<DashboardResponse> callAddressDetail=
                NetworkClient1.getInstance(context).create(ProfileServices.class).fetchDashboardDetail(request,strApiKey);

        callAddressDetail.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, retrofit2.Response<DashboardResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    Response =new DashboardResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            if(Response.getReferalurl().equals("") && Response.getReferalurl() == null){
                                //layoutLink.setVisibility(View.GONE);
                            }
                            else {
                                strRefLink=Response.getReferalurl();
                                //layoutLink.setVisibility(View.VISIBLE);
                                //txtRefLink.setText(Response.getReferalurl());

                            }

                        }
                        else {
                            String toast= Response.getResponse()+ ":" + Response.getMsg();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

                        }
                    }
                    else {
                        String toast="Something wrong..server error";
                        Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
        });
    }
}
