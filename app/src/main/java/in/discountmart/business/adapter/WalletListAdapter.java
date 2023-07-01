package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.WalletListResponse;

import java.util.ArrayList;


public class WalletListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<WalletListResponse.WalletList> arrayList;
    Context mContext;

    public WalletListAdapter(Context context, ArrayList<WalletListResponse.WalletList> bankLists) {
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
        WalletListResponse.WalletList bankListRes= arrayList.get(position);
       /* if(position % 2==0){

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }
        else {

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray5));
        }*/

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(arrayList.get(position).getWalletname());



        return itemView;
    }
}
