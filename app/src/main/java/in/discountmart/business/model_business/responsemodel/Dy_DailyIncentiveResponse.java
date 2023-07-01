package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_DailyIncentiveResponse extends BaseResponse{
    @SerializedName("dailyincentive")
    @Expose
    ArrayList<Map<String,String>> dailyincentive;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getDailyincentive() {
        return dailyincentive;
    }

    public void setDailyincentive(ArrayList<Map<String, String>> dailyincentive) {
        this.dailyincentive = dailyincentive;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
