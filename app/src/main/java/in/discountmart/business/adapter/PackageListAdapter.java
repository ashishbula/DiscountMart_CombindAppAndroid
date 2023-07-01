package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.PackageListResponse;

import java.util.ArrayList;

/**
 * Created by The Rock on 3/22/2018.
 */

public class PackageListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<PackageListResponse.PackageList> packageListArrayList;
    Context mContext;

    public PackageListAdapter(Context context, ArrayList<PackageListResponse.PackageList> packageLists) {
        super();
        this.mContext = context;
        this.packageListArrayList = packageLists;

    }

    @Override
    public int getCount() {
        return packageListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return packageListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_text_item_layout, parent, false);
        PackageListResponse.PackageList packageListRes= packageListArrayList.get(position);

        //View rowview = flater.inflate(R.layout.listitems_layout,null,true);
        //View view = mInflater.inflate(mResource, parent, false);
        // LayoutInflater inflater = ( mcontext.getLayoutInflater();
        //View row = inflater.inflate(R.layout.row, parent, false);

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.txt_item_name);
        textViewNameList.setText(packageListArrayList.get(position).getPkgname());



        return itemView;
    }
}
