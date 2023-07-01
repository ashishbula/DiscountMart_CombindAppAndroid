package in.discountmart.business.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient1;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.adapter.NewsAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.MyTeamService;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.NewsResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsEventFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressDialog pDialog;
    Button buttonSubmit;
    View view;
    LinearLayout layoutRecord;
    LinearLayout layoutNoRecord;
    Context context;
    ArrayList<NewsResponse.NewsList> newsListArrayList;
    NewsAdapter adapter;

    String strApiKey="";

    public NewsEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.business_fragment_news_event, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            recyclerView=(RecyclerView)rootView.findViewById(R.id.news_frag_recycler);

            layoutRecord=(LinearLayout)rootView.findViewById(R.id.news_frag_layout_record);
            layoutNoRecord=(LinearLayout)rootView.findViewById(R.id.news_frag_layout_noRecord);

            recyclerView.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            //DesignationLeaderRes.DesignationLeader arrList[];


            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            //newsListArrayList=new ArrayList<NewsResponse.NewsList>();
            //newsListArrayList.clear();

            /*Call api*/
            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                //new getPackageListDetails().execute();
                getNewList();
            }




        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }

    /*Get Package List Api Response*/
    private void getNewList(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();
        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.REQUEST_NEWS;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Parameter = gson.toJson(baseRequest);
        Log.e("RequestNews:", Parameter);

        Call<NewsResponse> listResponseCall=
                NetworkClient1.getInstance(context).create(MyTeamService.class).fetchNews(baseRequest,strApiKey);

        listResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                NewsResponse listResponse=new NewsResponse();
                try {



                    listResponse = response.body();

                    if(listResponse != null){
                        if (listResponse.getResponse().equals("OK")) {

                            if(listResponse.getNews() != null && listResponse.getNews().size() > 0) {

                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutNoRecord.setVisibility(View.GONE);
                                newsListArrayList=new ArrayList<NewsResponse.NewsList>();
                                newsListArrayList=listResponse.getNews();
                                adapter =new NewsAdapter(newsListArrayList,context);
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                layoutRecord.setVisibility(View.VISIBLE);
                                layoutNoRecord.setVisibility(View.GONE);
                            }
                        }
                        else {
                            String toast= listResponse.getResponse()+ " "+"Something went wrong..";
                            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        String toast="Something wrong.. may be server error";
                        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
