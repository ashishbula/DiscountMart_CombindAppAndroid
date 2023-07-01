package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class ComplaintDetailResponse extends BaseResponseEntity {

    String recordcount;//": "1",
    //String response;//": "OK"
    ArrayList<ComplaintDetails> complaintdetail;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<ComplaintDetails> getComplaintdetail() {
        return complaintdetail;
    }

    public void setComplaintdetail(ArrayList<ComplaintDetails> complaintdetail) {
        this.complaintdetail = complaintdetail;
    }

    public static class ComplaintDetails
    {
        String complaintid;//": "10019",
        String complaintdate;//": "04-Jul-2017",
        String complaint;//": "fvgv",
        String replydate;//": "",
        String reply;//": ""

        public String getComplaintid() {
            return complaintid;
        }

        public void setComplaintid(String complaintid) {
            this.complaintid = complaintid;
        }

        public String getComplaintdate() {
            return complaintdate;
        }

        public void setComplaintdate(String complaintdate) {
            this.complaintdate = complaintdate;
        }

        public String getComplaint() {
            return complaint;
        }

        public void setComplaint(String complaint) {
            this.complaint = complaint;
        }

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
