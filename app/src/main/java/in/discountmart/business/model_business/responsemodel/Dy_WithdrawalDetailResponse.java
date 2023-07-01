package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_WithdrawalDetailResponse extends BaseResponse{
    @SerializedName("Bankwithdrawaldetail")
    @Expose
    ArrayList<Map<String,String>>Bankwithdrawaldetail;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getBankwithdrawaldetail() {
        return Bankwithdrawaldetail;
    }

    public void setBankwithdrawaldetail(ArrayList<Map<String, String>> bankwithdrawaldetail) {
        Bankwithdrawaldetail = bankwithdrawaldetail;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
