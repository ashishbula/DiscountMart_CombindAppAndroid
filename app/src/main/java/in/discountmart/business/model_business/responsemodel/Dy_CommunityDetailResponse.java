package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_CommunityDetailResponse extends BaseResponse{
    @SerializedName("reentrycommunitypayout")
    @Expose
    ArrayList<Map<String,String>> reentrycommunitypayout;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getReentrycommunitypayout() {
        return reentrycommunitypayout;
    }

    public void setReentrycommunitypayout(ArrayList<Map<String, String>> reentrycommunitypayout) {
        this.reentrycommunitypayout = reentrycommunitypayout;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
