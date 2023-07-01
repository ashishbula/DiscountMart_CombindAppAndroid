package in.discountmart.utility_services.travel.hotel.hotel_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.discountmart.R;

public class HotelSortByAdapter extends RecyclerView.Adapter<HotelSortByAdapter.MyViewHolder> {

    public Context mContext;
    public List<String> shortTextList;
    private HotelShortAdapterListener listener;
    int selectedPosition=-1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtShort, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtShort = view.findViewById(R.id.hotel_sort_item_text);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    int pos=getAdapterPosition();
                    listener.onTextSelected(shortTextList.get(getAdapterPosition()));

                    selectedPosition = getAdapterPosition();
                    //notifyItemChanged(selectedPosition);
                    notifyDataSetChanged();
                }
            });
        }
    }


    public HotelSortByAdapter(Context context, List<String> shortList,HotelShortAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.shortTextList = shortList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_hotel_sort_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String promo = shortTextList.get(position);
        holder.txtShort.setText(shortTextList.get(position));

        if(selectedPosition==position){
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.colorprimary_bg_roundbox));
            holder.txtShort.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        else
        {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.full_round_corner_white_box));
            holder.txtShort.setTextColor(mContext.getResources().getColor(R.color.black));
        }
       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return shortTextList.size();
    }


    public interface HotelShortAdapterListener {
        void onTextSelected(String item);
    }
}
