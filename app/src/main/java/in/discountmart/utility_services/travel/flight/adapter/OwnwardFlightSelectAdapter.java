package in.discountmart.utility_services.travel.flight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.OwnwardFlightSearch;


public class OwnwardFlightSelectAdapter extends RecyclerView.Adapter<OwnwardFlightSelectAdapter.MyViewHolder> {

    private Context mContext;
    private List<OwnwardFlightSearch> ownflightList;
    private List<OwnwardFlightSearch.OwnFlightSegment> ownflightSegmentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtFlightName;
    public TextView txtFlightCode;
    public TextView txtDDate;
    public TextView txtDDay;
    public TextView txtDTime;
    public TextView txtFromCityCode;

    public TextView txtADate;
    public TextView txtADay;
    public TextView txtATime;
    public TextView txtToCity;

    public TextView txtTotalTime;
    public TextView txtStop;
    public TextView txtRefund;

    public TextView txtPrice;
    public TextView txtSeat;
    LinearLayout layoutLayHour;
    LinearLayout layoutPolicy;
    LinearLayout layoutNots;
    public TextView txtLayhour;
    public TextView txtNots;
    public ImageView imgFlightLogo;

    public MyViewHolder(View view) {
        super(view);

        imgFlightLogo=(ImageView)view.findViewById(R.id.own_flight_select_item_img_flight_logo);

        txtFlightName = view.findViewById(R.id.own_flight_select_list_item_txt_flight_name);
        txtFlightCode = view.findViewById(R.id.own_flight_select_list_item_txt_flight_code);
        txtDDate = view.findViewById(R.id.own_flight_select_list_item_txt_departure_date);
        txtDDay = view.findViewById(R.id.own_flight_select_list_item_txt_departure_day);
        txtDTime = view.findViewById(R.id.own_flight_select_list_item_txt_departure_time);
        txtFromCityCode = view.findViewById(R.id.own_flight_select_list_item_txt_from_city);
        txtADate = view.findViewById(R.id.own_flight_select_list_item_txt_arrive_date);
        txtADay = view.findViewById(R.id.own_flight_select_list_item_txt_arrive_day);
        txtATime = view.findViewById(R.id.own_flight_select_list_item_txt_arriv_time);
        txtToCity = view.findViewById(R.id.own_flight_select_list_item_txt_to_city);
        txtTotalTime = view.findViewById(R.id.own_flight_select_list_item_txt_total_hour);
        txtStop = view.findViewById(R.id.own_flight_select_list_item_txt_stop);
        txtRefund = view.findViewById(R.id.own_flight_select_list_item_txt_refundable);
        txtLayhour = view.findViewById(R.id.own_flight_select_list_item_txt_layhour);
        txtNots = view.findViewById(R.id.own_flight_select_list_item_txt_note);
        layoutLayHour=(LinearLayout)view.findViewById(R.id.own_flight_select_list_item_layout_layhour);
        layoutNots=(LinearLayout)view.findViewById(R.id.own_flight_select_list_item_layout_notes);
        layoutPolicy=(LinearLayout)view.findViewById(R.id.own_flight_select_item_layout_cancelpolicy);
        //txtSeat = view.findViewById(R.id.flight_search_list_item_txt_avail_seat);


/*
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(flightSearchList.get(getAdapterPosition()));
                }
            });*/
    }
}

public OwnwardFlightSelectAdapter(Context context, List<OwnwardFlightSearch> flightList, List<OwnwardFlightSearch.OwnFlightSegment> segmentList ) {
        this.mContext = context;
        //this.listener = listener;
        this.ownflightList = flightList;
        this.ownflightSegmentList = segmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_ownward_flight_select_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (holder != null) {

            if(ownflightSegmentList.size() == 0){

                holder.layoutNots.setVisibility(View.GONE);
                holder.layoutLayHour.setVisibility(View.GONE);
                Picasso.with(mContext).load(ownflightList.get(position).getAirlineLogo()).into(holder.imgFlightLogo);

                holder.txtFlightName.setText(ownflightList.get(position).getAirlineName());
                holder.txtFlightCode.setText(ownflightList.get(position).getAirlineCode()+ "-" +ownflightList.get(position).getFlightNumber());

                String[] strDepartDate=ownflightList.get(position).getDepartureDateTime().split(",");
                holder.txtDDate.setText(strDepartDate[1]);
                holder.txtDDay.setText(strDepartDate[0]);

                holder.txtDTime.setText(ownflightList.get(position).getDepartureTime());
                holder.txtFromCityCode.setText(ownflightList.get(position).getOrigin());

                String[] strArriveDate=ownflightList.get(position).getDepartureDateTime().split(",");
                holder.txtADate.setText(strArriveDate[1]);
                holder.txtADay.setText(strArriveDate[0]);

                holder.txtATime.setText(ownflightList.get(position).getArrivaltime());
                holder.txtToCity.setText(ownflightList.get(position).getDestination());

                holder.txtStop.setText(ownflightList.get(position).getStopage());
                holder.txtRefund.setText(ownflightList.get(position).getFairType());
                holder.txtTotalTime.setText(ownflightList.get(position).getDifferenceTime());
                holder.layoutPolicy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            else if(ownflightSegmentList.size() > 0) {

                if(ownflightSegmentList.get(position) == (ownflightSegmentList.get(getItemCount()-1))){
                    holder.layoutNots.setVisibility(View.GONE);
                    holder.layoutLayHour.setVisibility(View.GONE);
                }
                else {
                    holder.layoutNots.setVisibility(View.VISIBLE);
                    holder.layoutLayHour.setVisibility(View.VISIBLE);
                }


                Picasso.with(mContext).load(ownflightSegmentList.get(position).getAirlineLogo()).into(holder.imgFlightLogo);

                holder.txtFlightName.setText(ownflightSegmentList.get(position).getAirlineName());
                holder.txtFlightCode.setText(ownflightSegmentList.get(position).getAirlineCode()+ "-"
                        +ownflightSegmentList.get(position).getFlightNumber());

                String[] strDepartDate=ownflightSegmentList.get(position).getDepartureDateTime().split(",");
                holder.txtDDate.setText(strDepartDate[1]);
                holder.txtDDay.setText(strDepartDate[0]);
                holder.txtDTime.setText(ownflightSegmentList.get(position).getDepartureTime());

                holder.txtFromCityCode.setText(ownflightSegmentList.get(position).getOrigin());

                String[] strArriveDate=ownflightSegmentList.get(position).getDepartureDateTime().split(",");
                holder.txtADay.setText(strArriveDate[0]);
                holder.txtADate.setText(strArriveDate[1]);
                holder.txtATime.setText(ownflightSegmentList.get(position).getArrivaltime());
                holder.txtToCity.setText(ownflightSegmentList.get(position).getDestination());

                holder.txtStop.setText(ownflightSegmentList.get(position).getStopage());
                holder.txtRefund.setText(ownflightSegmentList.get(position).getFairType());
                holder.txtTotalTime.setText(ownflightSegmentList.get(position).getDifferenceTime());
            }
        }
    }

    @Override
    public int getItemCount() {
    if(ownflightSegmentList.size() == 0)
        return ownflightList == null ? 0 : ownflightList.size();
    else
        return ownflightSegmentList == null ? 0 : ownflightSegmentList.size(); }


}
