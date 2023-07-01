package in.discountmart.business.model_business.requestmodel;

public class FreeProductRequest extends BaseRequest {
    //String reqtype;//":"freeproduct","
    String totalamount;//":"2100","
    String totalpv;//":"83.64"}

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getTotalpv() {
        return totalpv;
    }

    public void setTotalpv(String totalpv) {
        this.totalpv = totalpv;
    }
}
