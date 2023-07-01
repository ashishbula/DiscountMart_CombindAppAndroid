package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/26/2018.
 */

public class TopupDownlineResponse extends BaseResponseEntity {

    String recordcount;//": "27748",
    String leftbv;//": "450651",
    String rightbv;//": "490132",
    String totalbv;//": "940783",

    TopupDownline downlinerepurchase[];

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

    public TopupDownline[] getDownlinerepurchase() {
        return downlinerepurchase;
    }

    public void setDownlinerepurchase(TopupDownline[] downlinerepurchase) {
        this.downlinerepurchase = downlinerepurchase;
    }

    //String response;//": "OK"

    public class TopupDownline{
        String idno;//": "DT866957",
        String memname;//": "SIDDHARTH DATTARAO MORE",
        String group;//": "Group A",
        String repurchasebv;//": "10.00",
        String billAmount;//": "7500.00",
        String billdate;//": "26-Mar-2018",
        String pkgname;//": "GROW AGRO MINI (RS - 7500)"

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

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getRepurchasebv() {
            return repurchasebv;
        }

        public void setRepurchasebv(String repurchasebv) {
            this.repurchasebv = repurchasebv;
        }

        public String getBillAmount() {
            return billAmount;
        }

        public void setBillAmount(String billAmount) {
            this.billAmount = billAmount;
        }

        public String getBilldate() {
            return billdate;
        }

        public void setBilldate(String billdate) {
            this.billdate = billdate;
        }

        public String getPkgname() {
            return pkgname;
        }

        public void setPkgname(String pkgname) {
            this.pkgname = pkgname;
        }
    }


}
