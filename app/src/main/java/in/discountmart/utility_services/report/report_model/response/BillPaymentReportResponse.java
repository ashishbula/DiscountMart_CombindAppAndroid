package in.discountmart.utility_services.report.report_model.response;

import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BillPaymentReportResponse extends BaseResponse implements Serializable {
    String TotalCount;//": "744",
    ArrayList<BillPayment> BillList;

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public ArrayList<BillPayment> getBillList() {
        return BillList;
    }

    public void setBillList(ArrayList<BillPayment> billList) {
        BillList = billList;
    }

    public static class BillPayment implements Serializable {
        String ID;//": "2743",
        String UserName;//": "holistic",
        String Company;//": "7fold",
        String Amount;//": "2447.00",
        String ReqDate;//": "23-Sep-2019 05:17:39 PM",
        String IsApprove;//": "Approved",
        String Service;//": "DHBVN - HARYANA",
        String FormNo;//": "7949",
        String OperatorId;//": "BD01CAAOBW6D",
        String UserStatus;//": "SUCCESS",
        String Remark;//": "BD01CAAOBW6D\n",
        String PromoCode;//": "",
        String Discount;//": "0.00"
        String ServiceType;
        String Fileurl;
        String Fileimage;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

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

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public String getReqDate() {
            return ReqDate;
        }

        public void setReqDate(String reqDate) {
            ReqDate = reqDate;
        }

        public String getIsApprove() {
            return IsApprove;
        }

        public void setIsApprove(String isApprove) {
            IsApprove = isApprove;
        }

        public String getService() {
            return Service;
        }

        public void setService(String service) {
            Service = service;
        }

        public String getFormNo() {
            return FormNo;
        }

        public void setFormNo(String formNo) {
            FormNo = formNo;
        }

        public String getOperatorId() {
            return OperatorId;
        }

        public void setOperatorId(String operatorId) {
            OperatorId = operatorId;
        }

        public String getUserStatus() {
            return UserStatus;
        }

        public void setUserStatus(String userStatus) {
            UserStatus = userStatus;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getPromoCode() {
            return PromoCode;
        }

        public void setPromoCode(String promoCode) {
            PromoCode = promoCode;
        }

        public String getDiscount() {
            return Discount;
        }

        public void setDiscount(String discount) {
            Discount = discount;
        }

        public String getServiceType() {
            return ServiceType;
        }

        public void setServiceType(String serviceType) {
            ServiceType = serviceType;
        }

        public String getFileurl() {
            return Fileurl;
        }

        public void setFileurl(String fileurl) {
            Fileurl = fileurl;
        }

        public String getFileimage() {
            return Fileimage;
        }

        public void setFileimage(String fileimage) {
            Fileimage = fileimage;
        }
    }
}
