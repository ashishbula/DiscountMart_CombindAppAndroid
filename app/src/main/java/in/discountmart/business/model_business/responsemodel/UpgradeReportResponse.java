package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/7/2018.
 */

public class UpgradeReportResponse extends BaseResponseEntity {

    UpgradeReport upgrddetail[];

    public UpgradeReport[] getUpgrddetail() {
        return upgrddetail;
    }

    public void setUpgrddetail(UpgradeReport[] upgrddetail) {
        this.upgrddetail = upgrddetail;
    }

    public class UpgradeReport{

        String sno;//": "1",
        String pkgname;//": "AQUA NEEL PLATINUM",
        String bv;//": "20.00",
        String upgradedate;//": "24-Nov-2016 12:00AM"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getPkgname() {
            return pkgname;
        }

        public void setPkgname(String pkgname) {
            this.pkgname = pkgname;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }

        public String getUpgradedate() {
            return upgradedate;
        }

        public void setUpgradedate(String upgradedate) {
            this.upgradedate = upgradedate;
        }
    }

}
