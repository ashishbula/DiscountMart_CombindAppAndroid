package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.android.material.snackbar.Snackbar;
import in.discountmart.R;
import in.discountmart.business.adapter.MyPurchaseDetailAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyShoppingServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.MyPurchaseDetailResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPurchaseDetailFragment extends Fragment {

    Context context;
    View view;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    LinearLayout layoutTotal;
    RecyclerView recyclerView;
    ProgressDialog pDialog;
    MyPurchaseDetailAdapter detailAdapter;
    ArrayList<MyPurchaseDetailResponse.MyPurchaseDetail> detailArrayList;


    public MyPurchaseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.business_fragment_my_purchase_detail, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);

            recyclerView=(RecyclerView)mainView.findViewById(R.id.mypurchase_detail_frag_recycler);
            layoutNoRecord=(LinearLayout)mainView.findViewById(R.id.mypurchase_detail_frag_layout_noRecord);
            layoutRecord=(LinearLayout)mainView.findViewById(R.id.mypurchase_detail_frag_layout_Record);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);

            if (!ConnectivityUtils.isNetworkEnabled(context)) {
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {

                //new getDownlineLeftDetails().execute();
                getComplaintReplyDetail();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  mainView;
    }

    /* Get Complaint Detail Api*/
    private void getComplaintReplyDetail(){

        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest Request = new BaseRequest();

        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_MYPURCHASE_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
       // Request.setComplaintid(strComplaintId);


        Call<MyPurchaseDetailResponse> downlineDetailResponseCall
                = NetworkClient1.getInstance(context).create(MyShoppingServices.class).fetchPurchaseDetail(Request);

        downlineDetailResponseCall.enqueue(new Callback<MyPurchaseDetailResponse>() {
            @Override
            public void onResponse(Call<MyPurchaseDetailResponse> call, Response<MyPurchaseDetailResponse> response) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                try {

                    MyPurchaseDetailResponse detailResponse=new MyPurchaseDetailResponse();
                    detailResponse=response.body();

                    if(detailResponse != null){
                        if (detailResponse.getResponse().equals("OK")) {

                            if(detailResponse.getMypurchase() != null && detailResponse.getMypurchase().size() > 0){

                                layoutNoRecord.setVisibility(View.GONE);
                                layoutRecord.setVisibility(View.VISIBLE);
                                detailArrayList = detailResponse.getMypurchase();

                                detailAdapter=new MyPurchaseDetailAdapter(detailArrayList,context);
                                recyclerView.setAdapter(detailAdapter);
                                detailAdapter.notifyDataSetChanged();

                            }
                            else {

                                layoutNoRecord.setVisibility(View.VISIBLE);
                                layoutRecord.setVisibility(View.GONE);
                                //detailArrayList = detailResponse.getMypurchase();
                               // detailArrayList.clear();
                               // detailAdapter=new MyPurchaseDetailAdapter(detailArrayList,context);
                                //recyclerView.setAdapter(detailAdapter);
                                //detailAdapter.notifyDataSetChanged();
                            }




                        }
                        else {


                            String toast = detailResponse.getResponse()+ ":"+detailResponse.getMsg();
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
                        String toast = "Something went wrong..";
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
            public void onFailure(Call<MyPurchaseDetailResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
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

}
