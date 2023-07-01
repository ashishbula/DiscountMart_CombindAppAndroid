package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class WelcomeBannerResponse extends BaseResponse{
    ArrayList<WelcomeBanner>popupdetail;//": [{"popupurl":"http://admin.goldwingsglobal.com/images/UploadImage/20210513032635116.png"}],"response":"OK"}

    public ArrayList<WelcomeBanner> getPopupdetail() {
        return popupdetail;
    }

    public void setPopupdetail(ArrayList<WelcomeBanner> popupdetail) {
        this.popupdetail = popupdetail;
    }

    public static class WelcomeBanner{
       String  popupurl;//

        public String getPopupurl() {
            return popupurl;
        }

        public void setPopupurl(String popupurl) {
            this.popupurl = popupurl;
        }
    }
}
