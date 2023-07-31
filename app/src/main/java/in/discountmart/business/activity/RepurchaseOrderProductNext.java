package in.discountmart.business.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.adapter.SelectProductAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.model_business.requestmodel.IDActivationRequest;
import in.discountmart.business.model_business.responsemodel.IDActivationResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.listener.AlertDialogButtonListener;
import in.discountmart.utility.AlertDialogUtils;

import java.util.ArrayList;

import in.base.network.NetworkClient;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.model_business.requestmodel.IDActivationRequest_new;
import in.discountmart.business.model_business.responsemodel.IDActivationResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepurchaseOrderProductNext extends AppCompatActivity {

    Context context;
    View view;
    RecyclerView recyclerView;
    SelectProductAdapter adapter;
    ArrayList<AddProductModel> selectProdtList;
    TextView txtTitle;
    EditText edMobile;
    EditText edRemark;
    EditText edTPaswd;

    Button btnSendRequest;
    Button btnCancel;

    ProgressDialog pDialog;
    Bundle bundle;

    public RepurchaseOrderProductNext() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_repurchase_product_next);
        try {
            context = this;

            view = findViewById(android.R.id.content);

            bundle = getIntent().getExtras();

            txtTitle = (TextView) findViewById(R.id.repurchase_prodt_next_txt_title);
            txtTitle.setText("Product Request");

            recyclerView = (RecyclerView) findViewById(R.id.repurchase_prodt_next_recycler_list);
            // layoutFooter.setVisibility(View.GONE);
            edMobile = (EditText) findViewById(R.id.id_activation_act_edTxt_mob);
            edRemark = (EditText) findViewById(R.id.id_activation_act_edTxt_remark);
            edTPaswd = (EditText) findViewById(R.id.id_activation_act_edTxt_trans_pass);
            btnSendRequest = (Button) findViewById(R.id.id_activation_act_btn_send_request);
            btnCancel = (Button) findViewById(R.id.id_activation_act_btn_cancel);
            /*Get Selected Product List from shared preference*/
            edMobile.setText(bundle.getString("mobileno"));
          /*  bundle.putString("idno", strMemId);
            bundle.putString("shoppingwallet", String.valueOf(availWalletBal));
            bundle.putString("mobileno", mobNo);*/
            selectProdtList = new ArrayList<AddProductModel>();
            selectProdtList = SharedPrefrence_Business.getInstance().addProductList;

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

            adapter = new SelectProductAdapter(context, selectProdtList);
            recyclerView.setAdapter(adapter);

            btnSendRequest.setOnClickListener(view -> {
                if (edRemark.getText().toString().equals("")) {
                    Toast.makeText(RepurchaseOrderProductNext.this, "Please enter remark.", Toast.LENGTH_LONG).show();
                    edRemark.setFocusable(true);
                } else if (edTPaswd.getText().toString().equals("")) {
                    Toast.makeText(RepurchaseOrderProductNext.this, "Please enter Transaction Password.", Toast.LENGTH_LONG).show();
                    edTPaswd.setFocusable(true);
                } else
                    iDActivationAPICall();
            });

            btnCancel.setOnClickListener(view -> {
                Intent activationIntent = new Intent(RepurchaseOrderProductNext.this, BusinessDashboardActivity.class);
                activationIntent.putExtras(bundle);
                startActivity(activationIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void iDActivationAPICall() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        double TotalMRP = 0.0;
        double TotalPV = 0.0;
        ArrayList<IDActivationRequest_new.Product> productLists = new ArrayList<>();
        for (int i = 0; i < selectProdtList.size(); i++) {
            IDActivationRequest_new.Product product = new IDActivationRequest_new.Product();
            product.setProdid(selectProdtList.get(i).getProductId());
            product.setQty(String.valueOf(selectProdtList.get(i).getProdusQuantity()));
            productLists.add(product);
            TotalMRP = TotalMRP + selectProdtList.get(i).getTotalRcp();
            TotalPV = TotalPV + selectProdtList.get(i).getTotalFVRV();
        }
        IDActivationRequest_new.Product[] products = new IDActivationRequest_new.Product[productLists.size()];
        products = productLists.toArray(products);

        IDActivationRequest_new baseRequest = new IDActivationRequest_new();
        /*Set value in Entity class*/

        baseRequest.setReqtype(ApiConstants.REQUEST_SHOPPING);
        baseRequest.setProducts(products);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(this));
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(this));
        baseRequest.setIdno(bundle.getString("idno"));
        baseRequest.setShoppingwallet(bundle.getString("shoppingwallet"));
        baseRequest.setTotalamount(String.valueOf(TotalMRP));
        baseRequest.setTotalpv(String.valueOf(TotalPV));
        baseRequest.setDeliveryfor("WR");
        baseRequest.setDeliverytype("H");
        baseRequest.setAddress("test bispl");
        baseRequest.setRemarks(edRemark.getText().toString());
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(this));
        baseRequest.setTransactionpassword(edTPaswd.getText().toString());
        Call<IDActivationResponse> memberNameResponseCall =
                NetworkClient.getInstance(this).create(MyTeamService.class).fetchIdForActivation_new(baseRequest, SharedPrefrence_Business.getApiKey(this));

        memberNameResponseCall.enqueue(new Callback<IDActivationResponse>() {
            @Override
            public void onResponse(Call<IDActivationResponse> call, Response<IDActivationResponse> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                try {
                    IDActivationResponse Response = new IDActivationResponse();
                    Response = response.body();
                    if (Response != null) {
                        if (Response.getResponse().equals("OK")) {
                            AlertDialogUtils.showMaterialDialogWithOneButton_2(RepurchaseOrderProductNext.this, new AlertDialogButtonListener() {
                                @Override
                                public void onButtontapped(String btnText) {
                                    if (btnText.equals("OK")) {
                                        Intent activationIntent = new Intent(RepurchaseOrderProductNext.this, BusinessDashboardActivity.class);
                                        activationIntent.putExtras(bundle);
                                        startActivity(activationIntent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                                    }
                                }
                            }, "Alert Dialog", Response.getMsg() + " Order no. is " + Response.getOrderno(), "OK");

                        } else if (Response.getResponse().contains("FAILED") && Response.getMsg().contains("Invalid Login")) {
                            new BusinessDashboardActivity().blankValueFromSharedPreference(RepurchaseOrderProductNext.this);
                        } else if (Response.getResponse().contains("FAILED")) {
                            String toast = Response.getResponse() + ":" + Response.getMsg();
                            Toast.makeText(RepurchaseOrderProductNext.this, toast, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String msg = "Something went wrong.";
                        Toast.makeText(RepurchaseOrderProductNext.this, msg, Toast.LENGTH_SHORT).show();
                        new BusinessDashboardActivity().blankValueFromSharedPreference(RepurchaseOrderProductNext.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<IDActivationResponse> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(RepurchaseOrderProductNext.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
