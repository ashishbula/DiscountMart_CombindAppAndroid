package in.discountmart.utility_services.travel.hotel.hotel_adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.travel.hotel.hotel_activity.RoomCancelPolicyActivity;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelSearchResponse;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.RoomDetail;

public class HotelRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<RoomDetail> roomDetailList;
    private List<HotelSearchResponse> contactListFiltered;
    private RoomListAdapterListener listener;

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
     int selectedPosition=-1;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRoomTypeName;

        public TextView txtHotelAddress;
        public TextView txtRoomPrice;
        public TextView txtTotPrice;
        public TextView txtCancelPolicy;
        public TextView txtInclusion;
        LinearLayout layoutPolicy;
        LinearLayout layoutInclusion;
        public RatingBar ratingBar;

        public ImageView imgHotelImg;

        public MyViewHolder(View view) {
            super(view);



            txtRoomTypeName = view.findViewById(R.id.hotel_room_item_txt_roomName);
            //txtHotelCode = view.findViewById(R.id.flight_search_list_item_txt_flight_code);
            txtRoomPrice = view.findViewById(R.id.hotel_room_item_txt_roomPrice);
            txtTotPrice = view.findViewById(R.id.hotel_room_item_txt_roomtotPrice);
            txtCancelPolicy = view.findViewById(R.id.hotel_room_item_txt_roomCanPolicy);
            txtInclusion = view.findViewById(R.id.hotel_room_item_txt_roomInclusion);
            layoutInclusion=view.findViewById(R.id.hotel_room_item_layout_inclusion);
            layoutPolicy=view.findViewById(R.id.hotel_room_item_layout_CancelPolicy);





            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    int pos=getAdapterPosition();
                    listener.onRoomSelected(roomDetailList.get(getAdapterPosition()),pos);

                    selectedPosition = getAdapterPosition();
                    //notifyItemChanged(selectedPosition);
                    notifyDataSetChanged();
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

    public HotelRoomAdapter(Context context, ArrayList<RoomDetail> roomList, RoomListAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.roomDetailList = roomList;
        //this.contactListFiltered = contactList;
    }

    public void setData(Context context, ArrayList<RoomDetail> roomList, RoomListAdapterListener listener, String strShort){
        this.mContext=context;
        this.listener=listener;
        this.roomDetailList=roomList;

       /* if(strShort.contentEquals("Cheapest")){
            cheapest();
        }
        else if (strShort.contentEquals("Highest")){
            highest();
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
                    .inflate(R.layout.utility_hotel_room_item, parent, false);
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
                /*if(roomDetailList.get(position).getHotelPicture().equals("")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Picasso.with(mContext).load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))).into(myViewHolder.imgHotelImg);
                    }
                }

                else{
                    Picasso.with(mContext).load(hotelSearchList.get(position).getHotelPicture()).into(myViewHolder.imgHotelImg);
                }*/
                myViewHolder.txtRoomPrice.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+String.valueOf(roomDetailList.get(position).getPrice().getPublishedPrice()));
                myViewHolder.txtRoomTypeName.setText(roomDetailList.get(position).getRoomTypeName());
                //myViewHolder.txtRoomPrice.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+roomDetailList.get(position).getPrice());
               // myViewHolder.txtTotPrice.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+roomDetailList.get(position).getPrice());
                myViewHolder.txtCancelPolicy.setText("Cancellation Policies");
                myViewHolder.layoutPolicy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(roomDetailList.get(position).getCancellationPolicies().size() > 0){
                            Intent intent=new Intent(mContext, RoomCancelPolicyActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("CancelPolicy",roomDetailList.get(position).getCancellationPolicies());
                            bundle.putString("Policy",roomDetailList.get(position).getCancellationPolicy());
                            intent.putExtras(bundle);

                            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mContext,R.anim.slide_up,R.anim.slide_down);
                            mContext.startActivity(intent, options.toBundle());
                        }
                        else {
                            Toast.makeText(mContext,"Cancellation policy not available",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if(roomDetailList.get(position).getInclusion().size() > 0 && !roomDetailList.get(position).getInclusion().toString().equals("")){
                    myViewHolder.layoutInclusion.setVisibility(View.VISIBLE);
                    ArrayList<String> inclusion=new ArrayList<>();
                    String strInclusion="";
                    inclusion=roomDetailList.get(position).getInclusion();
                    for(int i=0; i< inclusion.size(); i++){

                        if(strInclusion.contentEquals("")){
                            strInclusion=inclusion.get(i);
                        }
                        else {
                            strInclusion=strInclusion+","+inclusion.get(i);
                        }
                    }
                    myViewHolder.txtInclusion.setText(strInclusion);
                }
                else {
                    myViewHolder.layoutInclusion.setVisibility(View.GONE);
                    myViewHolder.txtInclusion.setText("");
                }

                if(selectedPosition==position){
                    myViewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                    //holder.tv1.setTextColor(Color.parseColor("#ffffff"));
                }
                else
                {
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
        if (position == roomDetailList.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }


    private  boolean isPositionFooter(int position) {
        return position == roomDetailList.size()+1;
    }

    /* @Override
     public int getItemCount() {
         return flightSearchList == null ? 0 : flightSearchList.size()+1;
     }*/
    @Override
    public int getItemCount() {
        if (roomDetailList == null) {
            return 0;
        }

        if (roomDetailList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return roomDetailList.size() + 1;
    }

    public interface RoomListAdapterListener {
        void onRoomSelected(RoomDetail contact, int pos);

    }

    /*Method for find lowest price flight*/
   /* public void cheapest(){
        //shorting
        Collections.sort(hotelSearchList, new Comparator<HotelSearchResponse>() {
            @Override
            public int compare(HotelSearchResponse o1, HotelSearchResponse o2) {
                Double totalAmount = Double.parseDouble(o1.getGrossAmount());

                Double totalAmount1 = Double.parseDouble(o2.getGrossAmount());

                return totalAmount.compareTo(totalAmount1);
            }
        });
    }*/

    /*Method for find highest price flight*/
   /* public void highest(){
        //shorting
        Collections.sort(flightSearchList, new Comparator<FlightSearchResponse>() {
            @Override
            public int compare(FlightSearchResponse o1, FlightSearchResponse o2) {
                Double totalAmount = Double.parseDouble(o1.getGrossAmount());

                Double totalAmount1 = Double.parseDouble(o2.getGrossAmount());

                return totalAmount1.compareTo(totalAmount);
            }
        });
    }*/


    /*Method for find early take off (time) flight*/
   /* public void earlyTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);//24 hour time formet
        final int[] date = new int[1];
        Collections.sort(flightSearchList, new Comparator<FlightSearchResponse>() {
            @Override
            public int compare(FlightSearchResponse o1, FlightSearchResponse o2) {
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
    }*/

    /*Method for find late take off (time) flight*/
   /* public void lateTakeOff(){
        //shorting

        final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.UK);
        final int[] date = new int[1];
        Collections.sort(flightSearchList, new Comparator<FlightSearchResponse>() {
            @Override
            public int compare(FlightSearchResponse o1, FlightSearchResponse o2) {
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
    }*/

    /*Method for Early Landing flight*/
   /* public void earlyLanding(){
        //shorting

        Collections.sort(flightSearchList, new Comparator<FlightSearchResponse>() {
            @Override
            public int compare(FlightSearchResponse o1, FlightSearchResponse o2) {
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
    }*/

    /*Method for Late Landing flight*/
    /*public void lateLanding(){
        //shorting

        Collections.sort(flightSearchList, new Comparator<FlightSearchResponse>() {
            @Override
            public int compare(FlightSearchResponse o1, FlightSearchResponse o2) {
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
    }*/

}
