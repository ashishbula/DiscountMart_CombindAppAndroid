package in.discountmart.business.model_business.requestmodel;

/**
 * Created by The Rock on 4/18/2018.
 */

public class PinRequestRequest extends BaseRequest {

    //String userid;//": "DT009189",
    //String reqtype;//": "pinrequest",
    //String passwd;//": "zcsx19",
    String tpasswd;//": "tyurew1234",
    String pkgids;//": "8,5",
    String qtys;//": "4,2",
    String amts;//": "6000,5000",
    String ramt;//": "50",
    String wamt;//": "00",
    String bamt;//": "2950",
    String oamt;//": "8000",
    String tamt;//": "11000",
    String remarks;//": "",
    String delvadrs;//": "Address..",
    String mobl;//": "812345698",
    String pincode;//": "311001"
    String paymodeid;
    String paymode;
    String transno;
    String transdate;
    String transbank;
    String transbankid;
    String transbranch;

    public String getTpasswd() {
        return tpasswd;
    }

    public void setTpasswd(String tpasswd) {
        this.tpasswd = tpasswd;
    }

    public String getPkgids() {
        return pkgids;
    }

    public void setPkgids(String pkgids) {
        this.pkgids = pkgids;
    }

    public String getQtys() {
        return qtys;
    }

    public void setQtys(String qtys) {
        this.qtys = qtys;
    }

    public String getAmts() {
        return amts;
    }

    public void setAmts(String amts) {
        this.amts = amts;
    }

    public String getRamt() {
        return ramt;
    }

    public void setRamt(String ramt) {
        this.ramt = ramt;
    }

    public String getWamt() {
        return wamt;
    }

    public void setWamt(String wamt) {
        this.wamt = wamt;
    }

    public String getBamt() {
        return bamt;
    }

    public void setBamt(String bamt) {
        this.bamt = bamt;
    }

    public String getOamt() {
        return oamt;
    }

    public void setOamt(String oamt) {
        this.oamt = oamt;
    }

    public String getTamt() {
        return tamt;
    }

    public void setTamt(String tamt) {
        this.tamt = tamt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelvadrs() {
        return delvadrs;
    }

    public void setDelvadrs(String delvadrs) {
        this.delvadrs = delvadrs;
    }

    public String getMobl() {
        return mobl;
    }

    public void setMobl(String mobl) {
        this.mobl = mobl;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPaymodeid() {
        return paymodeid;
    }

    public void setPaymodeid(String paymodeid) {
        this.paymodeid = paymodeid;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getTransno() {
        return transno;
    }

    public void setTransno(String transno) {
        this.transno = transno;
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    public String getTransbank() {
        return transbank;
    }

    public void setTransbank(String transbank) {
        this.transbank = transbank;
    }

    public String getTransbankid() {
        return transbankid;
    }

    public void setTransbankid(String transbankid) {
        this.transbankid = transbankid;
    }

    public String getTransbranch() {
        return transbranch;
    }

    public void setTransbranch(String transbranch) {
        this.transbranch = transbranch;
    }
}
