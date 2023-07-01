package in.discountmart.business.model_business.requestmodel;

public class PinTransferWithOtpRequest extends BaseRequest {

    //String reqtype;//":"business_pintransfer","
    //String userid;//":"005152","
    //String passwd;//":"@navneet","
    String otp;//":"39795","
    String toid;//":"123775","
    String pkgid;//":"20","
    String qty;//":"1","
    String remark;//":"test by bispl"}

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
