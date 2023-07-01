package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 5/3/2018.
 */

public class CheckEpinRequestResponse extends BaseResponseEntity {

   String recordcount;
   CheckEpin reqdetail[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public CheckEpin[] getReqdetail() {
        return reqdetail;
    }

    public void setReqdetail(CheckEpin[] reqdetail) {
        this.reqdetail = reqdetail;
    }

    public class CheckEpin{
       String idno;//": "DT999381",
       String memname;//": "HARISHCHANDRA SHANKAR GIRAMKAR      ",
       String mobl;//": "9657575753",
       String reqno;//": "1002",
       String ReqDate;//": "02 Dec 2016",
       String totalamt;//": "545000.00",
       String status;//": "Rejected",
       CheckEpinKit kitdetail[];

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

        public String getMobl() {
            return mobl;
        }

        public void setMobl(String mobl) {
            this.mobl = mobl;
        }

        public String getReqno() {
            return reqno;
        }

        public void setReqno(String reqno) {
            this.reqno = reqno;
        }

        public String getReqDate() {
            return ReqDate;
        }

        public void setReqDate(String reqDate) {
            ReqDate = reqDate;
        }

        public String getTotalamt() {
            return totalamt;
        }

        public void setTotalamt(String totalamt) {
            this.totalamt = totalamt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public CheckEpinKit[] getKitdetail() {
            return kitdetail;
        }

        public void setKitdetail(CheckEpinKit[] kitdetail) {
            this.kitdetail = kitdetail;

        }
    }

}
