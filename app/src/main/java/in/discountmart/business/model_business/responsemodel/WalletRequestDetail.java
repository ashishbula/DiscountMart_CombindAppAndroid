package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 5/10/2018.
 */

public class WalletRequestDetail extends BaseResponseEntity {
    String recordcount;
    WalletRequest pymtreqdetail[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public WalletRequest[] getPymtreqdetail() {
        return pymtreqdetail;
    }

    public void setPymtreqdetail(WalletRequest[] pymtreqdetail) {
        this.pymtreqdetail = pymtreqdetail;
    }

    public static class WalletRequest{
        String reqno;//": "1001",
        String reqdate;//": "10-May-2018",
        String paymode;//": "Cash",
        String acno;//": "",
        String transdate;//": "dd-MMM-yyyy",
        String bankname;//": "PUNJAB NATIONAL BANK",
        String branchname;//": "",
        String amount;//": "50.00",
        String transno;//": "123456",
        String remarks;//": "0",
        String status;//": "Pending",
        String scannedfile;//": ""

        public String getReqno() {
            return reqno;
        }

        public void setReqno(String reqno) {
            this.reqno = reqno;
        }

        public String getReqdate() {
            return reqdate;
        }

        public void setReqdate(String reqdate) {
            this.reqdate = reqdate;
        }

        public String getPaymode() {
            return paymode;
        }

        public void setPaymode(String paymode) {
            this.paymode = paymode;
        }

        public String getAcno() {
            return acno;
        }

        public void setAcno(String acno) {
            this.acno = acno;
        }

        public String getTransdate() {
            return transdate;
        }

        public void setTransdate(String transdate) {
            this.transdate = transdate;
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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getScannedfile() {
            return scannedfile;
        }

        public void setScannedfile(String scannedfile) {
            this.scannedfile = scannedfile;
        }
    }

}
