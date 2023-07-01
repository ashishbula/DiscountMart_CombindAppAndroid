package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/26/2018.
 */

public class DownlineRepurchaseRequest extends BaseRequest {

    //String reqtype;//":"downlinerepurchase"," +
    //String userid;////":"DT009189","
    //String passwdl;//":"sxdert12121","
    String fromdate;//":"1-Jan-2018","
    String todate;//":"15-Jan-2018","
    String from;//":"1","
    String to;//":"5","
    String level;//":"5"}

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
