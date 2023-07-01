package in.discountmart.utility_services.travel.flight.flight_model.flight_response_model;


import in.discountmart.utility_services.model.response_model.BaseResponse;

public class FlightMarginResponse extends BaseResponse {

    String ID;//":0,"
    String GroupId;//":null,"
    String FlightNumber;//":null,"
    String FlightMarginInAmount;//":0.00,"
    String Commission;//":0.00,"
    String MaxCommission;//":0.00,"
    String DeductMainAmtPer;//":0.00,"
    String DeductMainAmt;//":0.00}"

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getFlightNumber() {
        return FlightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        FlightNumber = flightNumber;
    }

    public String getFlightMarginInAmount() {
        return FlightMarginInAmount;
    }

    public void setFlightMarginInAmount(String flightMarginInAmount) {
        FlightMarginInAmount = flightMarginInAmount;
    }

    public String getCommission() {
        return Commission;
    }

    public void setCommission(String commission) {
        Commission = commission;
    }

    public String getMaxCommission() {
        return MaxCommission;
    }

    public void setMaxCommission(String maxCommission) {
        MaxCommission = maxCommission;
    }

    public String getDeductMainAmtPer() {
        return DeductMainAmtPer;
    }

    public void setDeductMainAmtPer(String deductMainAmtPer) {
        DeductMainAmtPer = deductMainAmtPer;
    }

    public String getDeductMainAmt() {
        return DeductMainAmt;
    }

    public void setDeductMainAmt(String deductMainAmt) {
        DeductMainAmt = deductMainAmt;
    }
}
