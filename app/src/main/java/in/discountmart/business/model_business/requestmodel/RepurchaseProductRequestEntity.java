package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/20/2018.
 */

public class RepurchaseProductRequestEntity extends BaseRequest {

    String productName;
    String productID;
    String productDP;
    String totalBV;
    String productAmnt;
    String productRP;
    String productBV;
    String totalAmount;
    String productQty;

    public String getTotalBV() {
        return totalBV;
    }

    public void setTotalBV(String totalBV) {
        this.totalBV = totalBV;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductDP() {
        return productDP;
    }

    public void setProductDP(String productDP) {
        this.productDP = productDP;
    }

    public String getProductAmnt() {
        return productAmnt;
    }

    public void setProductAmnt(String productAmnt) {
        this.productAmnt = productAmnt;
    }

    public String getProductRP() {
        return productRP;
    }

    public void setProductRP(String productRP) {
        this.productRP = productRP;
    }

    public String getProductBV() {
        return productBV;
    }

    public void setProductBV(String productBV) {
        this.productBV = productBV;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
