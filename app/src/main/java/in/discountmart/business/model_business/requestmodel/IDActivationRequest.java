package in.discountmart.business.model_business.requestmodel;

public class IDActivationRequest extends BaseRequest {

   // String passwd;//":"LP@1234", "
    //String reqtype;//":"idactivation","
    //String userid;//":"LP223344","
    String packageid;//":"6"}
    String toidno;//":"LP479129","
    String newkitid;//": "2",
   // String memberid;//": "SK482985"
    String transpassword;//": "SK482985"

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public String getToidno() {
        return toidno;
    }

    public void setToidno(String toidno) {
        this.toidno = toidno;
    }

    public String getNewkitid() {
        return newkitid;
    }

    public void setNewkitid(String newkitid) {
        this.newkitid = newkitid;
    }

    public String getTranspassword() {
        return transpassword;
    }

    public void setTranspassword(String transpassword) {
        this.transpassword = transpassword;
    }
}
