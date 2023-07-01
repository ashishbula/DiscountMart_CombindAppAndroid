package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class RankReportResponse extends BaseResponse{
    ArrayList<DownlineRank>downlinerank;
    String recordcount;//": "70",
            //"response": "OK"


    public ArrayList<DownlineRank> getDownlinerank() {
        return downlinerank;
    }

    public void setDownlinerank(ArrayList<DownlineRank> downlinerank) {
        this.downlinerank = downlinerank;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class DownlineRank{
         String sno;//": "1",
        String idno;//": "A1785",
        String memfirstname;//": "AHMED KHAN",
        String mobl;//": "9588265793",
        String postion;//": "Right",
        String rank;//": "STAR",
        String date;//": "20-Aug-2020"

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public String getMemfirstname() {
            return memfirstname;
        }

        public void setMemfirstname(String memfirstname) {
            this.memfirstname = memfirstname;
        }

        public String getMobl() {
            return mobl;
        }

        public void setMobl(String mobl) {
            this.mobl = mobl;
        }

        public String getPostion() {
            return postion;
        }

        public void setPostion(String postion) {
            this.postion = postion;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
