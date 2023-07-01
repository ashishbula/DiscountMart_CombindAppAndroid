package in.discountmart.utility_services.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.adapter.PromocodeAdapter;
import in.discountmart.utility_services.model.response_model.PromocodeRespose;
import in.discountmart.utility_services.utilities.ConnectivityUtils;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class PromoCodeDialog extends DialogFragment implements PromocodeAdapter.PromoCodeAdapterListener {

    RecyclerView recyViewPromo;
    PromocodeAdapter promocodeAdapter;
    ProgressDialog progressDialog;
    ArrayList<PromocodeRespose> promocodeList;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            context=getActivity();

            view = inflater.inflate(R.layout.utility_dialog_promocode, container, false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
            getDialog().getWindow().setTitle("Apply Promocode");

            recyViewPromo=(RecyclerView)view.findViewById(R.id.dialog_promocode_recycler);

            promocodeList = new ArrayList<PromocodeRespose>();
            promocodeAdapter = new PromocodeAdapter(context, promocodeList, (PromocodeAdapter.PromoCodeAdapterListener) getActivity());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyViewPromo.setLayoutManager(mLayoutManager);
            recyViewPromo.setItemAnimator(new DefaultItemAnimator());
            recyViewPromo.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST,10));
            recyViewPromo.setAdapter(promocodeAdapter);

            /*Call Api Promocode List*/

            if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                Toast.makeText(getActivity(),getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            }
            else {
                //getPromocodeList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }



    @Override
    public void onPromoSelected(PromocodeRespose promocode) {

        try {
            if(promocode != null){

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
