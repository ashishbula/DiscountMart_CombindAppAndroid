package in.discountmart.business.model_business.responsemodel;

public class GetBankDetailResponse extends BaseResponseEntity {

    //String response;//":"OK","
    String payeename;//":"Vision Root","
    String bankid;//":"0", "
    String branchname;//":"","
    String acno;//":"","

    String panno;//":"Pan details"}
    String bankname;//": "PUNJAB NATIONAL BANK",
    String ifsccode;//": "PUNB0098740",
    String mobileno;//": "9828996926",


    public String getPayeename() {
        return payeename;
    }

    public void setPayeename(String payeename) {
        this.payeename = payeename;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }
}
