package in.discountmart.business.model_business.responsemodel;

public class BankInfoResponse extends BaseResponseEntity {

    String bankid;//":"17","
    String branchname;//":"vasai west ","
    String actype;//":"0","SAVING ACCOUNT"
    String acno;//":"3912101001464","
    String ifsc;//":"CNRB0003912","
    String refidno;//":"DT999381","
    String refname;//":"HARISHCHANDRA SHANKAR GIRAMKAR      ","" +
    String uplnidno;
    String uplnname;
    String legno;//":"2"}

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

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getRefidno() {
        return refidno;
    }

    public void setRefidno(String refidno) {
        this.refidno = refidno;
    }

    public String getRefname() {
        return refname;
    }

    public void setRefname(String refname) {
        this.refname = refname;
    }

    public String getLegno() {
        return legno;
    }

    public void setLegno(String legno) {
        this.legno = legno;
    }

    public String getUplnidno() {
        return uplnidno;
    }

    public void setUplnidno(String uplnidno) {
        this.uplnidno = uplnidno;
    }

    public String getUplnname() {
        return uplnname;
    }

    public void setUplnname(String uplnname) {
        this.uplnname = uplnname;
    }
}
