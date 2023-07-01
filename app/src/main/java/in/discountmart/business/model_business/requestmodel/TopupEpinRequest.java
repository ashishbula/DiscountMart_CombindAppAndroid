package in.discountmart.business.model_business.requestmodel;

public class TopupEpinRequest extends BaseRequest {

    //String reqtype;//: upgradeid
    String pinno;//scratch no
    String referralid;
    String side;
    String actype;
    String bankcode;
    String branch;
    String accountno;
    String ifsc;
    String delvcenter;

    public String getPinno() {
        return pinno;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }

    public String getReferralid() {
        return referralid;
    }

    public void setReferralid(String referralid) {
        this.referralid = referralid;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getDelvcenter() {
        return delvcenter;
    }

    public void setDelvcenter(String delvcenter) {
        this.delvcenter = delvcenter;
    }
}
