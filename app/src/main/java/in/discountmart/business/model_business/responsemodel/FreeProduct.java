package in.discountmart.business.model_business.responsemodel;

public class FreeProduct {

    String prodid;//": "205",
    String product;//": "FREE PANCH TULSI",
    String dp;//": "0.00",
    String mrp;//": "0.00",
    String bv;//": "0.00",
    String pv;//": "0.00",
    String tax;//": "0.00",
    String imagepath;//": "www.visionroots.co.in/Images/noproduct.jpg",
    String isfixed;//": "N"
    private boolean isSelected=false;

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

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getIsfixed() {
        return isfixed;
    }

    public void setIsfixed(String isfixed) {
        this.isfixed = isfixed;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean setSelected(boolean selected) {
        isSelected = selected;
        return selected;
    }
}
