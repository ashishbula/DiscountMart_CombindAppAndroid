package in.discountmart.business.model_business.responsemodel;

public class MyPurchaseResponse extends BaseResponseEntity {

    String recordcount;//": "1",
    String totalamount;//": "14753",
    String totalvrp;//": "1",
    MyPurchase mypurchase[];

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

    public String getTotalvrp() {
        return totalvrp;
    }

    public void setTotalvrp(String totalvrp) {
        this.totalvrp = totalvrp;
    }

    public MyPurchase[] getMypurchase() {
        return mypurchase;
    }

    public void setMypurchase(MyPurchase[] mypurchase) {
        this.mypurchase = mypurchase;
    }

    public static class MyPurchase
    {
        String billdate;//": "11-Jan-2018",
        String bv;//": "1.00",
        String amount;//": "14753.00",
        String status;//": "Combo Request"
        String billno;

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }



}
