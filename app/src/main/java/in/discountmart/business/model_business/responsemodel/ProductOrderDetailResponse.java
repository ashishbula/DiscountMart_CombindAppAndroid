package in.discountmart.business.model_business.responsemodel;

import java.io.Serializable;

/**
 * Created by The Rock on 4/11/2018.
 */

public class ProductOrderDetailResponse implements Serializable {

    String prodid;//": "4",
    String productname;//": "ALOEVERA JUICE",
    String rate;//": "328.00",
    String qty;//": "1",
    String amount;//": "328.00",
    String status;//": "Pending",
    String imagepath;//": "www.visionroots.co.in/TransientStorage/../images/products/23022018220725.jpg"

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
