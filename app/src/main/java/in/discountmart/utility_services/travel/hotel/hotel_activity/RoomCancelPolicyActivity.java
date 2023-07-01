package in.discountmart.utility_services.travel.hotel.hotel_activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model.RoomCancelPolicy;

public class RoomCancelPolicyActivity extends AppCompatActivity {

    LinearLayout layoutHeader;
    TextView txtCancelDetail;
    TextView txtCancelCharge;
    TextView txtFromDate;
    TextView txtToDate;

    String strPolicyDetail="";
    String strFromDate="";
    String strToDate="";
    String strCancelCharge="";
    ArrayList<RoomCancelPolicy> roomCancelPolicies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_room_cancel_policy);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {
            txtCancelCharge=(TextView)findViewById(R.id.room_cancel_policy_act_txt_charge);
            txtFromDate=(TextView)findViewById(R.id.room_cancel_policy_act_txt_fromdate);
            txtToDate=(TextView)findViewById(R.id.room_cancel_policy_act_txt_todate);
            txtCancelDetail=(TextView)findViewById(R.id.room_cancel_policy_act_txt_detail);
            layoutHeader=(LinearLayout) findViewById(R.id.room_cancel_policy_act_layout_header);
            Bundle bundle=new Bundle();
            bundle=getIntent().getExtras();

            if(bundle != null){

                roomCancelPolicies=(ArrayList<RoomCancelPolicy>)bundle.getSerializable("CancelPolicy");
                strPolicyDetail=bundle.getString("Policy");

                txtCancelDetail.setText(strPolicyDetail);

                if(roomCancelPolicies.size() > 0){
                    for (int r=0;r < roomCancelPolicies.size(); r++){
                        txtCancelCharge.setText("Cancel Charge - " + roomCancelPolicies.get(r).getCharge()+ "%");
                        txtFromDate.setText("From Date - " + roomCancelPolicies.get(r).getFromDate());
                        txtToDate.setText("To Date - " + roomCancelPolicies.get(r).getToDate());
                    }
                }
            }

            layoutHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
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
