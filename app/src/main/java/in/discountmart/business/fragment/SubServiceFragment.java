package in.discountmart.business.fragment;


import static in.discountmart.business.business_constants.AppConstants.CURRENT_TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.daimajia.slider.library.SliderLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.activity.CommonReportActivity;
import in.discountmart.business.adapter.SingleTextItemRecyAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.ProfileServices;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.SingleItemModel;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.DashboardResponse;
import in.discountmart.business.model_business.responsemodel.WalletListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubServiceFragment extends Fragment implements SingleTextItemRecyAdapter.ItemSelectListener {

    Context context;
    private SliderLayout mSlider1;
    TextView txtTitle;
    TextView txtMatchBonus;
    TextView txtDirectIncome;
    TextView txtSponsLevlIncome;
    TextView txtRoyaltyIncome;
    TextView txtLevelIncome;
    TextView txtTotIncome;
    TextView txtRank;
    LinearLayout layoutTeamReport;
    LinearLayout layoutBusinessReport;
    LinearLayout layoutVoucher;
    LinearLayout layoutMyIncome;
    LinearLayout layoutWallet;
    LinearLayout layoutWebsite;
    LinearLayout serviceMydirect;
    LinearLayout serviceWallet;
    LinearLayout serviceRankWise;
    LinearLayout serviceLevelWise;
    LinearLayout serviceDownlineDetail;
    LinearLayout serviceMyInvoice;
    LinearLayout serviceTeamPurchase;
    LinearLayout serviceUtilityVoucher;
    LinearLayout serviceShopVoucher;
    LinearLayout serviceMainWallet;
    LinearLayout serviceShopWallet;
    LinearLayout serviceShopWeb;
    LinearLayout serviceBusinessWeb;
    LinearLayout serviceUtilityWeb;
    LinearLayout serviceGiftShopWeb;
    LinearLayout layoutNote;
    ProgressDialog pDialog;
    LinearLayout layoutWalletTypeList;
    RecyclerView recyclerWalletList;
    View view;
    String service="";
    String strApiKey="";

    ArrayList<SingleItemModel> itemArrayList;
    ArrayList<WalletListResponse.WalletList> walletLists;
    SingleTextItemRecyAdapter singleItemAdapter;

    public SubServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.business_sub_service_fragment, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            mSlider1=(SliderLayout)mainView.findViewById(R.id.frag_home_slider);
            txtTitle=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_title);
            layoutTeamReport=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_team_report);
            layoutBusinessReport=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_business_report);
            layoutVoucher=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_voucher);
            layoutWallet=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_wallet);
            layoutMyIncome=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_myincome);
            layoutWebsite=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_website);
            layoutNote=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_note);
            serviceMydirect=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_mydirect);
            serviceRankWise=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_rankwise);
            serviceLevelWise=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_levelwise);
            serviceDownlineDetail=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_downline);
            serviceMyInvoice=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_mypurchase);
            serviceTeamPurchase=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_team_purchase);
            serviceUtilityVoucher=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_utility);
            serviceShopVoucher=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_shopping);
            serviceMainWallet=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_main_wallet);
            serviceShopWallet=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_shop_wallet);

            serviceShopWeb=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_shop_site);
            serviceUtilityWeb=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_utility_site);
            serviceBusinessWeb=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_business_site);
            serviceGiftShopWeb=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_bigbazaar_site);

            layoutWalletTypeList=(LinearLayout)mainView.findViewById(R.id.sub_service_frag_layout_walletlist);
            recyclerWalletList=(RecyclerView)mainView.findViewById(R.id.sub_service_frag_recycler);

            /*My income*/
            txtSponsLevlIncome=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_spons_lvl_income);
            txtMatchBonus=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_match_bonus);
            txtTotIncome=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_tot_income);
            txtDirectIncome=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_dir_income);
            txtRoyaltyIncome=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_roylty_income);
            txtLevelIncome=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_level_income);
            txtRank=(TextView)mainView.findViewById(R.id.sub_service_frag_txt_rank);

            /*Recycler View */
            recyclerWalletList.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyclerWalletList.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerWalletList.setLayoutManager(llm);
            recyclerWalletList.setItemAnimator(new DefaultItemAnimator());
            singleItemAdapter=new SingleTextItemRecyAdapter(context);
            recyclerWalletList.setAdapter(singleItemAdapter);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            /*Get Bundle value*/
            Bundle bundle=getArguments();
            if(bundle != null){
                service=bundle.getString("Service");
            }

            if(service.equals("TeamReport")){
                layoutTeamReport.setVisibility(View.VISIBLE);
                layoutBusinessReport.setVisibility(View.GONE);
                layoutVoucher.setVisibility(View.GONE);
                layoutWallet.setVisibility(View.GONE);
                layoutNote.setVisibility(View.VISIBLE);
                txtTitle.setText("Team Report");
            }
            else if(service.equals("BusinessReport")){
                layoutTeamReport.setVisibility(View.GONE);
                layoutBusinessReport.setVisibility(View.VISIBLE);
                layoutVoucher.setVisibility(View.GONE);
                layoutWallet.setVisibility(View.GONE);
                layoutNote.setVisibility(View.VISIBLE);
                txtTitle.setText("Business Report");
            }
            else if(service.equals("Voucher")){
                layoutTeamReport.setVisibility(View.GONE);
                layoutBusinessReport.setVisibility(View.GONE);
                layoutVoucher.setVisibility(View.VISIBLE);
                layoutWallet.setVisibility(View.GONE);
                layoutNote.setVisibility(View.VISIBLE);
                txtTitle.setText("My Voucher");
            }//MyIncome
            else if(service.equals("Wallet")){
                layoutTeamReport.setVisibility(View.GONE);
                layoutBusinessReport.setVisibility(View.GONE);
                layoutVoucher.setVisibility(View.GONE);
                layoutWallet.setVisibility(View.GONE);
                layoutWalletTypeList.setVisibility(View.VISIBLE);
                layoutNote.setVisibility(View.VISIBLE);
                txtTitle.setText("Wallet List");
                getWalletList();

            }

            else if(service.equals("MyIncome")){
                layoutMyIncome.setVisibility(View.VISIBLE);
                layoutTeamReport.setVisibility(View.GONE);
                layoutBusinessReport.setVisibility(View.GONE);
                layoutVoucher.setVisibility(View.GONE);
                layoutWallet.setVisibility(View.GONE);
                layoutNote.setVisibility(View.GONE);
                txtTitle.setText("My Income");
            }

            else if(service.equals("Website")){
                layoutWebsite.setVisibility(View.VISIBLE);
                layoutMyIncome.setVisibility(View.GONE);
                layoutTeamReport.setVisibility(View.GONE);
                layoutBusinessReport.setVisibility(View.GONE);
                layoutVoucher.setVisibility(View.GONE);
                layoutWallet.setVisibility(View.GONE);
                layoutNote.setVisibility(View.GONE);

                txtTitle.setText("Website");
            }

            /*call api*/
           /* if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                getDashboardDetail();
            }*/

            //Service Profile on click
            serviceMydirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((BusinessDashboardActivity)context).replaceFragment_business(new MyDirectFragment(), CURRENT_TAG, null);
                }
            });

            //Service Rank Wise Detail on click
            serviceRankWise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new RankWiseDownlineReportFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Rank Wise",null);

                }
            });


            /*Service Level wise on click*/
            serviceLevelWise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new LevelWiseFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Level Wise",null);
                }
            });

            /*Service Downline Detail on click*/
            serviceDownlineDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new DownlineFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"downline",null);
                }
            });


            /* service my invoice / My purchase on  click*/
            serviceMyInvoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myPurchaseIntent=new Intent(context, CommonReportActivity.class);
                    myPurchaseIntent.putExtra("Type","MyPurchase");
                    myPurchaseIntent.putExtra("Title","My Purchase Detail");
                    context.startActivity(myPurchaseIntent);
                   getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Downline Purchase  on click*/
            serviceTeamPurchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new DownlinePurchaseFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Bank withdrawl",null);
                }
            });

            /*Service Main Wallet Report  on click*/
            serviceMainWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new MainWalletReportFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Wallet Report",null);
                }
            });

            /*Service Shop Wallet Report  on click*/
            serviceShopWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new ShoppingWalletReportFragment();
                    ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"Wallet Report",null);
                }
            });
           /* *//*Service Utility on click*//*
            serviceUtilityVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent fundIntent=new Intent(context, PromocodeListActivity.class);
                    context.startActivity(fundIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });*/

            /*Service Shopping on click*/
           /* serviceShopVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent fundIntent=new Intent(context, PromocodeDetaillActivity.class);
//                    context.startActivity(fundIntent);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);

                }
            });*/

            /*Service Business Web on click*/
            serviceBusinessWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(context, WebViewActivity.class);
