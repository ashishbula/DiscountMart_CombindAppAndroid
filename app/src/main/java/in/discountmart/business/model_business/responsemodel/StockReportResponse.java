package in.discountmart.business.model_business.responsemodel;

public class StockReportResponse extends BaseResponseEntity {

    String recordcount;//": "33",
    //String response;//": "OK"
    StockReport stockdetail[];

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public StockReport[] getStockdetail() {
        return stockdetail;
    }

    public void setStockdetail(StockReport[] stockdetail) {
        this.stockdetail = stockdetail;
    }

    public static class StockReport
    {
        String sno;//": "1",
        String prodid;//": "22",
        String productname;//": "ALOE FACE WASH",
        String qty;//": "6.00"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getProdid() {
            return prodid;
        }

        public void setProdid(String prodid) {
            this.prodid = prodid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }
}
