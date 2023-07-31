package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.model_business.AddProductModel;

import java.util.ArrayList;
import java.util.List;


public class SelectProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

private Context mContext;
public static List<AddProductModel> selectProductList;
public static ArrayList<AddProductModel> addProductList;
int quantity=0;
private static final int TYPE_FOOTER=1;
private static final int TYPE_ITEM=0;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtProdtName;
    public TextView txtHotelCode;

    public TextView txtProdtPrice;
    public TextView txtProdtTotPrice;
    public TextView txtProdtQty;
    public TextView txtProdtRcp;
    public TextView txtProdtTotRcp;
    public TextView txtProdtFv;
    public TextView txtProdtTotFv;
    public TextView txtProdtDiscount;

    LinearLayout layoutRemove;

    public ImageView imgProdtImg;

    public MyViewHolder(View view) {
        super(view);
        txtProdtName = view.findViewById(R.id.select_product_item_txt_pname);
        //txtHotelCode = view.findViewById(R.id.flight_search_list_item_txt_flight_code);
        txtProdtPrice = view.findViewById(R.id.select_product_item_txt_pprice);
        txtProdtTotPrice = view.findViewById(R.id.select_product_item_txt_totprice);
        txtProdtQty = view.findViewById(R.id.select_product_item_txt_qty);
        txtProdtRcp = view.findViewById(R.id.select_product_item_txt_rcp);
        txtProdtTotRcp=view.findViewById(R.id.select_product_item_txt_totrcp);
        txtProdtFv=view.findViewById(R.id.select_product_item_txt_fv);
        txtProdtTotFv=view.findViewById(R.id.select_product_item_txt_totfv);
        txtProdtDiscount=view.findViewById(R.id.select_product_item_txt_totdiscount);

        /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onHotelSelected(hotelSearchList.get(getAdapterPosition()));
                }
            });*/
    }

}


public  class MyFooter extends RecyclerView.ViewHolder{
        public LinearLayout layoutFooter;
        public TextView txtTotQty;
        public TextView txtTotPrice;
        public TextView txtTotFv;
        public TextView txtTotRcp;
        public TextView txtPaidAmount;
        public TextView txtDiscount;
        public TextView txtDisCal;

