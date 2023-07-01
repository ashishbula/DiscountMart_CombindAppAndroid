package in.discountmart.utility_services.travel.bus.bus_model.bus_response;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BusSeatsResponse extends BaseResponse implements Serializable {

    String maxSeatsPerTicket;
    BusSeats seats[];

    public String getMaxSeatsPerTicket() {
        return maxSeatsPerTicket;
    }

    public void setMaxSeatsPerTicket(String maxSeatsPerTicket) {
        this.maxSeatsPerTicket = maxSeatsPerTicket;
    }

    public BusSeats[] getSeats() {
        return seats;
    }

    public void setSeats(BusSeats[] seats) {
        this.seats = seats;
    }

    public static class BusSeats implements Serializable{
        String available;///": "true",
        String bankTrexAmt;//": "0.0",
        String baseFare;//": "550.00",
        String childFare;//": "0.0",
        String column;//": "0",
        String concession;//": "0.0",
        String fare;//": "577.50",
        String ladiesSeat;//": "false",
        String length;//": "2",
        String levyFare;//": "0.0",
        String malesSeat;//": "false",
        String markupFareAbsolute;//": "0.00",
        String markupFarePercentage;//": "0",
        String name;//": "A",
        String operatorServiceChargeAbsolute;//": "0.00",
        String operatorServiceChargePercent;//": "0",
        String row;//": "4",
        String serviceTaxAbsolute;//": "27.50",
        String serviceTaxPercentage;//": "5.0000",
        String srtFee;//": "0.0",
        String tollFee;//": "0.0",
        String width;//": "1",
        String zIndex;//": "1"

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getBankTrexAmt() {
            return bankTrexAmt;
        }

        public void setBankTrexAmt(String bankTrexAmt) {
            this.bankTrexAmt = bankTrexAmt;
        }

        public String getBaseFare() {
            return baseFare;
        }

        public void setBaseFare(String baseFare) {
            this.baseFare = baseFare;
        }

        public String getChildFare() {
            return childFare;
        }

        public void setChildFare(String childFare) {
            this.childFare = childFare;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getConcession() {
            return concession;
        }

        public void setConcession(String concession) {
            this.concession = concession;
        }

        public String getFare() {
            return fare;
        }

        public void setFare(String fare) {
            this.fare = fare;
        }

        public String getLadiesSeat() {
            return ladiesSeat;
        }

        public void setLadiesSeat(String ladiesSeat) {
            this.ladiesSeat = ladiesSeat;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getLevyFare() {
            return levyFare;
        }

        public void setLevyFare(String levyFare) {
            this.levyFare = levyFare;
        }

        public String getMalesSeat() {
            return malesSeat;
        }

        public void setMalesSeat(String malesSeat) {
            this.malesSeat = malesSeat;
        }

        public String getMarkupFareAbsolute() {
            return markupFareAbsolute;
        }

        public void setMarkupFareAbsolute(String markupFareAbsolute) {
            this.markupFareAbsolute = markupFareAbsolute;
        }

        public String getMarkupFarePercentage() {
            return markupFarePercentage;
        }

        public void setMarkupFarePercentage(String markupFarePercentage) {
            this.markupFarePercentage = markupFarePercentage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOperatorServiceChargeAbsolute() {
            return operatorServiceChargeAbsolute;
        }

        public void setOperatorServiceChargeAbsolute(String operatorServiceChargeAbsolute) {
            this.operatorServiceChargeAbsolute = operatorServiceChargeAbsolute;
        }

        public String getOperatorServiceChargePercent() {
            return operatorServiceChargePercent;
        }

        public void setOperatorServiceChargePercent(String operatorServiceChargePercent) {
            this.operatorServiceChargePercent = operatorServiceChargePercent;
        }

        public String getRow() {
            return row;
        }

        public void setRow(String row) {
            this.row = row;
        }

        public String getServiceTaxAbsolute() {
            return serviceTaxAbsolute;
        }

        public void setServiceTaxAbsolute(String serviceTaxAbsolute) {
            this.serviceTaxAbsolute = serviceTaxAbsolute;
        }

        public String getServiceTaxPercentage() {
            return serviceTaxPercentage;
        }

        public void setServiceTaxPercentage(String serviceTaxPercentage) {
            this.serviceTaxPercentage = serviceTaxPercentage;
        }

        public String getSrtFee() {
            return srtFee;
        }

        public void setSrtFee(String srtFee) {
            this.srtFee = srtFee;
        }

        public String getTollFee() {
            return tollFee;
        }

        public void setTollFee(String tollFee) {
            this.tollFee = tollFee;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getzIndex() {
            return zIndex;
        }

        public void setzIndex(String zIndex) {
            this.zIndex = zIndex;
        }
    }

}
