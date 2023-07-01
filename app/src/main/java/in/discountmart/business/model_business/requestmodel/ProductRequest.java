package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/20/2018.
 */

public class ProductRequest extends BaseRequest {

    //String reqtype;//": "productrequest",
    //String userid;//": "005152",
    //String passwd;//": "@navneet",
    String idno;//": "005152",
    String remarks;//": "test by bispl",
    String mobl;//": "0",
    String wamt;//": "345",
    String rwdamt;//": "0",
    String prodids;//": "22,19",
    String qtys;//": "2,1",
    String totalamt;//": "345",
    String totalbv;//": "4.55",
    String address;//": "",
    String delvby;//": "H",
    String requestfor;//": "WRD06"

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMobl() {
        return mobl;
    }

    public void setMobl(String mobl) {
        this.mobl = mobl;
    }

    public String getWamt() {
        return wamt;
    }

    public void setWamt(String wamt) {
        this.wamt = wamt;
    }

    public String getRwdamt() {
        return rwdamt;
    }

    public void setRwdamt(String rwdamt) {
        this.rwdamt = rwdamt;
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

    public String getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(String totalamt) {
        this.totalamt = totalamt;
    }

    public String getTotalbv() {
        return totalbv;
    }

    public void setTotalbv(String totalbv) {
        this.totalbv = totalbv;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDelvby() {
        return delvby;
    }

    public void setDelvby(String delvby) {
        this.delvby = delvby;
    }

    public String getRequestfor() {
        return requestfor;
    }

    public void setRequestfor(String requestfor) {
        this.requestfor = requestfor;
    }
}
