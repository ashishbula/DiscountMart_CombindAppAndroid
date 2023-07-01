package in.discountmart.business.model_business.responsemodel;

/**
 * Created by Dell on 19-01-2018.
 */

public class PackageListResponse {

    String response;
    PackageList packages[];

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public PackageList[] getPackages() {
        return packages;
    }

    public void setPackages(PackageList[] packages) {
        this.packages = packages;
    }

    public static class PackageList{

        String pkgid;//":"0",",6
        String pkgname;//":"-- ALL --"
        String qty;//":"1"
        String kitamount;

        public String getPkgid() {
            return pkgid;
        }

        public void setPkgid(String pkgid) {
            this.pkgid = pkgid;
        }

        public String getPkgname() {
            return pkgname;
        }

        public void setPkgname(String pkgname) {
            this.pkgname = pkgname;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getKitamount() {
            return kitamount;
        }

        public void setKitamount(String kitamount) {
            this.kitamount = kitamount;
        }
    }
}
