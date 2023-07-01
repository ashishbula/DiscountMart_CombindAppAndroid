package in.discountmart.utility_services.travel.utility_cab.cab_model.cab_request_mdel;

import java.io.Serializable;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class CabBookRequest extends DataRequest implements Serializable {
     String ID;//": 0,
    String Formno;//": "1002",
    String Email;//": "abc@gmail.com",
    String MobileNo;//": "9414369001",
    String Title;//": "Male",
    String NoOfPax;//": "1",
    String PickupAddress;//":"Jaipur",
    String DropAddress;//":"Ajmer",
    String TotalAmount;//":10.00,
    String PromoDiscount;//":0.0,
    String Discount;//":0.0,
    String DOJ;//":"2021-02-27",
    String PickupTime;//":"5:30 pm",
    String City;//":"Jaipur",
    String Destination;//":"Ajmer",
    String PromoCode;//":"",
    String Name;//":"srishti",
    String regMobileNo;//":"8890885477",
    String regEmailId;//":"",
    String MemName;//":"Amit"
    String CabID;//":"Amit"

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFormno() {
        return Formno;
    }

    public void setFormno(String formno) {
        Formno = formno;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNoOfPax() {
        return NoOfPax;
    }

    public void setNoOfPax(String noOfPax) {
        NoOfPax = noOfPax;
    }

    public String getPickupAddress() {
        return PickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        PickupAddress = pickupAddress;
    }

    public String getDropAddress() {
        return DropAddress;
    }

    public void setDropAddress(String dropAddress) {
        DropAddress = dropAddress;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getPromoDiscount() {
        return PromoDiscount;
    }

    public void setPromoDiscount(String promoDiscount) {
        PromoDiscount = promoDiscount;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String DOJ) {
        this.DOJ = DOJ;
    }

    public String getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(String pickupTime) {
        PickupTime = pickupTime;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegMobileNo() {
        return regMobileNo;
    }

    public void setRegMobileNo(String regMobileNo) {
        this.regMobileNo = regMobileNo;
    }

    public String getRegEmailId() {
        return regEmailId;
    }

    public void setRegEmailId(String regEmailId) {
        this.regEmailId = regEmailId;
    }

    public String getMemName() {
        return MemName;
    }

    public void setMemName(String memName) {
        MemName = memName;
    }

    public String getCabID() {
        return CabID;
    }

    public void setCabID(String cabID) {
        CabID = cabID;
    }
}
