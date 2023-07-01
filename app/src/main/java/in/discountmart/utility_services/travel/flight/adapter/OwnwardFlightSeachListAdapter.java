package in.discountmart.utility_services.travel.flight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.OwnwardFlightSearch;

public class OwnwardFlightSeachListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<OwnwardFlightSearch> flightSearchList;
    private List<OwnwardFlightSearch> contactListFiltered;
    private OwnFlightListAdapterListener listener;

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    int selectedPosition=0;


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
        public ImageView imgFlightLogo;

        public MyViewHolder(View view) {
            super(view);

            imgFlightLogo=(ImageView)view.findViewById(R.id.own_flight_search_list_item_img_flight_logo);

            txtFlightName = view.findViewById(R.id.own_flight_search_list_item_txt_flight_name);
            txtFlightCode = view.findViewById(R.id.own_flight_search_list_item_txt_flight_code);
            txtDDate = view.findViewById(R.id.own_flight_search_list_item_txt_departure_date);
            txtDDay = view.findViewById(R.id.own_flight_search_list_item_txt_departure_day);
            txtDTime = view.findViewById(R.id.own_flight_search_list_item_txt_departure_time);
            txtFromCityCode = view.findViewById(R.id.own_flight_search_list_item_txt_from_city);
            txtADate = view.findViewById(R.id.own_flight_search_list_item_txt_arrive_date);
            txtADay = view.findViewById(R.id.own_flight_search_list_item_txt_arrive_day);
            txtATime = view.findViewById(R.id.own_flight_search_list_item_txt_arriv_time);
            txtToCity = view.findViewById(R.id.own_flight_search_list_item_txt_to_city);
            txtTotalTime = view.findViewById(R.id.own_flight_search_list_item_txt_total_hour);
            txtStop = view.findViewById(R.id.own_flight_search_list_item_txt_stop);
            txtRefund = view.findViewById(R.id.own_flight_search_list_item_txt_refundable);
            txtPrice = view.findViewById(R.id.own_flight_search_list_item_txt_price);
            txtSeat = view.findViewById(R.id.own_flight_search_list_item_txt_avail_seat);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onOwnwardFlightSelected(flightSearchList.get(getAdapterPosition()));
                   // selectedPosition = getAdapterPosition();
                    //notifyDataSetChanged();

                    int currentPosition = getAdapterPosition();
                    if(selectedPosition != currentPosition){
                        // Temporarily save the last selected position
                        int lastSelectedPosition = selectedPosition;
                        // Save the new selected position
                        selectedPosition = currentPosition;
                        // update the previous selected row
                        notifyItemChanged(lastSelectedPosition);
                        // select the clicked row
                        //itemView.setSelected(true);
                        notifyDataSetChanged();
                    }
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


    public OwnwardFlightSeachListAdapter(Context context, List<OwnwardFlightSearch> flightList, OwnFlightListAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.flightSearchList = flightList;
        //this.contactListFiltered = contactList;
    }

    public void setData(Context context, List<OwnwardFlightSearch> flightList, OwnFlightListAdapterListener listener, String strShort){
        this.mContext=context;
        this.listener=listener;
        this.flightSearchList=flightList;

        if(strShort.contentEquals("Cheapest")){
            cheapest();
        }
        else if (strShort.contentEquals("Highest")){
            highest();
        }
        else if (strShort.contentEquals("Fastest")){
            fastest();
        }
        else if(strShort.contentEquals("Early Take off")){
            earlyTakeOff();
        }
        else if(strShort.contentEquals("Late Take off")){
            lateTakeOff();
        }
        else if(strShort.contentEquals("Early Landing")){
            earlyLanding();
        }

        else if(strShort.contentEquals("Late Landing")){
            lateLanding();
        }

        else {
            notifyDataSetChanged();
        }

        notifyDataSetChanged();
        selectedPosition=0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_flight_search_list_item, parent, false);*/

        // return new MyViewHolder(itemView);

        if (viewType == TYPE_ITEM ) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.utility_ownward_flight_search_item, parent, false);
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
                //myViewHolder.layoutItemView.setVisibility(View.VISIBLE);
                //final FlightSearchResponse contact = flightSearchList.get(position);
                Picasso.with(mContext).load(flightSearchList.get(position).getAirlineLogo()).into(myViewHolder.imgFlightLogo);

                myViewHolder.txtFlightName.setText(flightSearchList.get(position).getAirlineName());
                myViewHolder.txtFlightCode.setText(flightSearchList.get(position).getAirlineCode()+ "-" +flightSearchList.get(position).getFlightNumber());

                String[] strDepartDate=flightSearchList.get(position).getDepartureDateTime().split(",");
                myViewHolder.txtDDate.setText(strDepartDate[0].substring(0,3));
                myViewHolder.txtDDay.setText(strDepartDate[1]);
                myViewHolder.txtDTime.setText(flightSearchList.get(position).getDepartureTime());
                //myViewHolder.txtFromCityCode.setText(flightSearchList.get(position).getOrigin());

                String[] strArrivalDate=flightSearchList.get(position).getArrivalDatetime().split(",");
                myViewHolder.txtADate.setText(strArrivalDate[0].substring(0,3));
                myViewHolder.txtADay.setText(strArrivalDate[1]);
                myViewHolder.txtATime.setText(flightSearchList.get(position).getArrivaltime());
                //myViewHolder.txtToCity.setText(flightSearchList.get(position).getDestination());

                myViewHolder.txtStop.setText(flightSearchList.get(position).getStopage());
                if(flightSearchList.get(position).getFairType().contentEquals("Refundable")){
                    myViewHolder.txtRefund.setText(flightSearchList.get(position).getFairType());
                }
                else {
                    String fairType=flightSearchList.get(position).getFairType().substring(0,10);
                    myViewHolder.txtRefund.setText(fairType);
                }
                myViewHolder.txtTotalTime.setText(flightSearchList.get(position).getDifferenceTime());

                myViewHolder.txtPrice.setText(mContext.getResources().getString(R.string.rs_symbol) +flightSearchList.get(position).getGrossAmount());

                String strSeat="Seat Available";
                int seat;
                if(flightSearchList.get(position).getAvailableSeat().equals("") || flightSearchList.get(position).getAvailableSeat()== null){
                    seat=0;
                }
                else {
                    seat= Integer.parseInt(flightSearchList.get(position).getAvailableSeat());
                }

                if(seat >= 5){
                    myViewHolder.txtSeat.setTextColor(mContext.getResources().getColor(R.color.yellow11));
                }
                else {
                    myViewHolder.txtSeat.setTextColor(mContext.getResources().getColor(R.color.red));
                }
                myViewHolder.txtSeat.setText(flightSearchList.get(position).getAvailableSeat()+ " Seat Left");


                if(position == selectedPosition){
                    holder.itemView.setSelected(true);
                    myViewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.gray5));
                    //holder.tv1.setTextColor(Color.parseColor("#ffffff"));
                }
                else
                {
                    holder.itemView.setSelected(false);
                    myViewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
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
        if (position == flightSearchList.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }


    private  boolean isPositionFooter(int position) {
        return position == flightSearchList.size()+1;
    }

    /* @Override
     public int getItemCount() {
         return flightSearchList == null ? 0 : flightSearchList.size()+1;
     }*/
    @Override
    public int getItemCount() {
        if (flightSearchList == null) {
            return 0;
        }

        if (flightSearchList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return flightSearchList.size() + 1;
    }

    public interface OwnFlightListAdapterListener {
        void onOwnwardFlightSelected(OwnwardFlightSearch contact);

    }

    /*Method for find lowest price flight*/
    public void cheapest(){
        //shorting
        Collections.sort(flightSearchList, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {
                Double totalAmount = Double.parseDouble(o1.getGrossAmount());

                Double totalAmount1 = Double.parseDouble(o2.getGrossAmount());

                return totalAmount.compareTo(totalAmount1);
            }
        });
    }

    /*Method for find highest price flight*/
    public void highest(){
        //shorting
        Collections.sort(flightSearchList, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {
                Double totalAmount = Double.parseDouble(o1.getGrossAmount());

                Double totalAmount1 = Double.parseDouble(o2.getGrossAmount());

                return totalAmount1.compareTo(totalAmount);
            }
        });
    }


    /*Method for find early take off (time) flight*/
    public void earlyTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet
        final int[] date = new int[1];
        Collections.sort(flightSearchList, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {//differenceTime
                try{
                    String depTime1 = o1.getDepartureTime();

                    String depTime2 = o2.getDepartureTime();

                    return dateFormat.parse(depTime1).compareTo(dateFormat.parse(depTime2));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }

    /*Method for find fastest flight*/
    public void fastest(){
        //shorting
        Collections.sort(flightSearchList, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {//differenceTime
                String fast = o1.getDifferenceTime();

                String fast1= o2.getDifferenceTime();

                return fast.compareTo(fast1);
            }
        });
    }

    /*Method for find late take off (time) flight*/
    public void lateTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);
        final int[] date = new int[1];
        Collections.sort(flightSearchList, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {
                try{
                    String depTime1 = o1.getDepartureTime();

                    String depTime2 = o2.getDepartureTime();

                    return dateFormat.parse(depTime2).compareTo(dateFormat.parse(depTime1));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }

    /*Method for Early Landing flight*/
    public void earlyLanding(){
        //shorting

        Collections.sort(flightSearchList, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
                try{
                    String depTime1 = o1.getArriDate();

                    String depTime2 = o2.getArriDate();

                    return dateFormat.parse(depTime1).compareTo(dateFormat.parse(depTime2));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }

    /*Method for Late Landing flight*/
    public void lateLanding(){
        //shorting

        Collections.sort(flightSearchList, new Comparator<OwnwardFlightSearch>() {
            @Override
            public int compare(OwnwardFlightSearch o1, OwnwardFlightSearch o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
                try{
                    String depTime1 = o1.getArriDate();

                    String depTime2 = o2.getArriDate();

                    return dateFormat.parse(depTime2).compareTo(dateFormat.parse(depTime1));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }
}
