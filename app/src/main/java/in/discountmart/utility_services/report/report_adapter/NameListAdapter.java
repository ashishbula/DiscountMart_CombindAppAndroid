package in.discountmart.utility_services.report.report_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;

public class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.MyViewHolder> {

    public Context mContext;
    public ArrayList<String> shortModelList;
    private ListAdapterListener listener;
    public String strReport="";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtShort;
        public TextView txtBlank;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtShort = view.findViewById(R.id.list_item_txt_name);
            //txtBlank = view.findViewById(R.id.list_item_txt_blank);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onSelected(shortModelList.get(getAdapterPosition()));
                }
            });
        }
    }


    public NameListAdapter(Context context, ArrayList<String> shortList,ListAdapterListener listener,String report) {
        this.mContext = context;
        this.listener = listener;
        this.shortModelList = shortList;
        this.strReport=report;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_single_text_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String promo = shortModelList.get(position);


        /*if(strReport.contentEquals(AppConstants.TAG_FUND)){
            holder.txtShort.setGravity(Gravity.CENTER);
            holder.txtBlank.setVisibility(View.GONE);
        }

        else if(strReport.contentEquals(AppConstants.TAG_REPORT)){
            holder.txtShort.setGravity(Gravity.RIGHT);
            holder.txtBlank.setVisibility(View.VISIBLE);
        }

        else if(strReport.contentEquals(AppConstants.TAG_ACCOUNT)){
            holder.txtShort.setGravity(Gravity.LEFT);
            holder.txtBlank.setVisibility(View.VISIBLE);
        }*/


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


    public interface ListAdapterListener {
        void onSelected(String item);
    }
}
