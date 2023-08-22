package in.discountmart.utility_services.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;


import in.base.ui.BaseFragment;
import in.discountmart.R;

import in.discountmart.utility.AlertDialogUtils;
import in.discountmart.utility_services.activity.MainActivity_utility;
import in.discountmart.utility_services.activity.MyWalletActivity;
import in.discountmart.utility_services.billpayment.activity.BillPayServiceListActivity;
import in.discountmart.utility_services.billpayment.billpay_shared_preferance.BillPaySharedPreferance;
import in.discountmart.utility_services.fund.activity.AddFundActivity;
import in.discountmart.utility_services.fund.activity.FundRequestActivity;
import in.discountmart.utility_services.recharge.activity.PostpaidActivity;
import in.discountmart.utility_services.recharge.activity.PrepaidActivity;
import in.discountmart.utility_services.recharge.activity.ServiceProviderActivity;
import in.discountmart.utility_services.recharge.recharge_shared_preferance.RechargeSharedPreferance;
import in.discountmart.utility_services.report.activity.LedgerReportActivity;
import in.discountmart.utility_services.travel.bus.bus_activity.BusSearchActivity;
import in.discountmart.utility_services.travel.flight.flight_activity.FlightSearchActivity;
import in.discountmart.utility_services.travel.hotel.hotel_activity.HotelSearchActivity;


public class HomeFragment_utility extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
{

    ImageView imageViewGift;
    RecyclerView recycoupon;
    LinearLayout layoutFlight;
    LinearLayout layoutTrain;
    LinearLayout layoutHotel;
    LinearLayout layoutBus;
    LinearLayout layoutCab;
    LinearLayout serviceWallet;
    LinearLayout serviceTransaction;
    LinearLayout serviceAddFund;
    LinearLayout serviceFundREquest;
    LinearLayout serviceMore;
    LinearLayout serviceGV;
    LinearLayout servicePromo;
    LinearLayout serviceCashback;

    LinearLayout layoutElectricBill;
    LinearLayout layoutInsurance;
    LinearLayout layoutCreditCard;
    LinearLayout layoutGas;

    LinearLayout layoutPostpaid;
    LinearLayout layoutPrepaid;
    LinearLayout layoutDTH;
    LinearLayout layoutDatacard;

    private SliderLayout mSlider;
    private SliderLayout mSlider2;

    Context context;

    public static TextView textview_balance;
    float mainBal = 0;
    String stringUrl="";
    String strNews="";
    ProgressDialog pDialog=null;




    //ArrayList<NewsResponse.NewsList> newsLists;


