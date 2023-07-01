package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/26/2018.
 */

public class TopupDownlineRequest extends BaseRequest {

    //String reqtype;//":"downlinepurchase","
    //String userid;//":"DT009189","
    //String passwd;//":"zcsx19","
    String fromdate;//":"1-Jan-2018","
    String todate;//":"15-Jan-2018","
    String from;//":"1","
    String to;//":"5","
    String side;//":"1"}

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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
