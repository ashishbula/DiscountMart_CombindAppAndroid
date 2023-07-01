package in.discountmart.business.model_business.responsemodel;

public class MonthSessionResponse extends BaseResponseEntity {

    MonthSession months[];

    String recordcount;

    public MonthSession[] getMonths() {
        return months;
    }

    public void setMonths(MonthSession[] months) {
        this.months = months;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public static class MonthSession{

        String year;//": "July 2018",
        String sessid;//": "8"

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getSessid() {
            return sessid;
        }

        public void setSessid(String sessid) {
            this.sessid = sessid;
        }
    }
}
