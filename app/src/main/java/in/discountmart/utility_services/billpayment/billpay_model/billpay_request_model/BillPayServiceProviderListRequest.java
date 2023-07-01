package in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class BillPayServiceProviderListRequest extends DataRequest {
    String ServiceTypeId;//": "2"

    public String getServiceTypeId() {
        return ServiceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        ServiceTypeId = serviceTypeId;
    }
}