        public MyFooter(@NonNull View itemHeaderView) {
            super(itemHeaderView);
           // layoutFooter=(LinearLayout)itemHeaderView.findViewById(R.id.cart_layout);
            txtTotQty=(TextView)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_totitem);
            txtTotPrice=(TextView)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_mrp);
            txtTotFv=(TextView)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_fv);
            txtTotRcp=(TextView)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_rcp);
            txtDiscount=(TextView)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_tot_dis);
            txtDisCal=(TextView)itemHeaderView.findViewById(R.id.selected_prodt_footer_item_txt_dis_calcu);
            //txtPaidAmount=(TextView)itemHeaderView.findViewById(R.id.cart_footer_item_txt_total_paid);
        }
    }

    public SelectProductAdapter(Context context, List<AddProductModel> productList ) {
        this.mContext = context;
        //this.cartActivity=activity;
        this.selectProductList = productList;
        //this.contactListFiltered = contactList;
        notifyDataSetChanged();
    }
  /*  public void setData(Context context, List<AddProductModel> productList) {
        this.mContext = context;
        //this.cartActivity=activity;
        this.selectProductList = productList;

        notifyDataSetChanged();
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new MyViewHolder(itemView);*/

        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.selected_product_item, parent, false);
            return new MyViewHolder(itemView);
        }

        if (viewType == TYPE_FOOTER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_product_footer_item, parent, false);
            return new MyFooter(layoutView);
        }

        throw new RuntimeException("No match for " + viewType + ".");

        /*RecyclerView.ViewHolder vh;
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
            vh= new MyViewHolder(view);
            //return new MyViewHolder(view);
        }
        else  //(viewType == VIEW_TYPE_LOADING)
        {

            View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item_footer_layout, parent, false);
            vh= new MyFooter(view);
            //return new LoadingViewHolder(view);
        }
        return vh;*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

    //try {
        if (holder != null) {

            if (holder instanceof MyViewHolder) {
                final MyViewHolder myViewHolder = (MyViewHolder) holder;
                //myViewHolder.layoutItemView.setVisibility(View.VISIBLE);
                //final FlightSearchResponse contact = flightSearchList.get(position);
               /* if (cartList.get(position).get().equals("")) {
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
                            .load(String.valueOf(productArrayList.get(position).getImagePath()))
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


                myViewHolder.txtProdtName.setText("Name : "+selectProductList.get(position).getProductname());

                myViewHolder.txtProdtPrice.setText("Shipping : "+mContext.getResources().getString(R.string.rs_symbol) + " " + selectProductList.get(position).getMrp());
                myViewHolder.txtProdtQty.setText("Quantity : "+String.valueOf(selectProductList.get(position).getProdusQuantity()));
                myViewHolder.txtProdtRcp.setText("DP Value : "+String.valueOf(selectProductList.get(position).getRcp()));
                myViewHolder.txtProdtFv.setText("PV Value : "+String.valueOf(selectProductList.get(position).getFv_rv()));
                myViewHolder.txtProdtTotFv.setText("Total PV : "+String.valueOf(selectProductList.get(position).getTotalFVRV()));
                myViewHolder.txtProdtTotRcp.setText("Total Amount : "+String.valueOf(selectProductList.get(position).getTotalRcp()));
                myViewHolder.txtProdtTotPrice.setText("Total Mrp : "+String.valueOf(selectProductList.get(position).getTotalMrp()));

                int totQty=0;
                double totMrp=0;
                double totRcp=0;
                double totFv=0;
                double totDiscount=0;

                totMrp=selectProductList.get(position).getTotalMrp();
                totRcp=selectProductList.get(position).getRcp();

                totDiscount= totMrp- totRcp;
                myViewHolder.txtProdtDiscount.setText("Total Discount : "+String.valueOf(totDiscount));
            }
            else if(holder instanceof MyFooter){
                final MyFooter myFooter = (MyFooter) holder;
                int totQty=0;
                double totMrp=0;
                double totRcp=0;
                double totFv=0;
                double totDiscount=0;
                for(int i=0;i < selectProductList.size();i++){
                    totMrp= totMrp+ selectProductList.get(i).getTotalMrp();
                    totRcp= totRcp+ selectProductList.get(i).getTotalRcp();
                    totFv=totFv+ selectProductList.get(i).getTotalFVRV();
                    totQty=totQty+ selectProductList.get(i).getProdusQuantity();
                }
                double tot_dscount= totMrp - totRcp;
                myFooter.txtTotPrice.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+ String.valueOf(totMrp));
                myFooter.txtTotFv.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+ String.valueOf(totFv));
                myFooter.txtTotRcp.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+String.valueOf(totRcp));
                myFooter.txtDiscount.setText(mContext.getResources().getString(R.string.rs_symbol)+" "+String.valueOf(tot_dscount));
                myFooter.txtTotQty.setText("Total Qty : " +totQty);
                myFooter.txtDisCal.setText("( "+String.valueOf(totMrp) + " - "  +String.valueOf(totRcp)+" )");
                //myFooter.txtDiscount.set

            }


        }
   // }catch (Exception e){
   //     e.printStackTrace();
   // }

}

    @Override
    public int getItemViewType(int position) {
        //return cartList.get(position) == null ? TYPE_FOOTER : TYPE_ITEM;
        if (position == selectProductList.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);


    }




    private boolean isPositionFooter(int position) {
        return position == selectProductList.size();
    }

    /*@Override
    public int getItemCount() {
        //return cartList == null ? 0 : cartList.size();
        return (null != cartList ? cartList.size() : 0);
    }*/
    @Override
    public int getItemCount() {
        if (selectProductList == null) {
            return 0;
        }

        if (selectProductList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return selectProductList.size() + 1;
    }


   /* public interface HotelListAdapterListener {
        void onHotelSelected(HotelSearchResponse contact);

    }
*/






}
