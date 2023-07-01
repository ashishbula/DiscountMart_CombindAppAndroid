package in.discountmart.utility_services.travel.bus.bus_fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusPolicyFragment extends Fragment {

    RecyclerView recyclerPolicy;
    BusSearchListResponse busSearchListResponse;

    public BusPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.utility_fragment_bus_policy, container, false);

        try {

            recyclerPolicy=(RecyclerView)mainView.findViewById(R.id.bus_policy_frag_recycler);
            Bundle bundle=getArguments();
            busSearchListResponse=new BusSearchListResponse();
            if(bundle != null){
                BusSearchListResponse busSearch=(BusSearchListResponse)bundle.getSerializable("BusSearch");
                if (busSearch != null){

                    busSearchListResponse=busSearch;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;
    }

}
