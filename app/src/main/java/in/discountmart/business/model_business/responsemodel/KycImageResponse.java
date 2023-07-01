package in.discountmart.business.model_business.responsemodel;

public class KycImageResponse extends BaseResponse {
   // String response;//":"OK","
    String type;//":"pancard","
    String image;//":"https://cpanel.life4ever.co.in/images/UploadImage/202106151436364471030.png" }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
