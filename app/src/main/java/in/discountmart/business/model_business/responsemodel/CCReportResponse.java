package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class CCReportResponse extends BaseResponseEntity {
    ArrayList <CCReport> campcouponreport;
    String recordcount;

    public ArrayList<CCReport> getCampcouponreport() {
        return campcouponreport;
    }

    public void setCampcouponreport(ArrayList<CCReport> campcouponreport) {
        this.campcouponreport = campcouponreport;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class CCReport{
         String sno;//": "1",
        String billno;//": "Manually Allotted.",
        String billamount;//": "0.00",
        String billdate;//": "23-Oct-2019",
        String startdate;//": "23-Oct-2019",
        String fv;//": "0.00",
        String couponamount;//": "2310.00",
        String couponno;//": "AD38225E",
        String status;//": "Used",
        String expirydate;//": "22-Nov-2019",
        String useddate;//": "24-Oct-2019",
        String usedbillno;//": "PG/MFR/00116"

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

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFv() {
            return fv;
        }

        public void setFv(String fv) {
            this.fv = fv;
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

        public String getUsedbillno() {
            return usedbillno;
        }

        public void setUsedbillno(String usedbillno) {
            this.usedbillno = usedbillno;
        }
    }
}
