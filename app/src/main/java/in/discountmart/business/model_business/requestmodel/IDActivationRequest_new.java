package in.discountmart.business.model_business.requestmodel;

public class IDActivationRequest_new extends BaseRequest {

    Product[] products;
    String idno;
    String shoppingwallet;
    String totalamount;
    String totalpv;
    String deliveryfor;
    String address;
    String remarks;
    String deliverytype;

    String transactionpassword;

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getShoppingwallet() {
        return shoppingwallet;
    }

    public void setShoppingwallet(String shoppingwallet) {
        this.shoppingwallet = shoppingwallet;
    }

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

    public String getDeliveryfor() {
        return deliveryfor;
    }

    public void setDeliveryfor(String deliveryfor) {
        this.deliveryfor = deliveryfor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype;
    }

    public String getTransactionpassword() {
        return transactionpassword;
    }

    public void setTransactionpassword(String transactionpassword) {
        this.transactionpassword = transactionpassword;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public static class Product {
        String prodid;
        String qty;

        public String getProdid() {
            return prodid;
        }

        public void setProdid(String prodid) {
            this.prodid = prodid;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }

}