package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.model_business.SingleItemModel;

import java.util.ArrayList;


public class SingleTextItemRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<SingleItemModel> itemList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;
    ItemSelectListener mListener;

    private boolean isLoadingAdded = false;


    public SingleTextItemRecyAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        itemList=new ArrayList<>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }
   /* public DownlineDetailAdapter(Context context) {
        //this.fragment2=fragmet;
        this.mContext = context;
        downlineDetailsList=new ArrayList<DownlineDetailResponse.DownlineDetails>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }*/



    public void setList(ArrayList<SingleItemModel> list, ItemSelectListener listener) {
        this.itemList = list;
        this.mListener=listener;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        viewHolder = getViewHolder(parent, inflater);
        /*switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.load_more_progressbar, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }*/
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.single_text_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        SingleItemModel itemModel = itemList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.txtName.setText(itemList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

   /* @Override
    public int getItemViewType(int position) {
        return (position == DetailsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }*/

    /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public CardView cardViewItem;
        public TextView Status;
        public TextView Sno;


        public MyViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.single_item_txt_name);

            //cardViewItem=(CardView)view.findViewById(R.id.complain_detail_item_cardView_item);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    mListener.onItemClick(itemList.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface ItemSelectListener {
        void onItemClick(SingleItemModel item);
    }

}
