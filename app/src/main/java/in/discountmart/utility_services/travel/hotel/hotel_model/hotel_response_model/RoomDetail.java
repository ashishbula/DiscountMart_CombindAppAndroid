package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

import java.io.Serializable;
import java.util.ArrayList;

public class RoomDetail implements Serializable {
  String ID;//": null,
    String RoomIndex;//": "1",
    String RoomTypeCode;//": "3030904|61a36dfc-4ccc-3001-ecfa-51da3e47164d|1^^1^^613447||61a36dfc-4ccc-3001-ecfa-51da3e47164d~!:~1~!:~1",
    String RoomTypeName;//": "Standard Air Conditioning King Bed",
    String RatePlanCode;//": "613447||61a36dfc-4ccc-3001-ecfa-51da3e47164d",
    String BedTypeCode;//": null,
    String SmokingPreference;//": null,
    String Supplements;//": null,
    String SupplierPrice;
    /*Class*/
    RoomPriceDetail Price;
    String HotelPassenger;//": null,
    ArrayList<String> Amenity;//": null,
    String BedTypes;//": null,
    ArrayList<RoomCancelPolicy> CancellationPolicies;
    String CancellationPolicy;
    ArrayList<String> Inclusion;
    String LastCancelDate;
    String InfoSource;//": "FixedCombination",;

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getRoomIndex() {
    return RoomIndex;
  }

  public void setRoomIndex(String roomIndex) {
    RoomIndex = roomIndex;
  }

  public String getRoomTypeCode() {
    return RoomTypeCode;
  }

  public void setRoomTypeCode(String roomTypeCode) {
    RoomTypeCode = roomTypeCode;
  }

  public String getRoomTypeName() {
    return RoomTypeName;
  }

  public void setRoomTypeName(String roomTypeName) {
    RoomTypeName = roomTypeName;
  }

  public String getRatePlanCode() {
    return RatePlanCode;
  }

  public void setRatePlanCode(String ratePlanCode) {
    RatePlanCode = ratePlanCode;
  }

  public String getBedTypeCode() {
    return BedTypeCode;
  }

  public void setBedTypeCode(String bedTypeCode) {
    BedTypeCode = bedTypeCode;
  }

  public String getSmokingPreference() {
    return SmokingPreference;
  }

  public void setSmokingPreference(String smokingPreference) {
    SmokingPreference = smokingPreference;
  }

  public String getSupplements() {
    return Supplements;
  }

  public void setSupplements(String supplements) {
    Supplements = supplements;
  }

  public String getSupplierPrice() {
    return SupplierPrice;
  }

  public void setSupplierPrice(String supplierPrice) {
    SupplierPrice = supplierPrice;
  }

  public RoomPriceDetail getPrice() {
    return Price;
  }

  public void setPrice(RoomPriceDetail price) {
    Price = price;
  }

  public String getHotelPassenger() {
    return HotelPassenger;
  }

  public void setHotelPassenger(String hotelPassenger) {
    HotelPassenger = hotelPassenger;
  }

  public ArrayList<String> getAmenity() {
    return Amenity;
  }

  public void setAmenity(ArrayList<String> amenity) {
    Amenity = amenity;
  }

  public String getBedTypes() {
    return BedTypes;
  }

  public void setBedTypes(String bedTypes) {
    BedTypes = bedTypes;
  }

  public ArrayList<RoomCancelPolicy> getCancellationPolicies() {
    return CancellationPolicies;
  }

  public void setCancellationPolicies(ArrayList<RoomCancelPolicy> cancellationPolicies) {
    CancellationPolicies = cancellationPolicies;
  }

  public String getCancellationPolicy() {
    return CancellationPolicy;
  }

  public void setCancellationPolicy(String cancellationPolicy) {
    CancellationPolicy = cancellationPolicy;
  }

  public ArrayList<String> getInclusion() {
    return Inclusion;
  }

  public void setInclusion(ArrayList<String> inclusion) {
    Inclusion = inclusion;
  }

  public String getLastCancelDate() {
    return LastCancelDate;
  }

  public void setLastCancelDate(String lastCancelDate) {
    LastCancelDate = lastCancelDate;
  }

  public String getInfoSource() {
    return InfoSource;
  }

  public void setInfoSource(String infoSource) {
    InfoSource = infoSource;
  }
}
