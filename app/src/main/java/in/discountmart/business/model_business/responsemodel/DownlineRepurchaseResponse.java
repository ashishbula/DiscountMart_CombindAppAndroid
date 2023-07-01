package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/23/2018.
 */

public class DownlineRepurchaseResponse extends BaseResponseEntity {
    String recordcount;//": "2852",
    String totalvrp;//": "3092.4",
    String totalamount;//": "0",
    //    String response;//": "OK"
    DownlineRepurchase downlinerepurchase[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getTotalvrp() {
        return totalvrp;
    }

    public void setTotalvrp(String totalvrp) {
        this.totalvrp = totalvrp;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public DownlineRepurchase[] getDownlinerepurchase() {
        return downlinerepurchase;
    }

    public void setDownlinerepurchase(DownlineRepurchase[] downlinerepurchase) {
        this.downlinerepurchase = downlinerepurchase;
    }

    public static class DownlineRepurchase{
        String idno;//": "817528",
        String memname;//": "Pankaj Kumar",
        String level;//": "313",
        String vrp;//": "29.99",
        String billAmount;//": "0.00",
        String billdate;//": "25-Jul-2018",
        String status;//": "Repurchase"

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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
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

        public String getVrp() {
            return vrp;
        }

        public void setVrp(String vrp) {
            this.vrp = vrp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
