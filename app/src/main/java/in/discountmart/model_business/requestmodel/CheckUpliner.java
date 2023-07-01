package in.discountmart.model_business.requestmodel;

public class CheckUpliner extends BaseRequest {
    //String reqtype;//": "checkupline",
    //String userid;//": "22334455",
    //String passwd;//": "OL@1324",
    String uplinerid;//":"27786266",
    String sponsorid;//":"22334455"

    public String getUplinerid() {
        return uplinerid;
    }

    public void setUplinerid(String uplinerid) {
        this.uplinerid = uplinerid;
    }

    public String getSponsorid() {
        return sponsorid;
    }

    public void setSponsorid(String sponsorid) {
        this.sponsorid = sponsorid;
    }
}
