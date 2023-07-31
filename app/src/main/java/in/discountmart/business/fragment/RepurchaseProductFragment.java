package in.discountmart.business.fragment;


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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import in.base.network.NetworkClient1;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.adapter.RepurchaseProductAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyShoppingServices;
import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.model_business.requestmodel.GetProductRequest;
import in.discountmart.business.model_business.responsemodel.RepurchaseProductResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepurchaseProductFragment extends Fragment {
    View view;
    Context context;
    TextView txtHeader;
    RecyclerView recyclerView;
    RepurchaseProductAdapter adapter;
    LinearLayout layoutRecord;
    public LinearLayout layoutFooter;
    LinearLayout layoutNoRecord;

    public TextView txtQuantity;
    public TextView txtPrice;
    public TextView txtOrderNow;
    public TextView txtTotProduct;
    public TextView txtTotPV;

    String token = "";
    ProgressDialog progressDialog;
    ArrayList<RepurchaseProductResponse.ProductList> productArrayList;
    // ArrayList<AddCartRequest.Product> addproductArrayList;
    ArrayList<AddProductModel> tempSelectProductList;
    // ArrayList<CartListResponse.CartProduct> cartProductArrayList;

    public RepurchaseProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_repurchase_product, container, false);
        view = getActivity().findViewById(android.R.id.content);
        context = getActivity();
        try {
            layoutNoRecord = (LinearLayout) rootView.findViewById(R.id.repurchase_product_frag_layout_noRecord);
            layoutRecord = (LinearLayout) rootView.findViewById(R.id.repurchase_product_frag_layout_Record);
            layoutFooter = (LinearLayout) rootView.findViewById(R.id.repurchase_product_frag_layout_footer);
            txtPrice = (TextView) rootView.findViewById(R.id.repurchase_product_frag_txt_totprice);
            txtQuantity = (TextView) rootView.findViewById(R.id.repurchase_product_frag_txt_quantity);
            txtOrderNow = (TextView) rootView.findViewById(R.id.repurchase_product_frag_txt_order);
            txtTotProduct = (TextView) rootView.findViewById(R.id.repurchase_product_frag_txt_totProduct);
            txtTotPV = (TextView) rootView.findViewById(R.id.repurchase_product_frag_txt_totpv);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.repurchase_product_frag_recycler_list);
            // layoutFooter.setVisibility(View.GONE);

            productArrayList = new ArrayList<RepurchaseProductResponse.ProductList>();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

            adapter = new RepurchaseProductAdapter(context, productArrayList, RepurchaseProductFragment.this);
            recyclerView.setAdapter(adapter);

            /*Call api Get Product*/
            if (!ConnectivityUtils.isNetworkEnabled(context)) {
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                getProducts();
            }

            /*Text next on click */
            txtOrderNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        String data = "";
                        boolean chekQty = false;
                        boolean selectOrder = false;
                        Bundle bundle = new Bundle();
                        // ProductShadeNoFragment fragment = new ProductShadeNoFragment();

                        ArrayList<RepurchaseProductResponse.ProductList> tempSelectList =
                                new ArrayList<RepurchaseProductResponse.ProductList>();
                        ArrayList<RepurchaseProductResponse.ProductList> stList = ((RepurchaseProductAdapter) adapter)
                                .getOrderReceiveList();

                        tempSelectProductList = new ArrayList<AddProductModel>();
                        tempSelectProductList = RepurchaseProductAdapter.addProductList;

                        for (int i = 0; i < stList.size(); i++) {

                            RepurchaseProductResponse.ProductList receiveOrderResponse = stList.get(i);
                            int qty = Integer.parseInt(receiveOrderResponse.getQty());
                            if (receiveOrderResponse.isSelected()) {

                                tempSelectList.add(receiveOrderResponse);
                                selectOrder = true;

                            } else {
                                tempSelectList.size();
                                //selectOrder=false;
                            }

                        }

                        if (tempSelectProductList != null && tempSelectProductList.size() > 0) {
                            if (tempSelectProductList.size() == tempSelectList.size()) {
                                for (int k = 0; k < tempSelectList.size(); k++) {
                                    int prodQty = Integer.parseInt(tempSelectList.get(k).getQty());
                                    if (tempSelectProductList.get(k).getProdusQuantity() == prodQty) {
                                        chekQty = true;
                                    }

                                }
                            }
                        } else {
                            tempSelectProductList.size();
                        }

                        if (tempSelectList.size() > 0 && selectOrder && chekQty) {

                            SharedPrefrence_Business.getInstance().addProductList = tempSelectProductList;
                            ((BusinessDashboardActivity) context).replaceFragment(new RepurchaseProductNextFragment(), "", null);

                        } else {
                            Toast.makeText(context, "Plz Select Product than enter quantity", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    /* Request and Response Product List*/
    public void getProducts() {
        try {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String apikey = SharedPrefrence_Business.getApiKey(getActivity());
            GetProductRequest request = new GetProductRequest();
            request.setReqtype(ApiConstants.REQUEST_GET_PRODUCT);
            //request.setReqtype("repurchaseproductrequest");
            request.setPasswd(SharedPrefrence_Business.getPassword(getActivity()));
            request.setUserid(SharedPrefrence_Business.getUserID(getActivity()));
            request.setType("");
            request.setIslogin(SharedPrefrence_Business.getIsLogin(getActivity()));
            String strApiRequest = new Gson().toJson(request);
            Log.e("Request", strApiRequest);

            Call<RepurchaseProductResponse> fetchProductList =
                    NetworkClient1.getInstance(getActivity()).create(MyShoppingServices.class).fetchIDActivationProduct(request, apikey);


            fetchProductList.enqueue(new Callback<RepurchaseProductResponse>() {
                @Override
                public void onResponse(Call<RepurchaseProductResponse> call, Response<RepurchaseProductResponse> response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {

                        RepurchaseProductResponse Response = new RepurchaseProductResponse();
                        Response = response.body();

                        if (Response != null) {
                            if (Response.getResponse().equals("OK")) {
                                productArrayList = new ArrayList<RepurchaseProductResponse.ProductList>();
                                productArrayList = Response.getProductlist();

                                if (productArrayList != null && productArrayList.size() > 0) {

                                    layoutNoRecord.setVisibility(View.GONE);
                                    layoutRecord.setVisibility(View.VISIBLE);

                                    adapter.setData(context, productArrayList, RepurchaseProductFragment.this);

                                } else {
                                    //dailyIncentiveArrayList = new ArrayList<DirectIncomeReponse.DirectIncome>(Arrays.asList(dailyIncentive));
                                    productArrayList.clear();
                                    adapter.setData(getActivity(), productArrayList, RepurchaseProductFragment.this);
                                    adapter.notifyDataSetChanged();
                                    layoutNoRecord.setVisibility(View.VISIBLE);
                                    layoutRecord.setVisibility(View.GONE);
                                }
                            }
                            else if (Response.getResponse().equals("FAILED") && Response.getMsg().contains("Invalid Login Details")) {
                                String toast = Response.getResponse() + "\n" + "Please Login Again. ";
                                Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                SharedPrefrence_Business.setApiKey(context, "");
                                SharedPrefrence_Business.setPassword(context, "");
                                SharedPrefrence_Business.setUserID(context, "");
                                startActivity(intent);
                               getActivity().finish();
                            }
                            else {
                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.GONE);
                                String toast = Response.getResponse();

                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();

                            }
                        }
                        else {
                            String toast = "Something went wrong ? Please try again later.";
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<RepurchaseProductResponse> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
