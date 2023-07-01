package in.discountmart.utility_services.report.report_model.request;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class RechargeDetailRequest extends DataRequest {
       String FromDate;//":"2019-01-01",
    String ToDate;//":"2019-10-30",
    String ServiceType;//":"D",
    String FormNo;//":"1002",
    String PageIndex;//":"1",
    String PageSize;//":"1000",
    String RecordCount;//":"1"

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(String pageIndex) {
        PageIndex = pageIndex;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }

    public String getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(String recordCount) {
        RecordCount = recordCount;
    }
}
