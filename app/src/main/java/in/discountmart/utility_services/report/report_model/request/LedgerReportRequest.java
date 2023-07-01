package in.discountmart.utility_services.report.report_model.request;
import in.discountmart.utility_services.model.request_model.DataRequest;

public class LedgerReportRequest extends DataRequest {
    String FromDate;//": "2020-01-01",
    String ToDate;//": "2021-10-30",
    String VType;//": "M",
    String FormNo;//": "1002",
    String SponsorFormNo;//": "0",
    String PageIndex;//": "2",
    String PageSize;//": "10",
    String RecordCount;//": "1"


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

    public String getVType() {
        return VType;
    }

    public void setVType(String VType) {
        this.VType = VType;
    }

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getSponsorFormNo() {
        return SponsorFormNo;
    }

    public void setSponsorFormNo(String sponsorFormNo) {
        SponsorFormNo = sponsorFormNo;
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
