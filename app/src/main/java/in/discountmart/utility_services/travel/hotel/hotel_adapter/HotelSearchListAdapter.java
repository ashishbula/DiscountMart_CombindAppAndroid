package in.discountmart.utility_services.travel.hotel.hotel_adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.HotelSearchResponse;

public class HotelSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<HotelSearchResponse> hotelSearchList;
    private List<HotelSearchResponse> contactListFiltered;
    private HotelListAdapterListener listener;

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHotelName;
        public TextView txtHotelCode;
        public TextView txtHotelAddress;
        public TextView txtHotelPrice;
        public TextView txtHotelStar;
        public TextView txtHotelAsper;
        public RatingBar ratingBar;

        public ImageView imgHotelImg;

        public MyViewHolder(View view) {
            super(view);

            imgHotelImg=(ImageView)view.findViewById(R.id.hotel_search_list_item_image);

            txtHotelName = view.findViewById(R.id.hotel_search_list_item_txt_hotel_nme);
            //txtHotelCode = view.findViewById(R.id.flight_search_list_item_txt_flight_code);
            txtHotelAddress = view.findViewById(R.id.hotel_search_list_item_txt_hotel_address);
            txtHotelPrice = view.findViewById(R.id.hotel_search_list_item_txt_hotel_price);
            txtHotelStar = view.findViewById(R.id.hotel_search_list_item_txt_hotel_star);
            txtHotelAsper = view.findViewById(R.id.hotel_search_list_item_txt_hotel_perroom);
            ratingBar=view.findViewById(R.id.hotel_search_list_item_rating_bar);




            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onHotelSelected(hotelSearchList.get(getAdapterPosition()));
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


    public HotelSearchListAdapter(Context context, List<HotelSearchResponse> hotelList, HotelListAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.hotelSearchList = hotelList;
        //this.contactListFiltered = contactList;
    }

    public void setData(Context context, List<HotelSearchResponse> hotelList, HotelListAdapterListener listener, String strShort){
        this.mContext=context;
        this.listener=listener;
        this.hotelSearchList=hotelList;

        if(strShort.contentEquals("Lowest Price")){
            cheapest();
        }
        else if (strShort.contentEquals("Highest Price")){
            highest();
        }
        else if(strShort.contentEquals("Highest Rating")){
            highestRating();
        }
        else if(strShort.contentEquals("Lowest Rating")){
            lowestRating();
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
                    .inflate(R.layout.utility_hotel_search_list_item, parent, false);
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
                if(hotelSearchList.get(position).getHotelPicture().equals("")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Picasso.with(mContext)
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.utility_ic_hotel)))
                                .placeholder(R.mipmap.utility_ic_hotel)
                                .error(R.mipmap.utility_ic_hotel)
                                .into(myViewHolder.imgHotelImg);

                        /*Glide.with(mContext)  //2
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.home_icon_hotel) //5
                                .error(R.mipmap.home_icon_hotel) //6
                                .fallback(R.mipmap.home_icon_hotel) //7
                                .into(myViewHolder.imgHotelImg);*/ //8
                    }
                }

                else{

                    Picasso.with(mContext)
                            .load(String.valueOf(hotelSearchList.get(position).getHotelPicture()))
                            .placeholder(R.mipmap.utility_ic_hotel)
                            .error(R.mipmap.utility_ic_hotel)
                            .into(myViewHolder.imgHotelImg);

                   /* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*/
                }


                myViewHolder.txtHotelName.setText(hotelSearchList.get(position).getHotelName());
                myViewHolder.txtHotelAddress.setText(hotelSearchList.get(position).getHotelAddress());
                myViewHolder.txtHotelPrice.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+hotelSearchList.get(position).getPrice());
                //myViewHolder.txtHotelStar.setText(hotelSearchList.get(position).getStarRating());
                myViewHolder.ratingBar.setRating(Float.parseFloat(hotelSearchList.get(position).getStarRating()));

                String strhotelName=hotelSearchList.get(position).getHotelName();
                String hotelAddress=hotelSearchList.get(position).getHotelAddress();

                /*Set Text hotel name */
               /* String hotelName = "";

                if (strhotelName.length() >= 25) {
                    hotelName = strhotelName.substring(0, 25) + "...";

                    myViewHolder.txtHotelName.setText(hotelName);

                } else {
                    hotelName = strhotelName;
                    myViewHolder.txtHotelName.setText(hotelName);

                }*/

                /*Set Text hotel Address */
               /* String address = "";
                if (hotelAddress.length() >= 35) {
                    address = hotelAddress.substring(0, 35) + "...";
                    myViewHolder.txtHotelAddress.setText(address);

                } else {
                    address = hotelAddress;
                    myViewHolder.txtHotelAddress.setText(address);

                }*/

            }
            else if(holder instanceof MyFooter){
                MyFooter footer=(MyFooter)holder;
                footer.layoutFooter.setVisibility(View.VISIBLE);
            }


        }



        /*Glide.with(context)
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
        if (position == hotelSearchList.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }


    private  boolean isPositionFooter(int position) {
        return position == hotelSearchList.size()+1;
    }

    /* @Override
     public int getItemCount() {
         return flightSearchList == null ? 0 : flightSearchList.size()+1;
     }*/
    @Override
    public int getItemCount() {
        if (hotelSearchList == null) {
            return 0;
        }

        if (hotelSearchList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return hotelSearchList.size() + 1;
    }



    public interface HotelListAdapterListener {
        void onHotelSelected(HotelSearchResponse contact);

    }

    /*Method for find lowest price hotel*/
    public void cheapest(){
        //shorting
        Collections.sort(hotelSearchList, new Comparator<HotelSearchResponse>() {
            @Override
            public int compare(HotelSearchResponse o1, HotelSearchResponse o2) {
                Double totalAmount = Double.parseDouble(o1.getPrice());

                Double totalAmount1 = Double.parseDouble(o2.getPrice());

                return totalAmount.compareTo(totalAmount1);
            }
        });
    }

    /*Method for find highest price hotel*/
    public void highest(){
        //shorting
        Collections.sort(hotelSearchList, new Comparator<HotelSearchResponse>() {
            @Override
            public int compare(HotelSearchResponse o1, HotelSearchResponse o2) {
                Double totalAmount = Double.parseDouble(o1.getPrice());

                Double totalAmount1 = Double.parseDouble(o2.getPrice());

                return totalAmount1.compareTo(totalAmount);
            }
        });
    }


    /*Method for find highest Rating hotel*/
    public void highestRating(){
        //shorting
        Collections.sort(hotelSearchList, new Comparator<HotelSearchResponse>() {
            @Override
            public int compare(HotelSearchResponse o1, HotelSearchResponse o2) {
                Double totalAmount = Double.parseDouble(o1.getStarRating());

                Double totalAmount1 = Double.parseDouble(o2.getStarRating());

                return totalAmount1.compareTo(totalAmount);
            }
        });
    }

    /*Method for find lowest Rating hotel*/
    public void lowestRating(){
        //shorting
        Collections.sort(hotelSearchList, new Comparator<HotelSearchResponse>() {
            @Override
            public int compare(HotelSearchResponse o1, HotelSearchResponse o2) {
                Double totalAmount = Double.parseDouble(o1.getStarRating());

                Double totalAmount1 = Double.parseDouble(o2.getStarRating());

                return totalAmount.compareTo(totalAmount1);
            }
        });
    }



}
