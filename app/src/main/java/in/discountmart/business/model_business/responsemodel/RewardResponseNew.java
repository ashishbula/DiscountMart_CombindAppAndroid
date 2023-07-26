package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class RewardResponseNew extends BaseResponseEntity {
    //RewardDetails Rewarddetail[];
    @SerializedName("Rewarddetail")
    @Expose
    ArrayList<Map<String,String>> Rewarddetail;
	String recordcount;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<Map<String, String>> getRewarddetail() {
        return Rewarddetail;
    }

    public void setRewarddetail(ArrayList<Map<String, String>> rewarddetail) {
        Rewarddetail = rewarddetail;
    }

    /*
    public class RewardDetails{
        String sno;
                String rank;
                String reward;
                String achivedate;

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getAchivedate() {
            return achivedate;
        }

        public void setAchivedate(String achivedate) {
            this.achivedate = achivedate;
        }
    }
*/
}
