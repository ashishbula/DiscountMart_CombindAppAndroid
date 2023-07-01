package in.discountmart.utility_services.report.report_model.request;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class BillPaymentReportRequest extends DataRequest {
   String FormNo;//": 0,
    String FromDate;//": "2019-01-01",
    String PageIndex;//": "1",
    String PageSize;//": "100",
    String RecordCount;//": "0",
    String ServiceType;//": "3",
    String ToDate;//": "2020-01-01"

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
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

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }
}
