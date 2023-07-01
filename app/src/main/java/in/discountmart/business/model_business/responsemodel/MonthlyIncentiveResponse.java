package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/30/2018.
 */

public class MonthlyIncentiveResponse extends BaseResponseEntity {

    String recordcount;
    MonthlyIncentive monthlyincentive[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public MonthlyIncentive[] getMonthlyincentive() {
        return monthlyincentive;
    }

    public void setMonthlyincentive(MonthlyIncentive[] monthlyincentive) {
        this.monthlyincentive = monthlyincentive;
    }

    public static class MonthlyIncentive{
        String payoutno;//": "5",
        String fromdate;//": "01 Dec 2017",
        String todate;//": "31 Dec 2017",
        String Differentialincome;//": "1416.93",
        String additionalbonus;//": "5484.77",
        String leadershipbonus;//": "0.00",
        String bikebudget;//": "0.00",
        String carbudget;//": "0.00",
        String housebudget;//": "0.00",
        String netincome;//": "6901.70",
        String tdsamt;//": "345.10",
        String admincharge;//": "207.05",
        String socialcharge;//": "207.05",
        String deduction;//": "759.20",
        String previous;//": "0.00",
        String netamount;//": "6142.00",
        String closing;//": "0.00"

        public String getPayoutno() {
            return payoutno;
        }

        public void setPayoutno(String payoutno) {
            this.payoutno = payoutno;
        }

        public String getFromdate() {
            return fromdate;
        }

        public void setFromdate(String fromdate) {
            this.fromdate = fromdate;
        }

        public String getTodate() {
            return todate;
        }

        public void setTodate(String todate) {
            this.todate = todate;
        }

        public String getDifferentialincome() {
            return Differentialincome;
        }

        public void setDifferentialincome(String differentialincome) {
            Differentialincome = differentialincome;
        }

        public String getAdditionalbonus() {
            return additionalbonus;
        }

        public void setAdditionalbonus(String additionalbonus) {
            this.additionalbonus = additionalbonus;
        }

        public String getLeadershipbonus() {
            return leadershipbonus;
        }

        public void setLeadershipbonus(String leadershipbonus) {
            this.leadershipbonus = leadershipbonus;
        }

        public String getBikebudget() {
            return bikebudget;
        }

        public void setBikebudget(String bikebudget) {
            this.bikebudget = bikebudget;
        }

        public String getCarbudget() {
            return carbudget;
        }

        public void setCarbudget(String carbudget) {
            this.carbudget = carbudget;
        }

        public String getHousebudget() {
            return housebudget;
        }

        public void setHousebudget(String housebudget) {
            this.housebudget = housebudget;
        }

        public String getNetincome() {
            return netincome;
        }

        public void setNetincome(String netincome) {
            this.netincome = netincome;
        }

        public String getTdsamt() {
            return tdsamt;
        }

        public void setTdsamt(String tdsamt) {
            this.tdsamt = tdsamt;
        }

        public String getAdmincharge() {
            return admincharge;
        }

        public void setAdmincharge(String admincharge) {
            this.admincharge = admincharge;
        }

        public String getSocialcharge() {
            return socialcharge;
        }

        public void setSocialcharge(String socialcharge) {
            this.socialcharge = socialcharge;
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

        public String getClosing() {
            return closing;
        }

        public void setClosing(String closing) {
            this.closing = closing;
        }
    }


}
