package in.discountmart.utility_services.recharge.fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.base.network.NetworkClient_Utility_1;
import in.discountmart.activity.LoginActivity;
import in.discountmart.R;
import in.discountmart.utility_services.model.response_model.BaseResponse;
import in.discountmart.utility_services.recharge.call_recharge_api.RechargeApi;
import in.discountmart.utility_services.recharge.recharge_model.recharge_request_model.CircleRequest;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.CircleListResponse;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CircleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CircleListFragment extends BottomSheetDialogFragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Listener mListener;

    View view;
    Context context;
    LinearLayout layoutHeader;
    RecyclerView recyclerViewList;
    CircleItemAdapter adapter;
    ArrayList<CircleListResponse> circleList;
    public static TextView txtAge1;
    public static TextView txtAge2;

    ProgressDialog progressDialog;
    String type="";
    String home="";
    String serviceType="";
    String serviceTypeId="";
    LinearLayout bottomSheet;
    BottomSheetBehavior sheetBehavior;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CircleListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CilcleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CircleListFragment newInstance(String param1) {
        CircleListFragment fragment = new CircleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.utility_frag_cilcle_list, container, false);

        try {
            view = getActivity().findViewById(android.R.id.content);
            context=getActivity();
            //layoutHeader = (LinearLayout) findViewById(R.id.service_provider_act_layout_header);
            recyclerViewList = (RecyclerView)  mainView.findViewById(R.id.circle_frag_recycler_list);
           // bottomSheet=(LinearLayout)mainView.findViewById(R.id.circle_act_bottom_sheet);
            //sheetBehavior = BottomSheetBehavior.from(bottomSheet);


            //if (serviceProviderArrayList.size() > 0) {
            circleList = new ArrayList<CircleListResponse>();
            adapter = new CircleItemAdapter(context, circleList);
            // }
            type=mParam1;

            /*REcycler View set divider item line*/
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerViewList.setLayoutManager(mLayoutManager);
            recyclerViewList.setItemAnimator(new DefaultItemAnimator());
            recyclerViewList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST, 0));
            recyclerViewList.setAdapter(adapter);
            /*Call api */

            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Snackbar.make(view, getResources().getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();

            }
            else
            {

                getCircleListForPrepaid();
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
    public interface Listener {
        void onItemSelect(CircleListResponse item);
    }



    /* Request and Response Service Provider List*/
    public void getCircleListForPrepaid() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        CircleRequest request = new CircleRequest();
        try {
            request.setAuthKey("");
            request.setClientID("");
            request.setServiceType(type);

            String strApiRequest = new Gson().toJson(request);

            Timber.e(strApiRequest,"CircleListRequest");
            Log.e("Value", strApiRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<BaseResponse> fetchServiceProviderListCall =
                NetworkClient_Utility_1.getInstance(context).create(RechargeApi.class).fetchCircleListForPrepaid(request);

        fetchServiceProviderListCall.enqueue(new Callback<BaseResponse>() {
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
                                //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                                Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            } else if (Response.getRESP_VALUE() != null || !Response.getRESP_VALUE().equals("")) {
                                String[] serviceListResponse = Response.getRESPONSE().split("");
                                if (serviceListResponse.length > 0) {
                                    CircleListResponse[] serviceList = new Gson().fromJson(Response.getRESP_VALUE(), CircleListResponse[].class);
                                    List<CircleListResponse> serviceArrayList = new ArrayList<CircleListResponse>(Arrays.asList(serviceList));

                                    // adding contacts to contacts list
                                    circleList.clear();
                                    circleList.addAll(serviceArrayList);

                                    // refreshing recycler view
                                    adapter.notifyDataSetChanged();
                                } else {
                                    String toast = " City List empty";
                                    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                    //showSnackbar(toast);
                                }
                            }
                        }
                        else if (Response.getRESP_CODE().equals("-1") && Response.getRESPONSE().equals("ERROR")) {

                            String toast = Response.getRESPONSE() + ":" + Response.getRESP_MSG();
                            //Toast.makeText(FlightCityListActivity.this, toast, Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

                        }
                        else if (Response.getRESP_CODE().equals("401") && Response.getRESPONSE().equals("FAILED")) {
                            String toast = Response.getRESPONSE() + "\n" + "Please Login Again. ";
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            // getActivity().finish();
                            dismiss();

                        }
                        else {

                            String toast= Response.getRESPONSE()+ ":" +Response.getRESP_MSG();
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                            // showSnackbar(toast);

                        }
                    }
                    else {
                        //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                        String toast = "something wrong";
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
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // Toast.makeText(FlightCityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
    /*Adapter that list item bind and show*/

    private class CircleItemAdapter extends RecyclerView.Adapter<CircleItemAdapter.ViewHolder> {

        public Context mContext;
        public ArrayList<CircleListResponse> serviceList;

        CircleItemAdapter(Context context,ArrayList<CircleListResponse> list) {
            this.serviceList = list;
            this.mContext=context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.utility_single_text_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(serviceList.get(position).getCircleName());
        }

        @Override
        public int getItemCount() {
            return serviceList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

             TextView text;
             ViewHolder(View view) {
                // TODO: Customize the item layout
               // super(inflater.inflate(R.layout.utility_single_text_item, parent, false));
               super(view);
                text = (TextView) view.findViewById(R.id.list_item_txt_name);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onItemSelect(serviceList.get(getAdapterPosition()));
                            dismiss();
                        }
                    }
                });
            }

        }

    }


}