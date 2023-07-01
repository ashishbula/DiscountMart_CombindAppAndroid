package in.discountmart.business.model_business.responsemodel;

import java.io.Serializable;

/**
 * Created by The Rock on 5/3/2018.
 */

public class CheckEpinKit implements Serializable {

     String kitid;//": "79",
    String kitname;//": "DREAMS AGRI JACKPOT DOUBLE (Rs.4500)",
    String qty;//": "5",
    String dispqty;//": "5",
    String status;//": "Sent"
    String availqty;
    String isavail;

    public String getKitid() {
        return kitid;
    }

    public void setKitid(String kitid) {
        this.kitid = kitid;
    }

    public String getKitname() {
        return kitname;
    }

    public void setKitname(String kitname) {
        this.kitname = kitname;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDispqty() {
        return dispqty;
    }

    public void setDispqty(String dispqty) {
        this.dispqty = dispqty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvailqty() {
        return availqty;
    }

    public void setAvailqty(String availqty) {
        this.availqty = availqty;
    }

    public String getIsavail() {
        return isavail;
    }

    public void setIsavail(String isavail) {
        this.isavail = isavail;
    }
}
