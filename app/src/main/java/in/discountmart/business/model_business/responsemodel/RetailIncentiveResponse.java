package in.discountmart.business.model_business.responsemodel;

public class RetailIncentiveResponse extends BaseResponseEntity {

    String recordcount;
    RetailIncentive retailincentive[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public RetailIncentive[] getRetailincentive() {
        return retailincentive;
    }

    public void setRetailincentive(RetailIncentive[] retailincentive) {
        this.retailincentive = retailincentive;
    }

    public static class RetailIncentive
    {
        String fromdate;//": "01 May 2018",
        String todate;//": "31 May 2018",
        String slab;//": "20.00",
        String performancebonus;//": "3036.40",
        String achieverroyalty;//": "0.00",
        String achieverbonus;//": "11559.82",
        String carfund;//": "0.00",
        String housefund;//": "0.00",
        String grossincome;//": "14596.22",
        String tdsamount;//": "729.80",
        String admincharge;//": "729.81",
        String totaldeduction;//": "1459.61",
        String netincome;//": "13136.00",
        String prevbal;//": "0.00",
        String pendingbal;//": "0.00"
        String loyaltybonus;

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

        public String getSlab() {
            return slab;
        }

        public void setSlab(String slab) {
            this.slab = slab;
        }

        public String getPerformancebonus() {
            return performancebonus;
        }

        public void setPerformancebonus(String performancebonus) {
            this.performancebonus = performancebonus;
        }

        public String getAchieverroyalty() {
            return achieverroyalty;
        }

        public void setAchieverroyalty(String achieverroyalty) {
            this.achieverroyalty = achieverroyalty;
        }

        public String getAchieverbonus() {
            return achieverbonus;
        }

        public void setAchieverbonus(String achieverbonus) {
            this.achieverbonus = achieverbonus;
        }

        public String getCarfund() {
            return carfund;
        }

        public void setCarfund(String carfund) {
            this.carfund = carfund;
        }

        public String getHousefund() {
            return housefund;
        }

        public void setHousefund(String housefund) {
            this.housefund = housefund;
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

        public String getTotaldeduction() {
            return totaldeduction;
        }

        public void setTotaldeduction(String totaldeduction) {
            this.totaldeduction = totaldeduction;
        }

        public String getNetincome() {
            return netincome;
        }

        public void setNetincome(String netincome) {
            this.netincome = netincome;
        }

        public String getPrevbal() {
            return prevbal;
        }

        public void setPrevbal(String prevbal) {
            this.prevbal = prevbal;
        }

        public String getPendingbal() {
            return pendingbal;
        }

        public void setPendingbal(String pendingbal) {
            this.pendingbal = pendingbal;
        }

        public String getLoyaltybonus() {
            return loyaltybonus;
        }

        public void setLoyaltybonus(String loyaltybonus) {
            this.loyaltybonus = loyaltybonus;
        }
    }
}
