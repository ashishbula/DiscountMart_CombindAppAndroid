package in.discountmart.utility_services.travel.hotel.hotel_model;

import java.io.Serializable;

public class HotelPassengerModel implements Serializable {
    public static final int HEADER_TYPE=0;
    public static final int ITEM_TYPE=1;
    public static final int FOOTER_TYPE=2;
    int type;

    String Name;
    String nametitle;
    String surName;
    String gender;
    String dob;
    String age;
    String pessengerType;

    String email;
    String mobile;
    int adult;
    int child;
    int infants;
    int count;
    int other;
    int countAdults;
    int countChilds;
    int countInfants;
    int countOther;
    String gstno;
    String gstemail;
    String companyName;
    String companyAddress;
    String contactno;
    String errorMsg;
    private boolean isAdultChecked;
    private boolean isChildChecked;
    private boolean isGenderChecked;
    private boolean isSelect;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNametitle() {
        return nametitle;
    }

    public void setNametitle(String nametitle) {
        this.nametitle = nametitle;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPessengerType() {
        return pessengerType;
    }

    public void setPessengerType(String pessengerType) {
        this.pessengerType = pessengerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getInfants() {
        return infants;
    }

    public void setInfants(int infants) {
        this.infants = infants;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getCountAdults() {
        return countAdults;
    }

    public void setCountAdults(int countAdults) {
        this.countAdults = countAdults;
    }

    public int getCountChilds() {
        return countChilds;
    }

    public void setCountChilds(int countChilds) {
        this.countChilds = countChilds;
    }

    public int getCountInfants() {
        return countInfants;
    }

    public void setCountInfants(int countInfants) {
        this.countInfants = countInfants;
    }

    public int getCountOther() {
        return countOther;
    }

    public void setCountOther(int countOther) {
        this.countOther = countOther;
    }

    public String getGstno() {
        return gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getGstemail() {
        return gstemail;
    }

    public void setGstemail(String gstemail) {
        this.gstemail = gstemail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isAdultChecked() {
        return isAdultChecked;
    }

    public void setAdultChecked(boolean adultChecked) {
        isAdultChecked = adultChecked;
    }

    public boolean isChildChecked() {
        return isChildChecked;
    }

    public void setChildChecked(boolean childChecked) {
        isChildChecked = childChecked;
    }

    public boolean isGenderChecked() {
        return isGenderChecked;
    }

    public void setGenderChecked(boolean genderChecked) {
        isGenderChecked = genderChecked;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
