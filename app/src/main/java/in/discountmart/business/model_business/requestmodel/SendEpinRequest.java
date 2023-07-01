package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 5/3/2018.
 */

public class SendEpinRequest extends BaseRequest {

    //String passwd;//":"sxdert12121","
    //String reqtype;//":"sendepin","
    //String userid;//":"DT009189","
    String idno;//":"DT145424","
    String walletamt;//":"5000","
    String prodids;//":"130","
    String qtys;//":"1","
    String remarks;//":"TEST"}

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getWalletamt() {
        return walletamt;
    }

    public void setWalletamt(String walletamt) {
        this.walletamt = walletamt;
    }

    public String getProdids() {
        return prodids;
    }

    public void setProdids(String prodids) {
        this.prodids = prodids;
    }

    public String getQtys() {
        return qtys;
    }

    public void setQtys(String qtys) {
        this.qtys = qtys;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
