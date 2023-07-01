package in.discountmart.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import in.discountmart.R;
import in.discountmart.activity.DashboardActivity;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.shared_pref_business.SharedPrefrence;
import in.discountmart.utility_services.activity.MainActivity_utility;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    Context context;
    TextView txtView;
    TextView txtName;
    TextView txtId;
    TextView txtRank;
    TextView txtWallet;
    LinearLayout btnNewJoin;
    private SliderLayout mSlider1;

    LinearLayout serviceWeb;

    LinearLayout serviceUtility;
    LinearLayout serviceBusiness;
    LinearLayout serviceHoliday;
    LinearLayout serviceWebsite;
    LinearLayout serviceTicket;
    LinearLayout serviceShopMart;
    LinearLayout serviceShopMall;
    LinearLayout serviceShopOnline;
    LinearLayout serviceFood;
    LinearLayout serviceShare;

    LinearLayout serviceShop;

    ProgressDialog pDialog;
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.fragment_home, container, false);
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            mSlider1=(SliderLayout)mainView.findViewById(R.id.homedash_frag_service_slider1);

            //serviceUtility=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_utility);
            //serviceWeb=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_portal);
            serviceShopMall=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_shopmall);
            serviceShopMart=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_shop_mart);
            serviceShopOnline=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_shop_online);
            serviceBusiness=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_business);
            serviceUtility=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_utility);
            serviceFood=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_food_market);
            serviceTicket=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_ticket);
            serviceWebsite=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_website);
            //serviceShare=(LinearLayout)mainView.findViewById(R.id.home_frag_layout_share);

            ((DashboardActivity)context).checkForAppUpdate();
            ArrayList<Integer> images1 = new ArrayList<>();
            images1.add(R.mipmap.slider_1);
            images1.add(R.mipmap.slider_2);
            //images1.add(R.mipmap.utility_banner_1);
            //images1.add(R.mipmap.utility_banner_4);
            //images1.add(R.mipmap.bigbazaar_strip);
            intiatSlider(images1);
            DashboardActivity.bottomNavigationView.setVisibility(View.VISIBLE);


            /*Call Api */
            //getDashboardDetail();

            //Service Cashback on click
            serviceUtility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        // http://utility.discountmart.in/login.aspx
                        String strUrl="https://utility.discountmart.in/postLogin.aspx";
                     /*WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("Title","Utility Services");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*/
                        //WebFragmentUtility fragment=new WebFragmentUtility();
                        //Bundle bundle=new Bundle();
                        //bundle.putString("Type","Discount Mart Utility");
                        //bundle.putString("From","Fragment");
                        //bundle.putString("URL",strUrl);
                        //fragment.setArguments(bundle);
                        //((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);

                        //String strUrl="https://www.b2meutility.in/IndexB2me.aspx";
                    //Log.i("Holiday 64 ", strUrl);
                    Intent i = new Intent(context, MainActivity_utility.class);
                    //i.putExtra("URL", strUrl);
                    //i.putExtra("Type","Website");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });
            serviceShopMart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        String strUrl="https://mart.discountmart.in/members/index.php?";
                        WebBrandProdtFragment fragment=new WebBrandProdtFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Shopping Mart");
                        bundle.putString("From","Fragment");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);

                   /* String strUrl="https://www.discountmart.in/";
                    WebFragment fragment=new WebFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("Type","Website");
                    bundle.putString("Title","Shopping Mart");
                    bundle.putString("URL",strUrl);
                    fragment.setArguments(bundle);
                    ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*/


                        /*ask user witch browser open*/
                        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl));
                        // Note the Chooser below. If no applications match,
                        // Android displays a system message.So here there is no need for try-catch.
                        //startActivity(Intent.createChooser(intent, "Browse with"));

                       /* WebFragment1 fragment=new WebFragment1();
                        Bundle bundleBusiness=new Bundle();
                        bundleBusiness.putString("Type","Shopping Mart");
                        bundleBusiness.putString("URL",loginUrl);
                        fragment.setArguments(bundleBusiness);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundleBusiness);*/

                       /* Intent i = new Intent(context, WebViewActivity.class);
                        i.putExtra("URL", loginUrl);
                        i.putExtra("Type","My Business");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }
            });
            serviceShopMall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                       // https://mall.discountmart.in/members/index.php?mod=interlogindiscountmart&uid=[USERID]&pwd=[PASSWORD]&token=9d2c0cb4aa3ce376b4ec255aaa9b374b

                        WebFragment fragment=new WebFragment();

                        String strUrl="https://mall.discountmart.in/members/index.php?";
                        String userName = "uid="+SharedPrefrence.getUserID(context);
                        String pass = "pwd="+SharedPrefrence.getPassword(context);
                        //String name = "name"+SharedPrefrence.getUsername(context);
                        String token= "token="+"9d2c0cb4aa3ce376b4ec255aaa9b374b";
                        String mod = "mod="+"interlogindiscountmart";
                        String method="method="+"Post";


                       // String infolDetail=userName+"&"+pass+"&"+token+"&"+mod+"&"+method;
                        String infolDetail=strUrl+mod+"&"+userName+"&"+pass+"&"+token+"&"+method;
                        //String str="UserName=SV6621159&Password=191519&Action=Login&Token=DEB13AD9-3C95-4C55-9268-6EFF826DD9F5&UtilityCoupon=0.00";


                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("Title","Shopping Mall");
                        bundle.putString("URL",infolDetail);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);


                   /* Log.i("Holiday 64 ", strUrl);
                    Intent i = new Intent(context, WebViewActivity.class);
                    i.putExtra("URL", strUrl);
                    i.putExtra("Type","Website");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            serviceShopOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        String strUrl="https://shopping.discountmart.in/members/index.php?";
                        WebBrandProdtFragment fragment=new WebBrandProdtFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Brand Shopping");
                        bundle.putString("From","Fragment");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);

                        /*String strUrl="https://b2meshopping.in/";
                        WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("Title","Online Branded Shopping");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*/

                        /* Log.i("Holiday 64 ", strUrl);
                        Intent i = new Intent(context, WebViewActivity.class);
                        i.putExtra("URL", strUrl);
                        i.putExtra("Type","Website");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            serviceFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String strUrl="https://food.discountmart.in/controller.aspx?";
                       /* WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("Title","Food Booking");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*/

                        WebBrandProdtFragment fragment=new WebBrandProdtFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Food Portal");
                        bundle.putString("From","Fragment");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);

                        DashboardActivity.bottomNav.setVisibility(View.GONE);
                  /*  Log.i("Holiday 64 ", strUrl);
                    Intent i = new Intent(context, WebViewActivity.class);
                    i.putExtra("URL", strUrl);
                    i.putExtra("Type","Website");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            serviceTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String strUrl="https://movie.discountmart.in/controller.aspx?";
                        /*WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("Title","Movie Ticket");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*/

                        WebBrandProdtFragment fragment=new WebBrandProdtFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Movie Ticket Portal");
                        bundle.putString("From","Fragment");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);

                  /*
                    Log.i("Holiday 64 ", strUrl);
                    Intent i = new Intent(context, WebViewActivity.class);
                    i.putExtra("URL", strUrl);
                    i.putExtra("Type","Website");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            serviceBusiness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                       // if( ((DashboardActivity)context).checkLocationPermission()){
                          /*String strUrl="https://www.b2me.co.in/";
                         WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("Title","My Business");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*/

                            String dob = new SimpleDateFormat("dd MM yyyy HH:mm" , Locale.getDefault()).format(new Date());

                            String[] curDat=dob.split(" ");
                            String date=curDat[0].toString();
                            String month=curDat[1].toString();
                            String year=curDat[2].toString();
                            String hour=curDat[3].toString();
                            String []curTime=hour.split(":");
                            String hur=curTime[0];
                            String mint=curTime[1];
                            byte[] login;
                            byte[] info;
                            String strUrl="https://cpanel.discountmart.in/directlogin.aspx?ref=";
                            String userName = SharedPrefrence.getUserID(context);
                            String pass = SharedPrefrence.getPassword(context);

                            String ref = "Login";

                            String infolDetail=userName+";"+pass+";"+year+month+date+hur+mint;
                            //String str="UserName=SV6621159&Password=191519&Action=Login&Token=DEB13AD9-3C95-4C55-9268-6EFF826DD9F5&UtilityCoupon=0.00";


                            login = ref.getBytes("UTF-8");
                            info = infolDetail.getBytes("UTF-8");
                            String url=strUrl+ref+"&"+"info="+infolDetail;
                            Log.e("BusinessUrl :",url );
                            //String base64="";
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            base64 = android.util.Base64.getEncoder().encodeToString(stream.toByteArray());
                        } else {
                            encode = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT);
                        }*/
                            String ref64 = android.util.Base64.encodeToString(login, android.util.Base64.DEFAULT);
                            String info64 = android.util.Base64.encodeToString(info, android.util.Base64.DEFAULT);
                            String loginUrl=strUrl+ref64+"&"+"info="+info64;

                            //http://localhost:50949/BasicMlm/Directlogin.aspx?ref=TG9naW4=&info=LP223344;LP@1234;202012191541

                            Log.i("Base 64 ", loginUrl);

                            // ask user witch browser open
                            // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl));
                            // Note the Chooser below. If no applications match,
                            // Android displays a system message.So here there is no need for try-catch.
                            //startActivity(Intent.createChooser(intent, "Browse with"));

                        String url1="https://mart.crypque.ae/index.php";
                            WebFragment fragment=new WebFragment();
                            Bundle bundleBusiness=new Bundle();
                            bundleBusiness.putString("Type","My Business");
                            bundleBusiness.putString("URL",url1);
                            fragment.setArguments(bundleBusiness);
                            ((DashboardActivity)context).replaceFragment(fragment,"Web",bundleBusiness);

                        Intent i = new Intent(context, BusinessDashboardActivity.class);
                        //i.putExtra("URL", strUrl);
                        //i.putExtra("Type","Website");
                        //startActivity(i);
                        //getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }

                }
            });
            serviceWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        String dob = new SimpleDateFormat("dd MM yyyy HH:mm:ss" , Locale.getDefault()).format(new Date());

                        String[] curDat=dob.split(" ");
                        String date=curDat[0].toString();
                        String month=curDat[1].toString();
                        String year=curDat[2].toString();
                        String hour=curDat[3].toString();
                        String []curTime=hour.split(":");
                        String hur=curTime[0];
                        String mint=curTime[1];
                        String sec=curTime[2];
                        byte[] login;
                        byte[] info;
                        String strUrl="https://Discountmart.in/Account/DirectLogin?refs=";
                        //https://Discountmart.in/Account/DirectLogin?refs=red&info=ww
                        String userName = SharedPrefrence.getUserID(context);
                        String pass = SharedPrefrence.getPassword(context);

                        String ref = "Login";

                        // String infolDetail=userName+";"+pass+";"+year+"-"+month+"-"+date+hur+mint+sec;
                        String infolDetail=userName+";"+pass+";"+date+"-"+month+"-"+year+" "+hur+":"+mint+":"+sec;
                        //String str="UserName=SV6621159&Password=191519&Action=Login&Token=DEB13AD9-3C95-4C55-9268-6EFF826DD9F5&UtilityCoupon=0.00";


                        login = ref.getBytes("UTF-8");
                        info = infolDetail.getBytes("UTF-8");
                        String url=strUrl+ref+"&"+"info="+infolDetail;
                        Log.e("BusinessUrl :",url );
                        //String base64="";
                       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            base64 = android.util.Base64.getEncoder().encodeToString(stream.toByteArray());
                        } else {
                            encode = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT);
                        }*/
                        String ref64 = android.util.Base64.encodeToString(login, android.util.Base64.DEFAULT);
                        String info64 = android.util.Base64.encodeToString(info, android.util.Base64.DEFAULT);
                        String loginUrl=strUrl+ref64+"&"+"info="+info64;

                        //http://localhost:50949/BasicMlm/Directlogin.aspx?ref=TG9naW4=&info=LP223344;LP@1234;202012191541

                        Log.i("Base 64 ", loginUrl);

                        /*ask user witch browser open*/
                        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl));
                        // Note the Chooser below. If no applications match,
                        // Android displays a system message.So here there is no need for try-catch.
                        //startActivity(Intent.createChooser(intent, "Browse with"));

                        WebFragment1 fragment=new WebFragment1();
                        Bundle bundleBusiness=new Bundle();
                        bundleBusiness.putString("Type","Discount Mart");
                        bundleBusiness.putString("URL",loginUrl);
                        fragment.setArguments(bundleBusiness);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundleBusiness);
                    }catch (Exception e){
                        e.printStackTrace();
                    }




                    /* WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("URL",loginUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*/

