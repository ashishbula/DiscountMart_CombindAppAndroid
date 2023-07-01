package in.discountmart.utility_services.travel.bus.bus_activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_adapter.BusCancelPolicyAdapter;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.utilities.DividerItemDecoration;

public class BusCancellationPolicyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BusSearchListResponse.CancellationCharge> cancellationArrayList;
    BusCancelPolicyAdapter adapter;

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_bus_cancellation_policy);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        view = findViewById(R.id.content);

        try {
            recyclerView=(RecyclerView)findViewById(R.id.bus_cancel_policy_act_act_recycler_list);

            Bundle bundle=getIntent().getExtras();

            if(bundle != null){
                cancellationArrayList=new ArrayList<BusSearchListResponse.CancellationCharge>((Collection<? extends BusSearchListResponse.CancellationCharge>) bundle.getSerializable("Policy"));

                if(cancellationArrayList.size() > 0){

                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0));
                    adapter=new BusCancelPolicyAdapter(this,cancellationArrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        return true;
    }
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
       /* if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

    }
}
