package in.discountmart.model_business.responsemodel;

public class NewRegisterResponse extends BaseResponseEntity {

    String idno;
    String url;
    String formno;


    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormno() {
        return formno;
    }

    public void setFormno(String formno) {
        this.formno = formno;
    }
}
