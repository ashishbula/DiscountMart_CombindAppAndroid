package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/7/2018.
 */

public class MyPurchaseTopupResponse extends BaseResponseEntity {
    String shopamt;//": "0.00",
    String utilamt;//": "5450.99",
    String joinamt;//": "20.00",
    String repurchamt;//": "1721.00",
    String recordcount;//": "1",
    TopupResponse mypurchase[];

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

    public TopupResponse[] getMypurchase() {
        return mypurchase;
    }

    public void setMypurchase(TopupResponse[] mypurchase) {
        this.mypurchase = mypurchase;
    }

    public class TopupResponse{
        String sessid;//": "1",
        String billno;//": " ",
        String billdate;//": "24-Nov-2016",
        String amount;//": "14000.00",
        String pkgname;//": "AQUA NEEL PLATINUM",
        String orderno;//": " ",
        String bv;//": "20.00",
        String status;//": "TopUp"

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

        public String getPkgname() {
            return pkgname;
        }

        public void setPkgname(String pkgname) {
            this.pkgname = pkgname;
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
