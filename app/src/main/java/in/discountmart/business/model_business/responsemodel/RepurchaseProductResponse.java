package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class RepurchaseProductResponse extends BaseResponse {

    ArrayList<ProductList> productlist;
    String recordcount;

    public static class ProductList {
        String prodid;
        String productname;
        String mrp;
        String dp;
        String rp;
        String bv;
        String pv;
        String qty;
        String bvval;
        String amount;

        String shipping;

        boolean isSelected;


        String TotalAmount;
        String Weight;

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            TotalAmount = totalAmount;
        }

        public String getWeight() {
            return Weight;
        }

        public void setWeight(String weight) {
            Weight = weight;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
        }

        public String getDp() {
            return dp;
        }

        public void setDp(String dp) {
            this.dp = dp;
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

        public String getPv() {
            return pv;
        }

        public void setPv(String pv) {
            this.pv = pv;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getBvval() {
            return bvval;
        }

        public void setBvval(String bvval) {
            this.bvval = bvval;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getProdid() {
            return prodid;
        }

        public void setProdid(String prodid) {
            this.prodid = prodid;
        }


        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }


        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public ArrayList<ProductList> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<ProductList> productlist) {
        this.productlist = productlist;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }


}
