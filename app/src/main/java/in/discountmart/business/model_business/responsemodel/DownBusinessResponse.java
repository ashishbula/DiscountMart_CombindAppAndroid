package in.discountmart.business.model_business.responsemodel;

public class DownBusinessResponse extends BaseResponseEntity {

    DownBusiness down[];
    String recordcount;

    public DownBusiness[] getDown() {
        return down;
    }

    public void setDown(DownBusiness[] down) {
        this.down = down;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class DownBusiness{
         String idno;//": "103303",
        String membername;//": "Kartar",
        String sponsorid;//": "683167",
        String previousbv;//": "1382.54",
        String selfbv;//: "0.00",
        String groupbv;//": "67.67",
        String totalbv;//": "1450.21",
        String level;//": "14.00"

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
