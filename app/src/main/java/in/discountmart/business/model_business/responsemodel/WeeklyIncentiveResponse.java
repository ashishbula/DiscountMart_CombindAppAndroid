package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

/**
 * Created by The Rock on 4/9/2018.
 */

public class WeeklyIncentiveResponse extends BaseResponseEntity {

    ArrayList<WeeklyIncentive> weeklyincentive;
    String recordcount;//": "20",

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<WeeklyIncentive> getWeeklyincentive() {
        return weeklyincentive;
    }

    public void setWeeklyincentive(ArrayList<WeeklyIncentive> weeklyincentive) {
        this.weeklyincentive = weeklyincentive;
    }

    public static class WeeklyIncentive{

        String payoutdate;//": "01 Apr 2020 - 10 Apr 2020",
        String matchingbonus;//": "0.00",
        String directincome;//": "500.00",
        String sponsorlevelincome;//": "0.00",
        String royaltyincome;//": "0.00",
        String grossincome;//": "500.00",
        String tdsamount;//": "25.00",
        String admincharge;//": "25.00",
        String deduction;//": "50.00",
        String previous;//": "180.00",
        String netamount;//": "0.00",
        String closingbal;//": "630.00"

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
    }
}




