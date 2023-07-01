package in.discountmart.business.model_business.responsemodel;

public class BinaryIncomeResponse extends BaseResponseEntity {

    String recordcount;
     BinaryIncome weeklyincentive[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public BinaryIncome[] getWeeklyincentive() {
        return weeklyincentive;
    }

    public void setWeeklyincentive(BinaryIncome[] weeklyincentive) {
        this.weeklyincentive = weeklyincentive;
    }

    public static class BinaryIncome
    {
        String payoutno;//": "20",
        String fromdate;//": "10 Jun 2018",
        String todate;//": "16 Jun 2018",
        String binarybonus;//": "0.00",
        String netincome;//": "0.00",
        String tdsamt;//": "0.00",
        String admincharge;//": "0.00",
        String previous;//": "450.00",
        String paidamt;//": "0.00",
        String pendingbal;//": "450.00",
        String matchedbv;//": "0.00"
        String totaldeduct;

        public String getTotaldeduct() {
            return totaldeduct;
        }

        public void setTotaldeduct(String totaldeduct) {
            this.totaldeduct = totaldeduct;
        }

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

        public String getBinarybonus() {
            return binarybonus;
        }

        public void setBinarybonus(String binarybonus) {
            this.binarybonus = binarybonus;
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

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getPaidamt() {
            return paidamt;
        }

        public void setPaidamt(String paidamt) {
            this.paidamt = paidamt;
        }

        public String getPendingbal() {
            return pendingbal;
        }

        public void setPendingbal(String pendingbal) {
            this.pendingbal = pendingbal;
        }

        public String getMatchedbv() {
            return matchedbv;
        }

        public void setMatchedbv(String matchedbv) {
            this.matchedbv = matchedbv;
        }
    }
}
