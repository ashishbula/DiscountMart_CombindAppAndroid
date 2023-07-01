package in.discountmart.business.model_business.responsemodel;

public class ValidateKitResponse extends BaseResponseEntity {

    //String response;//":"OK","
    String membername;//":"TEST1 ","
    String pkgname;//":"Registration Zero","
    String sponsorid;//":"DT009189","
    String isshowside;//":"N","
    String newpkgname;//":"","
    //String msg;//":"OK"}

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }

    public String getSponsorid() {
        return sponsorid;
    }

    public void setSponsorid(String sponsorid) {
        this.sponsorid = sponsorid;
    }

    public String getIsshowside() {
        return isshowside;
    }

    public void setIsshowside(String isshowside) {
        this.isshowside = isshowside;
    }

    public String getNewpkgname() {
        return newpkgname;
    }

    public void setNewpkgname(String newpkgname) {
        this.newpkgname = newpkgname;
    }


}
