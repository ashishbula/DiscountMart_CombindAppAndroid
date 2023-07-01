package in.discountmart.utility_services.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.adapter.PromocodeAdapter;
import in.discountmart.utility_services.fund.activity.AddFundActivity;
import in.discountmart.utility_services.fund.activity.FundRequestActivity;
import in.discountmart.utility_services.model.response_model.PromocodeRespose;

public class ChooseOptionBottomDialog extends BottomSheetDialogFragment {

    LinearLayout layoutAddFund;
    LinearLayout layoutFundRequest;
    LinearLayout layoutAddFund_Utility;
    LinearLayout layoutAddFund_Business;
    LinearLayout layoutFundReq_Business;
    LinearLayout layoutFundReq_Utility;

    RecyclerView recyViewPromo;
    PromocodeAdapter promocodeAdapter;
    ProgressDialog progressDialog;
    ArrayList<PromocodeRespose> promocodeList;
    Context context;
    String type="";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            context=getActivity();

            view = inflater.inflate(R.layout.choose_option_bottom_dialog_fragment, container, false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));


            layoutAddFund=(LinearLayout)view.findViewById(R.id.bottom_frag_layout_addfund);
            layoutFundRequest=(LinearLayout)view.findViewById(R.id.bottom_frag_layout_fund_reuqest);
            layoutAddFund_Utility=(LinearLayout)view.findViewById(R.id.bottom_frag_layout_addfund_utility);
            layoutAddFund_Business=(LinearLayout)view.findViewById(R.id.bottom_frag_layout_addfund_mainWallet);
            layoutFundReq_Business=(LinearLayout)view.findViewById(R.id.bottom_frag_layout_fund_req_mainWallet);
            layoutFundReq_Utility=(LinearLayout)view.findViewById(R.id.bottom_frag_layout_fund_req_utility);

            Bundle bundle=getArguments();
            if(bundle!= null){
                type=bundle.getString("Type");
                getDialog().getWindow().setTitle(type);
            }
            if(type.contains("Add Fund")){
                layoutAddFund.setVisibility(View.VISIBLE);
                layoutFundRequest.setVisibility(View.GONE);
            }
            else if(type.contains("Fund Request")){
                layoutAddFund.setVisibility(View.GONE);
                layoutFundRequest.setVisibility(View.VISIBLE);
            }

            /* option add fund in utility
            * on click open Add fund Page */
            layoutAddFund_Utility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddFundActivity.class);
                    intent.putExtra("Home", "true");
                    context.startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    dismiss();
                }
            });

            /* option add fund in Business
             * on click open Wallet Request Page */
            /*layoutAddFund_Business.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WalletRequestActivity.class);
                    //intent.putExtra("Edit", "false");
                    context.startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    dismiss();
                }
            });*/

            /* option  fund request in Business
             * on click open Wallet Request Page */
           /* layoutFundReq_Business.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WalletRequestActivity.class);
                    //intent.putExtra("Edit", "false");
                    context.startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    dismiss();
                }
            });*/

            /* option  fund request in Utility
             * on click open Fund Request Page */
            layoutFundReq_Utility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FundRequestActivity.class);
                    //intent.putExtra("Edit", "false");
                    context.startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    dismiss();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }



}
