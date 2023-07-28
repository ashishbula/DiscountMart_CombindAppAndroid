package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class CommunityPayoutResponse extends BaseResponse {
    @SerializedName("communitypayout")
    @Expose
    ArrayList<Map<String, String>> communitypayout;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getCommunitypayout() {
        return communitypayout;
    }

    public void setCommunitypayout(ArrayList<Map<String, String>> communitypayout) {
        this.communitypayout = communitypayout;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}