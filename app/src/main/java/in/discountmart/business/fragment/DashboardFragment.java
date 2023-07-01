package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import in.base.network.NetworkClient1;
import in.discountmart.R;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.DashboardResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    TextView txtRank;
    TextView txtClub;
    Button btnLinkLeft;
    Button btnLinkRight;

    /*For Profile*/
    TextView txtName;
    TextView txtId;

    TextView txtRefLink;
    TextView txtRefLinkRight;
    ImageView imgProfile;
    LinearLayout layoutLoginHistory;
    LinearLayout layoutTopSeller;
    LinearLayout layoutTopSellerClick;
    LinearLayout layoutTopSellerContent;
    LinearLayout layoutMyTeam;
    LinearLayout layoutMyTeamDetail;

    LinearLayout layoutRewardDetail;
    LinearLayout layoutMFADetail;
    LinearLayout layoutMFAContent;
    LinearLayout layoutReward;

    LinearLayout layoutMydirect;
    LinearLayout layoutMydirect_content;
    LinearLayout layoutMyIncome;
    LinearLayout layoutMyIncomeDetail;
    LinearLayout layoutWallet;
    LinearLayout layoutWalletDetail;
    LinearLayout layoutLink;


    /*For Reward*/
    Context context;
    View view;
    ProgressDialog pDialog;
    DashboardResponse Response;
    String from="";
    String strApiKey="";

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.fragment_dashboard, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);

            txtRefLink=(TextView)mainView.findViewById(R.id.business_mydash_frag_txt_referral_left);
           txtRefLinkRight=(TextView)mainView.findViewById(R.id.dash_detail_txt_referral_right);

           layoutMyTeam=(LinearLayout)mainView.findViewById(R.id.business_mydash_frag_layout_team_click);
            layoutMyTeamDetail=(LinearLayout)mainView.findViewById(R.id.business_mydash_frag_myteam_content);
            layoutTopSeller=(LinearLayout)mainView.findViewById(R.id.business_mydash_frag_layout_topseller);
            layoutTopSellerContent=(LinearLayout)mainView.findViewById(R.id.business_mydash_frag_topseller_content);
            layoutTopSellerClick=(LinearLayout)mainView.findViewById(R.id.business_mydash_frag_layout_topseller_click);
            layoutMydirect=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_mydirect);
            layoutMydirect_content=(LinearLayout)mainView.findViewById(R.id.business_mydash_frag_mydirect_content);
            layoutMyIncome=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_myincome);
            layoutMyIncomeDetail=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_myincome_content);
            layoutWallet=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_wallet);
            layoutWalletDetail=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_wallet_detail);
            layoutReward=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_reward_detail);
            layoutRewardDetail=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_reward_content);
            layoutMFADetail=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_mfa_detial);
            layoutMFAContent=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_mfa_content);
            layoutLink=(LinearLayout)mainView.findViewById(R.id.dash_detail_layout_link);

            /*For Profile*/
            txtName=(TextView)mainView.findViewById(R.id.dash_detail_txt_name);
