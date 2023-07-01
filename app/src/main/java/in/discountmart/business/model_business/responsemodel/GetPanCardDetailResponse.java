package in.discountmart.business.model_business.responsemodel;

public class GetPanCardDetailResponse extends BaseResponseEntity {

    String idno;//": "MP999999",
    String panno;//": "BYYPB1478T",
    String panimage;//": "http://localhost/upload/profilepic/20190628125433319.JPG",
    String panproofdate;//": "28-Jun-2019",
    String ispanverified;//": "N",
    String panverifydate;//": "",
    String panverf;//": "Verification Due",
    String rejectremark;//": "",
   // String rejectreason;//": "",
    //String response;//": "OK"



    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getPanimage() {
        return panimage;
    }

    public void setPanimage(String panimage) {
        this.panimage = panimage;
    }

    public String getPanproofdate() {
        return panproofdate;
    }

    public void setPanproofdate(String panproofdate) {
        this.panproofdate = panproofdate;
    }

    public String getIspanverified() {
        return ispanverified;
    }

    public void setIspanverified(String ispanverified) {
        this.ispanverified = ispanverified;
    }

    public String getPanverifydate() {
        return panverifydate;
    }

    public void setPanverifydate(String panverifydate) {
        this.panverifydate = panverifydate;
    }

    public String getPanverf() {
        return panverf;
    }

    public void setPanverf(String panverf) {
        this.panverf = panverf;
    }

    public String getRejectremark() {
        return rejectremark;
    }

    public void setRejectremark(String rejectremark) {
        this.rejectremark = rejectremark;
    }


}
