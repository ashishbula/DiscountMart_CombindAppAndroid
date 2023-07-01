package in.discountmart.business.model_business.responsemodel;

public class GetBankProofResponse extends BaseResponseEntity {


    String idno;//": "PC11000001",
    String bankid;//": "30",
    String acno;//": "123456789",
    String ifscode;//": "IFSC12345DS",
    String accounttype;//": "SAVING ACCOUNT",
    String branchname;//": "MALVIYA NAGAR, JAIPUR",
    String bankproof;//": "https://api.proglen.co.in/upload/profilepic/20200307174121415.jpg",
    String bankproofdate;//": "",
    String bankverf;//": "Verification Due",
    String isbankverified;//": "N",
    String rejectremark;//": "",
    String rejectreason;//": " ",
    //String response": "OK"


    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getIfscode() {
        return ifscode;
    }

    public void setIfscode(String ifscode) {
        this.ifscode = ifscode;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBankproof() {
        return bankproof;
    }

    public void setBankproof(String bankproof) {
        this.bankproof = bankproof;
    }

    public String getBankproofdate() {
        return bankproofdate;
    }

    public void setBankproofdate(String bankproofdate) {
        this.bankproofdate = bankproofdate;
    }

    public String getBankverf() {
        return bankverf;
    }

    public void setBankverf(String bankverf) {
        this.bankverf = bankverf;
    }

    public String getIsbankverified() {
        return isbankverified;
    }

    public void setIsbankverified(String isbankverified) {
        this.isbankverified = isbankverified;
    }

    public String getRejectremark() {
        return rejectremark;
    }

    public void setRejectremark(String rejectremark) {
        this.rejectremark = rejectremark;
    }

    public String getRejectreason() {
        return rejectreason;
    }

    public void setRejectreason(String rejectreason) {
        this.rejectreason = rejectreason;
    }
}
