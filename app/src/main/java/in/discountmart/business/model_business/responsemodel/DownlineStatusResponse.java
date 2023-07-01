package in.discountmart.business.model_business.responsemodel;

/**
 * Created by Dell on 22-01-2018.
 */

public class DownlineStatusResponse extends BaseResponse {

    String leftjoin;//":"10108","
    String rightjoin;//":"10992","
    String leftactive;//":"5142","
    String rightactive;//":"5938","
    String totalleftbv;//":"68.00","
    String totalrightbv;//":"0.00
    //String response;//":"OK"}

    public String getLeftjoin() {
        return leftjoin;
    }

    public void setLeftjoin(String leftjoin) {
        this.leftjoin = leftjoin;
    }

    public String getRightjoin() {
        return rightjoin;
    }

    public void setRightjoin(String rightjoin) {
        this.rightjoin = rightjoin;
    }

    public String getLeftactive() {
        return leftactive;
    }

    public void setLeftactive(String leftactive) {
        this.leftactive = leftactive;
    }

    public String getRightactive() {
        return rightactive;
    }

    public void setRightactive(String rightactive) {
        this.rightactive = rightactive;
    }




    public String getTotalleftbv() {
        return totalleftbv;
    }

    public void setTotalleftbv(String totalleftbv) {
        this.totalleftbv = totalleftbv;
    }

    public String getTotalrightbv() {
        return totalrightbv;
    }

    public void setTotalrightbv(String totalrightbv) {
        this.totalrightbv = totalrightbv;
    }
}
