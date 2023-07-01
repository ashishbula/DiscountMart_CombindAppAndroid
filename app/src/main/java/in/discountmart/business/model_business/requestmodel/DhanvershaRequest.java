package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/26/2018.
 */

public class DhanvershaRequest extends BaseRequest{
    //String reqtype;//":"dhanvarshaelig","
    //String userid;//":"DT2289866","
    //String passwd;//":"050179","
    String from;//":"1","
    String to;//":"5"}

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
}
