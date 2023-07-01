package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_WeeklyIncentiveResponse extends BaseResponse{
    @SerializedName("weeklyincentive")
    @Expose
    ArrayList<Map<String,String>>weeklyincentive;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getWeeklyincentive() {
        return weeklyincentive;
    }

    public void setWeeklyincentive(ArrayList<Map<String, String>> weeklyincentive) {
        this.weeklyincentive = weeklyincentive;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
