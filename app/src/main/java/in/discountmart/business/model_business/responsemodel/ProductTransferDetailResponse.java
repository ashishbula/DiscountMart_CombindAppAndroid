package in.discountmart.business.model_business.responsemodel;

public class ProductTransferDetailResponse extends BaseResponseEntity {

    String recordcount;
    ProductTransDetail prodtransferdetail[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ProductTransDetail[] getProdtransferdetail() {
        return prodtransferdetail;
    }

    public void setProdtransferdetail(ProductTransDetail[] prodtransferdetail) {
        this.prodtransferdetail = prodtransferdetail;
    }

    public static class ProductTransDetail
    {
        String orderno;//": "14003",
        String productname;///": "ALOE FACE WASH",
        String qty;//": "1",
        String ordertype;//": "Self",
        String date;//": "11 Jun 2018"

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
