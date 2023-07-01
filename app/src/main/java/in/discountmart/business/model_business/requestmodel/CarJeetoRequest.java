package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/28/2018.
 */

public class CarJeetoRequest extends BaseRequest {
    //String reqtype;//":"newcarjeeto","
    //String userid;//":"DT209718","
    //String passwd;//":"dtabc12345","
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
