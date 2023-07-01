package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class RewardResponse extends BaseResponseEntity {
    //String leftbv;//": "603.864",
    //String rightbv;//": "3.258",
    @SerializedName("rpinfo")
    @Expose
    ArrayList<Map<String,String>> rpinfo;
    @SerializedName("pendingrewards")
    @Expose
    ArrayList<Map<String,String>> pendingrewards;
    @SerializedName("achrewards")
    @Expose
    ArrayList<Map<String,String>> achrewards;
    @SerializedName("nextreward")
    @Expose
    ArrayList<Map<String,String>> nextreward;

    //ArrayList<PendingReward> pendingrewards;
   // ArrayList<AchiveReward> achrewards;
    //ArrayList<NextReward> nextreward;


    public ArrayList<Map<String, String>> getRpinfo() {
        return rpinfo;
    }

    public void setRpinfo(ArrayList<Map<String, String>> rpinfo) {
        this.rpinfo = rpinfo;
    }

    public ArrayList<Map<String, String>> getPendingrewards() {
        return pendingrewards;
    }

    public void setPendingrewards(ArrayList<Map<String, String>> pendingrewards) {
        this.pendingrewards = pendingrewards;
    }

    public ArrayList<Map<String, String>> getAchrewards() {
        return achrewards;
    }

    public void setAchrewards(ArrayList<Map<String, String>> achrewards) {
        this.achrewards = achrewards;
    }

    public ArrayList<Map<String, String>> getNextreward() {
        return nextreward;
    }

    public void setNextreward(ArrayList<Map<String, String>> nextreward) {
        this.nextreward = nextreward;
    }
}
