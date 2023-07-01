package in.discountmart.utility_services.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;

import in.discountmart.R;
import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility_services.billpayment.activity.BillPayServiceListActivity;
import in.discountmart.utility_services.billpayment.billpay_shared_preferance.BillPaySharedPreferance;
import in.discountmart.utility_services.fund.activity.AddFundActivity;
import in.discountmart.utility_services.fund.activity.FundRequestActivity;
import in.discountmart.utility_services.recharge.activity.DatacardActivity;
import in.discountmart.utility_services.recharge.activity.PostpaidActivity;
import in.discountmart.utility_services.recharge.activity.PrepaidActivity;
import in.discountmart.utility_services.recharge.activity.ServiceProviderActivity;
import in.discountmart.utility_services.recharge.recharge_shared_preferance.RechargeSharedPreferance;
import in.discountmart.utility_services.report.activity.BillPamentReportActivity;
import in.discountmart.utility_services.report.activity.BusBookReportActivity;
import in.discountmart.utility_services.report.activity.FlightBookReportActivity;
import in.discountmart.utility_services.report.activity.LedgerReportActivity;
import in.discountmart.utility_services.report.activity.RechargeReportActivity;
import in.discountmart.utility_services.travel.bus.bus_activity.BusSearchActivity;
import in.discountmart.utility_services.travel.flight.flight_activity.FlightSearchActivity;
import in.discountmart.utility_services.travel.hotel.hotel_activity.HotelSearchActivity;

public class MoreServicesActivity extends AppCompatActivity {

    LinearLayout serviceBus;
    LinearLayout serviceBusReport;
    LinearLayout serviceFlightReport;
    LinearLayout serviceBillReport;
    LinearLayout serviceRechargeReport;
    LinearLayout serviceLedgerReport;
    LinearLayout serviceFlight;
    LinearLayout serviceHotel;
    LinearLayout serviceTrain;
    LinearLayout serviceCab;
    LinearLayout servicePrepaid;
    LinearLayout servicePostpaid;
    LinearLayout serviceDataCard;
    LinearLayout serviceElectricity;
    LinearLayout serviceInsurance;
    LinearLayout serviceGas;
    LinearLayout serviceDth;
    LinearLayout serviceCreditCard;
    LinearLayout serviceWallet;
    LinearLayout serviceAddFund;
    LinearLayout serviceFundRequest;
    LinearLayout serviceFundRequestStatus;
    private SliderLayout mSlider;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_services);
        try {

            //imageViewGift = (ImageView) view.findViewById(R.id.homedash_ut_layout_giftvoucher);

            // Enabling Up / Back navigation
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("More & All Service");

            serviceBus = (LinearLayout) findViewById(R.id.more_service_act_bus);
            serviceFlight = (LinearLayout) findViewById(R.id.more_service_act_flight);
            serviceHotel = (LinearLayout) findViewById(R.id.more_service_act_hotel);
            serviceTrain = (LinearLayout) findViewById(R.id.more_service_act_train);
           // serviceCab = (LinearLayout) findViewById(R.id.more_service_act_c);
            servicePrepaid = (LinearLayout) findViewById(R.id.more_service_act_prepaid);
            servicePostpaid = (LinearLayout) findViewById(R.id.more_service_act_postpaid);
            serviceDataCard = (LinearLayout) findViewById(R.id.more_service_act_datacard);
            serviceElectricity = (LinearLayout) findViewById(R.id.more_service_act_electricity);
            serviceInsurance = (LinearLayout) findViewById(R.id.more_service_act_insurance);
            serviceDth = (LinearLayout) findViewById(R.id.more_service_act_dth);
            serviceGas = (LinearLayout) findViewById(R.id.more_service_act_gas);

            serviceCreditCard = (LinearLayout) findViewById(R.id.more_service_act_credit);
            serviceWallet = (LinearLayout) findViewById(R.id.more_service_act_wallet);
            serviceAddFund = (LinearLayout) findViewById(R.id.more_service_act_addfund);
            serviceFundRequest = (LinearLayout) findViewById(R.id.more_service_act_fundreq);

           // serviceFundRequestStatus = (LinearLayout) findViewById(R.id.more_service_act_fu);
            serviceBusReport = (LinearLayout) findViewById(R.id.more_service_act_bus_report);
            serviceFlightReport = (LinearLayout) findViewById(R.id.more_service_act_filght_report);
            serviceBillReport = (LinearLayout) findViewById(R.id.more_service_act_bill_report);
            serviceRechargeReport = (LinearLayout) findViewById(R.id.more_service_act_recharge_report);
            serviceLedgerReport = (LinearLayout) findViewById(R.id.more_service_act_transaction);
            
            mSlider = (SliderLayout) findViewById(R.id.more_service_act_slider1);
           

           
            ArrayList<Integer> images1 = new ArrayList<>();
            images1.add(R.mipmap.utility_slider_1);
            images1.add(R.mipmap.utility_slider_2);
            images1.add(R.mipmap.utility_slider_3);
            //images1.add(R.mipmap.utility_slider_4);
            //images1.add(R.mipmap.utility_slider_5);
            //images1.add(R.mipmap.utility_slider_6);
            //images1.add(R.mipmap.utility_slider_7);
            //images1.add(R.mipmap.utility_slider_8);
            //images1.add(R.mipmap.utility_slider_9);
           // intiatSlider2();
            intiatSlider(images1);


            /*Service Add Fund */
            serviceLedgerReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, LedgerReportActivity.class);
                    //intent.putExtra("Home", "true");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Add Fund */
            serviceWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, MyWalletActivity.class);
                    //intent.putExtra("Home", "true");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Add Fund */
            serviceAddFund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Bundle bundle=new Bundle();
