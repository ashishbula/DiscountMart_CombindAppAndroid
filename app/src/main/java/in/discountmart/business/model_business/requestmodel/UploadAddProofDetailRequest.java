package in.discountmart.business.model_business.requestmodel;

public class UploadAddProofDetailRequest extends BaseRequest {
    //String reqtype;//": "addressproof",
    //String userid;//": "005152",
    //String passwd;//": "@navneet",
    String address1;//": "Engineers Colony",
    String city;//": "ajmer",
    String statecode;//": "10",
    String district;//": "bhilwara",
    String aadharno1;//": "4321",
    String aadharno2;//": "8765",
    String aadharno3;//": "4785",
    String pincode;//": "511458"
    String frontimg;
    String backimg;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAadharno1() {
        return aadharno1;
    }

    public void setAadharno1(String aadharno1) {
        this.aadharno1 = aadharno1;
    }

    public String getAadharno2() {
        return aadharno2;
    }

    public void setAadharno2(String aadharno2) {
        this.aadharno2 = aadharno2;
    }

    public String getAadharno3() {
        return aadharno3;
    }

    public void setAadharno3(String aadharno3) {
        this.aadharno3 = aadharno3;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
