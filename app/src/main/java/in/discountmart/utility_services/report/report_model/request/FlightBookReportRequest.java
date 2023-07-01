package in.discountmart.utility_services.report.report_model.request;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class FlightBookReportRequest extends DataRequest {
    String Formno;//": "16880",
    String FromDate;//": "2021-01-01",
    String PageIndex;//": "1",
    String PageSize;//": "10",
    String RecordCount;//": "",
    String ResultType;//": "1",
    String ToDate;//": "2021-03-09"

    public String getFormno() {
        return Formno;
    }

    public void setFormno(String formno) {
        Formno = formno;
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

    public String getResultType() {
        return ResultType;
    }

    public void setResultType(String resultType) {
        ResultType = resultType;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }
}
