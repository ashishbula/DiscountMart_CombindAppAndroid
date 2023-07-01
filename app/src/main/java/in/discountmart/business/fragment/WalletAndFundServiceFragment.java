package in.discountmart.business.fragment;


import static in.discountmart.business.business_constants.AppConstants.CURRENT_TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.activity.WalletTransferActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletAndFundServiceFragment extends Fragment  {

    Context context;
    private SliderLayout mSlider1;
    LinearLayout layoutRegistratiion;
    LinearLayout serviceMainWallet;
    LinearLayout serviceShopWallet;
    LinearLayout serviceMainToShopTransfer;
    LinearLayout serviceMainToOtherTransfer;
    LinearLayout serviceBankWithdrawl;
    LinearLayout serviceWithdrawlDetail;
    LinearLayout serviceWithdrawl;
    LinearLayout serviceBank;
    LinearLayout serviceWallet;
    LinearLayout serviceFund;
    ProgressDialog pDialog;
    View view;
    String service="";

    public WalletAndFundServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.business_my_wallet_fund_trans_fragment, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            mSlider1=(SliderLayout)mainView.findViewById(R.id.frag_home_slider);
            serviceMainWallet=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_mainwallet);
            serviceShopWallet=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_shopwallet);
            serviceMainToShopTransfer=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_maintoshop);
            serviceMainToOtherTransfer=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_maintoother);
            serviceBank=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_bank);
            serviceBankWithdrawl=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_bankwithdrawl);
            serviceWithdrawl=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_withdrawl);
            serviceWithdrawlDetail=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_withdrawl_detail);
            serviceWallet=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_wallet);
            serviceFund=(LinearLayout)mainView.findViewById(R.id.wallet_fund_frag_layout_fund);

            /*Get Bundle value*/
            Bundle bundle=getArguments();
            if(bundle != null){
                service=bundle.getString("Service");
            }

            if(service.contains("Wallet")){
                serviceWallet.setVisibility(View.VISIBLE);
                serviceFund.setVisibility(View.GONE);
            }
            else {
                serviceWallet.setVisibility(View.GONE);
                serviceFund.setVisibility(View.VISIBLE);
            }


            //Service Profile on click
            serviceMainWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((BusinessDashboardActivity)context).replaceFragment_business(new MainWalletReportFragment(), CURRENT_TAG, null);
                }
            });

            //Service News on click
            serviceShopWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Fragment fragment = new ShoppingWalletReportFragment();

                   ((BusinessDashboardActivity)context).replaceFragment_business(fragment, CURRENT_TAG, null);
                }
            });


            /*Service Main to shopping wallet transfer on click*/
            serviceMainToShopTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new WalletTransferFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Wallet Transfer",null);
                }
            });

            /*Service Level Wise direct on click*/
            serviceMainToOtherTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, WalletTransferActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });


            /* Layout  Bank on click*/
            serviceBank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(serviceWithdrawl.getVisibility() == View.VISIBLE)
                        serviceWithdrawl.setVisibility(View.GONE);
                    else
                        serviceWithdrawl.setVisibility(View.VISIBLE);
                }
            });

            /*Service Bank withdrawl  on click*/
            serviceBankWithdrawl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new BankWithdrawalFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Bank withdrawl",null);
                }
            });
            /*Service Bank withdrawl detail on click*/
            serviceWithdrawlDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new BankWithdrawalReportFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Bank withdrawl",null);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;


    }




}
