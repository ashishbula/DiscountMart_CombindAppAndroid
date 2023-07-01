package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/26/2018.
 */

public class DhanvershaResponse extends BaseResponseEntity {

    String topupdate;//": "30 Dec 2017",
    String eligstatus;//": "Eligible",
    String achdate;//": "30-Dec-2017",
    //String response;//": "OK"

    Dhanversha dhanvarsha[];

    public String getTopupdate() {
        return topupdate;
    }

    public void setTopupdate(String topupdate) {
        this.topupdate = topupdate;
    }

    public String getEligstatus() {
        return eligstatus;
    }

    public void setEligstatus(String eligstatus) {
        this.eligstatus = eligstatus;
    }

    public String getAchdate() {
        return achdate;
    }

    public void setAchdate(String achdate) {
        this.achdate = achdate;
    }

    public Dhanversha[] getDhanvarsha() {
        return dhanvarsha;
    }

    public void setDhanvarsha(Dhanversha[] dhanvarsha) {
        this.dhanvarsha = dhanvarsha;
    }

    public class Dhanversha{
        String sno;//": "1",
        String date;//": "25-Apr-2018",
        String status;//": "Eligible",
        String amount;//": "1000.00",
        String remarks;//": "Pending"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }



}
