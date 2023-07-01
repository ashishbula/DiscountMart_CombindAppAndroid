package in.discountmart.utility_services.billpayment.billpay_model.billpay_response_model;


import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BillPayServiceProviderListResonse extends BaseResponse implements Serializable {
    String Id;//": 0,
    String Service;//": "Adani Gas",
    String ServiceId;//": 776,
            /*"CircleId": 0,
            "ServiceTypeId": 0,
            "ActiveStatus": null,
            "ReferanceNumber": null,
            "BillerName": null,
            "LocationCode": null,
            "MeterNumber": null,
            "BillUnit": null,
            "MeterReadingDate": null,
            "ProcessCycle": null,
            "CycleNumber": null,
            "Amount": null,
            "GroupNo": null,
            "District": null,
            "ConsumerType": null,
            "TypeOfPayment": null,
            "ERO": null,
            "servicetype": null,
            "DiviSionName": null,
            "PanNo": null,
            "RegMobileNo": null,
            "ClientId": null,
            "EmailId": null,
            "Dob": null,
            "BAnk": null,
            "RRNo": null,
            "BillAmount": null,
            "MonthBill": null,
            "CreditCard": null,
            "Sector": null,
            "Block": null,
            "FlatNo": null,
            "SubCode": null,
            "subdivisioncode": null,
            "DueDate": null,
            "ApiCode": null,
            "City": null,
            "CardType": null,
            "BillPath": null,
            "BillUpload": null*/

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }
}
