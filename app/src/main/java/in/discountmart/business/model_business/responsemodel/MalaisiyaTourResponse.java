package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/28/2018.
 */

public class MalaisiyaTourResponse extends BaseResponseEntity {

    Malaisiyatour malasiaaward[];
    String recordcount;

    public Malaisiyatour[] getMalasiaaward() {
        return malasiaaward;
    }

    public void setMalasiaaward(Malaisiyatour[] malasiaaward) {
        this.malasiaaward = malasiaaward;
    }

    public class Malaisiyatour{
        String recog;//": "Platinum & Above",
        String achleftbv;//": "20151.00",
        String achrightbv;//": "909.00",
        String leftbv;//": "0.00",
        String rightbv;//": "0.00",
        String leftdirect;//": "1",
        String rightdirect;//": "1",
        String selfbv;//": "30.96",
        String status;//": "Achieved"

        public String getRecog() {
            return recog;
        }

        public void setRecog(String recog) {
            this.recog = recog;
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

        public String getLeftdirect() {
            return leftdirect;
        }

        public void setLeftdirect(String leftdirect) {
            this.leftdirect = leftdirect;
        }

        public String getRightdirect() {
            return rightdirect;
        }

        public void setRightdirect(String rightdirect) {
            this.rightdirect = rightdirect;
        }

        public String getSelfbv() {
            return selfbv;
        }

        public void setSelfbv(String selfbv) {
            this.selfbv = selfbv;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
