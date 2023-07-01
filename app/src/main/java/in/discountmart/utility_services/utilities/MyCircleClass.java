package in.discountmart.utility_services.utilities;

import android.animation.ObjectAnimator;
import android.util.Property;

public class MyCircleClass {

    public static final Property<MyCircleClass, Float> GROW_FACTOR = new Property<MyCircleClass, Float>(
            Float.class, "growFactor") {
        @Override
        public void set(MyCircleClass object, Float value) {
            object.setGrowGfactor(value);
        }

        @Override
        public Float get(MyCircleClass object) {
            return object.getGrowFactor();
        }
    };

    private float mGrowFactor = 0f;

    public void setGrowGfactor(float grow) {
        mGrowFactor = grow;
        //invalidate the view to cause a redraw
        //in the onDraw() use the mGrowFactor to calculate the new radius
    }

    public float getGrowFactor() {
        return mGrowFactor;
    }

    public void startGrowAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(this, GROW_FACTOR, 1f);
        anim.setDuration(3000); //make animation 3 seconds long
        anim.start();
    }
}
