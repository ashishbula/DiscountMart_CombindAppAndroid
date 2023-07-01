package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_MonthlyRewardPoints extends BaseResponse{
    @SerializedName("monthlyrewardpoints")
    @Expose
    ArrayList<Map<String,String>>monthlyrewardpoints;
    @SerializedName("recordcount")
    String recordcount;
    @SerializedName("totalrewardpoints")
    String totalrewardpoints;

    public ArrayList<Map<String, String>> getMonthlyrewardpoints() {
        return monthlyrewardpoints;
    }

    public void setMonthlyrewardpoints(ArrayList<Map<String, String>> monthlyrewardpoints) {
        this.monthlyrewardpoints = monthlyrewardpoints;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getTotalrewardpoints() {
        return totalrewardpoints;
    }

    public void setTotalrewardpoints(String totalrewardpoints) {
        this.totalrewardpoints = totalrewardpoints;
    }
}
