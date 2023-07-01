package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_MyRewardResponse extends BaseResponse {
    @SerializedName("rewards")
    @Expose
    ArrayList<Map<String, String>> rewards;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Map<String, String>> rewards) {
        this.rewards = rewards;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
