package in.discountmart.business.model_business.responsemodel;

public class FundReqDetailResponse extends BaseResponseEntity {

    String recordcount;//": "2",
    //String response;//": "OK"
     FundReqDetail fundreqdetail[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public FundReqDetail[] getFundreqdetail() {
        return fundreqdetail;
    }

    public void setFundreqdetail(FundReqDetail[] fundreqdetail) {
        this.fundreqdetail = fundreqdetail;
    }

    public static class FundReqDetail
    {
        String reqid;//": "1001",
        String payeename;//": "Vision Root",
        String acno;//": "",
        String bankname;//": "--Choose Bank Name--",
        String branch;//": "",
        String ifsccode;//": "",
        String requestamount;//": "500.00",
        String reqdate;//": "24 Jul 2018",
        String issueddate;//": ""
        String status;

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

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getIfsccode() {
            return ifsccode;
        }

        public void setIfsccode(String ifsccode) {
            this.ifsccode = ifsccode;
        }

        public String getRequestamount() {
            return requestamount;
        }

        public void setRequestamount(String requestamount) {
            this.requestamount = requestamount;
        }

        public String getReqdate() {
            return reqdate;
        }

        public void setReqdate(String reqdate) {
            this.reqdate = reqdate;
        }

        public String getIssueddate() {
            return issueddate;
        }

        public void setIssueddate(String issueddate) {
            this.issueddate = issueddate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
