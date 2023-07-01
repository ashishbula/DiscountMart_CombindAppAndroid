package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.BankListResponse;

import java.util.ArrayList;



public class BankListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<BankListResponse.BankList> bankListArrayList;
    Context mContext;

    public BankListAdapter(Context context, ArrayList<BankListResponse.BankList> bankLists) {
        super();
        this.mContext = context;
        this.bankListArrayList = bankLists;

    }

    @Override
    public int getCount() {
        return bankListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return bankListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        BankListResponse.BankList bankListRes= bankListArrayList.get(position);
       /* if(position % 2==0){

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }
        else {

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray5));
        }*/

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(bankListArrayList.get(position).getBankname());



        return itemView;
    }
}
