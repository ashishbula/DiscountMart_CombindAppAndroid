package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 3/28/2018.
 */

public class StatementRequest extends BaseRequest {
   String sessid;

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }
}
