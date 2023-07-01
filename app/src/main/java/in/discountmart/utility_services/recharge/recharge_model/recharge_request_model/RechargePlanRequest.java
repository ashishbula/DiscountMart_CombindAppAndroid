package in.discountmart.utility_services.recharge.recharge_model.recharge_request_model;

import java.io.Serializable;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class RechargePlanRequest extends DataRequest implements Serializable {

     String circle;//": "RJ",
    String ipCode;//": "BGP",
    String mobileno;//": "9414369901"

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getIpCode() {
        return ipCode;
    }

    public void setIpCode(String ipCode) {
        this.ipCode = ipCode;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
