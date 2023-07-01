package in.discountmart.business.model_business.requestmodel;

public class DownlineStatusRequest extends BaseRequest {
    //String userid;//":"005152","
    //String reqtype;//":"downstatus","
    //String passwd;//":"@navneet","
    String status;//":"N","
    String fromdate;//":"","
    String todate;//":""}

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
}
