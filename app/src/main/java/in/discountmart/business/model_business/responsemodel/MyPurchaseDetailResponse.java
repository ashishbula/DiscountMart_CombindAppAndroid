package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class MyPurchaseDetailResponse extends BaseResponse {

    String recordcount;
    ArrayList<MyPurchaseDetail> mypurchase;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<MyPurchaseDetail> getMypurchase() {
        return mypurchase;
    }

    public void setMypurchase(ArrayList<MyPurchaseDetail> mypurchase) {
        this.mypurchase = mypurchase;
    }

    public static class MyPurchaseDetail {
        String sno;//":"","
        String billnoorderno;//":"","
        String billdate;//":"","
        String request;//":"",
        String orderamount;//":"","
        String shopingwallet;//":"","
        String bv;//":"","
        String status;//":""}
        String couriername;
        String docketno;
        String docketdate;
        String website;

        public String getBillnoorderno() {
            return billnoorderno;
        }

        public void setBillnoorderno(String billnoorderno) {
            this.billnoorderno = billnoorderno;
        }

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getBilldate() {
            return billdate;
        }

        public void setBilldate(String billdate) {
            this.billdate = billdate;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getOrderamount() {
            return orderamount;
        }

        public void setOrderamount(String orderamount) {
            this.orderamount = orderamount;
        }

        public String getShopingwallet() {
            return shopingwallet;
        }

        public void setShopingwallet(String shopingwallet) {
            this.shopingwallet = shopingwallet;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCouriername() {
            return couriername;
        }

        public void setCouriername(String couriername) {
            this.couriername = couriername;
        }

        public String getDocketno() {
            return docketno;
        }

        public void setDocketno(String docketno) {
            this.docketno = docketno;
        }

        public String getDocketdate() {
            return docketdate;
        }

        public void setDocketdate(String docketdate) {
            this.docketdate = docketdate;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }
}
