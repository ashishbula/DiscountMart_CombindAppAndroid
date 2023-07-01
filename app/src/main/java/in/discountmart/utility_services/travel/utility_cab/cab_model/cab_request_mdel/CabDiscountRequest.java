package in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class CabDiscountRequest extends DataRequest {
    String groupId;//":1
    String SponsorFormNo;//": 1003

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }
}
