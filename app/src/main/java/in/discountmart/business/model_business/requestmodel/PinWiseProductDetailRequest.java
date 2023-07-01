package in.discountmart.business.model_business.requestmodel;

public class PinWiseProductDetailRequest extends BaseRequest {

    //userid,passwd,
    String pinno;

    public String getPinno() {
        return pinno;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }
}
