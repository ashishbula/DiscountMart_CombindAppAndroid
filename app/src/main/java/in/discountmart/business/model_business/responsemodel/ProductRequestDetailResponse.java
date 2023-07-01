package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/11/2018.
 */

public class ProductRequestDetailResponse extends BaseResponseEntity {

    ProductRequestDetail reqdetail[];
    String recordcount;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ProductRequestDetail[] getReqdetail() {
        return reqdetail;
    }

    public void setReqdetail(ProductRequestDetail[] reqdetail) {
        this.reqdetail = reqdetail;
    }

    public static class ProductRequestDetail{
         String orderno;//": "100002",
        String orderdate;//": "30-May-2018",
        String orderamt;//": "807.00",
        String orderqty;//": "7",
        String remarks;//": "test By Bispl",
        String bankamt;//": "0.00",
        String repurchasewallet;//": "0.00",
        String mainwallet;//": "807.00",
        String ostatus;//": "Pending",

        ProductOrderDetailResponse orderdetail[];

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

        public String getOrderamt() {
            return orderamt;
        }

        public void setOrderamt(String orderamt) {
            this.orderamt = orderamt;
        }

        public String getOrderqty() {
            return orderqty;
        }

        public void setOrderqty(String orderqty) {
            this.orderqty = orderqty;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getBankamt() {
            return bankamt;
        }

        public void setBankamt(String bankamt) {
            this.bankamt = bankamt;
        }

        public String getRepurchasewallet() {
            return repurchasewallet;
        }

        public void setRepurchasewallet(String repurchasewallet) {
            this.repurchasewallet = repurchasewallet;
        }

        public String getMainwallet() {
            return mainwallet;
        }

        public void setMainwallet(String mainwallet) {
            this.mainwallet = mainwallet;
        }

        public String getOstatus() {
            return ostatus;
        }

        public void setOstatus(String ostatus) {
            this.ostatus = ostatus;
        }

        public ProductOrderDetailResponse[] getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(ProductOrderDetailResponse[] orderdetail) {
            this.orderdetail = orderdetail;
        }
    }
}


