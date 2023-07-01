package in.discountmart.utility_services.report.report_model.response;

import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;


public class LedgerReportResponse extends BaseResponse implements Serializable {
    String TotalCount;//": "149",
    ArrayList<LedgerReport>ledger;

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public ArrayList<LedgerReport> getLedger() {
        return ledger;
    }

    public void setLedger(ArrayList<LedgerReport> ledger) {
        this.ledger = ledger;
    }

    public static class LedgerReport implements Serializable{
        String UserName;//": "Demo",
        String Company;//": "DT123",
        String MemName;//": "Amit saini",
        String RechargeNo;//": null,
        String ServiceType;//": "PREPAID",
        String AccountNo;//": "9782777633",
        String ReqUserName;//": "",
        String ReqRemark;//": "",
        String TransactionRemark;//": "Payment Deduction Against Mobile recharge Mobile No.:9782777633",
        String Debit;//": "10.000",
        String Credit;//": "0.000",
        String Balalnce;//": "447.950",
        String TID;//": "12669",
        String FromID;//": null,
        String Reason;//": null,
        String Status;//": "Debit",
        String TransactionDTime;//": "19-Jun-2020 12:48:42"

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getCompany() {
            return Company;
        }

        public void setCompany(String company) {
            Company = company;
        }

        public String getMemName() {
            return MemName;
        }

        public void setMemName(String memName) {
            MemName = memName;
        }

        public String getRechargeNo() {
            return RechargeNo;
        }

        public void setRechargeNo(String rechargeNo) {
            RechargeNo = rechargeNo;
        }

        public String getServiceType() {
            return ServiceType;
        }

        public void setServiceType(String serviceType) {
            ServiceType = serviceType;
        }

        public String getAccountNo() {
            return AccountNo;
        }

        public void setAccountNo(String accountNo) {
            AccountNo = accountNo;
        }

        public String getReqUserName() {
            return ReqUserName;
        }

        public void setReqUserName(String reqUserName) {
            ReqUserName = reqUserName;
        }

        public String getReqRemark() {
            return ReqRemark;
        }

        public void setReqRemark(String reqRemark) {
            ReqRemark = reqRemark;
        }

        public String getTransactionRemark() {
            return TransactionRemark;
        }

        public void setTransactionRemark(String transactionRemark) {
            TransactionRemark = transactionRemark;
        }

        public String getDebit() {
            return Debit;
        }

        public void setDebit(String debit) {
            Debit = debit;
        }

        public String getCredit() {
            return Credit;
        }

        public void setCredit(String credit) {
            Credit = credit;
        }

        public String getBalalnce() {
            return Balalnce;
        }

        public void setBalalnce(String balalnce) {
            Balalnce = balalnce;
        }

        public String getTID() {
            return TID;
        }

        public void setTID(String TID) {
            this.TID = TID;
        }

        public String getFromID() {
            return FromID;
        }

        public void setFromID(String fromID) {
            FromID = fromID;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String reason) {
            Reason = reason;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getTransactionDTime() {
            return TransactionDTime;
        }

        public void setTransactionDTime(String transactionDTime) {
            TransactionDTime = transactionDTime;
        }
    }
}
