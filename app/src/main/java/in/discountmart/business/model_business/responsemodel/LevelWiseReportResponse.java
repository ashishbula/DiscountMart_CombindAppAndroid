package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

/**
 * Created by Dell on 18-01-2018.
 */

public class LevelWiseReportResponse extends BaseResponseEntity {

    String recordcount;
    //String totaldirectleft;//": "6",
    //String totaldirectright;//": "210",
    //String activedirectleft;//": "6",
    //String activedirectright;//": "29",
    //String directbvleft;//": "8.00",
    //String directbvright;//": "39.00",
    //ArrayList<LevelWiseReport> directs;
    ArrayList<LevelWiseReport> LevelReportCountDetail;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<LevelWiseReport> getLevelReportCountDetail() {
        return LevelReportCountDetail;
    }

    public void setLevelReportCountDetail(ArrayList<LevelWiseReport> levelReportCountDetail) {
        LevelReportCountDetail = levelReportCountDetail;
    }

    public static class LevelWiseReport {
        String sno;//": "1",
        String idno;//": "hussainbano",
        String membername;//": "HUSSAIN BANO",
        //String pkg;//": "Shopping Package Rs. 2999/-",
        String sponsorid;//": "GW223344",
        String sponsorname;//": "GOLDWINGS GLOBAL BUSINESS PVT LTD",
        String active;//": "Active",
        String upgradedate;//": "02 Mar 2020 10:50 PM"
        String packagename;//": "02 Mar 2020 10:50 PM"
        String bv;

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

        public String getMembername() {
            return membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public String getSponsorid() {
            return sponsorid;
        }

        public void setSponsorid(String sponsorid) {
            this.sponsorid = sponsorid;
        }

        public String getSponsorname() {
            return sponsorname;
        }

        public void setSponsorname(String sponsorname) {
            this.sponsorname = sponsorname;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getUpgradedate() {
            return upgradedate;
        }

        public void setUpgradedate(String upgradedate) {
            this.upgradedate = upgradedate;
        }

        public String getPackagename() {
            return packagename;
        }

        public void setPackagename(String packagename) {
            this.packagename = packagename;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }
    }



}
