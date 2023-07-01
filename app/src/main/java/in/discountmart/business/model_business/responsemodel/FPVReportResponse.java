package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class FPVReportResponse extends BaseResponseEntity {
    ArrayList<FPVReport>fpvreport;
    String recordcount;

    public ArrayList<FPVReport> getFpvreport() {
        return fpvreport;
    }

    public void setFpvreport(ArrayList<FPVReport> fpvreport) {
        this.fpvreport = fpvreport;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class FPVReport{
        String sno;//": "1",
        String billno;//": "PG/YNG/00179",
        String billamount;//": "2142.00",
        String billdate;//": "09-Mar-2020",
        String fv;//": "0.00",
        String rv;//": "917.00",
        String couponamount;//": "200.00",
        String couponno;//": "988E6723",
        String status;//": "Unused",
        String expirydate;//": "08-Apr-2020",
        String useddate;//": "",
        String shopeeuser;//": "",
        String usedbillno;//": "",
        String usedfpv;//": "0.00",
        String type;//": "All"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getBillno() {
            return billno;
        }

        public void setBillno(String billno) {
            this.billno = billno;
        }

        public String getBillamount() {
            return billamount;
        }

        public void setBillamount(String billamount) {
            this.billamount = billamount;
        }

        public String getBilldate() {
            return billdate;
        }

        public void setBilldate(String billdate) {
            this.billdate = billdate;
        }

        public String getFv() {
            return fv;
        }

        public void setFv(String fv) {
            this.fv = fv;
        }

        public String getRv() {
            return rv;
        }

        public void setRv(String rv) {
            this.rv = rv;
        }

        public String getCouponamount() {
            return couponamount;
        }

        public void setCouponamount(String couponamount) {
            this.couponamount = couponamount;
        }

        public String getCouponno() {
            return couponno;
        }

        public void setCouponno(String couponno) {
            this.couponno = couponno;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExpirydate() {
            return expirydate;
        }

        public void setExpirydate(String expirydate) {
            this.expirydate = expirydate;
        }

        public String getUseddate() {
            return useddate;
        }

        public void setUseddate(String useddate) {
            this.useddate = useddate;
        }

        public String getShopeeuser() {
            return shopeeuser;
        }

        public void setShopeeuser(String shopeeuser) {
            this.shopeeuser = shopeeuser;
        }

        public String getUsedbillno() {
            return usedbillno;
        }

        public void setUsedbillno(String usedbillno) {
            this.usedbillno = usedbillno;
        }

        public String getUsedfpv() {
            return usedfpv;
        }

        public void setUsedfpv(String usedfpv) {
            this.usedfpv = usedfpv;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
