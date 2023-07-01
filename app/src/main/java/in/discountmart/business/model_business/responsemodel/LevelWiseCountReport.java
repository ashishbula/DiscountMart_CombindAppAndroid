package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class LevelWiseCountReport extends BaseResponse {
    @SerializedName("levelcountdetail")
    @Expose
    ArrayList<Map<String, String>> levelcountdetail;
    String recordcount;

    public ArrayList<Map<String, String>> getLevelcountdetail() {
        return levelcountdetail;
    }

    public void setLevelcountdetail(ArrayList<Map<String, String>> levelcountdetail) {
        this.levelcountdetail = levelcountdetail;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
