package in.discountmart.business.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.provider.Settings;

/**
 * Created by The Rock on 5/12/2018.
 */

public class MyContextWrapper extends ContextWrapper {
    public MyContextWrapper(Context base) {
        super(base);
    }


    public boolean isAirplaneModeOn() {
        return Settings.System.getInt(getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }
}
