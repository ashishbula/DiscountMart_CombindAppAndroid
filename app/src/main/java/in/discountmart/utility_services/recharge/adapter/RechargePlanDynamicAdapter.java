package in.discountmart.utility_services.recharge.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import in.discountmart.utility_services.recharge.fragment.FragmentDynamicTabs;
import in.discountmart.utility_services.recharge.recharge_model.RechargePlanDetails;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargePlanResponse;

public class RechargePlanDynamicAdapter extends FragmentStatePagerAdapter {


    private int mNumOfTabs;
    Fragment mFragment;
    String mIpcode="";
    String mCircle="";
    String mPlan="";
    //HashMap<String,ArrayList<RechargePlanDetails.PlanDetail>> planDetailList;
    ArrayList<RechargePlanDetails> planDetailList;
    //ArrayList<RechargePlanDetail> mPlanList;
    ArrayList<RechargePlanResponse.RechargePlansUnique> mPlanList;
    public RechargePlanDynamicAdapter(String planName,FragmentManager fm, int NumOfTabs,
                                      ArrayList<RechargePlanDetails> plansUniques,
                                      String circle, String ipcode) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        //this.mPlanList=plansUniques;
        this.planDetailList=plansUniques;
        this.mCircle=circle;
        this.mPlan=planName;
        this.mIpcode=ipcode;
        //this.mFragment=fragment;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("IpCode", mIpcode);
        b.putString("Plan", mPlan);
        b.putString("Circle", mCircle);
        b.putSerializable("PlanList", planDetailList);
        Fragment frag = FragmentDynamicTabs.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
