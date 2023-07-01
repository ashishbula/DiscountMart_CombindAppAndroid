package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/28/2018.
 */

public class CarJeetoResponse extends BaseResponseEntity {

    String recordcount;
    CarjeetoReward carjeeto[];

    public CarjeetoReward[] getCarjeeto() {
        return carjeeto;
    }

    public void setCarjeeto(CarjeetoReward[] carjeeto) {
        this.carjeeto = carjeeto;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public class CarjeetoReward {

     String achdate;//":"15-Sep-2017",
        String recog;//":"Platinum",
        String prevmatchedbv;//":"21049.00",
        String reqbv;//":"800.00",
        String achleftbv;//":"28543.00",
        String achrightbv;//":"3424.00",
        String achmatchbv;//":"3424.00",
        String reqleftbv;//":"0.00",
        String reqrightbv;//":"0.00",
        String sessid;//":"20170915",
        String reqrbv;//":"0.00",
        String status;//":"Achieved",
        String award;//":"Hyundai i10(Petrol)"

        public String getAchdate() {
            return achdate;
        }

        public void setAchdate(String achdate) {
            this.achdate = achdate;
        }

        public String getRecog() {
            return recog;
        }

        public void setRecog(String recog) {
            this.recog = recog;
        }

        public String getPrevmatchedbv() {
            return prevmatchedbv;
        }

        public void setPrevmatchedbv(String prevmatchedbv) {
            this.prevmatchedbv = prevmatchedbv;
        }

        public String getReqbv() {
            return reqbv;
        }

        public void setReqbv(String reqbv) {
            this.reqbv = reqbv;
        }

        public String getAchleftbv() {
            return achleftbv;
        }

        public void setAchleftbv(String achleftbv) {
            this.achleftbv = achleftbv;
        }

        public String getAchrightbv() {
            return achrightbv;
        }

        public void setAchrightbv(String achrightbv) {
            this.achrightbv = achrightbv;
        }

        public String getAchmatchbv() {
            return achmatchbv;
        }

        public void setAchmatchbv(String achmatchbv) {
            this.achmatchbv = achmatchbv;
        }

        public String getReqleftbv() {
            return reqleftbv;
        }

        public void setReqleftbv(String reqleftbv) {
            this.reqleftbv = reqleftbv;
        }

        public String getReqrightbv() {
            return reqrightbv;
        }

        public void setReqrightbv(String reqrightbv) {
            this.reqrightbv = reqrightbv;
        }

        public String getSessid() {
            return sessid;
        }

        public void setSessid(String sessid) {
            this.sessid = sessid;
        }

        public String getReqrbv() {
            return reqrbv;
        }

        public void setReqrbv(String reqrbv) {
            this.reqrbv = reqrbv;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAward() {
            return award;
        }

        public void setAward(String award) {
            this.award = award;
        }
    }

}
