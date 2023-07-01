package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

/**
 * Created by The Rock on 4/10/2018.
 */

public class PinReceiveDetailResponse extends BaseResponseEntity {

    ArrayList<PinReceiveDetail> receivedetail;
    String recordcount;//": "4",

    public ArrayList<PinReceiveDetail> getReceivedetail() {
        return receivedetail;
    }

    public void setReceivedetail(ArrayList<PinReceiveDetail> receivedetail) {
        this.receivedetail = receivedetail;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class PinReceiveDetail{
        String sno;//": "1",
        String fromidno;//": "DT000417",
        String frommember;//": "DTIPL ",
        String kitname;//": "Registration Zero",
        String pinno;//": "118367",
        String scratchno;//": "00BB7",
        String pindate;//": "11-Aug-2017 5:56PM",
        String PinStatus;//": "UnUsed"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getFromidno() {
            return fromidno;
        }

        public void setFromidno(String fromidno) {
            this.fromidno = fromidno;
        }

        public String getFrommember() {
            return frommember;
        }

        public void setFrommember(String frommember) {
            this.frommember = frommember;
        }

        public String getKitname() {
            return kitname;
        }

        public void setKitname(String kitname) {
            this.kitname = kitname;
        }

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

        public String getPindate() {
            return pindate;
        }

        public void setPindate(String pindate) {
            this.pindate = pindate;
        }

        public String getPinStatus() {
            return PinStatus;
        }

        public void setPinStatus(String pinStatus) {
            PinStatus = pinStatus;
        }
    }

}
