package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class RankListResponse extends BaseResponse {

    ArrayList<RankList>Rank;

    public ArrayList<RankList> getRank() {
        return Rank;
    }

    public void setRank(ArrayList<RankList> rank) {
        Rank = rank;
    }

    public static class RankList{
        String rankid;//":"1","
        String rank;//":"STAR

        public String getRankid() {
            return rankid;
        }

        public void setRankid(String rankid) {
            this.rankid = rankid;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }
}
