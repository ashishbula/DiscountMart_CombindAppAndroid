package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.base.network.NetworkClient1;
import in.base.ui.BaseFragment;
import in.discountmart.R;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.RewardService;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.RewardResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    LinearLayout layoutInfo;
    // private ViewPagerAdapter pagerAdapter;

    ProgressDialog pDialog;
    String strApiKey="";
    //ArrayList<RewardResponse.AchiveReward> achiveRewardArrayList;
    ArrayList<Map<String ,String>> achieveRewardList;
    ArrayList<Map<String ,String>>pendingRewardList;
    ArrayList<Map<String ,String>>nextRewardList;
    //ArrayList<RewardResponse.PendingReward> pendingRewardArrayList;
   // ArrayList<RewardResponse.NextReward> nextRewardArrayList;
    //ArrayList<RankRewardModel> rankRewardList;
    RewardResponse rewardResponse ;

    TextView textViewLeftBV;
    TextView textViewRightBV;
    Context context;

    public RewardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = null;
       try {
           v = inflater.inflate(R.layout.fragment_reward, container, false);
           context=getActivity();
           textViewLeftBV=(TextView)v.findViewById(R.id.reward_frag_textView_leftbv);
           textViewRightBV=(TextView)v.findViewById(R.id.reward_frag_textView_rightbv);
           viewPager = (ViewPager) v.findViewById(R.id.reward_frag_viewpager);
           tabLayout = (TabLayout)v. findViewById(R.id.reward_frag_tabslayout);
           layoutInfo = (LinearLayout) v. findViewById(R.id.reward_frag_layout_info);
           /* Get api key value from Shared Preference */
           strApiKey=SharedPrefrence_Business.getApiKey(context);

           /*Call Reward Api */
           if(!ConnectivityUtils.isNetworkEnabled(context)){
               Toast.makeText(context,context.getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
           }
           else {
               getRewardDetail();
           }

           //rankRewardList=new ArrayList<RankRewardModel>();
           //rankRewardList=initRankList();

       }catch (Exception e){
           e.printStackTrace();
       }
        return v;
    }

    /*Get Reward  Api */
    private void getRewardDetail(){

        showProgressDialog();

        BaseRequest request = new BaseRequest();
        /*Set value in Entity class*/
        /*Set value in Entity class*/
        request.setReqtype(ApiConstants.REQUEST_REWARD);
        request.setPasswd(SharedPrefrence_Business.getPassword(context));
        request.setUserid(SharedPrefrence_Business.getUserID(context));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<RewardResponse> calldailyIncentiveResponse=
                NetworkClient1.getInstance(context).create(RewardService.class).fetchReward(request,strApiKey);

        calldailyIncentiveResponse.enqueue(new Callback<RewardResponse>() {
            @Override
            public void onResponse(Call<RewardResponse> call, Response<RewardResponse> response) {

                hideProgressDialog();
                try {

                    rewardResponse  =new RewardResponse();
                    rewardResponse = response.body();
                    if(rewardResponse != null){
                        if (rewardResponse.getResponse().equals("OK")) {
                            if(rewardResponse.getRpinfo() != null){
                                ArrayList<Map<String ,String>> rewardInfoList= rewardResponse.getRpinfo();
                                //ArrayList<Map<String ,String>> walletStatus= Response.getStatus();
                                createRewardInfoDetail(rewardInfoList);
                            }
                            if(rewardResponse.getAchrewards() != null && rewardResponse.getAchrewards().size()>0){
                                achieveRewardList=new ArrayList<Map<String ,String>>();
                                achieveRewardList= rewardResponse.getAchrewards();
                                new RewardResponse().setAchrewards(achieveRewardList);
                            }
                            if(rewardResponse.getPendingrewards() != null && rewardResponse.getPendingrewards().size()>0){
                                pendingRewardList=new ArrayList<Map<String ,String>>();
                                pendingRewardList= rewardResponse.getPendingrewards();
                                new RewardResponse().setPendingrewards(pendingRewardList);
                            }
                            if(rewardResponse.getNextreward() != null && rewardResponse.getNextreward().size()>0){
                                nextRewardList=new ArrayList<Map<String ,String>>();
                                nextRewardList= rewardResponse.getNextreward();
                                new RewardResponse().setNextreward(nextRewardList);
                            }
                            //achiveRewardArrayList = rewardResponse.getAchrewards();
                            //pendingRewardArrayList=rewardResponse.getPendingrewards();
                            //nextRewardArrayList=rewardResponse.getNextrewards();
                            //textViewLeftBV.setText(rewardResponse.getLeftbv());
                            //textViewRightBV.setText(rewardResponse.getRightbv());
                            //new RewardResponse().setAchrewards(achiveRewardArrayList);
                            //new RewardResponse().setPendingrewards(pendingRewardArrayList);
                           // new RewardResponse().setNextrewards(nextRewardArrayList);

                            viewPager.setAdapter(new SectionPagerAdapter(getChildFragmentManager()));
                            tabLayout.setupWithViewPager(viewPager);

                        }
                    }
                    else {
                        Toast.makeText(context, rewardResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RewardResponse> call, Throwable t) {

                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter implements Serializable {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        Bundle b = new Bundle();

        @Override
        public Fragment getItem(int position) {

            switch (position) {


                case 0:
                    AchivedRewardStatusFragment tab1 = new AchivedRewardStatusFragment();

                    //ArrayList<RewardResponse. achrewards=rewardResponse.getAchrewards();
                    if(achieveRewardList != null && achieveRewardList.size() > 0){
                        b.putSerializable("AchiveRecog",achieveRewardList);
                        tab1.setArguments(b);
                    }
                    else {
                        Toast.makeText(context,"Record Null",Toast.LENGTH_SHORT).show();
                        achieveRewardList=new ArrayList<Map<String, String>>();
                        b.putSerializable("AchiveRecog",achieveRewardList);
                        tab1.setArguments(b);
                    }

                    return tab1;
                case 1:

                    PendingRewardStatusFragment tab2 = new PendingRewardStatusFragment();
                    //ArrayList<RewardResponse.PendingReward> pendingRewards=rewardResponse.getPendingrewards();
                    if(pendingRewardList != null && pendingRewardList.size() > 0){
                        b.putSerializable("PenRecog",pendingRewardList);
                        tab2.setArguments(b);
                    }
                    else {
                        Toast.makeText(context,"Record Null",Toast.LENGTH_SHORT).show();
                        pendingRewardList=new ArrayList<Map<String, String>>();
                        b.putSerializable("PenRecog",pendingRewardList);
                        tab2.setArguments(b);
                    }


                    return tab2;
                case 2:

                    NextRewardFragment tab3 = new NextRewardFragment();
                    //ArrayList<RewardResponse.NextReward> nextRewards=rewardResponse.getNextrewards();
                    if(nextRewardList != null && nextRewardList.size() > 0){
                        b.putSerializable("NextRecog",nextRewardList);
                        tab3.setArguments(b);
                    }
                    else {
                        Toast.makeText(context,"Record Null",Toast.LENGTH_SHORT).show();
                        nextRewardList=new ArrayList<Map<String, String>>();
                        b.putSerializable("NextRecog",nextRewardList);
                        tab3.setArguments(b);
                    }
                    return tab3;


                default:
                    return new AchivedRewardStatusFragment();
            }

        }

        @Override
        public int getCount() {

            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {

                case 0:
                    return "Achieve Reward";
                case 1:
                    return "Pending Reward";
                case 2:
                    return "Next Reward";

                default:
                    return "Achieve Reward";

            }
        }

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

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.VERTICAL);
    }

    /* This function for show Wallet Report Status
     * dynamically
     * */
    public void createRewardInfoDetail(final ArrayList<Map<String,String>> walletStatus) {
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        ArrayList<Map<String, String>> myWalletStatuslist = walletStatus;
        Map<String, String> map = new HashMap<String, String>();
        //myTeamlist=myTeam;
        layoutInfo.removeAllViews();

        for (int i = 0; i < myWalletStatuslist.size(); i++) {
            map = myWalletStatuslist.get(i);

            //Map<String, String> map =topSellerlist.get(i);
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


                if(i==0){
                    txtName.setText(String.valueOf(objKey));
                    txtName.setTypeface(null, Typeface.BOLD);
                   // txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    txtName.setGravity(Gravity.CENTER);
                }
                else {
                    txtName.setText(String.valueOf(objKey));
                    txtName.setTypeface(null, Typeface.NORMAL);
                    txtName.setGravity(Gravity.CENTER);
                }
//                if(obj.equals("name"))
//                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//                else
//                    txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                //txtName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                txtName.setTextColor(getResources().getColor(R.color.black));
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_medium));



                linearLayout.setLayoutParams(getLayoutParams());
                linearLayout.addView(txtName);

            }

            layoutInfo.addView(linearLayout);

        }

       /* for (Map.Entry<String, String> entry : map.entrySet()) {
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


            TextView txtkey=new TextView(this);
            txtkey.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            txtkey.setText(String.valueOf( entry.getKey()).toUpperCase());

            txtkey.setTypeface(null, Typeface.NORMAL);
            txtkey.setTextColor(getResources().getColor(R.color.black));
            txtkey.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_default));
            txtkey.setGravity(Gravity.LEFT);

            linearLayout.setLayoutParams(getLayoutParams());
            linearLayout.addView(txtkey);

            TextView txtvalue=new TextView(this);
            txtvalue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            txtvalue.setText(String.valueOf( entry.getValue()).toUpperCase());

            txtvalue.setTypeface(null, Typeface.NORMAL);
            txtvalue.setTextColor(getResources().getColor(R.color.black));
            txtvalue.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.txt_default));
            txtvalue.setGravity(Gravity.RIGHT);

            linearLayout.setLayoutParams(getLayoutParams());
            linearLayout.addView(txtvalue);

            layoutInfo.addView(linearLayout);

        }*/



    }

    /*public ArrayList<RankRewardModel> initRankList(){
        ArrayList<RankRewardModel> rankList=new ArrayList<RankRewardModel>();
        RankRewardModel model=new RankRewardModel();
        model.setMatchBV("25");
        model.setRank("STAR");
        model.setRewards("SMART PHONE");
        model.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "5,000");
        model.setMonthlyincentive("");

        rankList.add(model);

        RankRewardModel model2=new RankRewardModel();
        model2.setMatchBV("Next 50");
        model2.setRank("SILVER");
        model2.setRewards("GOA TOUR");
        model2.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "10,000");
        model2.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "600");
        rankList.add(model2);

        RankRewardModel model3=new RankRewardModel();
        model3.setMatchBV("Next 200");
        model3.setRank("GOLD");
        model3.setRewards("LED TV");
        model3.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "30,000");
        model3.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "1200");
        rankList.add(model3);

        RankRewardModel model4=new RankRewardModel();
        model4.setMatchBV("Next 500");
        model4.setRank("PLATINUM");
        model4.setRewards("LAPTOP");
        model4.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "50,000");
        model4.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "4000");
        rankList.add(model4);

        RankRewardModel model5=new RankRewardModel();
        model5.setMatchBV("Next 750");
        model5.setRank("RUBI");
        model5.setRewards("BIKE");
        model5.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "75,000");
        model5.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "6000");
        rankList.add(model5);

        RankRewardModel model6=new RankRewardModel();
        model6.setMatchBV("Next 1000");
        model6.setRank("PEARL");
        model6.setRewards("GOLD");
        model6.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "1,50000");
        model6.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "15000");
        rankList.add(model6);

        RankRewardModel model7=new RankRewardModel();
        model7.setMatchBV("Next 2000");
        model7.setRank("SAPPHIRE");
        model7.setRewards("ROYAL ENFIELD");
        model7.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "3,00000");
        model7.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "25000");
        rankList.add(model7);

        RankRewardModel model8=new RankRewardModel();
        model8.setMatchBV("Next 4000");
        model8.setRank("EMERALD");
        model8.setRewards("WAGON R CAR");
        model8.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "7,50000");
        model8.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "40000");
        rankList.add(model8);

        RankRewardModel model9=new RankRewardModel();
        model9.setMatchBV("Next 8000");
        model9.setRank("DIAMOND");
        model9.setRewards("NEXON CAR");
        model9.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "15,00000");
        model9.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "1,00000");
        rankList.add(model9);

        RankRewardModel model10=new RankRewardModel();
        model10.setMatchBV("Next 15000");
        model10.setRank("BLUE DIAMOND");
        model10.setRewards("FORTUNER CAR");
        model10.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "45,00000");
        model10.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "1,50000");
        rankList.add(model10);

        RankRewardModel model11=new RankRewardModel();
        model11.setMatchBV("Next 30000");
        model11.setRank("KOHINOOR");
        model11.setRewards("BMW CAR");
        model11.setAmount("Or " +getResources().getString(R.string.rs_symbol)+ "1,10,00000");
        model11.setMonthlyincentive(getResources().getString(R.string.rs_symbol)+ "2,00000");
        rankList.add(model11);
        return rankList;
    }*/

}
