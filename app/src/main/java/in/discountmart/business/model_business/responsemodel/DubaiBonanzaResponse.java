package in.discountmart.business.model_business.responsemodel;

public class DubaiBonanzaResponse extends BaseResponseEntity {

    DubaiBonanza dubaiaward[];

    public DubaiBonanza[] getDubaiaward() {
        return dubaiaward;
    }

    public void setDubaiaward(DubaiBonanza[] dubaiaward) {
        this.dubaiaward = dubaiaward;
    }

    public class DubaiBonanza
    {
        String Recognition;//": "Diamond",
        String mainlegrbv;//": "700000.00",
        String otherlegrbv;//": "700000.00",
        String bv;//": "7500.00",
        String achievedleftbv;//": "305063.21",
        String achievedrightbv;//": "38.40",
        String achievedmainlegbv;//": "706150.55",
        String achievedotherlegbv;//": "169.65",
        String requireleftbv;//": "0.00",
        String requirerightbv;//": "7461.60",
        String requiremainlegrbv;//": "0.00",
        String requireotherlegrbv;//": "699830.35",
        String status;//": "Not Achieve",
        String award;//": "Dubai Bonanza"

        public String getRecognition() {
            return Recognition;
        }

        public void setRecognition(String recognition) {
            Recognition = recognition;
        }

        public String getMainlegrbv() {
            return mainlegrbv;
        }

        public void setMainlegrbv(String mainlegrbv) {
            this.mainlegrbv = mainlegrbv;
        }

        public String getOtherlegrbv() {
            return otherlegrbv;
        }

        public void setOtherlegrbv(String otherlegrbv) {
            this.otherlegrbv = otherlegrbv;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }

        public String getAchievedleftbv() {
            return achievedleftbv;
        }

        public void setAchievedleftbv(String achievedleftbv) {
            this.achievedleftbv = achievedleftbv;
        }

        public String getAchievedrightbv() {
            return achievedrightbv;
        }

        public void setAchievedrightbv(String achievedrightbv) {
            this.achievedrightbv = achievedrightbv;
        }

        public String getAchievedmainlegbv() {
            return achievedmainlegbv;
        }

        public void setAchievedmainlegbv(String achievedmainlegbv) {
            this.achievedmainlegbv = achievedmainlegbv;
        }

        public String getAchievedotherlegbv() {
            return achievedotherlegbv;
        }

        public void setAchievedotherlegbv(String achievedotherlegbv) {
            this.achievedotherlegbv = achievedotherlegbv;
        }

        public String getRequireleftbv() {
            return requireleftbv;
        }

        public void setRequireleftbv(String requireleftbv) {
            this.requireleftbv = requireleftbv;
        }

        public String getRequirerightbv() {
            return requirerightbv;
        }

        public void setRequirerightbv(String requirerightbv) {
            this.requirerightbv = requirerightbv;
        }

        public String getRequiremainlegrbv() {
            return requiremainlegrbv;
        }

        public void setRequiremainlegrbv(String requiremainlegrbv) {
            this.requiremainlegrbv = requiremainlegrbv;
        }

        public String getRequireotherlegrbv() {
            return requireotherlegrbv;
        }

        public void setRequireotherlegrbv(String requireotherlegrbv) {
            this.requireotherlegrbv = requireotherlegrbv;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAward() {
            return award;
        }

        public void setAward(String award) {
            this.award = award;
        }
    }
}
