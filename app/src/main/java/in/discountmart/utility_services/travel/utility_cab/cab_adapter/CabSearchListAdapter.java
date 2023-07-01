package in.discountmart.utility_services.travel.utility_cab.cab_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.travel.utility_cab.cab_model.cab_response_model.CabSearchResponse;

public class CabSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CabSearchResponse> busSearchList;
    private List<CabSearchResponse> contactListFiltered;
    private CabListAdapterListener listener;

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCabName;
        public TextView txtCabType;

        public TextView txtDistance;
        public TextView txtPerKM;
        public TextView txtTotAmount;
        public TextView txtFromCity;
        public TextView txtDr;
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
            txtCabName = view.findViewById(R.id.cab_search_item_txt_cabname);
            txtCabType = view.findViewById(R.id.cab_search_item_txt_cabtype);
            txtDistance = view.findViewById(R.id.cab_search_item_txt_distance);
            txtPerKM = view.findViewById(R.id.cab_search_item_txt_km_rate);
            txtTotAmount = view.findViewById(R.id.cab_search_item_txt_tot_amount);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCabSelected(busSearchList.get(getAdapterPosition()));
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


    public CabSearchListAdapter(Context context, List<CabSearchResponse> busList, CabListAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.busSearchList = busList;
        //this.contactListFiltered = contactList;
    }

    public void setData(Context context, List<CabSearchResponse> busList, CabListAdapterListener listener, String strShort){
        this.mContext=context;
        this.listener=listener;
        this.busSearchList=busList;

       /* if(strShort.contentEquals("Sleeper")){
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
        }*/

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_flight_search_list_item, parent, false);*/

        // return new MyViewHolder(itemView);

        if (viewType == TYPE_ITEM ) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.utility_cab_search_list_item, parent, false);
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

                myViewHolder.txtCabName.setText(busSearchList.get(position).getCar());
                myViewHolder.txtCabType.setText(busSearchList.get(position).getType());
                myViewHolder.txtDistance.setText(busSearchList.get(position).getDistance()+"(Km)");
                myViewHolder.txtPerKM.setText("Per km. "+busSearchList.get(position).getRate());
                myViewHolder.txtTotAmount.setText(mContext.getResources().getString(R.string.rs_symbol) +busSearchList.get(position).getTotal());

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



    public interface CabListAdapterListener {
        void onCabSelected(CabSearchResponse contact);

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
   /* public void earlyTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm:ss",Locale.UK);//24 hour time formet
        final int[] date = new int[1];
        Collections.sort(busSearchList, new Comparator<CabSearchResponse>() {
            @Override
            public int compare(CabSearchResponse o1, CabSearchResponse o2) {
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
    }*/

    /*Method for find late start (time) bus*/
    /*public void lateTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm:ss", Locale.UK);
        final int[] date = new int[1];
        Collections.sort(busSearchList, new Comparator<CabSearchResponse>() {
            @Override
            public int compare(CabSearchResponse o1, CabSearchResponse o2) {
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
    }*/

    /*Method for Early arrival bus*/
   /* public void earlyLanding(){
        //shorting

        Collections.sort(busSearchList, new Comparator<CabSearchResponse>() {
            @Override
            public int compare(CabSearchResponse o1, CabSearchResponse o2) {
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
    }*/

    /*Method for Late arrival bus*/
    /*public void lateLanding(){
        //shorting

        Collections.sort(busSearchList, new Comparator<CabSearchResponse>() {
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
    }*/


}
