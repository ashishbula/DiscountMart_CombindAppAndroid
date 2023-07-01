package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 5/10/2018.
 */

public class WalletReqRequest extends BaseRequest {
    //String passwd;//": "sxdert12121",
    //String reqtype;//": "paymentrequest",
    //String userid;//": "DT009189",
     //"passwd": "@navneet",
             //"reqtype": "paymentrequest",
            // "userid": "005152",
    String paymodeid;//": "2",
    String paymode;//": "Cash",
    String amount;//": "50",
    String transno;//": "123456",
    String transdate;//": "15-May-2018",
    //String transbankid;//": "1",
    //String transbank;//": "TEST",
    String acno;//": "123456789",
    //String transbranch;//": "",
    String remarks;//": "TEST"
    String bankid;//": "1",
    String bankname;//": "ABHYUDAYA COOPERATIVE BANK LIMITED",
    String branchname;//": "Bhilwara",
    String transpassword;//": "DM@1234",
    String wallettype;//": "DM@1234",
    String imgpath;//": "https://discountmania.in/images/UploadImage/20211021133336958.png

    public String getPaymodeid() {
        return paymodeid;
    }

    public void setPaymodeid(String paymodeid) {
        this.paymodeid = paymodeid;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransno() {
        return transno;
    }

    public void setTransno(String transno) {
        this.transno = transno;
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getTranspassword() {
        return transpassword;
    }

    public void setTranspassword(String transpassword) {
        this.transpassword = transpassword;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getWallettype() {
        return wallettype;
    }

    public void setWallettype(String wallettype) {
        this.wallettype = wallettype;
    }
}
