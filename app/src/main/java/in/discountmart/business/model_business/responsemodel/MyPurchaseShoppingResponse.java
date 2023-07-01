package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/7/2018.
 */

public class MyPurchaseShoppingResponse extends BaseResponseEntity {
    String shopamt;//": "100.00",
    String utilamt;//": "162.47",
    String joinamt;//": "0.00",
    String repurchamt;//": "0.00",
    String recordcount;//": "1",
    ShoppingResponse mypurchase[];

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

    public ShoppingResponse[] getMypurchase() {
        return mypurchase;
    }

    public void setMypurchase(ShoppingResponse[] mypurchase) {
        this.mypurchase = mypurchase;
    }

    public class ShoppingResponse{

        String orderno;//": "",
        String orderdate;//": "05-May-2017",
        String amount;//": "",
        String qty;//": "",
        String bv;//": "100.00",
        String refno;//": ""


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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }

        public String getRefno() {
            return refno;
        }

        public void setRefno(String refno) {
            this.refno = refno;
        }
    }


}
