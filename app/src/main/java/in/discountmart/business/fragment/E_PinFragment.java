package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import in.base.network.NetworkClient;
import in.base.ui.BaseFragment;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.adapter.PackageListAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.PackageListResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_PinFragment extends BaseFragment {
    Spinner spinnerPackage;
    Button btnsubmit;
    RadioGroup rgpakage;
    RadioButton rbused;
    RadioButton rbunused;
    ProgressDialog pDialog;
    String stringPkgid;
    String stringRbtnValue;
    Context context;

    String stringSpinPackageId;
    String stringSpinPackageName;
    String strApiKey="";

    Boolean msgShown = false;

    int from;
    int to ;

    int check=0;

    PackageListResponse.PackageList packageList[];
    ArrayList<PackageListResponse.PackageList> packageListArrayList;
    PackageListAdapter packageListAdapter;

    public E_PinFragment(){
        //empty constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View rootView = inflater.inflate(R.layout.business_epin_fragment, container, false);
        context = getActivity();
        try {
            spinnerPackage=(Spinner)rootView.findViewById(R.id.epin_fragment_spin_package);
            btnsubmit=(Button)rootView.findViewById(R.id.epin_frag_btn_submit);
            rgpakage=(RadioGroup)rootView.findViewById(R.id.epin_frag_radiogroup);
            rbused=(RadioButton)rootView.findViewById(R.id.epin_frag_radiobt_used);
            rbunused=(RadioButton)rootView.findViewById(R.id.epin_frag_radiobt_unused);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            /*Call Package List APi*/
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                getPackageList();
                // new getPackageListDetails().execute();
            }

            /*Spinner Package List */
            spinnerPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // check = check + 1;
                    //if (check > 1)
                    // Get selected operator entity
                    PackageListResponse.PackageList packageList1 = (PackageListResponse.PackageList) parent.getItemAtPosition(position);
                    /*if(packageList1.getPkgid().equals("")){
                        Toast.makeText(getActivity(),"Please Id not Available",Toast.LENGTH_SHORT).show();
                    }*/

                    stringSpinPackageId=packageList1.getPkgid();
                    stringSpinPackageName=packageList1.getPkgname();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            /*Button Submit clicl listner*/
            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int selectedreg =rgpakage .getCheckedRadioButtonId();

                    stringRbtnValue = getAtmos( selectedreg );
                    Bundle args = new Bundle();
                    args.putString("pkgid", stringSpinPackageId);

                    args.putString("checkPin", stringRbtnValue);

                    EpindetailFragment epinSummeryFragment=new EpindetailFragment();
                    epinSummeryFragment.setArguments(args);
                    ((BusinessDashboardActivity)context).replaceFragment_business(epinSummeryFragment, AppConstants.E_PINDETAIL,null);


              /*if(rbunused.isChecked()) {

                  UnusedepinFragment unusedepinFragment = new UnusedepinFragment();
                  FragmentTransaction business_transaction = getActivity().getSupportFragmentManager().beginTransaction();
                  // Replace whatever is in the fragment_container view with this fragment,
                  // and add the business_transaction to the back stack so the user can navigate back
                  business_transaction.replace(R.id.content_frame, unusedepinFragment);
                  business_transaction.addToBackStack(null);
                  // Commit the business_transaction
                  business_transaction.commit();
                  unusedepinFragment.setArguments(args);
              }*/
               /*if(rbused.isChecked()) {

                   UsedepinFragment usedepinFragment = new UsedepinFragment();
                   FragmentTransaction business_transaction = getActivity().getSupportFragmentManager().beginTransaction();
                   // Replace whatever is in the fragment_container view with this fragment,
                   // and add the business_transaction to the back stack so the user can navigate back
                   business_transaction.replace(R.id.content_frame, usedepinFragment);
                   business_transaction.addToBackStack(null);
                   // Commit the business_transaction
                   business_transaction.commit();
                   usedepinFragment.setArguments(args);
               }*/
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



        return rootView;
    }

    private String getAtmos(int id ) {
        switch( id ) {
            case R.id.epin_frag_radiobt_used:
                stringRbtnValue = "U";
                break;
            case R.id.epin_frag_radiobt_unused:
                stringRbtnValue = "N";
                break;



        }

        return stringRbtnValue;
    }

    /*Get Package List Api*/
    private void getPackageList(){
        showProgressDialog();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.REQUEST_EPIN_PACKAGE_LIST;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));


        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Parameter=gson.toJson(baseRequest);
        Log.e("RequestPackageList:", Parameter);

        Call<PackageListResponse> listResponseCall=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchPackageList(baseRequest,strApiKey);

        listResponseCall.enqueue(new Callback<PackageListResponse>() {
            @Override
            public void onResponse(Call<PackageListResponse> call, Response<PackageListResponse> response) {
                hideProgressDialog();
                PackageListResponse listResponse=new PackageListResponse();
                listResponse=response.body();
                try {

                    if (listResponse.getResponse().equals("OK")) {
                        packageList = listResponse.getPackages();
                        if(packageList != null && packageList.length > 0) {
                            packageListArrayList = new ArrayList<PackageListResponse.PackageList>(Arrays.asList(packageList));
                            packageListAdapter = new PackageListAdapter(getActivity(), packageListArrayList);
                            spinnerPackage.setAdapter(packageListAdapter);
                        }
                        else {
                            Toast.makeText(context, "Package List Is Empty", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(context, listResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PackageListResponse> call, Throwable t) {

                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }
}
