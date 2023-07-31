package in.discountmart.business.model_business;

public class AddProductModel {
    String productname;
    double mrp;
    double rcp;
    double fv_rv;
    String productId;
    int produsQuantity;
    double totalMrp;
    double totalRcp;
    double totalFVRV;

    double shipping;

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getRcp() {
        return rcp;
    }

    public void setRcp(double rcp) {
        this.rcp = rcp;
    }

    public double getFv_rv() {
        return fv_rv;
    }

    public void setFv_rv(double fv_rv) {
        this.fv_rv = fv_rv;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProdusQuantity() {
        return produsQuantity;
    }

    public void setProdusQuantity(int produsQuantity) {
        this.produsQuantity = produsQuantity;
    }

    public double getTotalMrp() {
        return totalMrp;
    }

    public void setTotalMrp(double totalMrp) {
        this.totalMrp = totalMrp;
    }

    public double getTotalRcp() {
        return totalRcp;
    }

    public void setTotalRcp(double totalRcp) {
        this.totalRcp = totalRcp;
    }

    public double getTotalFVRV() {
        return totalFVRV;
    }

    public void setTotalFVRV(double totalFVRV) {
        this.totalFVRV = totalFVRV;
    }
}
