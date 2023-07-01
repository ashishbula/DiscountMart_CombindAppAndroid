package in.discountmart.business.model_business.requestmodel;

public class PincodeDetailRequest extends BaseRequest {
    //String reqtype;//":"getpincodedetail","
    //String userid;//":"RBM378361","
    //String passwd;//":"ajayajay","
    String pincode;//:"311001"}

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
