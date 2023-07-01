package in.discountmart.business.model_business.responsemodel;

public class YearlyRewardResponse extends BaseResponseEntity {

    YearlyRewards yearlyreward[];
    String recordcount;

    public YearlyRewards[] getYearlyreward() {
        return yearlyreward;
    }

    public void setYearlyreward(YearlyRewards[] yearlyreward) {
        this.yearlyreward = yearlyreward;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class YearlyRewards{
        String achivedate;
        String leftrewardpoint;
        String rightrewardpoint;
        String newrewardpoint;
        String reward;

        public String getAchivedate() {
            return achivedate;
        }

        public void setAchivedate(String achivedate) {
            this.achivedate = achivedate;
        }

        public String getLeftrewardpoint() {
            return leftrewardpoint;
        }

        public void setLeftrewardpoint(String leftrewardpoint) {
            this.leftrewardpoint = leftrewardpoint;
        }

        public String getRightrewardpoint() {
            return rightrewardpoint;
        }

        public void setRightrewardpoint(String rightrewardpoint) {
            this.rightrewardpoint = rightrewardpoint;
        }

        public String getNewrewardpoint() {
            return newrewardpoint;
        }

        public void setNewrewardpoint(String newrewardpoint) {
            this.newrewardpoint = newrewardpoint;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }
    }
}
