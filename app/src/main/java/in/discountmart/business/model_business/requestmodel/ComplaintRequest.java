package in.discountmart.business.model_business.requestmodel;



public class ComplaintRequest extends BaseRequest {

   // String reqtype;//": "savecomplaint",
   // String userid;//": "RBM378361",
    //String passwd;//": "ajayajay",
    String idno;//": "RBM378361",
    String name;//": "test",
    String subject;//": "test",
    String description;//": "Test Complaint List",
    String mobileno;//": "8233057616",
    String email;//": "84kirtibhadada@gmail.com",
    String complaintid;//": "1"

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(String complaintid) {
        this.complaintid = complaintid;
    }
}
