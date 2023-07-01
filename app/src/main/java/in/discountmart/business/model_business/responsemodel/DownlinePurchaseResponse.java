package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class DownlinePurchaseResponse extends BaseResponse {
    String recordcount;//": "15",
    String leftbv;//": "15",
    String rightbv;//": "0",
    String totalbv;//": "15",
    //String response;//": "OK"
    ArrayList<DownlinePurchaseItem> downlinepurchase;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getLeftbv() {
        return leftbv;
    }

    public void setLeftbv(String leftbv) {
        this.leftbv = leftbv;
    }

    public String getRightbv() {
        return rightbv;
    }

    public void setRightbv(String rightbv) {
        this.rightbv = rightbv;
    }

    public String getTotalbv() {
        return totalbv;
    }

    public void setTotalbv(String totalbv) {
        this.totalbv = totalbv;
    }

    public ArrayList<DownlinePurchaseItem> getDownlinepurchase() {
        return downlinepurchase;
    }

    public void setDownlinepurchase(ArrayList<DownlinePurchaseItem> downlinepurchase) {
        this.downlinepurchase = downlinepurchase;
    }

    public static class DownlinePurchaseItem   {
       String sno;//": "3",
        String billdate;//": "31-May-2020",
        String idno;//": "Banna01",
        String membername;//": "sunil kumar jaga ",
        String groupname;//": "Group A",
        String bilamount;//": "2999.00",
        String pkgname;//": "GOLD PACKAGE Rs.2999/- (S+U)",
        String repurchasebv;//": "3.00"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getBilldate() {
            return billdate;
        }

        public void setBilldate(String billdate) {
            this.billdate = billdate;
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

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getBilamount() {
            return bilamount;
        }

        public void setBilamount(String bilamount) {
            this.bilamount = bilamount;
        }

        public String getPkgname() {
            return pkgname;
        }

        public void setPkgname(String pkgname) {
            this.pkgname = pkgname;
        }

        public String getRepurchasebv() {
            return repurchasebv;
        }

        public void setRepurchasebv(String repurchasebv) {
            this.repurchasebv = repurchasebv;
        }
    }
}
