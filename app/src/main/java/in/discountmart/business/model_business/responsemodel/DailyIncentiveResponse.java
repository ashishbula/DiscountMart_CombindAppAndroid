package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

/**
 * Created by The Rock on 4/9/2018.
 */

public class DailyIncentiveResponse extends BaseResponseEntity {
   ArrayList <DailyIncentive> dailyincentive;
    String recordcount;//": "20",

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<DailyIncentive> getDailyincentive() {
        return dailyincentive;
    }

    public void setDailyincentive(ArrayList<DailyIncentive> dailyincentive) {
        this.dailyincentive = dailyincentive;
    }

    public static class DailyIncentive{

        String payoutdate;//": "20 Apr 2020",
        String matchingbonus;//": "0.00",
        String directincome;//": "300.00",
        String sponsorlevelincome;//": "0.00",
        String royaltyincome;//": "0.00",
        String grossincome;//": "300.00",
        String tdsamount;//": "15.00",
        String admincharge;//": "15.00",
        String deduction;//": "30",
        String previous;//": "0.00",
        String netamount;//": "0.00",
        String closingbal;//": "270.00"
        String url;
        String rankbonus;
        String rewardInc;
        String levelincome;
        String RepurchaseIncome;

        public String getPayoutdate() {
            return payoutdate;
        }

        public void setPayoutdate(String payoutdate) {
            this.payoutdate = payoutdate;
        }

        public String getMatchingbonus() {
            return matchingbonus;
        }

        public void setMatchingbonus(String matchingbonus) {
            this.matchingbonus = matchingbonus;
        }

        public String getDirectincome() {
            return directincome;
        }

        public void setDirectincome(String directincome) {
            this.directincome = directincome;
        }

        public String getSponsorlevelincome() {
            return sponsorlevelincome;
        }

        public void setSponsorlevelincome(String sponsorlevelincome) {
            this.sponsorlevelincome = sponsorlevelincome;
        }

        public String getRoyaltyincome() {
            return royaltyincome;
        }

        public void setRoyaltyincome(String royaltyincome) {
            this.royaltyincome = royaltyincome;
        }

        public String getGrossincome() {
            return grossincome;
        }

        public void setGrossincome(String grossincome) {
            this.grossincome = grossincome;
        }

        public String getTdsamount() {
            return tdsamount;
        }

        public void setTdsamount(String tdsamount) {
            this.tdsamount = tdsamount;
        }

        public String getAdmincharge() {
            return admincharge;
        }

        public void setAdmincharge(String admincharge) {
            this.admincharge = admincharge;
        }

        public String getDeduction() {
            return deduction;
        }

        public void setDeduction(String deduction) {
            this.deduction = deduction;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getNetamount() {
            return netamount;
        }

        public void setNetamount(String netamount) {
            this.netamount = netamount;
        }

        public String getClosingbal() {
            return closingbal;
        }

        public void setClosingbal(String closingbal) {
            this.closingbal = closingbal;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRankbonus() {
            return rankbonus;
        }

        public void setRankbonus(String rankbonus) {
            this.rankbonus = rankbonus;
        }

        public String getRewardInc() {
            return rewardInc;
        }

        public void setRewardInc(String rewardInc) {
            this.rewardInc = rewardInc;
        }

        public String getLevelincome() {
            return levelincome;
        }

        public void setLevelincome(String levelincome) {
            this.levelincome = levelincome;
        }

        public String getRepurchaseIncome() {
            return RepurchaseIncome;
        }

        public void setRepurchaseIncome(String repurchaseIncome) {
            RepurchaseIncome = repurchaseIncome;
        }
    }

}
