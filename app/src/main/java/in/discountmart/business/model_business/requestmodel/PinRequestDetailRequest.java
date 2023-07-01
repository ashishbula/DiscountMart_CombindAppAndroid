package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/11/2018.
 */

public class PinRequestDetailRequest extends BaseRequest {

    //String reqtype;//":"pinreqdetail","
    //String userid;//":"DT009189","
    //String passwd;//":"zcsx19","
    String fromdate;//":"1-Jan-2018","
    String todate;//":"15-Jan-2018","
    String from;//":"1","
    String to;//":"5","
    String dispstatus;//":"R"}

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

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

    public String getDispstatus() {
        return dispstatus;
    }

    public void setDispstatus(String dispstatus) {
        this.dispstatus = dispstatus;
    }
}
