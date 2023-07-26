package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import in.discountmart.business.model_business.responsemodel.RewardResponseNew;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFragmentNew extends BaseFragment {

    TableLayout tableLayAchieveRecord;
    ScrollView scrollView;
    ImageView imgNoResult;

    ProgressDialog pDialog;
    String strApiKey="";
    RewardResponseNew rewardResponse ;
    ArrayList<Map<String ,String>> rewardRecords;
    Context context;
    public RewardFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = null;
       try {
           v = inflater.inflate(R.layout.fragment_achived_reward_status, container, false);
           context=getActivity();
           tableLayAchieveRecord=(TableLayout)v.findViewById(R.id.achive_reward_frag_tablelayout_record);
           scrollView=(ScrollView)v.findViewById(R.id.achive_reward_frag_scroll_result);
           imgNoResult=(ImageView)v.findViewById(R.id.achive_reward_frag_img_noresult);
           context=getActivity();
           strApiKey=SharedPrefrence_Business.getApiKey(context);

           /*Call Reward Api */
           if(!ConnectivityUtils.isNetworkEnabled(context)){
               Toast.makeText(context,context.getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
           }
           else {
               getRewardDetail();
           }

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

        Call<RewardResponseNew> response =
                NetworkClient1.getInstance(context).create(RewardService.class).fetchRewardNew(request,strApiKey);

        response.enqueue(new Callback<RewardResponseNew>() {
            @Override
            public void onResponse(Call<RewardResponseNew> call, Response<RewardResponseNew> response) {

                hideProgressDialog();
                try {

                    rewardResponse  =new RewardResponseNew();
                    rewardResponse = response.body();
                    if(rewardResponse != null){
                        if (rewardResponse.getResponse().equals("OK")) {
                            if(Integer.parseInt(rewardResponse.getRecordcount()) > 0){
                                scrollView.setVisibility(View.VISIBLE);
                                imgNoResult.setVisibility(View.GONE);
                                rewardRecords = rewardResponse.getRewarddetail();
                                addAchieveRewardTableData(rewardRecords);
                            }
                        }
                    }
                    else {
                        scrollView.setVisibility(View.GONE);
                        imgNoResult.setVisibility(View.VISIBLE);
                        Toast.makeText(context, rewardResponse.getResponse(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RewardResponseNew> call, Throwable t) {

                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void addAchieveRewardTableData(ArrayList<Map<String, String>> walletList ) {
        tableLayAchieveRecord.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=walletList;

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            TableRow tableRow = new TableRow(context);

            for(Object obj : map.keySet()){
                Object objKey=map.get(obj);
                tableRow.setLayoutParams(getLayoutParams());
                if (i==0){
                    tableRow.addView(getTextView(i, String.valueOf(obj), Color.WHITE, Typeface.BOLD, Color.BLACK));
                }
                else
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.LightGray)));
            }
            tableLayAchieveRecord.addView(tableRow);
        }
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
}
