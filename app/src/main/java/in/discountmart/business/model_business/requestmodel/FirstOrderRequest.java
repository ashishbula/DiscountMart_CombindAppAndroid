package in.discountmart.business.model_business.requestmodel;

public class FirstOrderRequest extends BaseRequest {

    //String reqtype;// firstorder;,
    //String userid;//,
    //String passwd;
    String requestfor;//\
    String idno;//,
    String mobl;
    String remarks;
    String delvby;
    String purwamt;
    String prodids;
    String qtys;

    public String getRequestfor() {
        return requestfor;
    }

    public void setRequestfor(String requestfor) {
        this.requestfor = requestfor;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMobl() {
        return mobl;
    }

    public void setMobl(String mobl) {
        this.mobl = mobl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelvby() {
        return delvby;
    }

    public void setDelvby(String delvby) {
        this.delvby = delvby;
    }

    public String getPurwamt() {
        return purwamt;
    }

    public void setPurwamt(String purwamt) {
        this.purwamt = purwamt;
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
}
