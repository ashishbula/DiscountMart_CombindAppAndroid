package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class WalletReportResponse extends BaseResponse{
    @SerializedName("status")
    @Expose
    ArrayList<Map<String,String>> status;
    @SerializedName("wallet")
    @Expose
    ArrayList<Map<String,String>> wallet;
    String recordcount;

    public ArrayList<Map<String, String>> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<Map<String, String>> status) {
        this.status = status;
    }

    public ArrayList<Map<String, String>> getWallet() {
        return wallet;
    }

    public void setWallet(ArrayList<Map<String, String>> wallet) {
        this.wallet = wallet;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
