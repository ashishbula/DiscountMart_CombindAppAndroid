package in.discountmart.business.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.model_business.responsemodel.DeliveryCenterResponse;
import in.discountmart.utility_services.fund.activity.AddFundActivity;
import in.discountmart.utility_services.fund.adapter.WalletTypeAdapter;
import in.discountmart.utility_services.fund.fund_api.FundApi;
import in.discountmart.utility_services.fund.fund_model.request.WalletTypeRequest;
import in.discountmart.utility_services.fund.fund_model.response.WalletTypeResponse;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SelectProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public static List<AddProductModel> selectProductList;
    public static ArrayList<AddProductModel> addProductList;
    public ArrayList<DeliveryCenterResponse.DeliveryCenter> deliveryCentereList;
    ProgressDialog progressDialog;
    DeliveryCenterAdapter deliveryCenterAdapter;
    int quantity = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProdtName;

        public TextView txtProdtPrice;
        public TextView txtProdtTotPrice;
        public TextView txtProdtQty;
        public TextView txtProdtRcp;
        public TextView txtProdtTotRcp;
        public TextView txtProdtFv;
        public TextView txtProdtTotFv;
        public TextView txtProdtDiscount;

        LinearLayout layoutRemove;

        public ImageView imgProdtImg;

        public MyViewHolder(View view) {
            super(view);
            txtProdtName = view.findViewById(R.id.select_product_item_txt_pname);
            //txtHotelCode = view.findViewById(R.id.flight_search_list_item_txt_flight_code);
            txtProdtPrice = view.findViewById(R.id.select_product_item_txt_pprice);
            txtProdtTotPrice = view.findViewById(R.id.select_product_item_txt_totprice);
            txtProdtQty = view.findViewById(R.id.select_product_item_txt_qty);
            txtProdtRcp = view.findViewById(R.id.select_product_item_txt_rcp);
            txtProdtTotRcp = view.findViewById(R.id.select_product_item_txt_totrcp);
            txtProdtFv = view.findViewById(R.id.select_product_item_txt_fv);
            txtProdtTotFv = view.findViewById(R.id.select_product_item_txt_totfv);
            txtProdtDiscount = view.findViewById(R.id.select_product_item_txt_totdiscount);

        /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onHotelSelected(hotelSearchList.get(getAdapterPosition()));
                }
            });*/
        }

    }

    public class MyFooter extends RecyclerView.ViewHolder {
        public LinearLayout layoutFooter;
        public TextView txtTotQty;
        public TextView txtTotPrice;
        public TextView txtTotFv;
        public TextView txtTotRcp;
        public TextView txtPaidAmount;
        public TextView txtDiscount;
        public TextView txtDisCal;

        RadioGroup rdgType;
        RadioButton rdbDemostic;
        RadioButton rdbInternationl;
        public TextView txtShipping;
        public LinearLayout layoutShipping;

        public TextView txtPayableAmt;

        AppCompatSpinner spinnerWalletType;

        public MyFooter(@NonNull View itemHeaderView) {
            super(itemHeaderView);
            // layoutFooter=(LinearLayout)itemHeaderView.findViewById(R.id.cart_layout);
            rdgType=(RadioGroup)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_rdb_type);
            rdbDemostic=(RadioButton)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_rdb_byhand);
            rdbInternationl=(RadioButton)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_rdb_bycourier);
            spinnerWalletType = (AppCompatSpinner)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_spinner_delivery);

            txtTotQty = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_totitem);
            txtTotPrice = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_mrp);
            txtTotFv = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_fv);
            txtTotRcp = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_rcp);
            txtDiscount = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_dis);
            txtDisCal = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_dis_calcu);
            txtTotQty = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_totitem);
            layoutShipping = (LinearLayout) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_layout_shipping);
            txtShipping = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_shipping);
            txtPayableAmt = (TextView) itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_payable_amt);

            //txtPaidAmount=(TextView)itemHeaderView.findViewById(R.id.cart_footer_item_txt_total_paid);
        }
    }

    public SelectProductAdapter(Context context, List<AddProductModel> productList) {
        this.mContext = context;
        //this.cartActivity=activity;
        this.selectProductList = productList;
        //this.contactListFiltered = contactList;
        notifyDataSetChanged();
    }
  /*  public void setData(Context context, List<AddProductModel> productList) {
        this.mContext = context;
        //this.cartActivity=activity;
        this.selectProductList = productList;

        notifyDataSetChanged();
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new MyViewHolder(itemView);*/

        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.selected_product_item, parent, false);
            return new MyViewHolder(itemView);
        }

        if (viewType == TYPE_FOOTER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_product_footer_item, parent, false);
            return new MyFooter(layoutView);
        }

        throw new RuntimeException("No match for " + viewType + ".");

        /*RecyclerView.ViewHolder vh;
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
            vh= new MyViewHolder(view);
            //return new MyViewHolder(view);
        }
        else  //(viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item_footer_layout, parent, false);
            vh= new MyFooter(view);
            //return new LoadingViewHolder(view);
        }
        return vh;*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder != null) {

            if (holder instanceof MyViewHolder) {
                final MyViewHolder myViewHolder = (MyViewHolder) holder;
                //myViewHolder.layoutItemView.setVisibility(View.VISIBLE);
                //final FlightSearchResponse contact = flightSearchList.get(position);
               /* if (cartList.get(position).get().equals("")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Picasso.with(mContext)
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.round_logo)))
                                .placeholder(R.mipmap.round_logo)
                                .error(R.mipmap.round_logo)
                                .into(myViewHolder.imgProdtImg);

                        *//*Glide.with(mContext)  //2
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.home_icon_hotel) //5
                                .error(R.mipmap.home_icon_hotel) //6
                                .fallback(R.mipmap.home_icon_hotel) //7
                                .into(myViewHolder.imgHotelImg);*//* //8
                    }
                } else {

                    Picasso.with(mContext)
                            .load(String.valueOf(productArrayList.get(position).getImagePath()))
                            .placeholder(R.mipmap.round_logo)
                            .error(R.mipmap.round_logo)
                            .into(myViewHolder.imgProdtImg);

                   *//* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*//*
                }*/


                myViewHolder.txtProdtName.setText("Name : " + selectProductList.get(position).getProductname());

                myViewHolder.txtProdtPrice.setText("Weight : " + mContext.getResources().getString(R.string.rs_symbol) + " " + selectProductList.get(position).getMrp());
                myViewHolder.txtProdtQty.setText("Quantity : " + String.valueOf(selectProductList.get(position).getProdusQuantity()));
                myViewHolder.txtProdtRcp.setText("DP Value : " + String.valueOf(selectProductList.get(position).getRcp()));
                myViewHolder.txtProdtFv.setText("BV Value : " + String.valueOf(selectProductList.get(position).getFv_rv()));
                myViewHolder.txtProdtTotFv.setText("Total PV : " + String.valueOf(selectProductList.get(position).getTotalFVRV()));
                myViewHolder.txtProdtTotRcp.setText("Total Amount : " + String.valueOf(selectProductList.get(position).getTotalRcp()));
                myViewHolder.txtProdtTotPrice.setText("Total Mrp : " + String.valueOf(selectProductList.get(position).getTotalMrp()));

                int totQty = 0;
                double totMrp = 0;
                double totRcp = 0;
                double totFv = 0;
                double totDiscount = 0;

                totMrp = selectProductList.get(position).getTotalMrp();
                totRcp = selectProductList.get(position).getRcp();

                totDiscount = totMrp - totRcp;
                myViewHolder.txtProdtDiscount.setText("Total Discount : " + String.valueOf(totDiscount));
            }
            else if (holder instanceof MyFooter) {
                final MyFooter myFooter = (MyFooter) holder;
                int totQty = 0;
                double totMrp = 0;
                double totRcp = 0;
                double totFv = 0;
                double totDiscount = 0;
                double totShipping = 0;
                double tot_pay = 0;
                for (int i = 0; i < selectProductList.size(); i++) {
                    totMrp = totMrp + selectProductList.get(i).getTotalMrp();
                    totRcp = totRcp + selectProductList.get(i).getTotalRcp();
                    totFv = totFv + selectProductList.get(i).getTotalFVRV();
                    totQty = totQty + selectProductList.get(i).getProdusQuantity();
                }

                double finalTot_pay = totRcp;
                double tot_dscount = totMrp - totRcp;
                myFooter.txtTotPrice.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totMrp));
                myFooter.txtTotFv.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totFv));
                myFooter.txtTotRcp.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totRcp));
                myFooter.txtDiscount.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(tot_dscount));
                myFooter.txtTotQty.setText("Total Qty : " + totQty);
                myFooter.txtDisCal.setText("( " + String.valueOf(totMrp) + " - " + String.valueOf(totRcp) + " )");
                myFooter.txtShipping.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(totShipping));
                myFooter.txtPayableAmt.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(finalTot_pay));

                myFooter.rdgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int selectId = myFooter.rdgType.getCheckedRadioButtonId();
                        RadioButton rb=(RadioButton)group.findViewById(checkedId);
                        String strRbtnVlaue=rb.getText().toString();
                        if(strRbtnVlaue.equals("By Hand")){
                            myFooter.txtShipping.setText(mContext.getResources().getString(R.string.rs_symbol) + " " +0);
                            myFooter.txtPayableAmt.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(finalTot_pay));
                        }
                        else if(strRbtnVlaue.equals("By Courier")){
                            myFooter.txtShipping.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + 40);
                            myFooter.txtPayableAmt.setText(mContext.getResources().getString(R.string.rs_symbol) + " " + String.valueOf(finalTot_pay+40));
                        }
                    }
                });
                myFooter.spinnerWalletType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // check = check + 1;
                        //if (check > 1)
                        // Get selected operator entity
                        DeliveryCenterResponse.DeliveryCenter deliveryCenterList = (DeliveryCenterResponse.DeliveryCenter) parent.getItemAtPosition(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                deliveryCentereList = new ArrayList<>();
                deliveryCenterAdapter = new DeliveryCenterAdapter(mContext, deliveryCentereList);
                myFooter.spinnerWalletType.setAdapter(deliveryCenterAdapter);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //return cartList.get(position) == null ? TYPE_FOOTER : TYPE_ITEM;
        if (position == selectProductList.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);


    }

    private boolean isPositionFooter(int position) {
        return position == selectProductList.size();
    }

    /*@Override
    public int getItemCount() {
        //return cartList == null ? 0 : cartList.size();
        return (null != cartList ? cartList.size() : 0);
    }*/
    @Override
    public int getItemCount() {
        if (selectProductList == null) {
            return 0;
        }

        if (selectProductList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return selectProductList.size() + 1;
    }

    public void getDeliveryCenterList() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String strApiRequest = "";
        JSONObject object = null;
        String strToken = TokenBase64.getToken();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse = new LoginPreferences_Utility(mContext).getLoggedinUser();
        String companyId = loginResponse.getSponsorFormNo().substring(0, loginResponse.getSponsorFormNo().length() - 2);
        String formNo = loginResponse.getFormNo().substring(0, loginResponse.getFormNo().length() - 2);

        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/
            BaseHeaderRequest headerRequest = new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);

            WalletTypeRequest request = new WalletTypeRequest();
            request.setSponsorFormNo(companyId);

            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(request);

            strApiRequest = new Gson().toJson(apiRequest);

            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<BaseResponse> fetchWalletTypeCall =
                NetworkClient_Utility_1.getInstance(mContext).create(FundApi.class).fetchWalletType(apiRequest, strToken);

        fetchWalletTypeCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response = new BaseResponse();
                    Response = response.body();

                    if (Response != null) {
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if (Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()) {

                                String toast = Response.getRESP_MSG();
                                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();

                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] walletList = Response.getRESPONSE().split("");
                                if (walletList.length > 0) {
                                    DeliveryCenterResponse.DeliveryCenter[] walletResList = new Gson().fromJson(Response.getRESP_VALUE(), DeliveryCenterResponse.DeliveryCenter[].class);
                                    List<DeliveryCenterResponse.DeliveryCenter> tempList = new ArrayList<DeliveryCenterResponse.DeliveryCenter>(Arrays.asList(walletResList));

                                    // adding contacts to contacts list
                                    deliveryCentereList.clear();
                                    deliveryCentereList.addAll(tempList);
                                    // refreshing recycler view
                                    deliveryCenterAdapter.notifyDataSetChanged();
                                } else {
                                    String toast = " Delivery Centere  List empty";
                                    Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
                        } else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mContext.startActivity(intent);

                        } else {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String toast = "Something wrong..";
                        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}