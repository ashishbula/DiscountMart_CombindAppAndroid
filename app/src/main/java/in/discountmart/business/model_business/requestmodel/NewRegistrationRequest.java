package in.discountmart.business.model_business.requestmodel;


import java.io.Serializable;

public class NewRegistrationRequest extends BaseRequest implements Serializable {


   //String reqtype;//": "register",
   String mobile;//": "RBM378361",
    String username;//": "test123",
    String referralid;//": "RBM378361",
    String uplinerid;//": "RBM378361",
    String side;//": "1",
    String name;//": "test",
    String fname;//: "bispl",
    String dob;//": "08-04-1992",
    String ismarried;//": "Y",
    String marriagedate;//": "08-05-2018",
    String address;//": "Engineer Colony",
    String statecode;//": "21",
    String district;//": "BHILWARA",
    String city;//": "bhilwara",
    String pincode;//": "311001",
    String mobl;//": "8233057616",
    String phoneno;//": "1482220754",
    String email;//": "kirtibhadada.bispl@gmail.com",
    String nominee;//": "testjfds",
    String relation;//": "dsdd",
    String mpasswd;//": "123456",
  // String registrationtype;//": "PF",
    //String registrationno;//": "123456 ",
    String areacode;//": "247453",
    String citycode;//": "19841",
    String districtcode;//": "94",
    //String companyname;//": " Bispl Company",
    String frelation;//": "D/O",
    String actype;//": "CHOOSE Account Type",
    String bankcode;//": "30",
    String panno;//": "BYYPB1008R",
    String accountno;//": "123456868",
    String ifsc;//": "PUNB112478",
    String branch;//": "sanganerigate"
    //String transpasswd;
    String aadharno ;
    String password ;
    //String mpasswd ;
    String longitutude;//": "123","
    String latitude;//": "122331"


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReferralid() {
        return referralid;
    }

    public void setReferralid(String referralid) {
        this.referralid = referralid;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIsmarried() {
        return ismarried;
    }

    public void setIsmarried(String ismarried) {
        this.ismarried = ismarried;
    }

    public String getMarriagedate() {
        return marriagedate;
    }

    public void setMarriagedate(String marriagedate) {
        this.marriagedate = marriagedate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobl() {
        return mobl;
    }

    public void setMobl(String mobl) {
        this.mobl = mobl;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getMpasswd() {
        return mpasswd;
    }

    public void setMpasswd(String mpasswd) {
        this.mpasswd = mpasswd;
    }

    public String getFrelation() {
        return frelation;
    }

    public void setFrelation(String frelation) {
        this.frelation = frelation;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getAadharno() {
        return aadharno;
    }
    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUplinerid() {
        return uplinerid;
    }

    public void setUplinerid(String uplinerid) {
        this.uplinerid = uplinerid;
    }

    public String getLongitutude() {
        return longitutude;
    }

    public void setLongitutude(String longitutude) {
        this.longitutude = longitutude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
