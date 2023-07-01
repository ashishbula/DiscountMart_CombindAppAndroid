package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/13/2018.
 */

public class PayModeListResponse extends BaseResponseEntity {

    PaymodeList paymode[];

    public PaymodeList[] getPaymode() {
        return paymode;
    }

    public void setPaymode(PaymodeList[] paymode) {
        this.paymode = paymode;
    }

    public static class PaymodeList{
       String paymodeid;//": "0",
        String paymodename;//": "--Choose Payment Mode--",

        String isbankdtl;//": "Y",
        String isbranchdtl;//": "Y",
        String istransno;//": "Y",
        String allbank;//": " ",
        String transnotxt;//": "Transaction No",
        String transdatetxt;//": "Transaction Date"

        public String getPaymodeid() {
            return paymodeid;
        }

        public void setPaymodeid(String paymodeid) {
            this.paymodeid = paymodeid;
        }

        public String getPaymodename() {
            return paymodename;
        }

        public void setPaymodename(String paymodename) {
            this.paymodename = paymodename;
        }

        public String getIsbankdtl() {
            return isbankdtl;
        }

        public void setIsbankdtl(String isbankdtl) {
            this.isbankdtl = isbankdtl;
        }

        public String getIsbranchdtl() {
            return isbranchdtl;
        }

        public void setIsbranchdtl(String isbranchdtl) {
            this.isbranchdtl = isbranchdtl;
        }

        public String getIstransno() {
            return istransno;
        }

        public void setIstransno(String istransno) {
            this.istransno = istransno;
        }

        public String getAllbank() {
            return allbank;
        }

        public void setAllbank(String allbank) {
            this.allbank = allbank;
        }

        public String getTransnotxt() {
            return transnotxt;
        }

        public void setTransnotxt(String transnotxt) {
            this.transnotxt = transnotxt;
        }

        public String getTransdatetxt() {
            return transdatetxt;
        }

        public void setTransdatetxt(String transdatetxt) {
            this.transdatetxt = transdatetxt;
        }
    }

}
