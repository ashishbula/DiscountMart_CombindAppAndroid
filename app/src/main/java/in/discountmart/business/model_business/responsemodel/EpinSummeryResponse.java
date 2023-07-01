package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/22/2018.
 */

public class EpinSummeryResponse extends BaseResponseEntity {
    EpinSummeryList epinsumm[];

    public EpinSummeryList[] getEpinsumm() {
        return epinsumm;
    }

    public void setEpinsumm(EpinSummeryList[] epinsumm) {
        this.epinsumm = epinsumm;
    }

    public class EpinSummeryList{
        String productname;//": "DREAMS KIT",
        String qty;//": "2",
        String pkgid;//": "5"
        String pkgtype;

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getPkgid() {
            return pkgid;
        }

        public void setPkgid(String pkgid) {
            this.pkgid = pkgid;
        }

        public String getPkgtype() {
            return pkgtype;
        }

        public void setPkgtype(String pkgtype) {
            this.pkgtype = pkgtype;
        }
    }
}
