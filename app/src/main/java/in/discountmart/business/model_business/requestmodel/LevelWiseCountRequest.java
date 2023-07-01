package in.discountmart.business.model_business.requestmodel;

public class LevelWiseCountRequest extends BaseRequest{
      //"passwd": "DM@1234",  "reqtype": "levelwisecount",  "userid": "DM123456","transdate":""

    String transdate;

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }
}
