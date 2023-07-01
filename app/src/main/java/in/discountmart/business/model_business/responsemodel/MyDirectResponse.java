package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class MyDirectResponse extends BaseResponse {
   // @SerializedName("recordcount")
    String recordcount;
    String totaldirectleft;//": "6",
    String totaldirectright;//": "210",
    String activedirectleft;//": "6",
    String activedirectright;//": "29",
    String directbvleft;//": "8.00",
    String directbvright;//": "39.00",
    ArrayList<MyDirect> directs;
   //@SerializedName("levelcountdetail")
   //@Expose
   // ArrayList<Map<String,String>> directs;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }



    public String getTotaldirectleft() {
        return totaldirectleft;
    }

    public void setTotaldirectleft(String totaldirectleft) {
        this.totaldirectleft = totaldirectleft;
    }

    public String getTotaldirectright() {
        return totaldirectright;
    }

    public void setTotaldirectright(String totaldirectright) {
        this.totaldirectright = totaldirectright;
    }

    public String getActivedirectleft() {
        return activedirectleft;
    }

    public void setActivedirectleft(String activedirectleft) {
        this.activedirectleft = activedirectleft;
    }

    public String getActivedirectright() {
        return activedirectright;
    }

    public void setActivedirectright(String activedirectright) {
        this.activedirectright = activedirectright;
    }

    public String getDirectbvleft() {
        return directbvleft;
    }

    public void setDirectbvleft(String directbvleft) {
        this.directbvleft = directbvleft;
    }

    public String getDirectbvright() {
        return directbvright;
    }

    public void setDirectbvright(String directbvright) {
        this.directbvright = directbvright;
    }

    public void setDirects(ArrayList<MyDirect> directs) {
        this.directs = directs;
    }

    public static class MyDirect{
        String level;//": "1",
        String idno;//": "hussainbano",
        String memname;//": "HUSSAIN BANO",
        String pkg;//": "Shopping Package Rs. 2999/-",
        String sponsorid;//": "GW223344",
        String sponsorname;//": "GOLDWINGS GLOBAL BUSINESS PVT LTD",
        String activestatus;//": "Active",
        String activationdate;//": "02 Mar 2020 10:50 PM"
        String bv;
        String mobileno;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public String getMemname() {
            return memname;
        }

        public void setMemname(String memname) {
            this.memname = memname;
        }

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
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

        public String getActivestatus() {
            return activestatus;
        }

        public void setActivestatus(String activestatus) {
            this.activestatus = activestatus;
        }

        public String getActivationdate() {
            return activationdate;
        }

        public void setActivationdate(String activationdate) {
            this.activationdate = activationdate;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }
    }
}
