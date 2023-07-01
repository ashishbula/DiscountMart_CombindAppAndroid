package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class IDActivePackageList extends BaseResponse{
    ArrayList<IDPackageList>getkit;
    String recordcount;

    public ArrayList<IDPackageList> getGetkit() {
        return getkit;
    }

    public void setGetkit(ArrayList<IDPackageList> getkit) {
        this.getkit = getkit;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class IDPackageList{
        String kitid;//":"3","
        String kitname;//":"Shopping Package Rs. 2000/-","
        String kitamount;//":"2000.00"

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

        public String getKitamount() {
            return kitamount;
        }

        public void setKitamount(String kitamount) {
            this.kitamount = kitamount;
        }
    }

}
