package in.discountmart.utility_services.fund.fund_model.request;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class FundRequest extends DataRequest {
    String FormNo;
    String Amount;
    String ModeID;
    String Remark;
    String FileName;

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getModeId() {
        return ModeID;
    }

    public void setModeId(String modeId) {
        ModeID = modeId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}
