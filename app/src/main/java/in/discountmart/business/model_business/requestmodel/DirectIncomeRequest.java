package in.discountmart.business.model_business.requestmodel;

public class DirectIncomeRequest extends BaseRequest {
    //String userid;//":"107040","
    //String reqtype;//":"directincome","
    //String passwd;//":"sunil07","
    String from;//":"1","
    String to;//":"10"}

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
