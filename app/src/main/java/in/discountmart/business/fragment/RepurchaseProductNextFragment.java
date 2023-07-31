package in.discountmart.business.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.adapter.SelectProductAdapter;
import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepurchaseProductNextFragment extends Fragment {

    Context context;
    View view;
    RecyclerView recyclerView;
    SelectProductAdapter adapter;
    ArrayList<AddProductModel> selectProdtList;

    public RepurchaseProductNextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.fragment_repurchase_product_next, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);

            recyclerView=(RecyclerView)mainView.findViewById(R.id.repurchase_prodt_next_recycler_list);
            // layoutFooter.setVisibility(View.GONE);

            /*Get Selected Product List from shared preference*/

            selectProdtList = new ArrayList<AddProductModel>();
            selectProdtList=  SharedPrefrence_Business.getInstance().addProductList;

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));

            adapter = new SelectProductAdapter(context, selectProdtList);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;
    }

}
