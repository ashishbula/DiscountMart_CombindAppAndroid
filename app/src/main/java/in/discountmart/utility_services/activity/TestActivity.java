package in.discountmart.utility_services.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import in.discountmart.R;

public class TestActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

     SliderLayout mSlider1;
     SliderLayout mSlider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_activity_test);
        mSlider1=(SliderLayout)findViewById(R.id.slider);
        //intiatSlider2();
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

    /*Slider 2*/
   /* public void intiatSlider2(){
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("DTH Recharge",R.mipmap.utility_billpay1_banner_home);
        file_maps.put("Bill Pay",R.mipmap.utility_billpay2_banner_home);
        //file_maps.put("Mobile Recharge",R.mipmap.mobile_recharge);
        //file_maps.put("Cab", R.mipmap.taxi_image);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
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

            mSlider1.addSlider(textSliderView);
        }
        mSlider1.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        mSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider1.setCustomAnimation(new DescriptionAnimation());
        // mSlider.setCustomAnimation(new D);
        mSlider1.setDuration(4000);
        mSlider1.addOnPageChangeListener(this);
           *//* ListView l = (ListView)findViewById(R.id.transformers);
            l.setAdapter(new TransformerAdapter(this));
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
                    Toast.makeText(MainActivity_gift.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });*//*
    }*/
}
