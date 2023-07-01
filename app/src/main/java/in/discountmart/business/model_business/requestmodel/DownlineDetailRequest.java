package in.discountmart.business.model_business.requestmodel;

/**
 * Created by Dell on 09-01-2018.
 */

public class DownlineDetailRequest extends BaseRequest {
    //String userid;//":"9994","
    //String reqtype;//":"downline","
    //String passwd;//":"dnaradmin","
    String side;//":"1","
    String legno;//":"1","
    String from;//":"1","
    String to;//":"10"}
    String srchid;
    String status;
    String fromdate;//":"1-Jan-2018","
    String todate;//":"15-Jan-2018","


    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
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

    public String getSrchid() {
        return srchid;
    }

    public void setSrchid(String srchid) {
        this.srchid = srchid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLegno() {
        return legno;
    }

    public void setLegno(String legno) {
        this.legno = legno;
    }
}
