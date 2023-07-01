package in.discountmart.utility_services.report;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import in.discountmart.R;
import in.discountmart.utility_services.report.activity.BusBookReportActivity;
import in.discountmart.utility_services.report.activity.CabBookReportActivity;
import in.discountmart.utility_services.report.activity.FlightBookReportActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelReportFragment extends BottomSheetDialogFragment {

    LinearLayout layoutFlight;
    LinearLayout layoutBus;
    LinearLayout layoutCab;
    LinearLayout layoutHotel;

    View view;
    Context context;

    public TravelReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.utility_fragment_travel_report_list_item, container, false);
        try {
            context=getActivity();
            layoutBus=(LinearLayout)mainView.findViewById(R.id.travel_report_frag_layout_bus);
            layoutFlight=(LinearLayout)mainView.findViewById(R.id.travel_report_frag_layout_flight);
            layoutHotel=(LinearLayout)mainView.findViewById(R.id.travel_report_frag_layout_hotel);
            layoutCab=(LinearLayout)mainView.findViewById(R.id.travel_report_frag_layout_cab);

            /* Bus report */
            layoutBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent busIntent=new Intent(context, BusBookReportActivity.class);
                    context.startActivity(busIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    dismiss();
                }
            });

            /* Flight report */
            layoutFlight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent flghtIntent=new Intent(context, FlightBookReportActivity.class);
                    context.startActivity(flghtIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    dismiss();
                }
            });

            /* Cab report */
            layoutCab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cabIntent=new Intent(context, CabBookReportActivity.class);
                    context.startActivity(cabIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    dismiss();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return  mainView;
    }

}
