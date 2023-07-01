package in.discountmart.business.model_business.requestmodel;

public class NewJoinOtpRequest extends BaseRequest {
    //String reqtype;//":"reqjoiningotp","
    //String  userid;//":"Pc11000001","
   // String passwd;//":"123456","
    String mobileno;//":"8233832624","
    String membername;//":"test"}

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }
}
