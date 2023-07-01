package in.discountmart.utility_services.model.response_model;

public class ServicesListResponse extends BaseResponse {

    String Id;//":18.0,"
    String ServiceType;//":"X","
    String ServiceDesc;//":"CAB","
    String ActiveStatus;//":null,"
    String IsCredit;//":null,"
    String SeqNo;//":null

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getServiceDesc() {
        return ServiceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        ServiceDesc = serviceDesc;
    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getIsCredit() {
        return IsCredit;
    }

    public void setIsCredit(String isCredit) {
        IsCredit = isCredit;
    }

    public String getSeqNo() {
        return SeqNo;
    }

    public void setSeqNo(String seqNo) {
        SeqNo = seqNo;
    }
}
