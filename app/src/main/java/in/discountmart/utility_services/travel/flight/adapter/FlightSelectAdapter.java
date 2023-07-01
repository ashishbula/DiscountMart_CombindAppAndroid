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
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.FlightSearchResponse;

public class FlightSelectAdapter extends RecyclerView.Adapter<FlightSelectAdapter.MyViewHolder> {


    private Context mContext;
    private List<FlightSearchResponse> flightSearchList;
    private List<FlightSearchResponse.FlightSearchSegment> flightSearchSegmentList;


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

            imgFlightLogo=(ImageView)view.findViewById(R.id.flight_select_item_img_flight_logo);

            txtFlightName = view.findViewById(R.id.flight_select_list_item_txt_flight_name);
            txtFlightCode = view.findViewById(R.id.flight_select_list_item_txt_flight_code);
            txtDDate = view.findViewById(R.id.flight_select_list_item_txt_departure_date);
            txtDDay = view.findViewById(R.id.flight_select_list_item_txt_departure_day);
            txtDTime = view.findViewById(R.id.flight_select_list_item_txt_departure_time);
            txtFromCityCode = view.findViewById(R.id.flight_select_list_item_txt_from_city);
            txtADate = view.findViewById(R.id.flight_select_list_item_txt_arrive_date);
            txtADay = view.findViewById(R.id.flight_select_list_item_txt_arrive_day);
            txtATime = view.findViewById(R.id.flight_select_list_item_txt_arriv_time);
            txtToCity = view.findViewById(R.id.flight_select_list_item_txt_to_city);
            txtTotalTime = view.findViewById(R.id.flight_select_list_item_txt_total_hour);
            txtStop = view.findViewById(R.id.flight_select_list_item_txt_stop);
            txtRefund = view.findViewById(R.id.flight_select_list_item_txt_refundable);
            txtLayhour = view.findViewById(R.id.flight_select_list_item_txt_layhour);
            txtNots = view.findViewById(R.id.flight_select_list_item_txt_note);
            layoutLayHour=(LinearLayout)view.findViewById(R.id.flight_select_list_item_layout_layhour);
            layoutNots=(LinearLayout)view.findViewById(R.id.flight_select_list_item_layout_notes);
            layoutPolicy=(LinearLayout)view.findViewById(R.id.flight_select_item_layout_cancelpolicy);
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


    public FlightSelectAdapter(Context context, List<FlightSearchResponse> flightList,List<FlightSearchResponse.FlightSearchSegment> segmentList ) {
        this.mContext = context;
        //this.listener = listener;
        this.flightSearchList = flightList;
        this.flightSearchSegmentList = segmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_flight_select_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (holder != null) {


//            final FlightSearchResponse contact = flightSearchList.get(position);
            if(flightSearchSegmentList.size() == 0){

                holder.layoutNots.setVisibility(View.GONE);
                holder.layoutLayHour.setVisibility(View.GONE);
                Picasso.with(mContext).load(flightSearchList.get(position).getAirlineLogo()).into(holder.imgFlightLogo);

                holder.txtFlightName.setText(flightSearchList.get(position).getAirlineName());
                holder.txtFlightCode.setText(flightSearchList.get(position).getAirlineCode()+ "-" +flightSearchList.get(position).getFlightNumber());

                holder.txtDDate.setText(flightSearchList.get(position).getDepartureDateTime());
                holder.txtDTime.setText(flightSearchList.get(position).getDepartureTime());
                holder.txtFromCityCode.setText(flightSearchList.get(position).getOrigin());

                holder.txtADate.setText(flightSearchList.get(position).getArrivalDatetime());
                holder.txtATime.setText(flightSearchList.get(position).getArrivaltime());
                holder.txtToCity.setText(flightSearchList.get(position).getDestination());

                holder.txtStop.setText(flightSearchList.get(position).getStopage());
                holder.txtRefund.setText(flightSearchList.get(position).getFairType());
                holder.txtTotalTime.setText(flightSearchList.get(position).getDifferenceTime());
                holder.layoutPolicy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            else if(flightSearchSegmentList.size() > 0) {

                if(flightSearchSegmentList.get(position) == (flightSearchSegmentList.get(getItemCount()-1))){
                    holder.layoutNots.setVisibility(View.GONE);
                    holder.layoutLayHour.setVisibility(View.GONE);
                }
                else {
                    holder.layoutNots.setVisibility(View.VISIBLE);
                    holder.layoutLayHour.setVisibility(View.VISIBLE);
                }


                Picasso.with(mContext).load(flightSearchSegmentList.get(position).getAirlineLogo()).into(holder.imgFlightLogo);

                    holder.txtFlightName.setText(flightSearchSegmentList.get(position).getAirlineName());
                    holder.txtFlightCode.setText(flightSearchSegmentList.get(position).getAirlineCode()+ "-"
                            +flightSearchSegmentList.get(position).getFlightNumber());


                    holder.txtDDate.setText(flightSearchSegmentList.get(position).getDepartureDateTime());
                    holder.txtDTime.setText(flightSearchSegmentList.get(position).getDepartureTime());
                    holder.txtFromCityCode.setText(flightSearchSegmentList.get(position).getOrigin());

                    holder.txtADate.setText(flightSearchSegmentList.get(position).getArrivalDatetime());
                    holder.txtATime.setText(flightSearchSegmentList.get(position).getArrivaltime());
                    holder.txtToCity.setText(flightSearchSegmentList.get(position).getDestination());

                    holder.txtStop.setText(flightSearchSegmentList.get(position).getStopage());
                    holder.txtRefund.setText(flightSearchSegmentList.get(position).getFairType());
                    holder.txtTotalTime.setText(flightSearchSegmentList.get(position).getDifferenceTime());


            }


            //holder.txtPrice.setText(mContext.getResources().getString(R.string.rs_symbol) +flightSearchList.get(position).getGrossAmount());

            String strSeat="Seat Available";
           /* int seat= Integer.parseInt(flightSearchList.get(position).getAvailableSeat());
            if(seat >= 5){
                holder.txtSeat.setTextColor(mContext.getResources().getColor(R.color.yellow11));
            }
            else {
                holder.txtSeat.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            holder.txtSeat.setText(flightSearchList.get(position).getAvailableSeat()+ " Seat Left");*/
        }



       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {

        if(flightSearchSegmentList.size() == 0)
            return flightSearchList == null ? 0 : flightSearchList.size();
        else
            return flightSearchSegmentList == null ? 0 : flightSearchSegmentList.size();


    }

    public interface FlightListAdapterListener {
        void onContactSelected(FlightSearchResponse contact);

    }
}
