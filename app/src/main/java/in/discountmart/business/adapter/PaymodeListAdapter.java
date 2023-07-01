package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.PayModeListResponse;

import java.util.ArrayList;

/**
 * Created by Dell on 19-01-2018.
 */

public class PaymodeListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<PayModeListResponse.PaymodeList> paymodeListArrayList;
    Context mContext;

    public PaymodeListAdapter(Context context, ArrayList<PayModeListResponse.PaymodeList> paymodeLists) {
        super();
        this.mContext = context;
        this.paymodeListArrayList = paymodeLists;

    }

    @Override
    public int getCount() {
        return paymodeListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymodeListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        PayModeListResponse.PaymodeList paymodListRes= paymodeListArrayList.get(position);

        //View rowview = flater.inflate(R.layout.listitems_layout,null,true);
        //View view = mInflater.inflate(mResource, parent, false);
        // LayoutInflater inflater = ( mcontext.getLayoutInflater();
        //View row = inflater.inflate(R.layout.row, parent, false);

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(paymodeListArrayList.get(position).getPaymodename());



        return itemView;
    }

}

