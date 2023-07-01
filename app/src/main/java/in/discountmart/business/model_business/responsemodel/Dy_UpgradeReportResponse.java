package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_UpgradeReportResponse extends BaseResponse{
    @SerializedName("upgradereport")
    @Expose
    ArrayList<Map<String,String>>upgradereport;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getUpgradereport() {
        return upgradereport;
    }

    public void setUpgradereport(ArrayList<Map<String, String>> upgradereport) {
        this.upgradereport = upgradereport;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
