package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class LevelWiseCountResponse extends BaseResponse{

    ArrayList<LevelWiseCount> LevelReportCountWise;
    //String response;


    public ArrayList<LevelWiseCount> getLevelReportCountWise() {
        return LevelReportCountWise;
    }

    public void setLevelReportCountWise(ArrayList<LevelWiseCount> levelReportCountWise) {
        LevelReportCountWise = levelReportCountWise;
    }

    public static class LevelWiseCount{
        String sno;//":"1","
        String mlevel;//":"1","
        String activecount;//":"61","
        String deactivecount;//":"122","
        String total;//":"183

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getMlevel() {
            return mlevel;
        }

        public void setMlevel(String mlevel) {
            this.mlevel = mlevel;
        }

        public String getActivecount() {
            return activecount;
        }

        public void setActivecount(String activecount) {
            this.activecount = activecount;
        }

        public String getDeactivecount() {
            return deactivecount;
        }

        public void setDeactivecount(String deactivecount) {
            this.deactivecount = deactivecount;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
