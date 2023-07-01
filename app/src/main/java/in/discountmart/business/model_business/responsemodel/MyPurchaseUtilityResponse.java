package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/7/2018.
 */

public class MyPurchaseUtilityResponse extends BaseResponseEntity {
    String shopamt;//": "0.00",
    String utilamt;//": "5450.99",
    String joinamt;//": "20.00",
    String repurchamt;//": "1721.00",
    String recordcount;//": "22",

    UtilityResponse mypurchase[];

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

    public UtilityResponse[] getMypurchase() {
        return mypurchase;
    }

    public void setMypurchase(UtilityResponse[] mypurchase) {
        this.mypurchase = mypurchase;
    }

    public class UtilityResponse{

        String operatorname;//": "Jet Airways(9W) (7133)",
        String orderdate;//": "02-Apr-2018",
        String amount;//": "5028.00",
        String surcharge;//": "0.00",
        String totalamt;//": "5028.00",
        String bv;//": "125.70",
        String status;//": "Utility"

        public String getOperatorname() {
            return operatorname;
        }

        public void setOperatorname(String operatorname) {
            this.operatorname = operatorname;
        }

        public String getOrderdate() {
            return orderdate;
        }

        public void setOrderdate(String orderdate) {
            this.orderdate = orderdate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSurcharge() {
            return surcharge;
        }

        public void setSurcharge(String surcharge) {
            this.surcharge = surcharge;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
