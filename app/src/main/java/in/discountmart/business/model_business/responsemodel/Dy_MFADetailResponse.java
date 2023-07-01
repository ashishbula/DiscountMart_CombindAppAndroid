package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_MFADetailResponse extends BaseResponse{
    @SerializedName("mfareport")
    @Expose
    ArrayList<Map<String,String>>mfareport;
    @SerializedName("recordcount")
    String recordcount;
    String cycleclosingdate1;
    String cycleclosingdate2;
    String cyclepaymentdate1;
    String cyclepaymentdate2;

    public ArrayList<Map<String, String>> getMfareport() {
        return mfareport;
    }

    public void setMfareport(ArrayList<Map<String, String>> mfareport) {
        this.mfareport = mfareport;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getCycleclosingdate1() {
        return cycleclosingdate1;
    }

    public void setCycleclosingdate1(String cycleclosingdate1) {
        this.cycleclosingdate1 = cycleclosingdate1;
    }

    public String getCycleclosingdate2() {
        return cycleclosingdate2;
    }

    public void setCycleclosingdate2(String cycleclosingdate2) {
        this.cycleclosingdate2 = cycleclosingdate2;
    }

    public String getCyclepaymentdate1() {
        return cyclepaymentdate1;
    }

    public void setCyclepaymentdate1(String cyclepaymentdate1) {
        this.cyclepaymentdate1 = cyclepaymentdate1;
    }

    public String getCyclepaymentdate2() {
        return cyclepaymentdate2;
    }

    public void setCyclepaymentdate2(String cyclepaymentdate2) {
        this.cyclepaymentdate2 = cyclepaymentdate2;
    }
}
