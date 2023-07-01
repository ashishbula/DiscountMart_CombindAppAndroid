package in.discountmart.business.model_business.requestmodel;

public class DownlineRankRequest extends BaseRequest{
//"reqtype":"downlinerank",
        //"userid":"admin51",
        //"passwd":"winner9354",

        //"memberid":"",

    String rankid;//":"0",
    String legno;//":"0",
    String from;//":"1",
    String to;//":"10"

    public String getRankid() {
        return rankid;
    }

    public void setRankid(String rankid) {
        this.rankid = rankid;
    }

    public String getLegno() {
        return legno;
    }

    public void setLegno(String legno) {
        this.legno = legno;
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
}
