package in.discountmart.business.model_business.responsemodel;

public class ComplaintTypeListResponse extends BaseResponseEntity {

    ComplaintList complainttype[];

    public ComplaintList[] getComplainttype() {
        return complainttype;
    }

    public void setComplainttype(ComplaintList[] complainttype) {
        this.complainttype = complainttype;
    }

    public static class ComplaintList
    {
        String complaintid;//": "1",
        String complaintname;//": "Payout Related"

        public String getComplaintid() {
            return complaintid;
        }

        public void setComplaintid(String complaintid) {
            this.complaintid = complaintid;
        }

        public String getComplaintname() {
            return complaintname;
        }

        public void setComplaintname(String complaintname) {
            this.complaintname = complaintname;
        }
    }
}
