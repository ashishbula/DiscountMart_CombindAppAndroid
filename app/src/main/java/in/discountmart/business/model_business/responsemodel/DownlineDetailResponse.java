package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

/**
 * Created by Dell on 09-01-2018.
 */

public class DownlineDetailResponse extends BaseResponseEntity {

    ArrayList<DownlineDetails> downline;

    String recordcount;

    public ArrayList<DownlineDetails> getDownline() {
        return downline;
    }

    public void setDownline(ArrayList<DownlineDetails> downline) {
        this.downline = downline;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class DownlineDetails  {
        String sno;//": "1",
        String idno;//": "439940",
        String member;//": "KS Das",
        String uplineid;//": "836058",
        String refid;//": "152320",
        String kitname;//": "VRCOMBO-4",
        String doj;//": "20 Jun 2018",
        String upgradedate;//": "20 Jun 2018",
        String kitamount;//": "14750.00",
        String bv;//": "1.00"



        public String getSno() {
            return sno;
        }
        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getUplineid() {
            return uplineid;
        }

        public void setUplineid(String uplineid) {
            this.uplineid = uplineid;
        }

        public String getRefid() {
            return refid;
        }

        public void setRefid(String refid) {
            this.refid = refid;
        }

        public String getKitname() {
            return kitname;
        }

        public void setKitname(String kitname) {
            this.kitname = kitname;
        }

        public String getDoj() {
            return doj;
        }

        public void setDoj(String doj) {
            this.doj = doj;
        }

        public String getUpgradedate() {
            return upgradedate;
        }

        public void setUpgradedate(String upgradedate) {
            this.upgradedate = upgradedate;
        }

        public String getKitamount() {
            return kitamount;
        }

        public void setKitamount(String kitamount) {
            this.kitamount = kitamount;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }
    }
}
