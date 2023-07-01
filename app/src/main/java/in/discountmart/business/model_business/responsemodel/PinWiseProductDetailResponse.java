package in.discountmart.business.model_business.responsemodel;

public class PinWiseProductDetailResponse extends BaseResponseEntity {

    ProductDetails proddetail[];

    public ProductDetails[] getProddetail() {
        return proddetail;
    }

    public void setProddetail(ProductDetails[] proddetail) {
        this.proddetail = proddetail;
    }

    public class ProductDetails{
        String productname;//": "DREAM LIVE STRONG  AMLA + TULSI JUICE (1 LT)",
        String qty;//": "3",
        String MRP;//": "1125.00",
        String BV;//": "1.42"

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

        public String getMRP() {
            return MRP;
        }

        public void setMRP(String MRP) {
            this.MRP = MRP;
        }

        public String getBV() {
            return BV;
        }

        public void setBV(String BV) {
            this.BV = BV;
        }
    }

}
