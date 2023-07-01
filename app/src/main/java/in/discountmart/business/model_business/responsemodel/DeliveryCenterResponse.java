package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/20/2018.
 */

public class DeliveryCenterResponse extends BaseResponseEntity {

    DeliveryCenter delvpoint[];

    public DeliveryCenter[] getDelvpoint() {
        return delvpoint;
    }

    public void setDelvpoint(DeliveryCenter[] delvpoint) {
        this.delvpoint = delvpoint;
    }

    public static class DeliveryCenter{

        String delvcode;//": "DT335637",
        String centername;//": "ABHAY BALWANT DANGALE-A/P PAIT TALUKA KHED DIST PUNE"

        public String getDelvcode() {
            return delvcode;
        }

        public void setDelvcode(String delvcode) {
            this.delvcode = delvcode;
        }

        public String getCentername() {
            return centername;
        }

        public void setCentername(String centername) {
            this.centername = centername;
        }
    }

}
