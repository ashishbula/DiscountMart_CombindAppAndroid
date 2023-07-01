package in.discountmart.utility_services.travel.flight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.travel.flight.flight_model.flight_response_model.GetCityListResponse;

public class FlightCityListAdapter extends RecyclerView.Adapter<FlightCityListAdapter.MyViewHolder> implements Filterable {

    public Context mContext;
    public List<GetCityListResponse> contactList;
    private List<GetCityListResponse> contactListFiltered;
    private CityListAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.flight_city_list_txt_name);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public FlightCityListAdapter(Context context, List<GetCityListResponse> contactList, CityListAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_flight_city_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GetCityListResponse contact = contactListFiltered.get(position);
        holder.name.setText(contact.getCityName());


       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<GetCityListResponse> filteredList = new ArrayList<>();
                    for (GetCityListResponse row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        /*if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }*/
                        if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<GetCityListResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CityListAdapterListener {
        void onContactSelected(GetCityListResponse contact);
    }
}
