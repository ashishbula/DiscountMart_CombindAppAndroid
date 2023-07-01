package in.discountmart.business.model_business.requestmodel;

public class OuterTeamReportRequest extends BaseRequest {
   // String reqtype;//":"outerteamreport","
    String legno;//":"1",
    //String userid;//":"Pc11000001","
    //String passwd;//":"123456","
    String fromno;//":"100","
    String tono;//"

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
