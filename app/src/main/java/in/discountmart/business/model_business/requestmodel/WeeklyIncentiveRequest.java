package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/9/2018.
 */

public class WeeklyIncentiveRequest extends BaseRequest {
    //String reqtype;//":"weeklyincentive","
    //String userid;//":"dt144176","
    //String passwd;//":"ani123","
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
