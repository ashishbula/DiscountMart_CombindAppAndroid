package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_DownlinePurchaseResponse extends BaseResponse{
    @SerializedName("downlinepurchase")
    @Expose
    ArrayList<Map<String,String>> downlinepurchase;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getDownlinepurchase() {
        return downlinepurchase;
    }

    public void setDownlinepurchase(ArrayList<Map<String, String>> downlinepurchase) {
        this.downlinepurchase = downlinepurchase;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
