package in.discountmart.business.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import in.discountmart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayoutSummeryFragment extends Fragment {
    Context context;
    View view;
    public PayoutSummeryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.business_fragment_payout_summery, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);

        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;
    }

}
