package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_MyDirectResponse extends BaseResponse {
    // @SerializedName("directssummary")
    //@Expose
    //ArrayList<DirectSummery_D> directssummary;


    // @SerializedName("directs")
    //         @Expose
    // ArrayList<Direct_D> directs;
    @SerializedName("directssummary")
    @Expose
    ArrayList<Map<String, String>> directssummary;

    @SerializedName("directs")
    @Expose
    ArrayList<Map<String, String>>directs;
    String recordcount;//": "8",
    //String response;//": "OK"



    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }



    public ArrayList<Map<String, String>> getDirectssummary() {
        return directssummary;
    }

    public void setDirectssummary(ArrayList<Map<String, String>> directssummary) {
        this.directssummary = directssummary;
    }

    public ArrayList<Map<String, String>> getDirects() {
        return directs;
    }

    public void setDirects(ArrayList<Map<String, String>> directs) {
        this.directs = directs;
    }
}
