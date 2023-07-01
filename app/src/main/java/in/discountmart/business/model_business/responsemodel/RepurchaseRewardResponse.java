package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class RepurchaseRewardResponse extends BaseResponseEntity {
    String leftbv;//": "603.864",
    String rightbv;//": "3.258",

    ArrayList<Repur_PendingReward> pendingrewards;
    ArrayList<Repur_AchiveReward> achrewards;

    public String getLeftbv() {
        return leftbv;
    }

    public void setLeftbv(String leftbv) {
        this.leftbv = leftbv;
    }

    public String getRightbv() {
        return rightbv;
    }

    public void setRightbv(String rightbv) {
        this.rightbv = rightbv;
    }

    public ArrayList<Repur_PendingReward> getPendingrewards() {
        return pendingrewards;
    }

    public void setPendingrewards(ArrayList<Repur_PendingReward> pendingrewards) {
        this.pendingrewards = pendingrewards;
    }

    public ArrayList<Repur_AchiveReward> getAchrewards() {
        return achrewards;
    }

    public void setAchrewards(ArrayList<Repur_AchiveReward> achrewards) {
        this.achrewards = achrewards;
    }

    public static class Repur_AchiveReward{
        String level;//": "",
        String rcoglevel;//": "",
        String achdate;//": ""

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getRcoglevel() {
            return rcoglevel;
        }

        public void setRcoglevel(String rcoglevel) {
            this.rcoglevel = rcoglevel;
        }

        public String getAchdate() {
            return achdate;
        }

        public void setAchdate(String achdate) {
            this.achdate = achdate;
        }
    }

    public static class Repur_PendingReward{
        String level;//": "1",
        String rcoglevel;//": "5000"

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getRcoglevel() {
            return rcoglevel;
        }

        public void setRcoglevel(String rcoglevel) {
            this.rcoglevel = rcoglevel;
        }
    }
}
