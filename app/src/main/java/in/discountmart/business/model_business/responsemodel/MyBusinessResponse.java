package in.discountmart.business.model_business.responsemodel;

public class MyBusinessResponse extends BaseResponseEntity {

    MyBusiness self[];

    public MyBusiness[] getSelf() {
        return self;
    }

    public void setSelf(MyBusiness[] self) {
        this.self = self;
    }

    public static class MyBusiness{
       String idno;//": "005152",
        String membername;//": "Vision Root ",
        String sponsorid;//": "",
        String previousbv;//": "11495.84",
        String selfbv;//": "0.00",
        String groupbv;//": "4047.31",
        String totalbv;//": "15543.15",
        String level;//": "20.00"

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public String getMembername() {
            return membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public String getSponsorid() {
            return sponsorid;
        }

        public void setSponsorid(String sponsorid) {
            this.sponsorid = sponsorid;
        }

        public String getPreviousbv() {
            return previousbv;
        }

        public void setPreviousbv(String previousbv) {
            this.previousbv = previousbv;
        }

        public String getSelfbv() {
            return selfbv;
        }

        public void setSelfbv(String selfbv) {
            this.selfbv = selfbv;
        }

        public String getGroupbv() {
            return groupbv;
        }

        public void setGroupbv(String groupbv) {
            this.groupbv = groupbv;
        }

        public String getTotalbv() {
            return totalbv;
        }

        public void setTotalbv(String totalbv) {
            this.totalbv = totalbv;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }



}
