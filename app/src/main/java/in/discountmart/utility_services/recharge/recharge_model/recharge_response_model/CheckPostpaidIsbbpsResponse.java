package in.discountmart.utility_services.recharge.recharge_model.recharge_response_model;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class CheckPostpaidIsbbpsResponse extends BaseResponse {
    String statuscode;//":"TXN","
    String status;//":"Transaction
    CheckData data;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CheckData getData() {
        return data;
    }

    public void setData(CheckData data) {
        this.data = data;
    }

    public static class CheckData{
        String dueamount;//":"100","
        String duedate;//":"NA","
        String customername;//":"NA","
        String billnumber;//":"NA","
        String billdate;//":"NA","
        String billperiod;//":"NA","
        String customerparamsdetails;//":"NA"," +
        String billdetails;//":"NA","
        String additionaldetails;//":"NA","
        String reference_id;//":767280

        public String getDueamount() {
            return dueamount;
        }

        public void setDueamount(String dueamount) {
            this.dueamount = dueamount;
        }

        public String getDuedate() {
            return duedate;
        }

        public void setDuedate(String duedate) {
            this.duedate = duedate;
        }

        public String getCustomername() {
            return customername;
        }

        public void setCustomername(String customername) {
            this.customername = customername;
        }

        public String getBillnumber() {
            return billnumber;
        }

        public void setBillnumber(String billnumber) {
            this.billnumber = billnumber;
        }

        public String getBilldate() {
            return billdate;
        }

        public void setBilldate(String billdate) {
            this.billdate = billdate;
        }

        public String getBillperiod() {
            return billperiod;
        }

        public void setBillperiod(String billperiod) {
            this.billperiod = billperiod;
        }

        public String getCustomerparamsdetails() {
            return customerparamsdetails;
        }

        public void setCustomerparamsdetails(String customerparamsdetails) {
            this.customerparamsdetails = customerparamsdetails;
        }

        public String getBilldetails() {
            return billdetails;
        }

        public void setBilldetails(String billdetails) {
            this.billdetails = billdetails;
        }

        public String getAdditionaldetails() {
            return additionaldetails;
        }

        public void setAdditionaldetails(String additionaldetails) {
            this.additionaldetails = additionaldetails;
        }

        public String getReference_id() {
            return reference_id;
        }

        public void setReference_id(String reference_id) {
            this.reference_id = reference_id;
        }
    }
}
