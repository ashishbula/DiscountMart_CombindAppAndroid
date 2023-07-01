package in.discountmart.utility_services.billpayment.billpay_model.billpay_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class GetParameterRequest extends DataRequest {
    String ServiceId;//": "806"

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }
}
