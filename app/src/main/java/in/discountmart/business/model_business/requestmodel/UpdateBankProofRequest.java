package in.discountmart.business.model_business.requestmodel;

public class UpdateBankProofRequest extends BaseRequest {

    //String reqtype;//":"bankproof","
    //String userid;//":"005152","
    //String passwd;//":"@navneet","
    String accountno;//":"123456789","
    String branchname;//":"sanganerigate","
    String ifsccode;//":"punb0009874561","
    String bankcode;//":"10","
    String accounttype;//":"Saving Account"}

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }
}