    public HomeFragment_utility() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_home_utility, container, false);
        try {

            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            context = getActivity();
            //imageViewGift = (ImageView) view.findViewById(R.id.homedash_ut_layout_giftvoucher);

            serviceAddFund = (LinearLayout) view.findViewById(R.id.homedash_frag_service_addfund);
            serviceFundREquest = (LinearLayout) view.findViewById(R.id.homedash_frag_service_fundreq);
           // serviceMore = (LinearLayout) view.findViewById(R.id.homedash_frag_service_more);
            serviceWallet = (LinearLayout) view.findViewById(R.id.homedash_frag_service_wallet);
            layoutFlight = (LinearLayout) view.findViewById(R.id.homedash_frag_service_flight);
            layoutHotel = (LinearLayout) view.findViewById(R.id.homedash_frag_service_hotel);
            layoutBus = (LinearLayout) view.findViewById(R.id.homedash_frag_service_bus);
            //layoutTrain = (LinearLayout) view.findViewById(R.id.homedash_frag_service_train);
           // serviceMore = (LinearLayout) view.findViewById(R.id.homedash_frag_service_more);
            layoutCab = (LinearLayout) view.findViewById(R.id.homedash_frag_service_cab);
            serviceTransaction = (LinearLayout) view.findViewById(R.id.homedash_frag_service_transaction);
            //serviceGV = (LinearLayout) view.findViewById(R.id.homedash_frag_service_gv);
            //serviceCashback = (LinearLayout) view.findViewById(R.id.homedash_frag_service_cashback);
            //servicePromo = (LinearLayout) view.findViewById(R.id.homedash_frag_service_promo);

            layoutGas = (LinearLayout) view.findViewById(R.id.homedash_frag_service_gas);
            layoutInsurance = (LinearLayout) view.findViewById(R.id.homedash_frag_service_insurance);
            layoutCreditCard = (LinearLayout) view.findViewById(R.id.homedash_frag_service_credit);
            layoutElectricBill = (LinearLayout) view.findViewById(R.id.homedash_frag_service_electricity);

            layoutDTH = (LinearLayout) view.findViewById(R.id.homedash_frag_service_dth);
            layoutPostpaid = (LinearLayout) view.findViewById(R.id.homedash_frag_service_postpaid);
            layoutPrepaid = (LinearLayout) view.findViewById(R.id.homedash_frag_service_prepaid);
           // layoutDatacard = (LinearLayout) view.findViewById(R.id.homedash_frag_service_datacard);
            //recycoupon = (RecyclerView) view.findViewById(R.id.homedash_frag_recy_coupon);

            // layoutRegistration = (LinearLayout) view.findViewById(R.id.homedash_ut_layout_registration);
            //layoutOnlineShopping = (LinearLayout) view.findViewById(R.id.homedash_ut_layout_onlineshop);
            //layoutGiftVouchers = (LinearLayout) view.findViewById(R.id.homedash_ut_layout_gift);
            //layoutBusinessOppurtunity = (LinearLayout) view.findViewById(R.id.homedash_ut_layout_referearn);
            //layoutNews = (LinearLayout) view.findViewById(R.id.homedash_service_layout_news);
            //layoutBusiness = (LinearLayout) view.findViewById(R.id.homedash_ut_layout_business);
           // layoutComplaint = (LinearLayout) view.findViewById(R.id.homedash_ut_layout_complaint);

            //textview_balance = (TextView) view.findViewById(R.id.dash_textview_bal);
            //txtNews = (TextView) view.findViewById(R.id.homedash_txt_news);
            mSlider = (SliderLayout) view.findViewById(R.id.homedash_frag_service_slider1);
           // mSlider2 = (SliderLayout) view.findViewById(R.id.homedash_frag_service_slider2);

             //txtHistory_Recharge = (TextView) view.findViewById(R.id.textview_history_recharge);
             //txtHistory_BillPayments = (TextView) view.findViewById(R.id.textview_history_billpayment);
             //txtHistory_BookingServies = (TextView) view.findViewById(R.id.textview_history_bookingservices);

            ((MainActivity_utility)context).checkForAppUpdate();


            ArrayList<Integer> images1 = new ArrayList<>();
            images1.add(R.mipmap.utility_slider_1);
            //images1.add(R.mipmap.utility_slider_2);
           // images1.add(R.mipmap.utility_slider_3);
            //images1.add(R.mipmap.utility_slider_4);
            //images1.add(R.mipmap.utility_slider_5);
            //images1.add(R.mipmap.utility_slider_6);
            //images1.add(R.mipmap.utility_slider_7);
            //images1.add(R.mipmap.utility_slider_8);
            //images1.add(R.mipmap.utility_slider_9);
            intiatSlider2();




            //intiatSlider2(images1);

            /*call news api news will
            show on text as left to right rotate
            * */
            //getNewList();



           /* txtHistory_Recharge.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent rechargeIntent=new Intent(context, RechargeReportActivity.class);
                    startActivity(rechargeIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });*/

            /*txtHistory_BillPayments.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent billIntent=new Intent(context, BillPamentReportActivity.class);
                    startActivity(billIntent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                }
            });*/

           /* txtHistory_BookingServies.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
    //                Intent ledgerIntent=new Intent(context, BusBookReportActivity.class);
    //                startActivity(ledgerIntent);
    //                getActivity().overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right);
                    new TravelReportFragment().show(getChildFragmentManager(),"");
                }
            });*/

            /*imageViewGift.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(context, MainActivity_gift.class);
                    startActivity(i);
                }
            });*/

            /*Service Add Fund */
            serviceTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LedgerReportActivity.class);
                    //intent.putExtra("Home", "true");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Add Fund */
            serviceWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MyWalletActivity.class);
                    //intent.putExtra("Home", "true");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
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
                    Intent intent = new Intent(context, AddFundActivity.class);
                    intent.putExtra("Home", "true");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Fund Request */
            serviceFundREquest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Bundle bundle=new Bundle();
