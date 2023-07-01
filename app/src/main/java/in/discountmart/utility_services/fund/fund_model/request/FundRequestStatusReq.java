package in.discountmart.utility_services.fund.fund_model.request;
import in.discountmart.utility_services.model.request_model.DataRequest;

public class FundRequestStatusReq extends DataRequest {
    String FormNo;//:"1002"

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }
}
