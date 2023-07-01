package in.discountmart.business.model_business.responsemodel;

public class MyPurchaseOpenChoiceResponse extends BaseResponseEntity {
    String shopamt;//": "0.00",
    String utilamt;//": "0.00",
    String joinamt;//": "6.00",
    String repurchamt;//": "0.00",
    String recordcount;//": "1",
    String totalamount;//": "5000",
    String totalbv;//": "6",
            //"response": "OK"
    OpenChoiceResponse mypurchase[];

    public String getShopamt() {
        return shopamt;
    }

    public void setShopamt(String shopamt) {
        this.shopamt = shopamt;
    }

    public String getUtilamt() {
        return utilamt;
    }

    public void setUtilamt(String utilamt) {
        this.utilamt = utilamt;
    }

    public String getJoinamt() {
        return joinamt;
    }

    public void setJoinamt(String joinamt) {
        this.joinamt = joinamt;
    }

    public String getRepurchamt() {
        return repurchamt;
    }

    public void setRepurchamt(String repurchamt) {
        this.repurchamt = repurchamt;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getTotalbv() {
        return totalbv;
    }

    public void setTotalbv(String totalbv) {
        this.totalbv = totalbv;
    }

    public OpenChoiceResponse[] getMypurchase() {
        return mypurchase;
    }

    public void setMypurchase(OpenChoiceResponse[] mypurchase) {
        this.mypurchase = mypurchase;
    }

    public class OpenChoiceResponse {
        String sessionno;//": "11",
        String orderno;//": "252924",
        String orderdate;//": "07-Jun-2018",
        String qty;//": "10",
        String amount;//": "5000.00",
        String dispatchqty;//": "0",
        String totalamt;//": "5000.00",
        String bv;//": "6.00",
        String remark;//": "First Order Request.",
        String status;//": "Pending"

        public String getSessionno() {
            return sessionno;
        }

        public void setSessionno(String sessionno) {
            this.sessionno = sessionno;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getOrderdate() {
            return orderdate;
        }

        public void setOrderdate(String orderdate) {
            this.orderdate = orderdate;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDispatchqty() {
            return dispatchqty;
        }

        public void setDispatchqty(String dispatchqty) {
            this.dispatchqty = dispatchqty;
        }

        public String getTotalamt() {
            return totalamt;
        }

        public void setTotalamt(String totalamt) {
            this.totalamt = totalamt;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
