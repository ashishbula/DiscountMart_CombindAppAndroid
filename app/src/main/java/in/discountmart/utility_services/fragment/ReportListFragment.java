package in.discountmart.utility_services.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.constants.AppConstants;
import in.discountmart.utility_services.report.activity.BusBookingDetailActivity;
import in.discountmart.utility_services.report.report_adapter.NameListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportListFragment extends Fragment implements NameListAdapter.ListAdapterListener {

    RecyclerView recyclerView;
    LinearLayout layoutHeader;
    LinearLayout layoutItem;
    LinearLayout layoutBlank;
    LinearLayout layoutFund;
    LinearLayout layoutAccount;
    LinearLayout layoutReport;
    ImageView imgFund;
    ImageView imgAccount;
    ImageView imgReport;
    View view;
    NameListAdapter adapter;
    ArrayList<String> reportList;
    Context context;
    String strReport="";

    public ReportListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.utility_fragment_report_list, container, false);
        try {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view=getActivity().findViewById(android.R.id.content);
        context=getActivity();
        Bundle bundle=getArguments();

        if(bundle != null){
            strReport=bundle.getString("Report");
        }
        /*View Reference*/
            layoutItem=(LinearLayout)rootView.findViewById(R.id.reportlist_frag_layout_item);
            //layoutBlank=(LinearLayout)rootView.findViewById(R.id.reportlist_frag_layout);
            layoutFund=(LinearLayout)rootView.findViewById(R.id.reportlist_frag_layout_fund);
            layoutAccount=(LinearLayout)rootView.findViewById(R.id.reportlist_frag_layout_account);
            layoutReport=(LinearLayout)rootView.findViewById(R.id.reportlist_frag_layout_report);
            imgAccount=(ImageView)rootView.findViewById(R.id.reportlist_frag_img_account);
            imgFund=(ImageView)rootView.findViewById(R.id.reportlist_frag_img_fund);
            imgReport=(ImageView)rootView.findViewById(R.id.reportlist_frag_img_report);

        recyclerView=(RecyclerView)rootView.findViewById(R.id.reportlist_frag_recycler);
        /*REcycler View set divider item line*/
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(llm);

            reportList=new ArrayList<>();
            if(strReport.contentEquals(AppConstants.TAG_FUND)){
                reportList=getFundReportList();
                imgFund.setVisibility(View.VISIBLE);
                imgReport.setVisibility(View.GONE);
                imgAccount.setVisibility(View.GONE);
            }


            else if(strReport.contentEquals(AppConstants.TAG_REPORT)){
                reportList=getReportList();
                imgFund.setVisibility(View.GONE);
                imgReport.setVisibility(View.VISIBLE);
                imgAccount.setVisibility(View.GONE);
            }

            else if(strReport.contentEquals(AppConstants.TAG_ACCOUNT)){
                reportList=getAccountReportList();
                imgFund.setVisibility(View.GONE);
                imgReport.setVisibility(View.GONE);
                imgAccount.setVisibility(View.VISIBLE);
            }


            if(reportList.size() > 0){
                //flightSearchList = new ArrayList<>();
                adapter = new NameListAdapter(context, reportList, this,strReport);
            }
            recyclerView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;

    }

    /*Initiat Fund Type list*/
    public ArrayList<String> getFundReportList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Add Fund");
        list.add("Fund Request");
        list.add("Fund Request Status");


        return list;
    }

    /*Initiat report Type list*/
    public ArrayList<String> getReportList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Recharge Report");
        list.add("Bill Payment Report");
        list.add("Bus Book Detail");
        list.add("Hotel Book Detail");
        list.add("Flight Book Detail");
        return list;
    }

    /*Initiat Account Type list*/
    public ArrayList<String> getAccountReportList(){

        ArrayList<String> list=new ArrayList<>();
        list.add("Ledger Report");
        list.add("Review Detail");
        list.add("Recharge Detail");

        return list;
    }


    @Override
    public void onSelected(String item) {
        try {
            if(!item.contentEquals("")){
                if(item.contentEquals("Add Fund")){
                    getActivity().getFragmentManager().popBackStack();
                }
                else if(item.contentEquals("Fund Request")){

                }
                else if(item.contentEquals("Fund Request Status")){

                }
                else if(item.contentEquals("Recharge Report")){

                }
                else if(item.contentEquals("Bill Payment Report")){

                }
                else if(item.contentEquals("Bus Book Detail")){

                    Intent busIntent=new Intent(context, BusBookingDetailActivity.class);
                    startActivity(busIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    getActivity().onBackPressed();
                }
                else if(item.contentEquals("Hotel Book Detail")){

                }
                else if(item.contentEquals("Flight Book Detail")){

                }
                else if(item.contentEquals("Ledger Report")){

                }
                else if(item.contentEquals("Review Detail")){

                }
                else if(item.contentEquals("Recharge Detail")){

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
