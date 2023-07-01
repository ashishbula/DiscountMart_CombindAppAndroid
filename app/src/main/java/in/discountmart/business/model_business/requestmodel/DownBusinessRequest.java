package in.discountmart.business.model_business.requestmodel;

public class DownBusinessRequest extends BaseRequest {

    //String passwd;//": "@navneet",
    //String reqtype;//": "groupbusiness",
    //String userid;//": "005152",
    //String memberid;//": "005152"
    String sessid;//": "8",
    String from;//": "1",
    String to;//": "10",
    String startdate;
    String enddate;


    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
