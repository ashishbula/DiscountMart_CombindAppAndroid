package in.discountmart.utility_services.fund.fund_model.response;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class FundRequestStatusResponse extends BaseResponse {
     String FormNo;//": null,
    String Amount;//": 1,
    String RequestDate;//": "2019-08-09T00:00:00",
    String CreatedBy;//": null,
    String CreatedDate;//": null,
    String UpdatedBy;//": null,
    String UpdatedDate;//": null,
    String RectimeStamp;//": null,
    String HostIP;//": "",
    String IsFundApprove;//": null,
    String ModeID;//": null,
    String Remark;//": " cash",
    String AdminRemark;//": "",
    String RequestedBy;//": "Demo",
    String Status;//": "Pending"

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }

    public String getIsFundApprove() {
        return IsFundApprove;
    }

    public void setIsFundApprove(String isFundApprove) {
        IsFundApprove = isFundApprove;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getAdminRemark() {
        return AdminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        AdminRemark = adminRemark;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
