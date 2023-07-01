package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.BankListResponse;

import java.util.ArrayList;

public class BankNameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    public Context mContext;
    public ArrayList<BankListResponse.BankList> productList;
    public ArrayList<BankListResponse.BankList> filterProductList;
    public BankListAdapterListener listener;

    int quantity=0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProdtName;
        public TextView txtHotelCode;

        public MyViewHolder(View view) {
            super(view);

            txtProdtName = view.findViewById(R.id.list_namelist);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onItemSelected(filterProductList.get(getAdapterPosition()));
                }
            });
        }
    }

    public BankNameListAdapter(Context context, ArrayList<BankListResponse.BankList> productList, BankListAdapterListener listAdapterListener) {
        this.mContext = context;
        this.productList = productList;
        this.filterProductList = productList;
        this.listener=listAdapterListener;
    }

    public void setData(Context context, ArrayList<BankListResponse.BankList> productList, BankListAdapterListener adapterListener) {
        this.mContext = context;
        this.productList = productList;
        this.filterProductList = productList;
        this.listener=adapterListener;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_list_item_layout, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {

            if (holder instanceof MyViewHolder) {
                final MyViewHolder myViewHolder = (MyViewHolder) holder;
                //myViewHolder.layoutItemView.setVisibility(View.VISIBLE);
                //final FlightSearchResponse contact = flightSearchList.get(position);
                /*if (m_orderAssignList.get(position).getProductImage().equals("")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Picasso.with(mContext)
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.round_logo)))
                                .placeholder(R.mipmap.round_logo)
                                .error(R.mipmap.round_logo)
                                .into(myViewHolder.imgProdtImg);

                        *//*Glide.with(mContext)  //2
                                .load(String.valueOf(mContext.getDrawable(R.mipmap.home_icon_hotel))) //3
                                .centerCrop() //4
                                .placeholder(R.mipmap.home_icon_hotel) //5
                                .error(R.mipmap.home_icon_hotel) //6
                                .fallback(R.mipmap.home_icon_hotel) //7
                                .into(myViewHolder.imgHotelImg);*//* //8
                    }
                } else {

                    Picasso.with(mContext)
                            .load(String.valueOf(m_orderAssignList.get(position).getProductImage()))
                            .placeholder(R.mipmap.round_logo)
                            .error(R.mipmap.round_logo)
                            .into(myViewHolder.imgProdtImg);

                   *//* Glide.with(mContext)  //2
                            .load(hotelSearchList.get(position).getHotelPicture()) //3
                            //.centerCrop() //4
                            .placeholder(R.mipmap.home_icon_hotel) //5
                            .error(R.mipmap.home_icon_hotel) //6
                            .fallback(R.mipmap.home_icon_hotel) //7
                            .into(myViewHolder.imgHotelImg); //8*//*
                }*/
                myViewHolder.txtProdtName.setText(filterProductList.get(position).getBankname());






            }
            /*else if(holder instanceof MyFooter){
                MyFooter footer=(MyFooter)holder;
                footer.layoutFooter.setVisibility(View.GONE);

            }*/
        }


    }

    @Override
    public int getItemCount() {
        return filterProductList == null ? 0 : filterProductList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterProductList = productList;
                } else {
                    ArrayList<BankListResponse.BankList> filteredList = new ArrayList<>();
                    for (BankListResponse.BankList row : productList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        /*if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);

                        }*/

                        if (row.getBankname().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }

                    }

                    filterProductList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterProductList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterProductList = (ArrayList<BankListResponse.BankList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface BankListAdapterListener {
        void onItemSelected(BankListResponse.BankList response);
    }


}