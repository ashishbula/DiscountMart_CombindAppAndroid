package in.discountmart.utility_services.travel.bus.bus_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.model.request_model.ApiRequest;
import in.discountmart.utility_services.model.request_model.BaseHeaderRequest;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusCityListAdapter;
import in.discountmart.utility_services.travel.bus.bus_model.bus_request.BusCityListRequest;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusCityListResponse;
import in.discountmart.utility_services.travel.bus.call_bus_api.BusServiceApi;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import in.discountmart.utility_services.utilities.TokenBase64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusCityListActivity extends AppCompatActivity implements BusCityListAdapter.CityListAdapterListener {

    private RecyclerView recyclerView;
    private List<BusCityListResponse> cityLists;
    private BusCityListAdapter mAdapter;
    private SearchView searchView;
    EditText edtxtSearch;
    ProgressDialog progressDialog;

    String strSelect="";
    String strCityId="";
    int value;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_city_list);

        view=findViewById(android.R.id.content);
        try {
            Toolbar toolbar = findViewById(R.id.bus_city_list_toolbar);
            edtxtSearch=(EditText)findViewById(R.id.bus_city_list_main_content_edtxt_search);
            setSupportActionBar(toolbar);

            // toolbar fancy stuff
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("City List");
            recyclerView = findViewById(R.id.bus_city_list_main_content_recycler_view);

            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                strSelect=bundle.getString("Select");
                strCityId=bundle.getString("CityId");
                value=bundle.getInt("Value");
            }



            cityLists = new ArrayList<>();
            mAdapter = new BusCityListAdapter(BusCityListActivity.this, cityLists, this);

            // white background notification bar
            //whiteNotificationBar(recyclerView);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(BusCityListActivity.this,DividerItemDecoration.VERTICAL_LIST,36));
            recyclerView.setAdapter(mAdapter);

            if(strSelect.equals("From")){
                if(!ConnectivityUtils.isNetworkEnabled(BusCityListActivity.this)){
                    Toast.makeText(BusCityListActivity.this,getResources().getString(R.string.network_error),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    /*Call Api From city List*/
                    getBusFromCityList();
                }

            }
            else if(strSelect.equals("To")){
                if(!ConnectivityUtils.isNetworkEnabled(BusCityListActivity.this)){
                    Toast.makeText(BusCityListActivity.this,getResources().getString(R.string.network_error),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    /*Call Api To city List*/
                    getBusToCityList();
                }
            }

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

    /* Request and Response From Bus City*/
    public void getBusFromCityList(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest="";
        JSONObject object=null;
        String strToken= TokenBase64.getToken();
        LoginResponse loginResponse=new LoginResponse();
        loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);

        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/

            BaseHeaderRequest headerRequest =new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Getcity List Request Model*/
            BusCityListRequest getCityList=new BusCityListRequest();
            getCityList.setName("");
            getCityList.setID("");
            String stringCity= new Gson().toJson(getCityList);
            //JsonElement dataRequest= new Gson().toJsonTree(getCityList);
            //DataRequest dataRequest1=new DataRequest();


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(getCityList);

            strApiRequest=new Gson().toJson(apiRequest);

            Log.e("Value",strApiRequest);
        }catch (Exception e){
            e.printStackTrace();
        }


        Call<BaseResponse> fetchBusFromCityListCall=
                NetworkClient_Utility_1.getInstance(BusCityListActivity.this).create(BusServiceApi.class).fetchBusFromCityList(apiRequest,strToken);

        fetchBusFromCityListCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if(Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()){

                                String toast= Response.getRESP_MSG();
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();
                            }

                            else if(Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")){
                                String[] cityListResponse=Response.getRESPONSE().split("");
                                if(cityListResponse.length > 0){
                                    BusCityListResponse[]cityList= new Gson().fromJson(Response.getRESP_VALUE(),BusCityListResponse[].class);
                                    List<BusCityListResponse>tempList=new ArrayList<BusCityListResponse>(Arrays.asList(cityList));

                                    // adding contacts to contacts list
                                    cityLists.clear();
                                    cityLists.addAll(tempList);

                                    // refreshing recycler view
                                    mAdapter.notifyDataSetChanged();
                                }
                                else {
                                    String toast= " City List empty";
                                    Toast.makeText(BusCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else  if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(BusCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(BusCityListActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(BusCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        String toast = "something wrong..";
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    /* Request and Response To Bus City*/
    public void getBusToCityList(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String strApiRequest="";
        JSONObject object=null;
        String strToken= TokenBase64.getToken();
        LoginResponse loginResponse=new LoginResponse();
        loginResponse=new LoginPreferences_Utility(this).getLoggedinUser();
        String companyId= loginResponse.getSponsorFormNo().substring(0,loginResponse.getSponsorFormNo().length()-2);

        ApiRequest apiRequest = new ApiRequest();
        try {

            /*Main Request Model*/

            BaseHeaderRequest headerRequest =new BaseHeaderRequest();
            headerRequest.setUserName(loginResponse.getUserName());
            headerRequest.setPassword(loginResponse.getPasswd());
            headerRequest.setSponsorFormNo(companyId);
//            if(loginResponse.getMemMode().equals("D"))
//                headerRequest.setSponsorFormNo(companyId);
//            else
//                headerRequest.setSponsorFormNo("");

            /*Getcity List Request Model*/
            BusCityListRequest getCityList=new BusCityListRequest();
            getCityList.setName("");
            getCityList.setID(strCityId);
            String stringCity= new Gson().toJson(getCityList);
            //JsonElement dataRequest= new Gson().toJsonTree(getCityList);
            //DataRequest dataRequest1=new DataRequest();


            apiRequest.setHEADER(headerRequest);
            apiRequest.setDATA(getCityList);

            strApiRequest=new Gson().toJson(apiRequest);

            Log.e("Value",strApiRequest);
        }catch (Exception e){
            e.printStackTrace();
        }


        Call<BaseResponse> fetchBusFromCityListCall=
                NetworkClient_Utility_1.getInstance(BusCityListActivity.this).create(BusServiceApi.class).fetchBusToCityList(apiRequest,strToken);

        fetchBusFromCityListCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                try {

                    BaseResponse Response =new BaseResponse();
                    Response=response.body();

                    if(Response != null){
                        if (Response.getRESP_CODE().equals("0") && Response.getRESPONSE().equals("SUCCESS")) {

                            if(Response.getRESP_VALUE().equals("") || Response.getRESP_VALUE().isEmpty()){

                                String toast= Response.getRESP_MSG();
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();
                            }

                            else if(Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")){
                                String[] cityListResponse=Response.getRESPONSE().split("");
                                if(cityListResponse.length > 0){
                                    BusCityListResponse[]cityList= new Gson().fromJson(Response.getRESP_VALUE(),BusCityListResponse[].class);
                                    List<BusCityListResponse>tempList=new ArrayList<BusCityListResponse>(Arrays.asList(cityList));

                                    // adding contacts to contacts list
                                    cityLists.clear();
                                    cityLists.addAll(tempList);

                                    // refreshing recycler view
                                    mAdapter.notifyDataSetChanged();
                                }
                                else {
                                    String toast= " City List empty";
                                    Toast.makeText(BusCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else  if(Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")){

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(BusCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(BusCityListActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(BusCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        String toast = "something wrong..";
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.utility_search_menu, menu);

        // Associate utility_searchable configuration with the SearchView
       /* SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancle) {
            finish();
            overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);

    }
    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        return true;
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(getResources().getColor(R.color.orange1));
        }
    }

    @Override
    public void onContactSelected(BusCityListResponse list) {

        try {

           // strCityId=list.getID();
            if(list != null){
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("CityId", list.getID());
                bundle.putString("City", list.getName());
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
            }


        }catch (Exception e){
            e.printStackTrace();
        }





        //Toast.makeText(getApplicationContext(), "Selected: " + contact.getCityName() + ", " + contact.getPhone(), Toast.LENGTH_LONG).show();
    }
}
