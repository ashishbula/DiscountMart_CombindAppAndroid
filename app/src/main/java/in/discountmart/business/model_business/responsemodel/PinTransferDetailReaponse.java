package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class PinTransferDetailReaponse extends BaseResponseEntity {

    String recordcount;
    ArrayList<PinTransferDetail> transferdetail;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<PinTransferDetail> getTransferdetail() {
        return transferdetail;
    }

    public void setTransferdetail(ArrayList<PinTransferDetail> transferdetail) {
        this.transferdetail = transferdetail;
    }

    public static class PinTransferDetail {
        String sno;//": "1",
        String toidno;//": "VR456466",
        String tomember;//": "TEST",
        String kitname;//": "LIMITED TIME FAST TRACK 4999/-",
        String pinno;//": "2000149",
        String scratchno;//": "E7C37",
        String pindate;//": "13-Aug-2018 5:27 PM",
        String PinStatus;//": "UnUsed"
        String remark;//": "UnUsed"


        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getToidno() {
            return toidno;
        }

        public void setToidno(String toidno) {
            this.toidno = toidno;
        }

        public String getTomember() {
            return tomember;
        }

        public void setTomember(String tomember) {
            this.tomember = tomember;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
