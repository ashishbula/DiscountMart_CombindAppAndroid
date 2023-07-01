package in.discountmart.utility_services.recharge.recharge_model.recharge_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class GetServiceProviderRequest extends DataRequest {
    String Service;

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }
}
