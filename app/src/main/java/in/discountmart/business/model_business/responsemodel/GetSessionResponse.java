package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/4/2018.
 */

public class GetSessionResponse extends BaseResponseEntity {

    GetSession session[];

    public GetSession[] getSession() {
        return session;
    }

    public void setSession(GetSession[] session) {
        this.session = session;
    }

    public class GetSession {
        String sessid;//": "8",
        String showpayout;//": "N",
        String fromdate;//": "01-Mar-2018",
        String todate;//": "31-Mar-2018",

        public String getSessid() {
            return sessid;
        }

        public void setSessid(String sessid) {
            this.sessid = sessid;
        }

        public String getShowpayout() {
            return showpayout;
        }

        public void setShowpayout(String showpayout) {
            this.showpayout = showpayout;
        }

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
}
