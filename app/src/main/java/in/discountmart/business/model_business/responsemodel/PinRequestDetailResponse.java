package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 4/11/2018.
 */

public class PinRequestDetailResponse extends BaseResponseEntity {

    PinRequestDetail pinreqdetail[];
    String recordcount;

    public PinRequestDetail[] getPinreqdetail() {
        return pinreqdetail;
    }

    public void setPinreqdetail(PinRequestDetail[] pinreqdetail) {
        this.pinreqdetail = pinreqdetail;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class PinRequestDetail{
         String sno;//": "1",
        String reqno;//": "3020",
        String reqdate;//": "11-Jan-2018",
        String orderamt;//": "4500.00",
        String orderqty;//": "1.00",
        String remarks;//": "hdgqghdvqhdgv",
        String address;//": "test by bispl",
        String pincode;//": "311001",
        String mobileno;//": "8233057616",
        String ostatus;//": "Rejected",

        OrderDetailResponse orderdetail[];

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getReqno() {
            return reqno;
        }

        public void setReqno(String reqno) {
            this.reqno = reqno;
        }

        public String getReqdate() {
            return reqdate;
        }

        public void setReqdate(String reqdate) {
            this.reqdate = reqdate;
        }

        public String getOrderamt() {
            return orderamt;
        }

        public void setOrderamt(String orderamt) {
            this.orderamt = orderamt;
        }

        public String getOrderqty() {
            return orderqty;
        }

        public void setOrderqty(String orderqty) {
            this.orderqty = orderqty;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getOstatus() {
            return ostatus;
        }

        public void setOstatus(String ostatus) {
            this.ostatus = ostatus;
        }

        public OrderDetailResponse[] getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(OrderDetailResponse[] orderdetail) {
            this.orderdetail = orderdetail;
        }
        /*
        public class OrderDetail extends PinRequestDetail{

            String kitid;//": "124",
            String kitname;//": "LADY FEEL FREE (RS - 4500)",
            String rate;//": "4500",
            String reqqty;//": "1",
            String sentqty;//": "0",
            String status;//": "Rejected"

            public String getKitid() {
                return kitid;
            }

            public void setKitid(String kitid) {
                this.kitid = kitid;
            }

            public String getKitname() {
                return kitname;
            }

            public void setKitname(String kitname) {
                this.kitname = kitname;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getReqqty() {
                return reqqty;
            }

            public void setReqqty(String reqqty) {
                this.reqqty = reqqty;
            }

            public String getSentqty() {
                return sentqty;
            }

            public void setSentqty(String sentqty) {
                this.sentqty = sentqty;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
*/
    }


}
