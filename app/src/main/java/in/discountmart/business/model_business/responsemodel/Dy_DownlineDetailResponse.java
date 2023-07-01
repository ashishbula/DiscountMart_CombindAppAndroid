package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_DownlineDetailResponse extends BaseResponse{
    @SerializedName("downline")
    @Expose
    ArrayList<Map<String, String>> downline;
    String recordcount;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<Map<String, String>> getDownline() {
        return downline;
    }

    public void setDownline(ArrayList<Map<String, String>> downline) {
        this.downline = downline;
    }
}
