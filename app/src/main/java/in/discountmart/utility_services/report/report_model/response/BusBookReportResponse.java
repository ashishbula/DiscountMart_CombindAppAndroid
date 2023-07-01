package in.discountmart.utility_services.report.report_model.response;

import java.io.Serializable;
import java.util.ArrayList;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class BusBookReportResponse extends BaseResponse implements Serializable {
    String TotalCount;
    ArrayList<BusReportDetail>BusReport;

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public ArrayList<BusReportDetail> getBusReport() {
        return BusReport;
    }

    public void setBusReport(ArrayList<BusReportDetail> busReport) {
        BusReport = busReport;
    }

    public static class BusReportDetail implements Serializable{
        String Distributor;//": "goldwing",
        String MobileNo;//": "8003043280",
        String BusId;//": "207",
        String SeatName;//": " 9, 10",
        String BusDoj;//": "05-Nov-2020",
        String TDate;//": "2020-11-05T13:53:14",
        String PNR;//": "114645670",
        String TIN;//": "C58BVA4N",
        String Status;//": "BOOKED",
        String TotalAmount;//": 840,
        String PromoDiscount;//": 500,
        String PromoCode;//": "05EA94"
        String primeDepartureTime;//": "1335",
        String doj;//": "07/11/2020 00:00:00",
        String droptime;//": "1680",
        String pickuptime;//": null,
        String Travels;//": "RS Chandra Travels (107190)",
        String BusType;//": "A/C Seater / Sleeper (2+1)",
        String pickupcontactno;//": "91-9829114909",
        String SourceCity;//": "Sikar",
        String DestinationCity;//": "Jodhpur",
        String PickupLocation;//": null,
        String DropLocation;//": "Paota",
        String PickupLocationLandMark;//": "Near kalyan cirlce,petrol pump",
        String ArrivalTime;//": "Sunday, 08 Nov 2020 04:00:00",
        String DeptTime;//": "Saturday, 07 Nov 2020 22:15:00",
        String ReportingTime;//": "Wednesday, 11 Nov 2020 11:12:45"

        public String getDistributor() {
            return Distributor;
        }

        public void setDistributor(String distributor) {
            Distributor = distributor;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String mobileNo) {
            MobileNo = mobileNo;
        }

        public String getBusId() {
            return BusId;
        }

        public void setBusId(String busId) {
            BusId = busId;
        }

        public String getSeatName() {
            return SeatName;
        }

        public void setSeatName(String seatName) {
            SeatName = seatName;
        }

        public String getBusDoj() {
            return BusDoj;
        }

        public void setBusDoj(String busDoj) {
            BusDoj = busDoj;
        }

        public String getTDate() {
            return TDate;
        }

        public void setTDate(String TDate) {
            this.TDate = TDate;
        }

        public String getPNR() {
            return PNR;
        }

        public void setPNR(String PNR) {
            this.PNR = PNR;
        }

        public String getTIN() {
            return TIN;
        }

        public void setTIN(String TIN) {
            this.TIN = TIN;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
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

        public String getPromoCode() {
            return PromoCode;
        }

        public void setPromoCode(String promoCode) {
            PromoCode = promoCode;
        }

        public String getPrimeDepartureTime() {
            return primeDepartureTime;
        }

        public void setPrimeDepartureTime(String primeDepartureTime) {
            this.primeDepartureTime = primeDepartureTime;
        }

        public String getDoj() {
            return doj;
        }

        public void setDoj(String doj) {
            this.doj = doj;
        }

        public String getDroptime() {
            return droptime;
        }

        public void setDroptime(String droptime) {
            this.droptime = droptime;
        }

        public String getPickuptime() {
            return pickuptime;
        }

        public void setPickuptime(String pickuptime) {
            this.pickuptime = pickuptime;
        }

        public String getTravels() {
            return Travels;
        }

        public void setTravels(String travels) {
            Travels = travels;
        }

        public String getBusType() {
            return BusType;
        }

        public void setBusType(String busType) {
            BusType = busType;
        }

        public String getPickupcontactno() {
            return pickupcontactno;
        }

        public void setPickupcontactno(String pickupcontactno) {
            this.pickupcontactno = pickupcontactno;
        }

        public String getSourceCity() {
            return SourceCity;
        }

        public void setSourceCity(String sourceCity) {
            SourceCity = sourceCity;
        }

        public String getDestinationCity() {
            return DestinationCity;
        }

        public void setDestinationCity(String destinationCity) {
            DestinationCity = destinationCity;
        }

        public String getPickupLocation() {
            return PickupLocation;
        }

        public void setPickupLocation(String pickupLocation) {
            PickupLocation = pickupLocation;
        }

        public String getDropLocation() {
            return DropLocation;
        }

        public void setDropLocation(String dropLocation) {
            DropLocation = dropLocation;
        }

        public String getPickupLocationLandMark() {
            return PickupLocationLandMark;
        }

        public void setPickupLocationLandMark(String pickupLocationLandMark) {
            PickupLocationLandMark = pickupLocationLandMark;
        }

        public String getArrivalTime() {
            return ArrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            ArrivalTime = arrivalTime;
        }

        public String getDeptTime() {
            return DeptTime;
        }

        public void setDeptTime(String deptTime) {
            DeptTime = deptTime;
        }

        public String getReportingTime() {
            return ReportingTime;
        }

        public void setReportingTime(String reportingTime) {
            ReportingTime = reportingTime;
        }
    }
}
