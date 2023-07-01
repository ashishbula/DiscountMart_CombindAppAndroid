package in.discountmart.business.model_business.requestmodel;

public class DownlinePurchaseRequest extends BaseRequest {
    //String userid;//":"005152","
    //String reqtype;//":"downlinepurchase","
    //String passwd;//":"@navneet","
    //tring side;//":"1","
    String from;//":"1","
    String fromno;//":"1","
    String to;//":"20","
    String tono;//":"20","
    String fromdate;//":"1-Jan-2018","
    String todate;//":"15-Jan-2018","
    String type;//":"T"}
    String level;//":"T"}
    String legno;//":"T"}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLegno() {
        return legno;
    }

    public void setLegno(String legno) {
        this.legno = legno;
    }

    public String getFromno() {
        return fromno;
    }

    public void setFromno(String fromno) {
        this.fromno = fromno;
    }

    public String getTono() {
        return tono;
    }

    public void setTono(String tono) {
        this.tono = tono;
    }
}
