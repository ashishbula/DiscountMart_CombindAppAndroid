package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

/**
 * Created by The Rock on 3/22/2018.
 */

public class EpinDetailResponse extends BaseResponseEntity {
 //"response": "OK"
   ArrayList<EpinDetail> epindetail;
    String recordcount;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<EpinDetail> getEpindetail() {
        return epindetail;
    }

    public void setEpindetail(ArrayList<EpinDetail> epindetail) {
        this.epindetail = epindetail;
    }

    public static class EpinDetail {
        String pinno;//": "500003",
        String scratchno;//": "E9C88",
        String productname;//": "DREAMS KIT",
        String issuedate;//": "02-Dec-2016",
        String epinstatus;//": "Used",
        String usedby;//": "DT234949",
        String mname;//": "DFSSD ",
        String useddate;//": "20-Jan-2017"
        String istopup;//"y"

        public String getPinno() {
            return pinno;
        }

        public void setPinno(String pinno) {
            this.pinno = pinno;
        }

        public String getScratchno() {
            return scratchno;
        }

        public void setScratchno(String scratchno) {
            this.scratchno = scratchno;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getIssuedate() {
            return issuedate;
        }

        public void setIssuedate(String issuedate) {
            this.issuedate = issuedate;
        }

        public String getEpinstatus() {
            return epinstatus;
        }

        public void setEpinstatus(String epinstatus) {
            this.epinstatus = epinstatus;
        }

        public String getUsedby() {
            return usedby;
        }

        public void setUsedby(String usedby) {
            this.usedby = usedby;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getUseddate() {
            return useddate;
        }

        public void setUseddate(String useddate) {
            this.useddate = useddate;
        }

        public String getIstopup() {
            return istopup;
        }

        public void setIstopup(String istopup) {
            this.istopup = istopup;
        }
    }



}
