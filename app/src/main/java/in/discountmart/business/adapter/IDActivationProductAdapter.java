package in.discountmart.business.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.activity.IDActivationProduct;
import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.model_business.responsemodel.RepurchaseProductResponse;

import java.util.ArrayList;
import java.util.Collections;

import in.discountmart.business.model_business.AddProductModel;
import in.discountmart.business.model_business.responsemodel.RepurchaseProductResponse;

public class IDActivationProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public static ArrayList<RepurchaseProductResponse.ProductList> productArrayList;
    public static ArrayList<AddProductModel> addProductList;
    // public static ArrayList<ProductQuantity> prodQuantityList;
    IDActivationProduct productFragment;

    int quantity = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProdtName;
        public TextView txtProdtPrice;
        public TextView txtProdtPV;
        public TextView txtProdtDP;
        public TextView txtIncrement;
        public TextView txtDecrement;
        public TextView txtQuantity;

        public ImageView imgProdtImg;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);

            txtProdtName = view.findViewById(R.id.product_item_txt_pname);
            txtProdtPrice = view.findViewById(R.id.product_item_txt_price);
            txtProdtPV = view.findViewById(R.id.product_item_txt_pv);
            txtProdtDP = view.findViewById(R.id.product_item_txt_dp);
            txtIncrement = view.findViewById(R.id.product_item_txt_increse);
            txtDecrement = view.findViewById(R.id.product_item_txt_decrese);
            txtQuantity = view.findViewById(R.id.product_item_txt_number);
            checkBox = view.findViewById(R.id.product_item_cnkbox);

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onHotelSelected(hotelSearchList.get(getAdapterPosition()));
                }
            });*/
        }
    }

   /* public static class MyFooter extends RecyclerView.ViewHolder{
        public  LinearLayout layoutFooter;
        public  TextView txtQuantity;
        public  TextView txtPrice;
        public  TextView txtOrderNow;
        public MyFooter(@NonNull View itemHeaderView) {
            super(itemHeaderView);
            layoutFooter=(LinearLayout)itemHeaderView.findViewById(R.id.cart_item_footer_layout);
            txtPrice=(TextView)itemHeaderView.findViewById(R.id.product_item_footer_totprice);
            txtQuantity=(TextView)itemHeaderView.findViewById(R.id.product_item_footer_quantity);
            txtOrderNow=(TextView)itemHeaderView.findViewById(R.id.product_item_footer_order);

        }
    }*/

    public IDActivationProductAdapter(Context context, ArrayList<RepurchaseProductResponse.ProductList> productList, IDActivationProduct fragment) {
        this.mContext = context;
        this.productFragment = fragment;
        this.productArrayList = productList;
        //this.contactListFiltered = contactList;
        addProductList = new ArrayList<AddProductModel>();
    }

    public void setData(Context context, ArrayList<RepurchaseProductResponse.ProductList> productList, IDActivationProduct fragment) {
        this.mContext = context;
        this.productFragment = fragment;
        this.productArrayList = productList;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new MyViewHolder(itemView);

      /*  if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_item, parent, false);
            return new MyViewHolder(itemView);
        }

        if (viewType == TYPE_FOOTER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_footer_layout, parent, false);
            return new MyFooter(layoutView);
        }*/

        //throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {

            if (holder instanceof MyViewHolder) {
                final MyViewHolder myViewHolder = (MyViewHolder) holder;
                final int pos = position;
                //myViewHolder.layoutItemView.setVisibility(View.VISIBLE);
                //final FlightSearchResponse contact = flightSearchList.get(position);

                myViewHolder.txtProdtName.setText(productArrayList.get(position).getProductname());

                myViewHolder.txtProdtPrice.setText(productArrayList.get(position).getShipping());
                myViewHolder.txtProdtPV.setText(productArrayList.get(position).getPv());
                myViewHolder.txtProdtDP.setText(productArrayList.get(position).getDp());

                //prodQuantityList= new ArrayList<ProductQuantity>();
                quantity = Integer.parseInt(productArrayList.get(position).getQty());
                myViewHolder.txtQuantity.setText(String.valueOf(productArrayList.get(position).getQty()));
                if (quantity == 0) {
                    //myViewHolder.txtQuantity.setText(String.valueOf(quantity));
                    //roomList.get(position).setAdult(String.valueOf(quantity));
                    // roomList.get(position).setAdultcount(quantity);
                    myViewHolder.txtDecrement.setFocusable(false);
                    myViewHolder.txtDecrement.setClickable(false);
                    myViewHolder.txtIncrement.setFocusable(true);
                    myViewHolder.txtIncrement.setClickable(true);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                        myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));

                    }
                } else {
                    myViewHolder.txtDecrement.setFocusable(true);
                    myViewHolder.txtDecrement.setClickable(true);
                    myViewHolder.txtIncrement.setFocusable(true);
                    myViewHolder.txtIncrement.setClickable(true);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                        myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

                    }
                }

                /*Button Adult Increment Click Listener*/
                myViewHolder.txtIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quantity = Integer.parseInt(productArrayList.get(position).getQty());
                        double totPrice = 0;
                        double totRcp = 0;
                        double totFVRV = 0;
                        double prodtPrice = Double.parseDouble(productArrayList.get(position).getShipping());
                        double prodtRcp = Double.parseDouble(productArrayList.get(position).getDp()) + Double.parseDouble(productArrayList.get(position).getShipping());
                        double prodtFV = Double.parseDouble(productArrayList.get(position).getPv());
                        int prodQuantity = 0;

                        if (!productArrayList.get(position).isSelected()) {
                            Toast.makeText(mContext, "Please select product", Toast.LENGTH_SHORT).show();
                        } else {
                            if (quantity == 0 || quantity < 10) {
                                quantity = quantity + 1;

                                myViewHolder.txtQuantity.setText(String.valueOf(quantity));
                                //roomList.get(position).setAdult(String.valueOf(quantity));
                                productArrayList.get(position).setQty(String.valueOf(quantity));
                                myViewHolder.txtIncrement.setFocusable(true);
                                myViewHolder.txtIncrement.setClickable(true);
                                myViewHolder.txtDecrement.setFocusable(true);
                                myViewHolder.txtDecrement.setClickable(true);
                            } else if (quantity == 10) {
                                quantity = 10;
                                myViewHolder.txtQuantity.setText(String.valueOf(quantity));
                                // roomList.get(position).setAdult(String.valueOf(quantity));
                                productArrayList.get(position).setQty(String.valueOf(quantity));
                                myViewHolder.txtIncrement.setFocusable(false);
                                myViewHolder.txtIncrement.setClickable(false);
                                myViewHolder.txtDecrement.setFocusable(true);
                                myViewHolder.txtDecrement.setClickable(true);
                            }
                            if (quantity == 0) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                                    myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
                                }
                            } else if (quantity > 0 && quantity < 10) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                                    myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

                                }
                            } else if (quantity == 10) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
                                    myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

                                }
                            }
                            productArrayList.get(position).setQty(String.valueOf(quantity));
                            totPrice = prodtPrice * quantity;
                            totRcp = prodtRcp * quantity;
                            totFVRV = prodtFV * quantity;
                            AddProductModel addProductModel = new AddProductModel();
                            addProductModel.setProductname(productArrayList.get(position).getProductname());
                            addProductModel.setProductId(productArrayList.get(position).getProdid());
                            addProductModel.setMrp(Double.parseDouble(productArrayList.get(position).getShipping()));
                            addProductModel.setRcp(Double.parseDouble(productArrayList.get(position).getDp()));
                            addProductModel.setFv_rv(Double.parseDouble(productArrayList.get(position).getPv()));
                            addProductModel.setProdusQuantity(quantity);
                            addProductModel.setTotalMrp(totPrice);
                            addProductModel.setTotalRcp(totRcp);
                            addProductModel.setTotalFVRV(totFVRV);
                            addProductModel.setShipping(Double.parseDouble(productArrayList.get(position).getShipping()));
                            ArrayList<AddProductModel> tempList = new ArrayList<AddProductModel>();
                            tempList.add(addProductModel);

                            if (contains(addProductList, productArrayList.get(position).getProdid())) {
                                updateProduct(addProductModel);
                            } else {
                                addProduct(addProductModel);
                            }
                        }

                    }
                });

                /*Button Adult Decrement Click Listener*/
                myViewHolder.txtDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //increaseInteger(v);
                        //auldinteger=0;
                        quantity = Integer.parseInt(productArrayList.get(position).getQty());
                        double totPrice = 0;
                        double totRcp = 0;
                        double totFVRV = 0;

                        double prodtPrice = Double.parseDouble(productArrayList.get(position).getShipping());
                        double prodtRcp = Double.parseDouble(productArrayList.get(position).getDp())+Double.parseDouble(productArrayList.get(position).getShipping());
                        double prodtFV = Double.parseDouble(productArrayList.get(position).getPv());
                        if (quantity == 0) {
                            quantity = 0;
                            myViewHolder.txtQuantity.setText(String.valueOf(quantity));
                            //roomList.get(position).setAdult(String.valueOf(quantity));
                            // roomList.get(position).setAdultcount(quantity);
                            myViewHolder.txtDecrement.setFocusable(false);
                            myViewHolder.txtDecrement.setClickable(false);
                            myViewHolder.txtIncrement.setFocusable(true);
                            myViewHolder.txtIncrement.setClickable(true);
                        } else if (quantity > 0) {
                            quantity = quantity - 1;
                            myViewHolder.txtQuantity.setText(String.valueOf(quantity));
                            // roomList.get(position).setAdult(String.valueOf(quantity));
                            productArrayList.get(position).setQty(String.valueOf(quantity));
                            myViewHolder.txtDecrement.setFocusable(true);
                            myViewHolder.txtDecrement.setClickable(true);
                            myViewHolder.txtIncrement.setFocusable(true);
                            myViewHolder.txtIncrement.setClickable(true);
                        }

                        if (quantity == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                                myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));

                            }
                            productArrayList.get(position).setQty(String.valueOf(quantity));
                            productArrayList.get(position).setSelected(false);
                            myViewHolder.checkBox.setChecked(productArrayList.get(position).isSelected());
                        } else if (quantity > 0 && quantity < 10) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                                myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

                            }
                            productArrayList.get(position).setQty(String.valueOf(quantity));
                        } else if (quantity == 10) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));
                                myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

                            }
                            // roomList.get(position).setAdultcount(adult);
                        }

                        AddProductModel addProductModel = new AddProductModel();
                        addProductModel.setProductname(productArrayList.get(position).getProductname());
                        addProductModel.setProductId(productArrayList.get(position).getProdid());
                        addProductModel.setMrp(Double.parseDouble(productArrayList.get(position).getShipping()));
                        addProductModel.setRcp(Double.parseDouble(productArrayList.get(position).getDp()));
                        addProductModel.setFv_rv(Double.parseDouble(productArrayList.get(position).getPv()));
                        addProductModel.setProdusQuantity(quantity);
                        addProductModel.setShipping(Double.parseDouble(productArrayList.get(position).getShipping()));
                        totPrice = prodtPrice * quantity;
                        totRcp = prodtRcp * quantity;
                        totFVRV = prodtFV * quantity;
                        addProductModel.setTotalMrp(totPrice);
                        addProductModel.setTotalRcp(totRcp);
                        addProductModel.setTotalFVRV(totFVRV);
                        ArrayList<AddProductModel> tempList = new ArrayList<AddProductModel>();
                        tempList.add(addProductModel);
                        deleteProduct(addProductModel);
                    }
                });

                myViewHolder.checkBox.setChecked(productArrayList.get(position).isSelected());

                myViewHolder.checkBox.setTag(productArrayList.get(position));

                myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        float avalqty = 0;
                        RepurchaseProductResponse.ProductList contact = (RepurchaseProductResponse.ProductList) cb.getTag();

                        contact.setSelected(cb.isChecked());
                        productArrayList.get(pos).setSelected(cb.isChecked());

                        if (!productArrayList.get(pos).isSelected()) {

                            removeProduct(productArrayList.get(pos).getProdid());
                            productArrayList.get(pos).setQty("0");
                            quantity = Integer.parseInt(productArrayList.get(pos).getQty());
                            if (quantity == 0) {
                                myViewHolder.txtQuantity.setText(String.valueOf(quantity));
                                //roomList.get(position).setAdult(String.valueOf(quantity));
                                // roomList.get(position).setAdultcount(quantity);
                                myViewHolder.txtDecrement.setFocusable(false);
                                myViewHolder.txtDecrement.setClickable(false);
                                myViewHolder.txtIncrement.setFocusable(true);
                                myViewHolder.txtIncrement.setClickable(true);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    myViewHolder.txtIncrement.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                                    myViewHolder.txtDecrement.setBackgroundColor(mContext.getResources().getColor(R.color.LightGray));

                                }
                            }


                        } else {
                            productArrayList.size();
                        }
                    }
                });

            }
            /*else if(holder instanceof MyFooter){
                MyFooter footer=(MyFooter)holder;
                footer.layoutFooter.setVisibility(View.GONE);

            }*/
        }
    }

   /* @Override
    public int getItemViewType(int position) {
        //Return item type according to requirement

         //if (isPositionFooter(position))
         //   return TYPE_FOOTER;
       // else
        //    return TYPE_ITEM;
        if (position == productArrayList.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }*/

    private boolean isPositionFooter(int position) {
        return position == productArrayList.size() + 1;
    }

    @Override
    public int getItemCount() {
        return productArrayList == null ? 0 : productArrayList.size();
    }
    /*@Override
    public int getItemCount() {
        if (productArrayList == null) {
            return 0;
        }

        if (productArrayList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return productArrayList.size() + 1;
    }*/

   /* public interface HotelListAdapterListener {
        void onHotelSelected(HotelSearchResponse contact);

    }
*/

    public void addProduct(AddProductModel list) {
        try {
            if (list != null) {
                if (addProductList.size() == 0) {
                    int qty = 0;
                    double price = 0;
                    double TotalPV = 0;

                    addProductList = new ArrayList<AddProductModel>(Collections.singleton(list));
                    productFragment.layoutFooter.setVisibility(View.VISIBLE);

                    for (int i = 0; i < addProductList.size(); i++) {
                        int index = i + 1;
                        //int prodQty=addProductList.get(i).getProdusQuantity();
                        qty = addProductList.get(i).getProdusQuantity();
                        price = addProductList.get(i).getTotalRcp();
                        TotalPV = addProductList.get(i).getTotalFVRV();

                    }
                    productFragment.txtQuantity.setText("Qty : " + String.valueOf(qty));
                    productFragment.txtPrice.setText("Total Amount : " + String.valueOf(price));
                    int size = addProductList.size();
                    productFragment.txtTotProduct.setText("Product : - " + String.valueOf(size));
                    productFragment.txtTotPV.setText("Total PV : " + String.valueOf(TotalPV));

                } else {
                    int qty = 0;
                    double price = 0;
                    double TotalPV = 0;

                    ArrayList<AddProductModel> tempAddProdList = new ArrayList<AddProductModel>();
                    tempAddProdList = addProductList;

                    tempAddProdList.add(list);

                    addProductList = tempAddProdList;

                    for (int i = 0; i < addProductList.size(); i++) {
                        qty = qty + addProductList.get(i).getProdusQuantity();
                        price = price + addProductList.get(i).getTotalRcp();;
                        TotalPV = TotalPV + addProductList.get(i).getTotalFVRV();

                    }
                    productFragment.txtQuantity.setText("Qty : " + String.valueOf(qty));
                    productFragment.txtPrice.setText("Total Amount : " + String.valueOf(price));
                    int size = addProductList.size();
                    productFragment.txtTotProduct.setText("Product : - " + String.valueOf(size));
                    productFragment.txtTotPV.setText("Total PV : " + String.valueOf(TotalPV));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(AddProductModel list) {
        try {
            if (list != null) {
                int qty = 0;
                double price = 0;
                double TotalPV = 0;
                ArrayList<AddProductModel> tempAddProdList = new ArrayList<AddProductModel>();
                tempAddProdList = addProductList;
                for (int i = 0; i < tempAddProdList.size(); i++) {
                    if (list.getProductId().equals(tempAddProdList.get(i).getProductId())) {
                        tempAddProdList.get(i).setProdusQuantity(list.getProdusQuantity());
                        tempAddProdList.get(i).setTotalMrp(list.getTotalMrp());
                        tempAddProdList.get(i).setTotalRcp(list.getTotalRcp());
                        tempAddProdList.get(i).setTotalFVRV(list.getTotalFVRV());
                    }

                }
                addProductList = tempAddProdList;

                for (int i = 0; i < addProductList.size(); i++) {
                    qty = qty + addProductList.get(i).getProdusQuantity();
                    price = price + addProductList.get(i).getTotalRcp() ;;
                    TotalPV = TotalPV + addProductList.get(i).getTotalFVRV();
                }
                productFragment.txtQuantity.setText("Qty :" + String.valueOf(qty));
                productFragment.txtPrice.setText("Total Amount : " + String.valueOf(price));
                int size = addProductList.size();
                productFragment.txtTotProduct.setText("Product : " + String.valueOf(size));
                productFragment.txtTotPV.setText("Total PV : " + String.valueOf(TotalPV));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(AddProductModel list) {
        try {
            if (list != null) {
                AddProductModel productModel = list;

                int qty = 0;
                double price = 0;
                double TotalPV = 0;

                ArrayList<AddProductModel> tempList = new ArrayList<>();

                if (addProductList != null && addProductList.size() > 0) {
                    tempList = addProductList;
                    for (int i = 0; i < addProductList.size(); i++) {
                        if (productModel.getProductId().equals(addProductList.get(i).getProductId())) {
                            addProductList.get(i).setProdusQuantity(productModel.getProdusQuantity());
                            addProductList.get(i).setTotalMrp(productModel.getTotalMrp());
                            addProductList.get(i).setTotalRcp(list.getTotalRcp());
                            addProductList.get(i).setTotalFVRV(list.getTotalFVRV());
                        } else
                            addProductList.size();
                    }

                    for (int j = 0; j < addProductList.size(); j++) {
                        if (addProductList.get(j).getProdusQuantity() == 0) {
                            int index = j + 1;
                            addProductList.remove(j);
                        } else
                            addProductList.size();
                    }
                    if (addProductList.size() > 0) {
                        for (int k = 0; k < addProductList.size(); k++) {
                            qty = qty + addProductList.get(k).getProdusQuantity();
                            price = price + addProductList.get(k).getTotalRcp();;
                            TotalPV = TotalPV + addProductList.get(k).getTotalFVRV();
                        }
                        productFragment.txtQuantity.setText("Qty : " + String.valueOf(qty));
                        productFragment.txtPrice.setText("Total Amount : " + String.valueOf(price));
                        int size = addProductList.size();
                        productFragment.txtTotProduct.setText("Product : " + String.valueOf(size));
                        productFragment.txtTotPV.setText("Total PV : " + String.valueOf(TotalPV));
                    } else {
                        productFragment.layoutFooter.setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(String id) {
        try {
            int qty = 0;
            double price = 0;
            double TotalPV = 0;

            ArrayList<AddProductModel> tempList = new ArrayList<>();

            if (addProductList != null && addProductList.size() > 0) {
                tempList = addProductList;

                for (int j = 0; j < addProductList.size(); j++) {
                    if (id.equals(addProductList.get(j).getProductId())) {
                        int index = j + 1;
                        addProductList.remove(j);
                    } else
                        addProductList.size();
                }
                if (addProductList.size() > 0) {
                    for (int k = 0; k < addProductList.size(); k++) {
                        qty = qty + addProductList.get(k).getProdusQuantity();
                        price = price + addProductList.get(k).getTotalRcp();;
                        TotalPV = TotalPV + addProductList.get(k).getTotalFVRV();
                    }
                    productFragment.txtQuantity.setText("Qty : " + String.valueOf(qty));
                    productFragment.txtPrice.setText("Total Amount : " + String.valueOf(price));
                    int size = addProductList.size();
                    productFragment.txtTotProduct.setText("Product : " + String.valueOf(size));
                    productFragment.txtTotPV.setText("Total PV : " + String.valueOf(TotalPV));
                } else {
                    productFragment.layoutFooter.setVisibility(View.GONE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean contains(ArrayList<AddProductModel> list, String name) {
        for (AddProductModel item : list) {
            if (item.getProductId().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // method to access in activity after updating selection
    public ArrayList<RepurchaseProductResponse.ProductList> getOrderReceiveList() {
        return productArrayList;
    }

}