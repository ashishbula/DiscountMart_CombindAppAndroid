package in.discountmart.business.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.adapter.BankNameListAdapter;
import in.discountmart.business.model_business.responsemodel.BankListResponse;

import java.util.ArrayList;


public class BankListActivity extends AppCompatActivity implements BankNameListAdapter.BankListAdapterListener {

    private RecyclerView recyclerView;
    private ArrayList<BankListResponse.BankList> productDetailsList;
    private BankNameListAdapter mAdapter;
    private SearchView searchView;
    EditText edtxtSearch;
    ProgressDialog progressDialog;

    String strFlightType="";
    int value;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity_bank_list);
        view=findViewById(android.R.id.content);
        try {
            Toolbar toolbar = findViewById(R.id.bank_list_toolbar);
            edtxtSearch = (EditText) findViewById(R.id.bank_list_edtxt_search);
            setSupportActionBar(toolbar);

            // toolbar fancy stuff
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bank Name List");
            recyclerView = findViewById(R.id.bank_list_content_recycler);



            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                productDetailsList = new ArrayList<BankListResponse.BankList>();
                productDetailsList = (ArrayList<BankListResponse.BankList>) bundle.getSerializable("BankList");

            }
            if(productDetailsList != null && productDetailsList.size() > 0){
                mAdapter = new BankNameListAdapter(BankListActivity.this, productDetailsList, this);
            }




            // white background notification bar
            //whiteNotificationBar(recyclerView);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(BankListActivity.this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(mAdapter);

            /*edit txt text change listener for search*/
            edtxtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // filter recycler view when query submitted
                    mAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // filter recycler view when query submitted
                    mAdapter.getFilter().filter(s.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(BankListResponse.BankList response) {

        try {
            if(response != null){
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                //bundle.putSerializable("Response",response);
                bundle.putString("BankId",response.getBankcode());
                bundle.putString("BankName",response.getBankname());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }catch (Exception e){

        }
    }


    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
       /* if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }
}
