package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 5/3/2018.
 */

public class SendEpinDetailResponse extends BaseResponseEntity {
    String recordcount;
    SendEpinDetail reqdetail[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public SendEpinDetail[] getReqdetail() {
        return reqdetail;
    }

    public void setReqdetail(SendEpinDetail[] reqdetail) {
        this.reqdetail = reqdetail;
    }

    public class SendEpinDetail{
        String issuedidno;//": "DT009189",
        String issuedname;//": " DREAM TOUCH TRADE (I) PVT LTD                                        ",
        String issueddate;//": "25-Apr-2018 4:57PM",
        String pkgname;//": "WELCOME",
        String qty;//": "1"

        public String getIssuedidno() {
            return issuedidno;
        }

        public void setIssuedidno(String issuedidno) {
            this.issuedidno = issuedidno;
        }

        public String getIssuedname() {
            return issuedname;
        }

        public void setIssuedname(String issuedname) {
            this.issuedname = issuedname;
        }

        public String getIssueddate() {
            return issueddate;
        }

        public void setIssueddate(String issueddate) {
            this.issueddate = issueddate;
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
    }

}
