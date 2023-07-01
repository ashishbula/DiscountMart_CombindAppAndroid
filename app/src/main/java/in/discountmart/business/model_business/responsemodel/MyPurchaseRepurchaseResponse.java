package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/4/2018.
 */

public class MyPurchaseRepurchaseResponse extends BaseResponseEntity {

    String shopamt;//": "0.00",
    String utilamt;//": "5450.99",
    String joinamt;//": "20.00",
    String repurchamt;//": "1721.00",
    String recordcount;//": "3",
    String totalamount;//": "2199",
    String totalbv;//": "1721",
    RepurchaseResponse mypurchase[];

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

    public RepurchaseResponse[] getMypurchase() {
        return mypurchase;
    }

    public void setMypurchase(RepurchaseResponse[] mypurchase) {
        this.mypurchase = mypurchase;
    }

    public class RepurchaseResponse{
        String sessid;//": "6",
        String billno;//": "DT/2017-18/WR/2087",
        String billdate;//": "18-Jan-2018",
        String amount;//": "700.00",
        String orderno;//": "",
        String bv;//": "408.00",
        String status;//": "Repurchase"

        public String getSessid() {
            return sessid;
        }

        public void setSessid(String sessid) {
            this.sessid = sessid;
        }

        public String getBillno() {
            return billno;
        }

        public void setBillno(String billno) {
            this.billno = billno;
        }

        public String getBilldate() {
            return billdate;
        }

        public void setBilldate(String billdate) {
            this.billdate = billdate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
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
    }
}
