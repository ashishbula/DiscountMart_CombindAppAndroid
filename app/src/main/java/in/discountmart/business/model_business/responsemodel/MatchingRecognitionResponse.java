package in.discountmart.business.model_business.responsemodel;

import java.io.Serializable;

/**
 * Created by The Rock on 3/28/2018.
 */

public class MatchingRecognitionResponse extends BaseResponseEntity  {

    String leftbv;//": "470371.00",
    String rightbv;//": "91326.00",

    AchivedRecognition achrewards[];
    PendingRecognition pendingrewards[];

    public String getLeftbv() {
        return leftbv;
    }

    public void setLeftbv(String leftbv) {
        this.leftbv = leftbv;
    }

    public String getRightbv() {
        return rightbv;
    }

    public void setRightbv(String rightbv) {
        this.rightbv = rightbv;
    }

    public AchivedRecognition[] getAchrewards() {
        return achrewards;
    }

    public void setAchrewards(AchivedRecognition[] achrewards) {
        this.achrewards = achrewards;
    }

    public PendingRecognition[] getPendingrewards() {
        return pendingrewards;
    }

    public void setPendingrewards(PendingRecognition[] pendingrewards) {
        this.pendingrewards = pendingrewards;
    }

    public class AchivedRecognition implements Serializable {
        String level;//": "1",
        String rcoglevel;//": "Executive",
        String achdate;//": "24 Nov 2016"

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getRcoglevel() {
            return rcoglevel;
        }

        public void setRcoglevel(String rcoglevel) {
            this.rcoglevel = rcoglevel;
        }

        public String getAchdate() {
            return achdate;
        }

        public void setAchdate(String achdate) {
            this.achdate = achdate;
        }
    }


  public class PendingRecognition implements Serializable {
        String level;//": "10",
      String rcoglevel;//": "Crown Diamond"

      public String getLevel() {
          return level;
      }

      public void setLevel(String level) {
          this.level = level;
      }

      public String getRcoglevel() {
          return rcoglevel;
      }

      public void setRcoglevel(String rcoglevel) {
          this.rcoglevel = rcoglevel;
      }
  }

}
