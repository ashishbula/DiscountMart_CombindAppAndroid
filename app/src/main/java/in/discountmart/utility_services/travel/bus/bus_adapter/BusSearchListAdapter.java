package in.discountmart.utility_services.travel.bus.bus_adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_activity.BusCancellationPolicyActivity;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.utilities.DateUtilities;

public class BusSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BusSearchListResponse> busSearchList;
    private List<BusSearchListResponse> contactListFiltered;
    private BusListAdapterListener listener;

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTravelName;
        public TextView txtBusType;

        public TextView txtDTime;
        public TextView txtDDate;
        public TextView txtDDay;
        public TextView txtCancel;
        public TextView txtDropPoint;
        public TextView txtStarRating;

        public TextView txtATime;
        public TextView txtADate;
        public TextView txtADay;
        public TextView txtToCity;
        public TextView txtTotalTime;
        public TextView txtAc_NoAc;
        public TextView txtPrice;
        public TextView txtSeat;
        public CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            txtTravelName = view.findViewById(R.id.bus_search_list_item_txt_travels_name);
            txtBusType = view.findViewById(R.id.bus_search_list_item_txt_bus_type);
            txtDTime = view.findViewById(R.id.bus_search_list_item_txt_departure_time);
            txtDDate = view.findViewById(R.id.bus_search_list_item_txt_departure_date);
            txtDDay = view.findViewById(R.id.bus_search_list_item_txt_departure_day);
            txtATime = view.findViewById(R.id.bus_search_list_item_txt_arrival_time);
            txtADate = view.findViewById(R.id.bus_search_list_item_txt_arrival_date);
            txtADay = view.findViewById(R.id.bus_search_list_item_txt_arrival_day);
            txtTotalTime = view.findViewById(R.id.bus_search_list_item_txt_total_hour);
            txtAc_NoAc = view.findViewById(R.id.bus_search_list_item_txt_bus_acnonAc);
            txtPrice = view.findViewById(R.id.bus_search_list_item_txt_price);
            txtSeat = view.findViewById(R.id.bus_search_list_item_txt_avail_seat);
            txtCancel = view.findViewById(R.id.bus_search_list_item_txt_cancel_policy);
            txtDropPoint = view.findViewById(R.id.bus_search_list_item_txt_drop_point);
            txtStarRating = view.findViewById(R.id.bus_search_list_item_txt_rating);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onBusSelected(busSearchList.get(getAdapterPosition()));
                }
            });
        }
    }

    public class MyFooter extends RecyclerView.ViewHolder{
        LinearLayout layoutFooter;
        public MyFooter(@NonNull View itemHeaderView) {
            super(itemHeaderView);
            layoutFooter=(LinearLayout)itemHeaderView.findViewById(R.id.flight_search_list_layout_footer);

        }
    }


    public BusSearchListAdapter(Context context, List<BusSearchListResponse> busList, BusListAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.busSearchList = busList;
        //this.contactListFiltered = contactList;
    }

    public void setData(Context context, List<BusSearchListResponse> busList, BusListAdapterListener listener, String strShort){
        this.mContext=context;
        this.listener=listener;
        this.busSearchList=busList;

        if(strShort.contentEquals("Sleeper")){
            //cheapest();
        }
        else if (strShort.contentEquals("Seater")){
           // highest();
        }
        else if(strShort.contentEquals("Early Start")){
            earlyTakeOff();
        }

        else if(strShort.contentEquals("Late Start")){
            lateTakeOff();
        }
        else if(strShort.contentEquals("Early Arrival")){
           earlyLanding();
        }
        else if(strShort.contentEquals("Late Arrival")){
           lateLanding();
        }

        else {
            notifyDataSetChanged();
        }

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_flight_search_list_item, parent, false);*/

        // return new MyViewHolder(itemView);

        if (viewType == TYPE_ITEM ) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.utility_bus_search_list_item, parent, false);
            return new MyViewHolder(itemView);
        }

        if (viewType == TYPE_FOOTER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_flight_search_list_item_footer, parent, false);
            return new MyFooter(layoutView);
        }

        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {

            if(holder instanceof MyViewHolder){
                final MyViewHolder myViewHolder = (MyViewHolder) holder;

                myViewHolder.txtTravelName.setText(busSearchList.get(position).getTravels());
                myViewHolder.txtBusType.setText(busSearchList.get(position).getBusType());

                String[] strDepartDate=busSearchList.get(position).getDepartureDateTime().split(",");
                myViewHolder.txtDTime.setText(strDepartDate[2]);
                myViewHolder.txtDDate.setText(strDepartDate[1]);
                myViewHolder.txtDDay.setText(strDepartDate[0]);

                String[] strArrivalDate=busSearchList.get(position).getArrivateDateTime().split(",");
                myViewHolder.txtATime.setText(strArrivalDate[2]);
                myViewHolder.txtADate.setText(strArrivalDate[1]);
                myViewHolder.txtADay.setText(strArrivalDate[0]);

                String depDate_Time="";
                String arriveDate_Time="";
                String depTime=strDepartDate[2];
                String depDate=strDepartDate[1];
                String aDate=strArrivalDate[1];
                String atime=strArrivalDate[2];

                depDate_Time=depDate+""+depTime;
                arriveDate_Time=aDate+""+atime;

                DateFormat recformet=new SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.US);
                DateFormat convertFormet=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",Locale.US);
                Date date1=null;
                Date date2=null;
                String convertDepDate="";
                String convertArriveDate="";
                try {
                    date1=recformet.parse(depDate_Time);
                    date2=recformet.parse(arriveDate_Time);
                    convertDepDate=convertFormet.format(date1);
                    convertArriveDate=convertFormet.format(date2);

                }catch (Exception e){
                    e.printStackTrace();
                }
                myViewHolder.txtTotalTime.setText(DateUtilities.getDifferenceTime(convertDepDate,convertArriveDate));

                myViewHolder.txtPrice.setText(mContext.getResources().getString(R.string.rs_symbol) +busSearchList.get(position).getFareText());

                String strSeat="Seat Available";
                int seat;
                if(busSearchList.get(position).getAvailableSeats().equals("") || busSearchList.get(position).getAvailableSeats()== null){
                    seat=0;
                }
                else {
                    seat= Integer.parseInt(busSearchList.get(position).getAvailableSeats());
                }

                if(seat >= 5){
                    myViewHolder.txtSeat.setTextColor(mContext.getResources().getColor(R.color.yellow11));
                }
                else {
                    myViewHolder.txtSeat.setTextColor(mContext.getResources().getColor(R.color.red));
                }
                myViewHolder.txtSeat.setText(busSearchList.get(position).getAvailableSeats()+ " Seat Left");

                if(seat == 0){
                    myViewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.gray5));
                }
                else {
                    myViewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }



                //Text Cancellation Policy on click go next BusSeatActivity
                myViewHolder.txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mContext, BusCancellationPolicyActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putSerializable("Policy",busSearchList.get(position).getCancelCharge());
                        intent.putExtras(bundle1);

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mContext,R.anim.slide_up,R.anim.slide_down);

                        mContext.startActivity(intent, options.toBundle());

                    }
                });




            }
            else if(holder instanceof MyFooter){
                MyFooter footer=(MyFooter)holder;
                footer.layoutFooter.setVisibility(View.VISIBLE);
            }


        }



       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }
    @Override
    public int getItemViewType(int position) {
        //Return item type according to requirement

        /* if (isPositionFooter(position))
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;*/
        if (position == busSearchList.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }


    private  boolean isPositionFooter(int position) {
        return position == busSearchList.size()+1;
    }

    /* @Override
     public int getItemCount() {
         return flightSearchList == null ? 0 : flightSearchList.size()+1;
     }*/
    @Override
    public int getItemCount() {
        if (busSearchList == null) {
            return 0;
        }

        if (busSearchList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return busSearchList.size() + 1;
    }



    public interface BusListAdapterListener {
        void onBusSelected(BusSearchListResponse contact);

    }



    /*Method for find highest price flight*/
   /* public void highest(){
        //shorting
        Collections.sort(flightSearchList, new Comparator<BusSearchListResponse>() {
            @Override
            public int compare(BusSearchListResponse o1, BusSearchListResponse o2) {
                Double totalAmount = Double.parseDouble(o1.getGrossAmount());

                Double totalAmount1 = Double.parseDouble(o2.getGrossAmount());

                return totalAmount1.compareTo(totalAmount);
            }
        });
    }*/


    /*Method for find early start  (time) bus
    * date formet Saturday, 14 Sep 2019, 07:30:00*/
    public void earlyTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm:ss",Locale.UK);//24 hour time formet
        final int[] date = new int[1];
        Collections.sort(busSearchList, new Comparator<BusSearchListResponse>() {
            @Override
            public int compare(BusSearchListResponse o1, BusSearchListResponse o2) {
                try{
                    String depTime1 = o1.getDepartureDateTime();

                    String depTime2 = o2.getDepartureDateTime();

                    return dateFormat.parse(depTime1).compareTo(dateFormat.parse(depTime2));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }

    /*Method for find late start (time) bus*/
    public void lateTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm:ss", Locale.UK);
        final int[] date = new int[1];
        Collections.sort(busSearchList, new Comparator<BusSearchListResponse>() {
            @Override
            public int compare(BusSearchListResponse o1, BusSearchListResponse o2) {
                try{
                    String depTime1 = o1.getDepartureDateTime();

                    String depTime2 = o2.getDepartureDateTime();

                    return dateFormat.parse(depTime2).compareTo(dateFormat.parse(depTime1));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }

    /*Method for Early arrival bus*/
    public void earlyLanding(){
        //shorting

        Collections.sort(busSearchList, new Comparator<BusSearchListResponse>() {
            @Override
            public int compare(BusSearchListResponse o1, BusSearchListResponse o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm:ss",Locale.ENGLISH);
                try{
                    String depTime1 = o1.getArrivateDateTime();

                    String depTime2 = o2.getArrivateDateTime();

                    return dateFormat.parse(depTime1).compareTo(dateFormat.parse(depTime2));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }

    /*Method for Late arrival bus*/
    public void lateLanding(){
        //shorting

        Collections.sort(busSearchList, new Comparator<BusSearchListResponse>() {
            @Override
            public int compare(BusSearchListResponse o1, BusSearchListResponse o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm:ss",Locale.ENGLISH);
                try{
                    String depTime1 = o1.getArrivateDateTime();

                    String depTime2 = o2.getArrivateDateTime();

                    return dateFormat.parse(depTime2).compareTo(dateFormat.parse(depTime1));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }


}
