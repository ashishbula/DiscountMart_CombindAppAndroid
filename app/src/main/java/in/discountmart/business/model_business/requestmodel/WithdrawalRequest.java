package in.discountmart.business.model_business.requestmodel;


public class WithdrawalRequest extends BaseRequest {
    //{"reqtype":"bankwithdrawls","userid":"mb223344","passwd":"MB@1234","" +
    String transpassword;//":"MB@1234","
    String reqamount;//":"100"}
    String from;
    String to;

    public String getTranspassword() {
        return transpassword;
    }

    public void setTranspassword(String transpassword) {
        this.transpassword = transpassword;
    }

    public String getReqamount() {
        return reqamount;
    }

    public void setReqamount(String reqamount) {
        this.reqamount = reqamount;
    }
}
