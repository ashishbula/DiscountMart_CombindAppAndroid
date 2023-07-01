package in.discountmart.business.model_business.requestmodel;

public class SetProfileRequest extends BaseRequest {
   //String reqtype;//": "setprofile",
   // String userid;//": "GD123456",
    //String passwd;//": "1234567",
    //String memberid;//": "GD123456",
   String name;//": " test",
    String fname;//": "",
    String relation;//": "S/O",
    String dob;//": "2/15/2014",
    String mobile;//": "8233057616",
    String email;//": "test.bispl@gmail.com",
    String nominee;//": "",
    String nomineerelation;//": "",
    String phno;//": "12346789",
    String ismarried;//": "Y",
    String marriagedate;//": "13-Nov-2018"


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getNomineerelation() {
        return nomineerelation;
    }

    public void setNomineerelation(String nomineerelation) {
        this.nomineerelation = nomineerelation;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getIsmarried() {
        return ismarried;
    }

    public void setIsmarried(String ismarried) {
        this.ismarried = ismarried;
    }

    public String getMarriagedate() {
        return marriagedate;
    }

    public void setMarriagedate(String marriagedate) {
        this.marriagedate = marriagedate;
    }
}