//            txtDOJ=(TextView)mainView.findViewById(R.id.dash_detail_txt_doj);
//            txtDOA=(TextView)mainView.findViewById(R.id.dash_detail_txt_doa);
            txtId=(TextView)mainView.findViewById(R.id.dash_detail_txt_id);
            //txtMobile=(TextView)mainView.findViewById(R.id.dash_detail_txt_mobile);
            txtRank=(TextView)mainView.findViewById(R.id.dash_detail_txt_rank);
            txtClub=(TextView)mainView.findViewById(R.id.dash_detail_txt_club);
            btnLinkLeft=(Button)mainView.findViewById(R.id.business_mydash_frag_btn_linkleft);
            btnLinkRight=(Button)mainView.findViewById(R.id.dash_detail_btn_linkright);
            //txtEmail=(TextView)mainView.findViewById(R.id.dash_detail_txt_email);

            imgProfile=(ImageView) mainView.findViewById(R.id.dash_detail_image_profile);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            /* Get Value from bundle*/
            Bundle bundle=getArguments();
            /*if(bundle != null){
                from=bundle.getString("From");
            }*/
            /*if(from.equals("Utility")){
                layoutLink.setVisibility(View.VISIBLE);
                layoutProfile.setVisibility(View.VISIBLE);
                layoutWalletSummery.setVisibility(View.VISIBLE);
                layoutMyTeam.setVisibility(View.VISIBLE);
                layoutDirect.setVisibility(View.VISIBLE);
                layoutIncome.setVisibility(View.VISIBLE);
            }
            else {
                layoutLink.setVisibility(View.GONE);
                layoutProfile.setVisibility(View.GONE);
                layoutWalletSummery.setVisibility(View.GONE);
                layoutMyTeam.setVisibility(View.VISIBLE);
                layoutDirect.setVisibility(View.GONE);
                layoutIncome.setVisibility(View.GONE);
            }*/

            /*call api*/
            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                getDashboardDetail();
            }

            /*Text Referral link left on click */
            txtRefLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String string = txtRefLink.getText().toString();
                        //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                        shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zado Biz");
                        startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            /*Text Referral link Right on click */
            txtRefLinkRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String string = txtRefLinkRight.getText().toString();
                        //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                        shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zado Biz");
                        startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            /* Button  Share link on click
             * share Referral link Left side*/
            btnLinkLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String string = txtRefLink.getText().toString();
                        //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                        shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zado Biz");
                        startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            /* Button  Share link on click
            * share Referral link right side*/
            btnLinkRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String string = txtRefLinkRight.getText().toString();
                        //   + "Sponser Code : " + userInfo_Shoppe.getPhone();

                        shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zado Biz");
                        startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });



          /*  *//* Text on Login history on click*//*
            layoutLoginHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Response.getLoginhistory() != null){
                        ((BusinessDashboardActivity)context).showLoginHistoryDialog(Response.getLoginhistory());
                    }
                    else {
                        Toast.makeText(context,"No Login History Available...",Toast.LENGTH_SHORT).show();
                    }

                }
            });*/

            /* Text on My Direct Income on click*/
            layoutMydirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(Response.getDirects() != null && Response.getDirects().size() > 0){
                            //((BusinessDashboardActivity)context).replaceFragment(new MyDirectFragment(),"MyDirect",null);
                            if(layoutMydirect_content.getVisibility() == View.GONE){
                                layoutMydirect_content.setVisibility(View.VISIBLE);
                            }
                            else {
                                layoutMydirect_content.setVisibility(View.GONE);
                            }
                        }
                        else {
                            Toast.makeText(context,"No  direct available...",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            layoutMyTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(Response.getMyteam() != null && Response.getMyteam().size() > 0){
                            if(layoutMyTeamDetail.getVisibility() == View.GONE){
                                layoutMyTeamDetail.setVisibility(View.VISIBLE);
                            }
                            else {
                                layoutMyTeamDetail.setVisibility(View.GONE);
                            }
                        }
                        else {
                            Toast.makeText(context,"No any My team data available...",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });


                //Layout team on click open detail
            layoutWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if(Response.getWallet() != null && Response.getWallet().size() > 0){
                                if(layoutWalletDetail.getVisibility() == View.GONE){
                                    layoutWalletDetail.setVisibility(View.VISIBLE);
                                }
                                else {
                                    layoutWalletDetail.setVisibility(View.GONE);
                                }
                            }
                            else {
                                Toast.makeText(context,"No any My team data available...",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                });





            /*Layout Reward on click open detail*/
            layoutMyIncome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(Response.getIncome() != null && Response.getIncome().size() > 0){
                            if(layoutMyIncomeDetail.getVisibility() == View.GONE){
                                layoutMyIncomeDetail.setVisibility(View.VISIBLE);
                            }
                            else {
                                layoutMyIncomeDetail.setVisibility(View.GONE);
                            }
                        }
                        else {
                            Toast.makeText(context,"No any My Income data available...",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });
            /*Layout Reward on click open detail*/
            layoutReward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(Response.getReward() != null && Response.getReward().size() > 0){
                            if(layoutRewardDetail.getVisibility() == View.GONE){
                                layoutRewardDetail.setVisibility(View.VISIBLE);
                            }
                            else {
                                layoutRewardDetail.setVisibility(View.GONE);
                            }
                        }
                        else {
                            Toast.makeText(context,"No any Reward data available...",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            /*Layout MFaA Business on click open detail*/
            layoutMFADetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(Response.getReward() != null && Response.getReward().size() > 0){
                            if(layoutMFAContent.getVisibility() == View.GONE){
                                layoutMFAContent.setVisibility(View.VISIBLE);
                            }
                            else {
                                layoutMFAContent.setVisibility(View.GONE);
                            }
                        }
                        else {
                            Toast.makeText(context,"No any MFA Business data available...",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });






        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;
    }


    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(context);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        //tv.setOnClickListener(context);
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    /*Get Address Detail Api Request and REsponse*/
    private void getDashboardDetail(){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest request=new BaseRequest();
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_DASHBOARD_DY);
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
                            txtRefLink.setText(Response.getReferalurlleft());
                            txtRefLinkRight.setText(Response.getReferalurlright());
                            txtName.setText(Response.getName());
                            txtId.setText(Response.getIdno());
                            txtRank.setText("Rank : "+Response.getRank());
                            txtClub.setText("Club : "+Response.getClub());

                            if(Response.getProfilepic().equals("")){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Picasso.with(context)
                                            .load(String.valueOf(getActivity().getDrawable(R.mipmap.logo)))
                                            .placeholder(R.mipmap.logo)
                                            .error(R.mipmap.logo)
                                            .into(imgProfile);

                        /*Glide.with(mContext)  //2
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.home_icon_hotel) //5
                                .error(R.mipmap.home_icon_hotel) //6
                                .fallback(R.mipmap.home_icon_hotel) //7
                                .into(myViewHolder.imgHotelImg);*/ //8
                                }
                            }

                            else{

                                Picasso.with(context)
                                        .load(Response.getProfilepic())
                                        .placeholder(R.mipmap.logo)
                                        .error(R.mipmap.logo)
                                        .into(imgProfile);

                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
                            }
                           /* if(Response.getReferalurlleft().equals("") && Response.getReferalurlleft() == null){
                                layoutLink.setVisibility(View.GONE);
                            }
                            else {
                                layoutLink.setVisibility(View.VISIBLE);
                                txtRefLink.setText(Response.getReferalurl());

                            }*/
                            /*if(Response.getReferalurl().equals("") && Response.getReferalurl() == null){
                                layoutLink.setVisibility(View.GONE);
                            }
                            else {
                                layoutLink.setVisibility(View.VISIBLE);
                                txtRefLink.setText(Response.getReferalurl());

                            }*/
                            /*For My Team Detail*/
                            if(Response.getMyteam() != null && Response.getMyteam().size() > 0 ){
                                layoutMyTeam.setVisibility(View.VISIBLE);

                                ArrayList<Map<String ,String>> mapMyTeam= Response.getMyteam();
                                //Map<String ,String> mapsLevelDirectSummery=Response.getMyteams();

                                createMyTeamDetail(mapMyTeam);

                            }
                            else {
                                layoutMyTeam.setVisibility(View.GONE);
                               // String toast="No record found";
                                //Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
                            }

                            /*For My Direct Detail*/
                            if(Response.getDirects() != null && Response.getDirects().size()>0){
                                layoutMydirect.setVisibility(View.VISIBLE);
                                ArrayList<Map<String ,String>> mapMyTeam= Response.getDirects();
                                //Map<String ,String> mapsLevelDirectSummery=Response.getMyteams();

                                createMyDirectDetail(mapMyTeam);

                            }
                            else {
                                layoutMydirect.setVisibility(View.GONE);
                               // String toast="No record found";
                                //Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
                            }

                            /*For My Income Detail*/
                            if(Response.getIncome() != null && Response.getIncome().size() > 0){
                                layoutMyIncome.setVisibility(View.VISIBLE);

                                ArrayList<Map<String ,String>> mapMyTeam= Response.getIncome();
                                //Map<String ,String> mapsLevelDirectSummery=Response.getMyteams();

                                createMyIncomeDetail(mapMyTeam);

                            }
                            else {
                                layoutMyIncome.setVisibility(View.GONE);
                                //String toast="No record found";
                                //Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
                            }

                            /*For Wallet  Detail*/
                            if(Response.getWallet() != null && Response.getWallet().size() > 0){
                                layoutWallet.setVisibility(View.VISIBLE);
                                ArrayList<Map<String ,String>> mapMyTeam= Response.getWallet();
                                //Map<String ,String> mapsLevelDirectSummery=Response.getMyteams();

                                createWalletDetail(mapMyTeam);

                            }
                            else {
                                layoutWallet.setVisibility(View.GONE);
                                //String toast="No record found";
                                //Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
                            }

                            /*For Reward  Detail*/
                            if(Response.getReward() != null && Response.getReward().size() > 0){
                                layoutReward.setVisibility(View.VISIBLE);
                                ArrayList<Map<String ,String>> mapReward= Response.getReward();
                                //Map<String ,String> mapsLevelDirectSummery=Response.getMyteams();

                                createRewardDetail(mapReward);

                            }
                            else {

                                //String toast="No record found";
                                layoutReward.setVisibility(View.GONE);
                                //Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
                            }
                            /*For MFA  Business Detail*/
                            if(Response.getMfabusiness() != null && Response.getMfabusiness().size() > 0){
                                layoutMFADetail.setVisibility(View.VISIBLE);
                                ArrayList<Map<String ,String>> mapReward= Response.getMfabusiness();
                                //Map<String ,String> mapsLevelDirectSummery=Response.getMyteams();

                                createMFABusinessDetail(mapReward);

                            }
                            else {

                                //String toast="No record found";
                                layoutMFADetail.setVisibility(View.GONE);
                                //Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
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

    /* This function for show My Team Detail
     * dynamically
     * */
    /*public void createMyIncomeDetail(final Map<String,String> myTeam) {
        //Set Dynamically add Linear Layout,TextView,CheckBox

        Map<String, String> myTeamlist = myTeam;
        //myTeamlist=myTeam;
        layoutMyIncomeDetail.removeAllViews();

        for (Map.Entry<String, String> entry : myTeamlist.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);


            TextView txtkey=new TextView(context);
            txtkey.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            txtkey.setText(String.valueOf( entry.getKey()).toUpperCase());

            txtkey.setTypeface(null, Typeface.NORMAL);
            txtkey.setTextColor(getResources().getColor(R.color.black));
            txtkey.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
            txtkey.setGravity(Gravity.LEFT);

            linearLayout.setLayoutParams(getLayoutParams());
            linearLayout.addView(txtkey);

            TextView txtvalue=new TextView(context);
            txtvalue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            txtvalue.setText(String.valueOf( entry.getValue()).toUpperCase());

            txtvalue.setTypeface(null, Typeface.NORMAL);
            txtvalue.setTextColor(getResources().getColor(R.color.black));
            txtvalue.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
            txtvalue.setGravity(Gravity.RIGHT);

            linearLayout.setLayoutParams(getLayoutParams());
            linearLayout.addView(txtvalue);

            layoutMyIncomeDetail.addView(linearLayout);

        }

       *//* for (int i = 0; i < myTeamlist.size(); i++)
        {
            //Map<String, String> map =myTeamlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Map.Entry<String, String> obj : myTeamlist.entrySet()){
                if(i==0){
                    //Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(obj.getKey()));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText("Particular");

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }

            layoutMyTeamDetail.addView(linearLayout);

        }*//*


       *//* for (int i = 0; i < myTeamlist.size(); i++)
        {
            //Map<String, String> map =myTeamlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Map.Entry<String, String> map : myTeamlist.entrySet()){

                Object objKey=map.getValue();
                //Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));
                if(obj.equals("Total"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutMyTeamDetail.addView(linearLayout);

        }*//*

    }*/



    /* This function for show My Team Detail
     * dynamically
     * */
    public void createTopSellerDetail(final ArrayList<Map<String,String>> topSeller) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String,String>> topSellerlist = new ArrayList<Map<String, String>>();
        topSellerlist=topSeller;
        layoutTopSellerContent.removeAllViews();



        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Object obj : map.keySet()){
                if(i==0){
                    Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(objKey));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText(String.valueOf( obj).toUpperCase());

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    if(obj.equals("activationdate"))
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    else
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }

            layoutTopSellerContent.addView(linearLayout);

        }


        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));
                if(obj.equals("activationdate"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));

                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutTopSellerContent.addView(linearLayout);

        }

    }

    /* This function for show My Direct Detail
     * dynamically
     * */
    public void createMyDirectDetail(final ArrayList<Map<String,String>> topSeller) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String,String>> topSellerlist = new ArrayList<Map<String, String>>();
        topSellerlist=topSeller;
        layoutMydirect_content.removeAllViews();



        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum((float) 3.5);

            for(Object obj : map.keySet()){

                    Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(objKey));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    if(obj.equals("name"))
                        txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.5));
                    else
                        txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,  1));

                    if(i==0){
                        txtHeader.setTypeface(null, Typeface.BOLD);
                        txtHeader.setText(String.valueOf( objKey).toUpperCase());
                    }
                    else {
                        txtHeader.setTypeface(null, Typeface.NORMAL);
                        txtHeader.setText(String.valueOf( objKey));
                    }


                    //txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);

            }

            layoutMydirect_content.addView(linearLayout);

        }


        /*for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum((float) 3.5);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                if(obj.equals("name"))
                    txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.5));
                else
                    txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,  1));

                txtName.setText(String.valueOf(objKey));
                txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));

                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutMydirect_content.addView(linearLayout);

        }*/

    }

    /* This function for show My Team Detail
     * dynamically
     * */
    public void createMyTeamDetail(final ArrayList<Map<String,String>> topSeller) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String,String>> topSellerlist = new ArrayList<Map<String, String>>();
        topSellerlist=topSeller;
        layoutMyTeamDetail.removeAllViews();



        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(4);

            for(Object obj : map.keySet()){
                if(i==0){
                    Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(objKey));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText(String.valueOf( obj).toUpperCase());

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    if(obj.equals("name"))
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    else
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }

            layoutMyTeamDetail.addView(linearLayout);

        }


        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(4);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));
                if(obj.equals("name"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                //txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));

                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutMyTeamDetail.addView(linearLayout);

        }

    }

    /* This function for show My Income Detail
     * dynamically
     * */
    public void createMyIncomeDetail(final ArrayList<Map<String,String>> topSeller) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String,String>> topSellerlist = new ArrayList<Map<String, String>>();
        topSellerlist=topSeller;
        layoutMyIncomeDetail.removeAllViews();



        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Object obj : map.keySet()){
                if(i==0){
                    Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(objKey));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText(String.valueOf( obj).toUpperCase());

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    if(obj.equals("amount"))
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    else
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }

            layoutMyIncomeDetail.addView(linearLayout);

        }


        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(2);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));
                if(obj.equals("amount"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));

                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutMyIncomeDetail.addView(linearLayout);

        }

    }

    /* This function for show Wallet  Detail
     * dynamically
     * */
    public void createWalletDetail(final ArrayList<Map<String,String>> topSeller) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String,String>> topSellerlist = new ArrayList<Map<String, String>>();
        topSellerlist=topSeller;
        layoutWalletDetail.removeAllViews();



        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(4);

           /* for(Object obj : map.keySet()){
                if(i==0){
                    Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(objKey));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText(String.valueOf( obj).toUpperCase());

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    if(obj.equals("amount"))
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    else
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }*/

           // layoutWalletDetail.addView(linearLayout);

        }


        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);


            linearLayout.setWeightSum(4);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));
                if(obj.equals("walletname"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                if(i==0){
                    txtName.setTypeface(null, Typeface.BOLD);
                }
                else
                    txtName.setTypeface(null, Typeface.NORMAL);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));

                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutWalletDetail.addView(linearLayout);

        }

    }

    /* This function for show Reward Detail
     * dynamically
     * */
    public void createRewardDetail(final ArrayList<Map<String,String>> topSeller) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String,String>> topSellerlist = new ArrayList<Map<String, String>>();
        topSellerlist=topSeller;
        layoutRewardDetail.removeAllViews();



        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            //linearLayout.setWeightSum(4);

           /* for(Object obj : map.keySet()){
                if(i==0){
                    Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(objKey));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText(String.valueOf( obj).toUpperCase());

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    if(obj.equals("amount"))
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    else
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }*/

            // layoutWalletDetail.addView(linearLayout);

        }


        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginStart(10);
            }
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);


            //linearLayout.setWeightSum(4);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                txtName.setText(String.valueOf(objKey));

                txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                if(obj.equals("achievedate"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
               // txtHeader.setGravity(Gravity.LEFT);

                if(i==0){
                    txtName.setTypeface(null, Typeface.BOLD);
                }
                else
                    txtName.setTypeface(null, Typeface.NORMAL);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_default));

                txtName.setGravity(Gravity.LEFT);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutRewardDetail.addView(linearLayout);

        }

    }

    /* This function for show Reward Detail
     * dynamically
     * */
    public void createMFABusinessDetail(final ArrayList<Map<String,String>> topSeller) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String,String>> topSellerlist = new ArrayList<Map<String, String>>();
        topSellerlist=topSeller;
        layoutMFAContent.removeAllViews();



        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //layoutParams.setMarginStart(10);
            layoutParams.setMargins(5,5,5,5);
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);
            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            //linearLayout.setWeightSum(4);

           /* for(Object obj : map.keySet()){
                if(i==0){
                    Object objKey=map.get(obj);
                    //obj.getKey()
                    Log.d("Key - ", String.valueOf(objKey));
                    TableRow tr = new TableRow(context);


                    TextView txtHeader=new TextView(context);
                    txtHeader.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    txtHeader.setText(String.valueOf( obj).toUpperCase());

                    txtHeader.setTypeface(null, Typeface.BOLD);
                    txtHeader.setTextColor(getResources().getColor(R.color.black));
                    txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));
                    if(obj.equals("amount"))
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    else
                        txtHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    txtHeader.setGravity(Gravity.LEFT);

                    linearLayout.setLayoutParams(getLayoutParams());
                    linearLayout.addView(txtHeader);
                }
            }*/

            // layoutWalletDetail.addView(linearLayout);

        }


        for (int i = 0; i < topSellerlist.size(); i++)
        {
            Map<String, String> map =topSellerlist.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //layoutParams.setMarginStart(10);
            layoutParams.setMargins(5,5,5,5);

            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(5,5,5,5);

            linearLayout.setLayoutParams(layoutParams);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);


            //linearLayout.setWeightSum(4);

            for(Object obj : map.keySet()){

                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);

                TextView txtName=new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2.0f));

                txtName.setText(String.valueOf(objKey));

                //txtName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                txtName.setPadding(15,5,15,5);
                /*if(obj.equals("achievedate"))
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                else
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);*/
                // txtHeader.setGravity(Gravity.LEFT);

                if(i==0){
                    txtName.setTypeface(null, Typeface.BOLD);
                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                else
                    txtName.setTypeface(null, Typeface.NORMAL);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_default));

                txtName.setGravity(Gravity.CENTER);

                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutMFAContent.addView(linearLayout);

        }

    }

}
