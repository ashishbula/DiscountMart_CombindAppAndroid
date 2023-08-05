package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.DeliveryCenterResponse;

public class DeliveryCenterAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<DeliveryCenterResponse.DeliveryCenter> arrayList;
    Context mContext;

    public DeliveryCenterAdapter(Context context, ArrayList<DeliveryCenterResponse.DeliveryCenter> bankLists) {
        super();
        this.mContext = context;
        this.arrayList = bankLists;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        DeliveryCenterResponse.DeliveryCenter bankListRes= arrayList.get(position);
       /* if(position % 2==0){

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }
        else {

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray5));
        }*/

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(arrayList.get(position).getCentername());

        return itemView;
    }
}