//                    String strUrl="https://www.ucmventures.com/";
//                    Log.i("Holiday 64 ", strUrl);
//                    Intent i = new Intent(context, WebViewActivity.class);
//                    i.putExtra("URL", strUrl);
//                    i.putExtra("Type","Website");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);

                }
            });


            /*serviceBusiness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                        *//* String strUrl="https://www.b2me.co.in/";
                         WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","Website");
                        bundle.putString("Title","My Business");
                        bundle.putString("URL",strUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);*//*

                        String dob = new SimpleDateFormat("dd MM yyyy HH:mm" , Locale.getDefault()).format(new Date());

                        String[] curDat=dob.split(" ");
                        String date=curDat[0].toString();
                        String month=curDat[1].toString();
                        String year=curDat[2].toString();
                        String hour=curDat[3].toString();
                        String []curTime=hour.split(":");
                        String hur=curTime[0];
                        String mint=curTime[1];
                        byte[] login;
                        byte[] info;
                        String strUrl="http://utility.elearningpathshala.com/handler/login.ashx?url=";
                        String userName = SharedPrefrence.getUserID(context);
                        String pass = SharedPrefrence.getPassword(context);
                        String Action = "login";
                        String Token = "45780F9D-4525-4929-8753-DAD56408857C";

                        String ref = "Login";

                        //String infolDetail=userName+";"+pass+;
                        String str="UserName=ELP625184&Password=4z5mJH&Action=Login&Token="+Token;


                       // login = ref.getBytes("UTF-8");
                        info = str.getBytes("UTF-8");
                        String url=strUrl+info;
                        String url1=strUrl+str;
                        Log.e("BusinessUrl :",url );
                        Log.e("BusinessUrl :",url1 );
                        //String base64="";
                       *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            base64 = android.util.Base64.getEncoder().encodeToString(stream.toByteArray());
                        } else {
                            encode = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT);
                        }*//*
                        //String ref64 = android.util.Base64.encodeToString(login, android.util.Base64.DEFAULT);
                        String info64 = android.util.Base64.encodeToString(info, android.util.Base64.DEFAULT);
                        String loginUrl=strUrl+info64;

                        //http://localhost:50949/BasicMlm/Directlogin.aspx?ref=TG9naW4=&info=LP223344;LP@1234;202012191541

                        Log.i("Base 64 ", loginUrl);

                        *//*ask user witch browser open*//*
                        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl));
                        // Note the Chooser below. If no applications match,
                        // Android displays a system message.So here there is no need for try-catch.
                        //startActivity(Intent.createChooser(intent, "Browse with"));

                        WebFragment1 fragment=new WebFragment1();
                        Bundle bundleBusiness=new Bundle();
                        bundleBusiness.putString("Type","My Business");
                        bundleBusiness.putString("URL",loginUrl);
                        fragment.setArguments(bundleBusiness);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundleBusiness);

                       *//* Intent i = new Intent(context, WebViewActivity.class);
                        i.putExtra("URL", loginUrl);
                        i.putExtra("Type","My Business");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*//*

                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }

                }
            });*/

            //Service Brand Product on click
           /* serviceUtility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String loginUrl="https://utility.stanveeservices.com/postLogin.aspx";
                        WebBrandProdtFragment fragment=new WebBrandProdtFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("Type","Stanvee Utility");
                    bundle.putString("From","Fragment");
                    bundle.putString("URL",loginUrl);
                    fragment.setArguments(bundle);
                    ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);

                        String strUrl="https://brandshop.knrtrade.in/members/index.php?";
                        String userName = "userid="+SharedPrefrence.getUserID(context);
                        String pass = "password="+SharedPrefrence.getPassword(context);
                        //String name = "name"+SharedPrefrence.getUsername(context);
                        String token= "token="+"a8c8839f4eeee61255a6f480c9971e09";
                        String mod = "mod="+"interLogin";
                        String method="method="+"Get";

                        // String logKey="8A9D7B04-40F3-4E2C-9953-00558AD531F1";

                        //String infolDetail=userName+"&"+pass+"&"+token+"&"+mod+"&"+method;
                        String infolDetail=mod+"&"+userName+"&"+pass+"&"+token+"&"+method;
                        //String str="UserName=SV6621159&Password=191519&Action=Login&Token=DEB13AD9-3C95-4C55-9268-6EFF826DD9F5&UtilityCoupon=0.00";

                        String loginUrl=strUrl+infolDetail;
                        Log.e("CashbackUrl :",loginUrl );

                        WebFragment fragment=new WebFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Type","KNR Brand Portal");
                        bundle.putString("URL",loginUrl);
                        fragment.setArguments(bundle);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);

                        Intent i = new Intent(context, WebViewActivity.class);
                        i.putExtra("URL", loginUrl);
                        i.putExtra("Type","CashBask Portal");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });*/


            //Service Shopping on click
          /*  serviceShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   try {
                      // https://brands.ucmventures.com/cpanel/index.php?mod=interLoginAdmin&userid=53f437a%86t%7Et%80%7Ftv%81%8578e1f7f&password=c41f9bd%86t%7EQBCDe442c8c&token=760bd7f354e49ad8866a722fa42b2c78
                       //String loginUrl=" https://thebrandtadka.com/index.php?mod=ashish ";
                       String loginUrl="https://brands.ucmventures.com/members/index.php?";
                       WebBrandProdtFragment fragment=new WebBrandProdtFragment();
                       Bundle bundle=new Bundle();
                       bundle.putString("Type","Online Shopping");
                       bundle.putString("From","Fragment");
                       bundle.putString("URL",loginUrl);
                       fragment.setArguments(bundle);
                       ((DashboardActivity)context).replaceFragment(fragment,"Web",bundle);
                   }catch (Exception e){
                       e.printStackTrace();
                   }


                }
            });*/

            //Service My Business on click
            /*serviceBusiness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String dob = new SimpleDateFormat("dd MM yyyy HH:mm" , Locale.getDefault()).format(new Date());
                    //https://cpanel.ucmventures.com/CheckLogin?token=abUnMar5489pidlAewUF4875brlE8a4i5n61038&UserName=UCM223344&Password=UCM@1234
                    String[] curDat=dob.split(" ");
                    String date=curDat[0].toString();
                    String month=curDat[1].toString();
                    String year=curDat[2].toString();
                    String hour=curDat[3].toString();
                    String []curTime=hour.split(":");
                    String hur=curTime[0];
                    String mint=curTime[1];
                    byte[] login;
                    byte[] info;
                    String strUrl="https://cpanel.ucmventures.com/directlogin.aspx?ref=";
                    String userName = SharedPrefrence.getUserID(context);
                    String pass = SharedPrefrence.getPassword(context);

                    String ref = "Login";

                    String infolDetail=userName+";"+pass+";"+year+month+date+hur+mint;
                    //String str="UserName=SV6621159&Password=191519&Action=Login&Token=DEB13AD9-3C95-4C55-9268-6EFF826DD9F5&UtilityCoupon=0.00";

                    try {
                        login = ref.getBytes("UTF-8");
                        info = infolDetail.getBytes("UTF-8");
                        String url=strUrl+ref+"&"+"info="+infolDetail;
                        Log.e("BusinessUrl :",url );
                        //String base64="";
                       *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            base64 = android.util.Base64.getEncoder().encodeToString(stream.toByteArray());
                        } else {
                            encode = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT);
                        }*//*
                        String ref64 = android.util.Base64.encodeToString(login, android.util.Base64.DEFAULT);
                        String info64 = android.util.Base64.encodeToString(info, android.util.Base64.DEFAULT);
                        String loginUrl=strUrl+ref64+"&"+"info="+info64;

                        //http://localhost:50949/BasicMlm/Directlogin.aspx?ref=TG9naW4=&info=LP223344;LP@1234;202012191541

                        Log.i("Base 64 ", loginUrl);

                        *//*ask user witch browser open*//*
                        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl));
                        // Note the Chooser below. If no applications match,
                        // Android displays a system message.So here there is no need for try-catch.
                        //startActivity(Intent.createChooser(intent, "Browse with"));

                        WebFragment fragment=new WebFragment();
                        Bundle bundleBusiness=new Bundle();
                        bundleBusiness.putString("Type","My Business");
                        bundleBusiness.putString("URL",loginUrl);
                        fragment.setArguments(bundleBusiness);
                        ((DashboardActivity)context).replaceFragment(fragment,"Web",bundleBusiness);

                       *//* Intent i = new Intent(context, WebViewActivity.class);
                        i.putExtra("URL", loginUrl);
                        i.putExtra("Type","My Business");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);*//*

                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();

                    }
                }
            });*/

            /*Service Share on click*/
           /* serviceShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    *//*Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String string = "\nGet the best deal on shopping by simply downloading Stanvee App"+"\n with referral id="+SharedPrefrence.getUserID(context) + "\nVisit us at\n\n";
                    string = string + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, string);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Stanvee");
                    startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));*//*
                    //Toast.makeText(context,"coming soon", Toast.LENGTH_SHORT).show();
                    String string = "\nGet the best deal on shopping by simply downloading UCM App"+"\n with referral id="+SharedPrefrence.getUserID(context) + "\nVisit us at\n\n";
                    string = string + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n";

                   //shareAppLinkViaFacebook(string);
                   //sendShareFacebook(string);
                    Toast.makeText(context,"Coming soon..",Toast.LENGTH_SHORT).show();




                }
            });*/
            

        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;


    }

    private void shareAppLinkViaFacebook(String urlToShare) {
        try {
            Intent intent1 = new Intent();
            intent1.setClassName("com.facebook.katana", "com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
            intent1.setAction("android.intent.action.SEND");
            intent1.setType("text/plain");
            intent1.putExtra("android.intent.extra.TEXT", urlToShare);
            startActivity(intent1);
        } catch (Exception e) {
            // If we failed (not native FB app installed), try share through SEND
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent);
        }


    }

    public void sendShareFacebook(String strShareMessage)
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        // String string = "\nGet the best deal on shopping by simply downloading Stanvee App"+"\n with referral id="+SharedPrefrence.getUserID(context) + "\nVisit us at\n\n";
        // string = string + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, strShareMessage);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "UCM");
        startActivity(Intent.createChooser(shareIntent, "SHARE_APP"));
    }

    /*Slider 1*/
    public void intiatSlider1(){
        // ArrayList<String> file_maps=new ArrayList<>();
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        //file_maps.put("Mid Brain",R.mipmap.slider1);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(context);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(HomeFragment.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider1.addSlider(textSliderView);
        }
        mSlider1.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider1.setCustomAnimation(new DescriptionAnimation());
        // mSlider.setCustomAnimation(new D);
        mSlider1.setDuration(4000);
        mSlider1.addOnPageChangeListener(HomeFragment.this);
    }
    public void intiatSlider(ArrayList<Integer> Image) {

        mSlider1.removeAllSliders();

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

                mSlider1.addSlider(textSliderView);
            }
        }
        mSlider1.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider1.setCustomAnimation(new DescriptionAnimation());
        mSlider1.setDuration(4000);
        mSlider1.addOnPageChangeListener(new ViewPagerEx.SimpleOnPageChangeListener());
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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
    @Override
    public void onResume(){
        super.onResume();
        ((DashboardActivity)context).checkNewAppVersionState();
    }



}