//                    bundle.putString("Type","Add Fund");
//                    bundle.putString("Home","Add Fund");
//                    ChooseOptionBottomDialog fragment=new ChooseOptionBottomDialog();
//                    fragment.setArguments(bundle);
//
//                    fragment.show(getChildFragmentManager(),"");
                    Intent intent = new Intent(MoreServicesActivity.this, AddFundActivity.class);
                    intent.putExtra("Home", "true");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Fund Request */
            serviceFundRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Bundle bundle=new Bundle();
//                    bundle.putString("Type","Fund Request");
//                    ChooseOptionBottomDialog fragment=new ChooseOptionBottomDialog();
//                    fragment.setArguments(bundle);
//
//                    fragment.show(getChildFragmentManager(),"");
                    Intent intent = new Intent(MoreServicesActivity.this, FundRequestActivity.class);
                    //intent.putExtra("Edit", "false");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Flight*/
            serviceFlight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, FlightSearchActivity.class);
                    intent.putExtra("Edit", "false");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Bus*/
            serviceBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ledgerIntent=new Intent(MoreServicesActivity.this, BusSearchActivity.class);
                    startActivity(ledgerIntent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);

                }
            });

            /*Service Hotel*/
            serviceHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, HotelSearchActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Cab*/
            /*layoutCab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialogUtils.showDialogWithNeutaral(context, "Notification!", "Cab service is coming soon..");

    //                    Intent intent = new Intent(context, CabSearchActivity.class);
    //                    startActivity(intent);
    //                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                });*/

            /*Service Postpaid*/
            servicePostpaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, PostpaidActivity.class);
                    RechargeSharedPreferance.setServiceType(MoreServicesActivity.this, "T");
                    intent.putExtra("Type", "T");
                    intent.putExtra("Home", "false");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*click Listener Prepaid*/
            servicePrepaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, PrepaidActivity.class);
                    RechargeSharedPreferance.setServiceType(MoreServicesActivity.this, "M");
                    intent.putExtra("Type", "M");
                    intent.putExtra("Home", "false");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*click Listener datacard*/
            serviceDataCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, DatacardActivity.class);
                    RechargeSharedPreferance.setServiceType(MoreServicesActivity.this, "M");
                    intent.putExtra("Type", "M");
                    intent.putExtra("Home", "false");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service DTH*/
            serviceDth.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MoreServicesActivity.this, ServiceProviderActivity.class);
                    RechargeSharedPreferance.setServiceType(MoreServicesActivity.this, "D");
                    RechargeSharedPreferance.setServiceTypeId(MoreServicesActivity.this, "2");
                    intent.putExtra("Type", "D");
                    intent.putExtra("Home", "true");
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });


            /*click Listener Insurance*/
            serviceInsurance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // ((MainActivity_utility)context).replaceFragment(new FlightSerachFragment(),"FlightSearchFragment",null);
                    //((MainActivity_utility)context).toolBarTitle("Flight");
                    Intent intent = new Intent(MoreServicesActivity.this, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(MoreServicesActivity.this, "I");
                    BillPaySharedPreferance.setServiceTypeID(MoreServicesActivity.this, "3");
                    //intent.putExtra("ServiceType","G");
                    //intent.putExtra("ServiceTypeId","2");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    //((BaseFragment)context).startActivity(intent);
                }
            });

            /*click Listener Credit card*/
            serviceCreditCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // ((MainActivity_utility)context).replaceFragment(new FlightSerachFragment(),"FlightSearchFragment",null);
                    //((MainActivity_utility)context).toolBarTitle("Flight");
                    Intent intent = new Intent(MoreServicesActivity.this, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(MoreServicesActivity.this, "C");
                    BillPaySharedPreferance.setServiceTypeID(MoreServicesActivity.this, "4");
                    //intent.putExtra("ServiceType","CC");
                    //intent.putExtra("ServiceTypeId","4");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    //((BaseFragment)context).startActivity(intent);
                }
            });

            /*Service Gas*/
            serviceGas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(MoreServicesActivity.this, "G");
                    BillPaySharedPreferance.setServiceTypeID(MoreServicesActivity.this, "2");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });

            /*Service Electricity*/
            serviceElectricity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(MoreServicesActivity.this, "E");
                    BillPaySharedPreferance.setServiceTypeID(MoreServicesActivity.this, "1");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });
            /*Service Electricity*/
            serviceTrain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialogUtils.showDialogWithNeutaral(MoreServicesActivity.this, "Notification!", "Contact to Support for Booking");
                }
            });

            /*Service Flight Report*/
            serviceFlightReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, FlightBookReportActivity.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });

            /*Service Bus Report*/
            serviceBusReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, BusBookReportActivity.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });

            /*Service Bill Report*/
            serviceBillReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, BillPamentReportActivity.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });

            /*Service Recharge Report*/
            serviceRechargeReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreServicesActivity.this, RechargeReportActivity.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });


        } catch (Exception e) {
                e.printStackTrace();
            }
    }

    //Back Press Arrow o ToolBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();

        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        return true;
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK ))
        {
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            if (fetchBusSearch != null || fetchBusSearch == null)
                fetchBusSearch.cancel();
            //onBackPressed();

        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        super.onBackPressed();
       /* if (fetchBusSearch != null){
            fetchBusSearch.cancel();
            finish();
        }*/
        finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

    }
    public void intiatSlider(ArrayList<Integer> Image) {

        mSlider.removeAllSliders();

        ArrayList<HashMap<Integer, Integer>> url_maps_array = new ArrayList<HashMap<Integer, Integer>>();

        for (int i = 0; i < Image.size(); i++) {
            HashMap<Integer, Integer> url_maps = new HashMap<Integer, Integer>();
            url_maps.put(i, Image.get(i));
            url_maps_array.add(url_maps);
        }
        for (int i = 0; i < url_maps_array.size(); i++) {
            for (Integer name : url_maps_array.get(i).keySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                //TextSliderView.. USE THIS IF U WANT DESCRIPTION ON SLIDER

                // initialize a SliderLayout
                textSliderView
                        //.description(name)//FOR DESC
                        .image(url_maps_array.get(i).get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        int j = Integer.parseInt(slider.getBundle().get("extra").toString());
                       /* if(j==0)
                            Utilities.goToPage(HomeActivity.this, HotelSearchActivity.class, null);*/
                    }
                });

                //add your extra informatio
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putInt("extra", name);

                mSlider.addSlider(textSliderView);
            }
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(new ViewPagerEx.SimpleOnPageChangeListener());
    }
}