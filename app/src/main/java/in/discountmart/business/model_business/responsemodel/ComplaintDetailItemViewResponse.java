package in.discountmart.business.model_business.responsemodel;

public class ComplaintDetailItemViewResponse extends BaseResponseEntity {

   String complainttype;//": "Payout Related",
    String complaint;//": "test",
    //String response;//": "OK"
    ComplaintReplyDetail complaintreplydetail[];

    public String getComplainttype() {
        return complainttype;
    }

    public void setComplainttype(String complainttype) {
        this.complainttype = complainttype;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public ComplaintReplyDetail[] getComplaintreplydetail() {
        return complaintreplydetail;
    }

    public void setComplaintreplydetail(ComplaintReplyDetail[] complaintreplydetail) {
        this.complaintreplydetail = complaintreplydetail;
    }

    public static class ComplaintReplyDetail
    {
        String replydate;//": "10-Dec-2018",
        String reply;//": "test"

        public String getReplydate() {
            return replydate;
        }

        public void setReplydate(String replydate) {
            this.replydate = replydate;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }
    }


}
