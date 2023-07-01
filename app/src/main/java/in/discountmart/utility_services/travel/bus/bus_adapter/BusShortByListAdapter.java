package in.discountmart.utility_services.travel.bus.bus_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.discountmart.R;

public class BusShortByListAdapter extends RecyclerView.Adapter<BusShortByListAdapter.MyViewHolder> {

    public Context mContext;
    public List<String> shortModelList;
    private BusShortAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView txtShort, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtShort = view.findViewById(R.id.fight_shortby_item_checktxt_text);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onSelected(shortModelList.get(getAdapterPosition()));
                }
            });
        }
    }


    public BusShortByListAdapter(Context context, List<String> shortList, BusShortAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.shortModelList = shortList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_flight_shortby_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String promo = shortModelList.get(position);
        holder.txtShort.setText(shortModelList.get(position));


       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return shortModelList.size();
    }


    public interface BusShortAdapterListener {
        void onSelected(String item);
    }
}