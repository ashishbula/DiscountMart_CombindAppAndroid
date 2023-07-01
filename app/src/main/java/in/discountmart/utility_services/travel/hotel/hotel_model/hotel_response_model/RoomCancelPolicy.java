package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.io.Serializable;

public class RoomCancelPolicy implements Serializable {

    String Charge;//": 100,
    String ChargeType;//": 2,
    String Currency;//": "INR",
    String FromDate;//": "2019-08-04T00:00:00",
    String ToDate;//": "2019-08-06T23:59:59"

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }

    public String getChargeType() {
        return ChargeType;
    }

    public void setChargeType(String chargeType) {
        ChargeType = chargeType;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }
}
