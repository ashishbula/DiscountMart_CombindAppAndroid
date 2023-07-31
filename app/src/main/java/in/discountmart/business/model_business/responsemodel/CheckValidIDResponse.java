package in.discountmart.business.model_business.responsemodel;


public class CheckValidIDResponse extends BaseResponse {

    String mobileno;
    String activestatus;

    public String getActivestatus() {
        return activestatus;
    }

    public void setActivestatus(String activestatus) {
        this.activestatus = activestatus;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}