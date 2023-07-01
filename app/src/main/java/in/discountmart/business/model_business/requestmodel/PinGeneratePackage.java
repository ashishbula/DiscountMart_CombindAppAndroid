package in.discountmart.business.model_business.requestmodel;

import in.discountmart.business.model_business.responsemodel.BaseResponse;

public class PinGeneratePackage extends BaseResponse {
    //String response;
    PackageList kit[];

    public PackageList[] getKit() {
        return kit;
    }

    public void setKit(PackageList[] kit) {
        this.kit = kit;
    }

    public static class PackageList{

      String kitamount;//":"999","
        String kitid;//":"7","
        String kitname;//":"STARTUP PACKAGE Rs.999/-"


        public String getKitamount() {
            return kitamount;
        }

        public void setKitamount(String kitamount) {
            this.kitamount = kitamount;
        }

        public String getKitid() {
            return kitid;
        }

        public void setKitid(String kitid) {
            this.kitid = kitid;
        }

        public String getKitname() {
            return kitname;
        }

        public void setKitname(String kitname) {
            this.kitname = kitname;
        }
    }
}
