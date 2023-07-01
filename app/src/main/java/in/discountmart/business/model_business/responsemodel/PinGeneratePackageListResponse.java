package in.discountmart.business.model_business.responsemodel;

public class PinGeneratePackageListResponse extends BaseResponseEntity {
    String response;
    GeneratePackageList packages[];

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public GeneratePackageList[] getPackages() {
        return packages;
    }

    public void setPackages(GeneratePackageList[] packages) {
        this.packages = packages;
    }

    public static class GeneratePackageList{

        String pkgid;//":"0",",6
        String pkgname;//":"-- ALL --"


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


    }
}
