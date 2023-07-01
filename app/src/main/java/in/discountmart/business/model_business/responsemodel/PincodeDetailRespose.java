package in.discountmart.business.model_business.responsemodel;

public class PincodeDetailRespose extends BaseResponseEntity {

    PincodeDetail pincodedetail[];
    String statecode;//": "21",
    String statename;//": "RAJASTHAN",
    String districtname;//": "BHILWARA",
    String districtcode;//": "94",
    String cityname;//": "Bhilwara",
    String citycode;//": "19841",
    //String response;": "OK"


    public PincodeDetail[] getPincodedetail() {
        return pincodedetail;
    }

    public void setPincodedetail(PincodeDetail[] pincodedetail) {
        this.pincodedetail = pincodedetail;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public static class PincodeDetail {
        String areacode;//": "381276",
        String areaname;//": "GANDHI NAGAR"

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }
    }
}
