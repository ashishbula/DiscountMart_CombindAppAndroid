package in.discountmart.business.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.NewsResponse;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter {
    @NonNull
    ArrayList<NewsResponse.NewsList> detailArrayList;
    private Context mContext;
    private Resources mResources;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtDetail;
        public TextView txtType;
        public CardView cardViewItem;


        public MyViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.news_item_txt_title);
            txtDetail = (TextView) view.findViewById(R.id.news_item_txt_detail);
            txtType = (TextView) view.findViewById(R.id.news_item_txt_type);

            cardViewItem = (CardView) view.findViewById(R.id.news_item_cardView_item);

        }
    }

    public NewsAdapter(ArrayList<NewsResponse.NewsList> arrayList, Context context) {
        this.detailArrayList = arrayList;
        this.mContext = context;
        //this.recyclerView = recyclerView;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View view = LayoutInflater.from(mContext).inflate(R.layout.business_news_item, parent, false);
        vh= new MyViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            NewsResponse.NewsList leaderList = detailArrayList.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            try{
                if(position % 2==0){
                    myViewHolder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                }
                else {
                    myViewHolder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                }
                myViewHolder.txtTitle.setText(detailArrayList.get(position).getNewshead());

                myViewHolder.txtDetail.setText(Html.fromHtml(detailArrayList.get(position).getNewsdetail()));
                myViewHolder.txtType.setText(detailArrayList.get(position).getType());

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return detailArrayList.size();
    }

}
