package in.discountmart.business.model_business.responsemodel;

public class DirectIncomeReponse extends BaseResponseEntity {
    String recordcount;
    DirectIncome dailyincentive[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public DirectIncome[] getDailyincentive() {
        return dailyincentive;
    }

    public void setDailyincentive(DirectIncome[] dailyincentive) {
        this.dailyincentive = dailyincentive;
    }

    public static class DirectIncome
    {
        String payoutno;//": "20200317",
        String fromdate;//": "17 Mar 2020",
        String todate;//": "17 Mar 2020",
        String directsponsorincome;//": "0.00",
        String matchingfvbonus;//": "0.00",
        String selffvincome;//": "0.00",
        String promotionalincentive;//": "0.00",
        String netincome;//": "0.00",
        String tdsamount;//": "0.00",
        String admincharge;//": "0.00",
        String deduction;//": "0.00",
        String previous;//": "97.00",
        String netamount;//": "0.00",
        String closingbal;//": "97.00"

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

        public String getPayoutno() {
            return payoutno;
        }

        public void setPayoutno(String payoutno) {
            this.payoutno = payoutno;
        }

        public String getDirectsponsorincome() {
            return directsponsorincome;
        }

        public void setDirectsponsorincome(String directsponsorincome) {
            this.directsponsorincome = directsponsorincome;
        }

        public String getMatchingfvbonus() {
            return matchingfvbonus;
        }

        public void setMatchingfvbonus(String matchingfvbonus) {
            this.matchingfvbonus = matchingfvbonus;
        }

        public String getSelffvincome() {
            return selffvincome;
        }

        public void setSelffvincome(String selffvincome) {
            this.selffvincome = selffvincome;
        }

        public String getPromotionalincentive() {
            return promotionalincentive;
        }

        public void setPromotionalincentive(String promotionalincentive) {
            this.promotionalincentive = promotionalincentive;
        }

        public String getNetincome() {
            return netincome;
        }

        public void setNetincome(String netincome) {
            this.netincome = netincome;
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
