package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/4/2018.
 */

public class MyPurchaseRequest extends BaseFromToRequest {
    //String reqtype;//":"mypurchase","
    //String userid;//":"240291","
    //String passwd;//":"vinod","
    String fromdate;//":"01-Jan-2018","
    String todate;//":"15-Jan-2018","
    //String from;//":"1","
    //String to;//":"5"}


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
}
