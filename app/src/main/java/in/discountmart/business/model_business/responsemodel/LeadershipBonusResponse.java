package in.discountmart.business.model_business.responsemodel;

public class LeadershipBonusResponse extends BaseResponseEntity {

    String recordcount;
    Leadership leadershipincentive[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public Leadership[] getLeadershipincentive() {
        return leadershipincentive;
    }

    public void setLeadershipincentive(Leadership[] leadershipincentive) {
        this.leadershipincentive = leadershipincentive;
    }

    public static class Leadership
    {
        String fromdate;//": "01 Jan 2018",
        String todate;
        String leadershipbonus;//": "2500.00",
        String grossincome;//": "2500.00",
        String tdsamt;//": "125.00",
        String admincharge;//": "125.00",
        String previous;//": "0.00",
        String paidamt;//": "2250.00",
        String pendingbal;//": "0.00"
        String totaldeduct;

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

        public String getLeadershipbonus() {
            return leadershipbonus;
        }

        public void setLeadershipbonus(String leadershipbonus) {
            this.leadershipbonus = leadershipbonus;
        }

        public String getGrossincome() {
            return grossincome;
        }

        public void setGrossincome(String grossincome) {
            this.grossincome = grossincome;
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

        public String getTotaldeduct() {
            return totaldeduct;
        }

        public void setTotaldeduct(String totaldeduct) {
            this.totaldeduct = totaldeduct;
        }
    }

}
