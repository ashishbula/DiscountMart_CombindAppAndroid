package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/19/2018.
 */

public class ProductListResponse extends BaseResponseEntity {

    ProductList products[];

    public ProductList[] getProducts() {
        return products;
    }

    public void setProducts(ProductList[] products) {
        this.products = products;
    }

    public static class ProductList{
        String prodid;//": "1131",
        String product;//": "DHANVARSHA DESIRE FORTE",
        String dp;//": "7500.00",
        String mrp;//": "10800.00",
        String rp;//": "0.00",
        String bv;//": "6000.00"

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

        public String getDp() {
            return dp;
        }

        public void setDp(String dp) {
            this.dp = dp;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getRp() {
            return rp;
        }

        public void setRp(String rp) {
            this.rp = rp;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }
    }

}
