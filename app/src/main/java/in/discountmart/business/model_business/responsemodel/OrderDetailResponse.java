package in.discountmart.business.model_business.responsemodel;

import java.io.Serializable;

/**
 * Created by The Rock on 4/11/2018.
 */

public class OrderDetailResponse implements Serializable {
    String kitid;//": "124",
    String kitname;//": "LADY FEEL FREE (RS - 4500)",
    String rate;//": "4500",
    String reqqty;//": "1",
    String sentqty;//": "0",
    String status;//": "Rejected"

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReqqty() {
        return reqqty;
    }

    public void setReqqty(String reqqty) {
        this.reqqty = reqqty;
    }

    public String getSentqty() {
        return sentqty;
    }

    public void setSentqty(String sentqty) {
        this.sentqty = sentqty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
