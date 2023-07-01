package in.discountmart.business.model_business.responsemodel;

/**
 * Created by Dell on 10-01-2018.
 */

public class LevelListResponse {

    private LevelList levels[];
    String response;
    String recordcount;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public LevelList[] getLevels() {
        return levels;
    }

    public void setLevels(LevelList[] levels) {
        this.levels = levels;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static class  LevelList{
        String levelid;//": "-5",
        String levelname;//": "Deactive"

        public String getLevelid() {
            return levelid;
        }

        public void setLevelid(String levelid) {
            this.levelid = levelid;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }
    }

}
