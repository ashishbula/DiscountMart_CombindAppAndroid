package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class WalletReqDetailResponse  extends BaseResponse{
    @SerializedName("walletrequestdetail")
    @Expose
    ArrayList<Map<String,String>> walletrequestdetail;
    String recordcount;

    public ArrayList<Map<String, String>> getWalletrequestdetail() {
        return walletrequestdetail;
    }

    public void setWalletrequestdetail(ArrayList<Map<String, String>> walletrequestdetail) {
        this.walletrequestdetail = walletrequestdetail;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