//                    i.putExtra("URL", ApiConstants.Business_Url);
//                    i.putExtra("Type","Website");
//                    i.putExtra("From","Business");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });


            /*Service Shop Web on click*/
            serviceShopWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(context, WebViewActivity.class);
//                    i.putExtra("URL", ApiConstants.Shopping_Url);
//                    i.putExtra("Type","Website");
//                    i.putExtra("From","Business");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Utility Web on click*/
            serviceUtilityWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(context, WebViewActivity.class);
//                    i.putExtra("URL", ApiConstants.Utility_Url);
//                    i.putExtra("Type","Website");
//                    i.putExtra("From","Business");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Big Bazaar Web on click*/
            serviceGiftShopWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(context, WebViewActivity.class);
//                    i.putExtra("URL", ApiConstants.BigBazaar_Url);
//                    i.putExtra("Type","Website");
//                    i.putExtra("From","Business");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;


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
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    DashboardResponse Response =new DashboardResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            /*For Rank*/
                          /*  if(Response.getRank() != null){
                                if(Response.getRank().getRank().equals(""))
                                    txtRank.setVisibility(View.GONE);
                                else {
                                    txtRank.setVisibility(View.VISIBLE);
                                    txtRank.setText(Response.getRank().getRank());
                                }

                            }*/


                            /*For get My Income */
                           /* if(Response.getIncome()!= null)
                            {
                                txtMatchBonus.setText(Response.getIncome().getDevelopmentBonus());//development bonus
                                txtDirectIncome.setText(Response.getIncome().getReferralBonus());// Referral Bonus
                                //txtRoyaltyIncome.setText(Response.getIncome().get());
                                txtLevelIncome.setText(Response.getIncome().getRepurchaseIncome());
                                txtSponsLevlIncome.setText(Response.getIncome().getLeadershipBonus()); // Leadership bonus
                                txtTotIncome.setText(Response.getIncome().getTotalAmount());
                                txtRank.setText(Response.getIncome().getRankReward());
                            }
                            else {
                                String toast="No record found";
                                Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
                            }*/


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

    /*Get Pin Wallet List Response Api*/
    private void getWalletList() {
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setReqtype(ApiConstants.REQUEST_WALLET_LIST);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Parameter=gson.toJson(baseRequest);
        Log.e("RequestLevelList:", Parameter);

        Call<WalletListResponse> responseCall = NetworkClient1.getInstance(context).create(WalletServices.class).fetchWalletList2(baseRequest,strApiKey);

        responseCall.enqueue(new Callback<WalletListResponse>() {
            @Override
            public void onResponse(Call<WalletListResponse> call, Response<WalletListResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                WalletListResponse listResponse = response.body();
                try {
                    if(listResponse != null){
                        if (listResponse.getResponse().equals("OK")) {

                            if(listResponse.getWallet() != null && listResponse.getWallet().size() > 0) {
                                walletLists = new ArrayList<WalletListResponse.WalletList>();
                                walletLists=listResponse.getWallet();
                                itemArrayList=new ArrayList<SingleItemModel>();
                                for(int i=0;i< walletLists.size(); i++){
                                    SingleItemModel itemModel=new SingleItemModel();
                                    itemModel.setId(walletLists.get(i).getActype());
                                    itemModel.setName(walletLists.get(i).getWalletname());
                                    itemArrayList.add(itemModel);
                                }
                                if(itemArrayList != null && itemArrayList.size()>0){
                                    singleItemAdapter.setList(itemArrayList,SubServiceFragment.this);
                                }

                            }
                            else {
                                Toast.makeText(context, " Pin Level List Is Empty", Toast.LENGTH_SHORT).show();
                            }

                        }

                        else if(listResponse.getResponse().equals("FAILED") && listResponse.getMsg().contains("Invalid Login Details")){
                            new BusinessDashboardActivity().blankValueFromSharePreference(context,listResponse.getMsg());
                        }
                        else if(listResponse.getResponse().equals("FAILED")) {
                            String msg=listResponse.getResponse()+ " "+"Something went wrong..";
                            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

                        }
                        else {
                            Snackbar.make(view, listResponse.getResponse(), Snackbar.LENGTH_LONG)
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

                        String msg="Something went wrong..";
                        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<WalletListResponse> call, Throwable t) {
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


    @Override
    public void onItemClick(SingleItemModel item) {
        try {
            if(item != null){
                String type= item.getId();
                String name=item.getName();
                //WalletReportFragment walletFragment=new WalletReportFragment();
                // fragment = new WalletReportFragment();

//                Bundle walletBundle=new Bundle();
//                walletBundle.putString("Type",type);
//                walletBundle.putString("Title",name);
//                walletFragment.setArguments(walletBundle);
//                String strTitle="Main Wallet Report";
//                ((DashboardActivity)context).replaceFragment(walletFragment, AppConstants.CURRENT_TAG, walletBundle);

                Intent wallteReqDetail=new Intent(context, CommonReportActivity.class);
                wallteReqDetail.putExtra("Type",type);
                wallteReqDetail.putExtra("Title",name);
                getActivity().startActivity(wallteReqDetail);
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
