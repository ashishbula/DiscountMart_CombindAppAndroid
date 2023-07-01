package in.discountmart.utility_services.fund.fund_model.request;


import in.discountmart.utility_services.model.request_model.DataRequest;

public class WalletTypeRequest extends DataRequest {
    String SponsorFormNo;

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
    }
}
