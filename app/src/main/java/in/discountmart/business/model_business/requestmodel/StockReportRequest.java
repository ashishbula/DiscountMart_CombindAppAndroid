package in.discountmart.business.model_business.requestmodel;

public class StockReportRequest extends BaseRequest {
    //String userid;//":"564194","
    //String reqtype;//":"stockreport","
    //String passwd;//":"lalit@123","
    String from;//":"1","
    String to;//":"4","
    String prodid;//":"0"}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }
}
