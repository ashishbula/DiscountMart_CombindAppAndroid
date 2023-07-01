package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 5/3/2018.
 */

public class CheckEpinRequest extends BaseRequest {
    //String passwd;//":"sxdert12121","
   // String reqtype;//":"stkstepindetail","
   // String userid;//":"DT009189","
    String fromdate;//":"1-May-2017","
    String todate;//":"1-May-2018"}

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
