package in.discountmart.utility_services.report.report_model.response;

import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class CabReportResponse extends BaseResponse implements Serializable {
    ArrayList<CabReport> CabBookingReport;
    String TotalCount;

    public ArrayList<CabReport> getCabBookingReport() {
        return CabBookingReport;
    }

    public void setCabBookingReport(ArrayList<CabReport> cabBookingReport) {
        CabBookingReport = cabBookingReport;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public static class CabReport implements Serializable{
        String RowNumber;//": "12",
        String ID;//": 13,
        String FormNo;//": 1002,
        String SponsorFormno;//": 0,
        String BookingRefNo;//": 10013,
        String Source;//": "Jaipur",
        String Status;//": "Failed",
        String Destination;//": "Ajmer",
        String TotalAmount;//": 10,
        String DOJ;//": "27-Feb-2021",
        String BookingId;//": "test",
        String BookStatus;//'//'": 1,
        String Name;//": "srishti",
        String Address;//": "",
        String PickupAddress;//": "Jaipur",
        String EmailID;//": "abc@gmail.com",
        String CabID;//": 0,
        String TransactionDate;//": "16-Feb-2021",
        String UserName;//": "Demo",
        String RegMobileNo;//": "9852367410",
        String MobileNo;//": "1234569870",
        String MemName;//": "Amit saini",
        String Company;//": "DT123",
        String IsPromoCode;//": false,
        String PromoDiscount;//": 0,
        String PromoCode;//": "",
        String Driver;//": null,
        String CabNo;//": "test",
        String DriverContactNo;//": null,
        String VehicalType;//": null,
        String ApporoveDate;//": null

        public String getRowNumber() {
            return RowNumber;
        }

        public void setRowNumber(String rowNumber) {
            RowNumber = rowNumber;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getFormNo() {
            return FormNo;
        }

        public void setFormNo(String formNo) {
            FormNo = formNo;
        }

        public String getSponsorFormno() {
            return SponsorFormno;
        }

        public void setSponsorFormno(String sponsorFormno) {
            SponsorFormno = sponsorFormno;
        }

        public String getBookingRefNo() {
            return BookingRefNo;
        }

        public void setBookingRefNo(String bookingRefNo) {
            BookingRefNo = bookingRefNo;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String source) {
            Source = source;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String destination) {
            Destination = destination;
        }

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            TotalAmount = totalAmount;
        }

        public String getDOJ() {
            return DOJ;
        }

        public void setDOJ(String DOJ) {
            this.DOJ = DOJ;
        }

        public String getBookingId() {
            return BookingId;
        }

        public void setBookingId(String bookingId) {
            BookingId = bookingId;
        }

        public String getBookStatus() {
            return BookStatus;
        }

        public void setBookStatus(String bookStatus) {
            BookStatus = bookStatus;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getPickupAddress() {
            return PickupAddress;
        }

        public void setPickupAddress(String pickupAddress) {
            PickupAddress = pickupAddress;
        }

        public String getEmailID() {
            return EmailID;
        }

        public void setEmailID(String emailID) {
            EmailID = emailID;
        }

        public String getCabID() {
            return CabID;
        }

        public void setCabID(String cabID) {
            CabID = cabID;
        }

        public String getTransactionDate() {
            return TransactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            TransactionDate = transactionDate;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getRegMobileNo() {
            return RegMobileNo;
        }

        public void setRegMobileNo(String regMobileNo) {
            RegMobileNo = regMobileNo;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String mobileNo) {
            MobileNo = mobileNo;
        }

        public String getMemName() {
            return MemName;
        }

        public void setMemName(String memName) {
            MemName = memName;
        }

        public String getCompany() {
            return Company;
        }

        public void setCompany(String company) {
            Company = company;
        }

        public String getIsPromoCode() {
            return IsPromoCode;
        }

        public void setIsPromoCode(String isPromoCode) {
            IsPromoCode = isPromoCode;
        }

        public String getPromoDiscount() {
            return PromoDiscount;
        }

        public void setPromoDiscount(String promoDiscount) {
            PromoDiscount = promoDiscount;
        }

        public String getPromoCode() {
            return PromoCode;
        }

        public void setPromoCode(String promoCode) {
            PromoCode = promoCode;
        }

        public String getDriver() {
            return Driver;
        }

        public void setDriver(String driver) {
            Driver = driver;
        }

        public String getCabNo() {
            return CabNo;
        }

        public void setCabNo(String cabNo) {
            CabNo = cabNo;
        }

        public String getDriverContactNo() {
            return DriverContactNo;
        }

        public void setDriverContactNo(String driverContactNo) {
            DriverContactNo = driverContactNo;
        }

        public String getVehicalType() {
            return VehicalType;
        }

        public void setVehicalType(String vehicalType) {
            VehicalType = vehicalType;
        }

        public String getApporoveDate() {
            return ApporoveDate;
        }

        public void setApporoveDate(String apporoveDate) {
            ApporoveDate = apporoveDate;
        }
    }

}
