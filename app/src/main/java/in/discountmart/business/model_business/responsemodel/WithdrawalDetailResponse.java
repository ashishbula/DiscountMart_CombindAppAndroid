package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class WithdrawalDetailResponse extends BaseResponse {
    ArrayList<WithdrawalDetail>bankwithdrawldetail;
    String recordcount;
    //String response;

    public ArrayList<WithdrawalDetail> getBankwithdrawldetail() {
        return bankwithdrawldetail;
    }

    public void setBankwithdrawldetail(ArrayList<WithdrawalDetail> bankwithdrawldetail) {
        this.bankwithdrawldetail = bankwithdrawldetail;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }



    public static class WithdrawalDetail{
        String reqid;//": "975675",
        String payeename;//": "SHREE NATH JI",
        String accountno;//": "32945869106",
        String bankname;//": "STATE BANK OF INDIA",
        String branchname;//": "NATHDAWARA",
        String ifsccode;//": "SBIN0031095",
        String reqamount;//": "10.00",
        String tdsamount;//": "0.00",
        String bankservice;//": "0.00",
        String netamount;//": "10.00",
        String reqdate;//": "06 Jul 2020",
        String issuedate;//": "06 Jul 2020",
        String status;//": "APPROVED"

        public String getReqid() {
            return reqid;
        }

        public void setReqid(String reqid) {
            this.reqid = reqid;
        }

        public String getPayeename() {
            return payeename;
        }

        public void setPayeename(String payeename) {
            this.payeename = payeename;
        }

        public String getAccountno() {
            return accountno;
        }

        public void setAccountno(String accountno) {
            this.accountno = accountno;
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

        public String getIfsccode() {
            return ifsccode;
        }

        public void setIfsccode(String ifsccode) {
            this.ifsccode = ifsccode;
        }

        public String getReqamount() {
            return reqamount;
        }

        public void setReqamount(String reqamount) {
            this.reqamount = reqamount;
        }

        public String getTdsamount() {
            return tdsamount;
        }

        public void setTdsamount(String tdsamount) {
            this.tdsamount = tdsamount;
        }

        public String getBankservice() {
            return bankservice;
        }

        public void setBankservice(String bankservice) {
            this.bankservice = bankservice;
        }

        public String getNetamount() {
            return netamount;
        }

        public void setNetamount(String netamount) {
            this.netamount = netamount;
        }

        public String getReqdate() {
            return reqdate;
        }

        public void setReqdate(String reqdate) {
            this.reqdate = reqdate;
        }

        public String getIssuedate() {
            return issuedate;
        }

        public void setIssuedate(String issuedate) {
            this.issuedate = issuedate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
