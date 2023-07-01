package in.discountmart.utility_services.report.report_model.response;

import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class RechargeReportResponse extends BaseResponse implements Serializable{
    String TotalCount;//": "1",
    ArrayList<RechargeReport> RechargeList;

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public ArrayList<RechargeReport> getRechargeList() {
        return RechargeList;
    }

    public void setRechargeList(ArrayList<RechargeReport> rechargeList) {
        RechargeList = rechargeList;
    }

    public static class RechargeReport implements Serializable {
        String UserName;//": "DT123",
        String MobileNo;//": "3010640068",
        String Reason;//": "",
        String RechargeDate;//": "11-Feb-2019 01:14 57 PM",
        String RchargeDate;//": "2019-02-11T13:14:57.097",
        String Amount;//": 50,
        String Status;//": "FAILED",
        String Service;//": "DTH",
        String TID;//": "",
        String MobileOperator;//": "Airtel Digitel Topup",
        String Sno;//": "4269",
        String SID;//": "20190211131426708",
        String OperatorCode;//": "0",
        String ReceiveStatus;//": "IRA",
        String OperatorID;//": ""

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String mobileNo) {
            MobileNo = mobileNo;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String reason) {
            Reason = reason;
        }

        public String getRechargeDate() {
            return RechargeDate;
        }

        public void setRechargeDate(String rechargeDate) {
            RechargeDate = rechargeDate;
        }

        public String getRchargeDate() {
            return RchargeDate;
        }

        public void setRchargeDate(String rchargeDate) {
            RchargeDate = rchargeDate;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getService() {
            return Service;
        }

        public void setService(String service) {
            Service = service;
        }

        public String getTID() {
            return TID;
        }

        public void setTID(String TID) {
            this.TID = TID;
        }

        public String getMobileOperator() {
            return MobileOperator;
        }

        public void setMobileOperator(String mobileOperator) {
            MobileOperator = mobileOperator;
        }

        public String getSno() {
            return Sno;
        }

        public void setSno(String sno) {
            Sno = sno;
        }

        public String getSID() {
            return SID;
        }

        public void setSID(String SID) {
            this.SID = SID;
        }

        public String getOperatorCode() {
            return OperatorCode;
        }

        public void setOperatorCode(String operatorCode) {
            OperatorCode = operatorCode;
        }

        public String getReceiveStatus() {
            return ReceiveStatus;
        }

        public void setReceiveStatus(String receiveStatus) {
            ReceiveStatus = receiveStatus;
        }

        public String getOperatorID() {
            return OperatorID;
        }

        public void setOperatorID(String operatorID) {
            OperatorID = operatorID;
        }
    }

}
