package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.MemberRelationListResponse;

import java.util.ArrayList;


public class MemberRelationAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<MemberRelationListResponse.MemberRelation> memRListArrayList;
    Context mContext;

    public MemberRelationAdapter(Context context, ArrayList<MemberRelationListResponse.MemberRelation> memberRelations) {
        super();
        this.mContext = context;
        this.memRListArrayList = memberRelations;

    }

    @Override
    public int getCount() {
        return memRListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return memRListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        MemberRelationListResponse.MemberRelation bankListRes= memRListArrayList.get(position);

        //View rowview = flater.inflate(R.layout.listitems_layout,null,true);
        //View view = mInflater.inflate(mResource, parent, false);
        // LayoutInflater inflater = ( mcontext.getLayoutInflater();
        //View row = inflater.inflate(R.layout.row, parent, false);

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(memRListArrayList.get(position).getMemrel());



        return itemView;
    }
}
