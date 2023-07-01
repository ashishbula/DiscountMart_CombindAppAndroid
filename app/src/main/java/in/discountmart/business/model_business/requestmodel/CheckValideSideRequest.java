package in.discountmart.business.model_business.requestmodel;

public class CheckValideSideRequest extends BaseRequest {
    //String reqtype;":"validside","
    String sponsorid;//;":"DT003210","
    String side;//":"1","
   // String userid;":"DT009189","
    //String passwd;":"sxdert12121"}


    public String getSponsorid() {
        return sponsorid;
    }

    public void setSponsorid(String sponsorid) {
        this.sponsorid = sponsorid;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