//                    bundle.putString("Type","Fund Request");
//                    ChooseOptionBottomDialog fragment=new ChooseOptionBottomDialog();
//                    fragment.setArguments(bundle);
//
//                    fragment.show(getChildFragmentManager(),"");
                    Intent intent = new Intent(context, FundRequestActivity.class);
                    //intent.putExtra("Edit", "false");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Flight*/
            layoutFlight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FlightSearchActivity.class);
                    intent.putExtra("Edit", "false");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Bus*/
            layoutBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BusSearchActivity.class);
                    intent.putExtra("Edit", "false");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Hotel*/
            layoutHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HotelSearchActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*Service Cab*/
            layoutCab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //AlertDialogUtils.showDialogWithNeutaral(context, "Notification!", "Cab service is coming soon..");
                        AlertDialogUtils.showDialogWithNeutaral(context, "Notification!", "Contact to Support for Booking");
                        //Intent intent = new Intent(context, CabSearchActivity.class);
                        //startActivity(intent);
                       // getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }
                });

            /*Service Postpaid*/
            layoutPostpaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostpaidActivity.class);
                    RechargeSharedPreferance.setServiceType(context, "T");
                    intent.putExtra("Type", "T");
                    intent.putExtra("Home", "false");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*click Listener Prepaid*/
            layoutPrepaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PrepaidActivity.class);
                    RechargeSharedPreferance.setServiceType(context, "M");
                    intent.putExtra("Type", "M");
                    intent.putExtra("Home", "false");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });

            /*click Listener datacard*/
            /*layoutDatacard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DatacardActivity.class);
                    RechargeSharedPreferance.setServiceType(context, "M");
                    intent.putExtra("Type", "M");
                    intent.putExtra("Home", "false");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

            /*Service DTH*/
            layoutDTH.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(context, ServiceProviderActivity.class);
                    RechargeSharedPreferance.setServiceType(context, "D");
                    RechargeSharedPreferance.setServiceTypeId(context, "2");
                    intent.putExtra("Type", "D");
                    intent.putExtra("Home", "true");
                    startActivity(intent);
                    //getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });


            /*click Listener Insurance*/
            layoutInsurance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // ((MainActivity_utility)context).replaceFragment(new FlightSerachFragment(),"FlightSearchFragment",null);
                    //((MainActivity_utility)context).toolBarTitle("Flight");
                    Intent intent = new Intent(context, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(context, "I");
                    BillPaySharedPreferance.setServiceTypeID(context, "3");
                    //intent.putExtra("ServiceType","G");
                    //intent.putExtra("ServiceTypeId","2");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    //((BaseFragment)context).startActivity(intent);
                }
            });

            /*click Listener Credit card*/
            layoutCreditCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // ((MainActivity_utility)context).replaceFragment(new FlightSerachFragment(),"FlightSearchFragment",null);
                    //((MainActivity_utility)context).toolBarTitle("Flight");
                    Intent intent = new Intent(context, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(context, "C");
                    BillPaySharedPreferance.setServiceTypeID(context, "4");
                    //intent.putExtra("ServiceType","CC");
                    //intent.putExtra("ServiceTypeId","4");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    //((BaseFragment)context).startActivity(intent);
                }
            });

            /*Service Gas*/
            layoutGas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(context, "G");
                    BillPaySharedPreferance.setServiceTypeID(context, "2");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                }
            });

            /*Service Electricity*/
            layoutElectricBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BillPayServiceListActivity.class);
                    intent.putExtra("Home", "true");
                    BillPaySharedPreferance.setServiceType(context, "E");
                    BillPaySharedPreferance.setServiceTypeID(context, "1");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            });


            /*Service Add Fund */
           /* serviceMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //throw new RuntimeException("Test Crash"); // Force a crash

                    Intent intent = new Intent(context, MoreServicesActivity.class);

                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });*/

           /* servicePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PromocodeListActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/

           /* serviceGV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String loginUrl="https://gv.discountmart.com/members/index.php";
                    WebBrandProdtFragment fragment=new WebBrandProdtFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("Type","Gift Voucher");
                    bundle.putString("From","Fragment");
                    bundle.putString("URL",loginUrl);
                    fragment.setArguments(bundle);
                    ((MainActivity_utility)context).replaceFragment(fragment,"Web",bundle);
                }
            });*/
            /*getActivity().addContentView(serviceMore, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));*/

           /* layoutRegistration.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(context, RegistrationActivity.class);
                    startActivity(i);
                }
            });*/

            /*layoutOnlineShopping.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(context, MainActivity_shopping.class);
                    startActivity(i);
                }
            });*/

            /*layoutGiftVouchers.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(context, MainActivity_gift.class);
                    startActivity(i);
                }
            });*/

           /* layoutBusinessOppurtunity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(context, BusinessOppurtunityActivity.class);
                    startActivity(i);
                }
            });*/

            /*layoutBusiness.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    ((MainActivity_utility)context).clearBackStack1();
                    Fragment fragment = new BusinessHomeFragment();
                    ((MainActivity_utility)context).replaceFragment(fragment, AppConstants.TAG_HOME, null);
                }
            });*/

          /*  layoutComplaint.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent=new Intent(context, ComplaintActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                }
            });*/


        }catch (Exception e){e.printStackTrace();}
        return view;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //int j = Integer.parseInt(slider.getBundle().get("extra").toString());
        //Toast.makeText(context,j,Toast.LENGTH_SHORT).show();
        //Toast.makeText(context,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
        String service= String.valueOf(slider.getBundle().get("extra"));
        if(service.contains("Insurance")){
            Intent intent = new Intent(context, BillPayServiceListActivity.class);
            intent.putExtra("Home", "true");
            BillPaySharedPreferance.setServiceType(context, "I");
            BillPaySharedPreferance.setServiceTypeID(context, "3");
            //intent.putExtra("ServiceType","G");
            //intent.putExtra("ServiceTypeId","2");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        }
        else if(service.contains("Bus")){
            Intent intent = new Intent(context, BusSearchActivity.class);
            intent.putExtra("Edit", "false");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
        }
        else if(service.contains("Gas")){
            Intent intent = new Intent(context, BillPayServiceListActivity.class);
            intent.putExtra("Home", "true");
            BillPaySharedPreferance.setServiceType(context, "G");
            BillPaySharedPreferance.setServiceTypeID(context, "2");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        }
        else if(service.contains("Electricity")){
            Intent intent = new Intent(context, BillPayServiceListActivity.class);
            intent.putExtra("Home", "true");
            BillPaySharedPreferance.setServiceType(context, "E");
            BillPaySharedPreferance.setServiceTypeID(context, "1");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        }
        else if(service.contains("Hotel")){
            Intent intent = new Intent(context, HotelSearchActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
        }
        else if(service.contains("Credit Card")){
            Intent intent = new Intent(context, BillPayServiceListActivity.class);
            intent.putExtra("Home", "true");
            BillPaySharedPreferance.setServiceType(context, "C");
            BillPaySharedPreferance.setServiceTypeID(context, "4");
            //intent.putExtra("ServiceType","CC");
            //intent.putExtra("ServiceTypeId","4");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        }
        else if(service.contains("Flight")){
            Intent intent = new Intent(context, FlightSearchActivity.class);
            intent.putExtra("Edit", "false");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
        }




    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
                DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
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



    public void intiatSlider2() {

        mSlider.removeAllSliders();

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Utility",R.mipmap.utility_slider_1);
        //file_maps.put("Bus",R.mipmap.utility_slider_2);
        //file_maps.put("Gas",R.mipmap.utility_slider_3);
        //file_maps.put("Electricity",R.mipmap.utility_slider_4);
        //file_maps.put("Hotel",R.mipmap.utility_slider_5);
        //file_maps.put("Credit Card",R.mipmap.utility_slider_6);
        //file_maps.put("Flight",R.mipmap.utility_slider_7);
        //file_maps.put("CashBack",R.mipmap.utility_slider_8);
        //file_maps.put("Bill",R.mipmap.utility_slider_9);



        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(context);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
        //ListView l = (ListView)findViewById(R.id.transformers);
       // l.setAdapter(new TransformerAdapter(this));
       /* l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
                Toast.makeText(MainActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    public void showDialogueImage() {
        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(context);
        //imageView.setImageResource(R.mipmap.app_pop_up);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }


    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity_utility)context).checkNewAppVersionState();
        /*if(pDialog.isShowing())
            pDialog.dismiss();*/
    }


}
