package in.discountmart.business.model_business.responsemodel;

public class ProductTransferListResponse extends BaseResponseEntity{

    String recordcount;
    ProdtTransList products[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ProdtTransList[] getProducts() {
        return products;
    }

    public void setProducts(ProdtTransList[] products) {
        this.products = products;
    }

    public static class ProdtTransList
    {
        String prodid;//": "22",
        String product;//": "ALOE FACE WASH",
        String qty;//": "2.00"

        public String getProdid() {
            return prodid;
        }

        public void setProdid(String prodid) {
            this.prodid = prodid;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }
}
