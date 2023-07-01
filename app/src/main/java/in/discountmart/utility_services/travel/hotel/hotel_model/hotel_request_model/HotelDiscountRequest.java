package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class HotelDiscountRequest extends DataRequest {
    String GroupID;//": "1",
    String SponsorFormNo;//": "1003"

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }
}
