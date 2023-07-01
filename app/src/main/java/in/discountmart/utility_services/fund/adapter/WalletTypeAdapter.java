package in.discountmart.utility_services.fund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.fund.fund_model.response.WalletTypeResponse;

public class WalletTypeAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<WalletTypeResponse> walletListArrayList;
    Context mContext;

    public WalletTypeAdapter(Context context, ArrayList<WalletTypeResponse> walletList) {
        super();
        this.mContext = context;
        this.walletListArrayList = walletList;
    }
    @Override
    public int getCount() {
        return walletListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return walletListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_single_text_item, parent, false);
        WalletTypeResponse packageListRes= walletListArrayList.get(position);

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_item_txt_name);
        textViewNameList.setText(walletListArrayList.get(position).getWalletType());

        return itemView;
    }
}
