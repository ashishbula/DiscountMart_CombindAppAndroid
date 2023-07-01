package in.discountmart.business.model_business.requestmodel;

public class ProdtTransRequestEntity {
    String prodtname;
    String qty;
    String totQty;
    String prodtid;

    public String getProdtname() {
        return prodtname;
    }

    public void setProdtname(String prodtname) {
        this.prodtname = prodtname;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotQty() {
        return totQty;
    }

    public void setTotQty(String totQty) {
        this.totQty = totQty;
    }

    public String getProdtid() {
        return prodtid;
    }

    public void setProdtid(String prodtid) {
        this.prodtid = prodtid;
    }
}
