package in.discountmart.utility_services.travel.bus.bus_fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusBoardingPointAdapter;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusDropPointAdapter;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class Boarding_DropPointFragment extends Fragment {

    TextView txtBoardPoint;
    TextView txtDropPoint;
    TextView txtFromCity;
    TextView txtToCity;
    LinearLayout layoutBoarding;
    LinearLayout layoutDrop;
    LinearLayout layoutSelectSeat;

    RecyclerView recyClerDroping;
    RecyclerView recyClerBoarding;
    Context context;

    BusSearchListResponse busSearchListResponse;
    ArrayList<BusSearchListResponse.BoardingPoints> boardingPointsArrayList;
    ArrayList<BusSearchListResponse.DropingPoints> dropingPointsArrayList;
    BusDropPointAdapter dropPointAdapter;
    BusBoardingPointAdapter boardingPointAdapter;

    public Boarding_DropPointFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.utility_fragment_boarding_drop_point, container, false);
        try {
            context=getActivity();

            txtBoardPoint=(TextView)mainView.findViewById(R.id.droppoint_frag_txt_board_point);
            txtDropPoint=(TextView)mainView.findViewById(R.id.droppoint_frag_txt_drop_point);
            txtToCity=(TextView)mainView.findViewById(R.id.droppoint_frag_txt_tocity);
            txtFromCity=(TextView)mainView.findViewById(R.id.droppoint_frag_txt_fromcity);
            recyClerBoarding=(RecyclerView) mainView.findViewById(R.id.droppoint_frag_recycler_boarding);
            recyClerDroping=(RecyclerView) mainView.findViewById(R.id.droppoint_frag_recycler_drop);
            layoutBoarding=(LinearLayout)mainView.findViewById(R.id.droppoint_frag_layout_boarding);
            layoutDrop=(LinearLayout)mainView.findViewById(R.id.droppoint_frag_layout_drop);
            layoutSelectSeat=(LinearLayout)mainView.findViewById(R.id.droppoint_frag_layout_select_seat);

            Bundle bundle=getArguments();
            busSearchListResponse=new BusSearchListResponse();
            if(bundle != null){
                BusSearchListResponse busSearch=(BusSearchListResponse)bundle.getSerializable("BusSearch");
                if (busSearch != null){

                    busSearchListResponse=busSearch;
                }
            }

            if(busSearchListResponse != null){
                boardingPointsArrayList=new ArrayList<BusSearchListResponse.BoardingPoints>(busSearchListResponse.getBoardingPoint());
                dropingPointsArrayList=new ArrayList<BusSearchListResponse.DropingPoints>(busSearchListResponse.getDroppingPoint());

            }

            layoutBoarding.setVisibility(View.VISIBLE);
            layoutDrop.setVisibility(View.GONE);

            /*Recycler Boarding Point*/
            recyClerBoarding.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyClerBoarding.setItemAnimator(new DefaultItemAnimator());
            recyClerBoarding.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST,0));
            boardingPointAdapter=new BusBoardingPointAdapter(getActivity(),boardingPointsArrayList);
            recyClerBoarding.setAdapter(boardingPointAdapter);

            /*Recycler Drop Point*/
            recyClerDroping.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyClerDroping.setItemAnimator(new DefaultItemAnimator());
            recyClerDroping.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST,0));
            dropPointAdapter=new BusDropPointAdapter(getActivity(),dropingPointsArrayList);
            //recyClerDroping.setAdapter(dropPointAdapter);

            /*Text Bording Point on click*/
            txtBoardPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutBoarding.setVisibility(View.VISIBLE);
                    layoutDrop.setVisibility(View.GONE);
                    boardingPointAdapter.setBoardPointData(boardingPointsArrayList);
                    recyClerBoarding.setAdapter(boardingPointAdapter);
                }
            });

            //Text Bording Point on click
            txtDropPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    layoutBoarding.setVisibility(View.GONE);
                    layoutDrop.setVisibility(View.VISIBLE);
                    if(dropingPointsArrayList.size() > 0) {
                        dropPointAdapter.setDropPointData(dropingPointsArrayList);
                        recyClerDroping.setAdapter(dropPointAdapter);
                    }
                }
            });

           /* *//*chekc seat is select or not*//*
            if(BusSeatsActivity.arrayListSeatName.size() > 0 && BusSeatsActivity.arrayListSeatFare.size() > 0){
                layoutSelectSeat.setVisibility(View.GONE);

            }
            else {
                layoutSelectSeat.setVisibility(View.VISIBLE);
            }*/
            /*Button Select Seat on click*/
           /* layoutSelectSeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle =new Bundle();
                    BusSeatsFragment fragment=new BusSeatsFragment();
                    bundle.putString("SelectionType","Seat");
                    fragment.setArguments(bundle);
                    ((BusSeatsActivity)context).replaceFragment1(fragment,"SeatFragment",bundle);
                }
            });*/


        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;
    }

}
