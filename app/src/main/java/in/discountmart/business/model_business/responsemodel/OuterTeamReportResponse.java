package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class OuterTeamReportResponse extends BaseResponseEntity {

    String recordcount;
    ArrayList<OuterTeamReport> outerteamreport;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public ArrayList<OuterTeamReport> getOuterteamreport() {
        return outerteamreport;
    }

    public void setOuterteamreport(ArrayList<OuterTeamReport> outerteamreport) {
        this.outerteamreport = outerteamreport;
    }

    public static class OuterTeamReport{
         String sno;//": "100",
        String idno;//": "PC11000742",
        String name;//": "UMMED SINGH KRISHNIA ",
        String city;//": "Jaipur",
        String team1fv;//": "1368042.00",
        String team2fv;//": "0.00",
        String team1rp;//": "396.50",
        String team2rp;//": "0.00"

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTeam1fv() {
            return team1fv;
        }

        public void setTeam1fv(String team1fv) {
            this.team1fv = team1fv;
        }

        public String getTeam2fv() {
            return team2fv;
        }

        public void setTeam2fv(String team2fv) {
            this.team2fv = team2fv;
        }

        public String getTeam1rp() {
            return team1rp;
        }

        public void setTeam1rp(String team1rp) {
            this.team1rp = team1rp;
        }

        public String getTeam2rp() {
            return team2rp;
        }

        public void setTeam2rp(String team2rp) {
            this.team2rp = team2rp;
        }
    }
}
