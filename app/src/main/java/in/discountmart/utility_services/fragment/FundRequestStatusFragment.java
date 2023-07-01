package in.discountmart.utility_services.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import in.discountmart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FundRequestStatusFragment extends Fragment {


    public FundRequestStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.utility_fragment_fund_request_status, container, false);
    }

}
