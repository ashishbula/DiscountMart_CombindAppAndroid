package in.discountmart.utility_services.travel.hotel.hotel_model.hotel_response_model;

public class BlockRoomPrice {
    String CurrencyCode;//": "INR",
    String RoomPrice;//": 656.01,
    double Tax;//": 80.5,
    double ExtraGuestCharge;//": 0,
    double ChildCharge;//": 0,
    double OtherCharges;//": 4.9,
    double Discount;//": 0,
    double PublishedPrice;//": 751.21,
    double PublishedPriceRoundedOff;//": 751,
    double OfferedPrice;//": 741.41,
    int OfferedPriceRoundedOff;//": 741,
    double AgentCommission;//": 9.8,
    double AgentMarkUp;//": 0,
    double ServiceTax;//": 0.7,
    double TDS;//": 4.2,
    double ServiceCharge;//": 0,
    double TotalGSTAmount;//": 0.7,
    BlockRoomGst GST;

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getRoomPrice() {
        return RoomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        RoomPrice = roomPrice;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public double getExtraGuestCharge() {
        return ExtraGuestCharge;
    }

    public void setExtraGuestCharge(double extraGuestCharge) {
        ExtraGuestCharge = extraGuestCharge;
    }

    public double getChildCharge() {
        return ChildCharge;
    }

    public void setChildCharge(double childCharge) {
        ChildCharge = childCharge;
    }

    public double getOtherCharges() {
        return OtherCharges;
    }

    public void setOtherCharges(double otherCharges) {
        OtherCharges = otherCharges;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getPublishedPrice() {
        return PublishedPrice;
    }

    public void setPublishedPrice(double publishedPrice) {
        PublishedPrice = publishedPrice;
    }

    public double getPublishedPriceRoundedOff() {
        return PublishedPriceRoundedOff;
    }

    public void setPublishedPriceRoundedOff(double publishedPriceRoundedOff) {
        PublishedPriceRoundedOff = publishedPriceRoundedOff;
    }

    public double getOfferedPrice() {
        return OfferedPrice;
    }

    public void setOfferedPrice(double offeredPrice) {
        OfferedPrice = offeredPrice;
    }

    public int getOfferedPriceRoundedOff() {
        return OfferedPriceRoundedOff;
    }

    public void setOfferedPriceRoundedOff(int offeredPriceRoundedOff) {
        OfferedPriceRoundedOff = offeredPriceRoundedOff;
    }

    public double getAgentCommission() {
        return AgentCommission;
    }

    public void setAgentCommission(double agentCommission) {
        AgentCommission = agentCommission;
    }

    public double getAgentMarkUp() {
        return AgentMarkUp;
    }

    public void setAgentMarkUp(double agentMarkUp) {
        AgentMarkUp = agentMarkUp;
    }

    public double getServiceTax() {
        return ServiceTax;
    }

    public void setServiceTax(double serviceTax) {
        ServiceTax = serviceTax;
    }

    public double getTDS() {
        return TDS;
    }

    public void setTDS(double TDS) {
        this.TDS = TDS;
    }

    public double getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    public double getTotalGSTAmount() {
        return TotalGSTAmount;
    }

    public void setTotalGSTAmount(double totalGSTAmount) {
        TotalGSTAmount = totalGSTAmount;
    }

    public BlockRoomGst getGST() {
        return GST;
    }

    public void setGST(BlockRoomGst GST) {
        this.GST = GST;
    }

    public static class BlockRoomGst{
        int CGSTAmount;//": 0,
        int CGSTRate;//": 0,
        int CessAmount;//": 0,
        int CessRate;//": 0,
        double IGSTAmount;//": 0.7,
        double IGSTRate;//": 18,
        double SGSTAmount;//": 0,
        double SGSTRate;//": 0,
        double TaxableAmount;//": 5

        public int getCGSTAmount() {
            return CGSTAmount;
        }

        public void setCGSTAmount(int CGSTAmount) {
            this.CGSTAmount = CGSTAmount;
        }

        public int getCGSTRate() {
            return CGSTRate;
        }

        public void setCGSTRate(int CGSTRate) {
            this.CGSTRate = CGSTRate;
        }

        public int getCessAmount() {
            return CessAmount;
        }

        public void setCessAmount(int cessAmount) {
            CessAmount = cessAmount;
        }

        public int getCessRate() {
            return CessRate;
        }

        public void setCessRate(int cessRate) {
            CessRate = cessRate;
        }

        public double getIGSTAmount() {
            return IGSTAmount;
        }

        public void setIGSTAmount(double IGSTAmount) {
            this.IGSTAmount = IGSTAmount;
        }

        public double getIGSTRate() {
            return IGSTRate;
        }

        public void setIGSTRate(double IGSTRate) {
            this.IGSTRate = IGSTRate;
        }

        public double getSGSTAmount() {
            return SGSTAmount;
        }

        public void setSGSTAmount(double SGSTAmount) {
            this.SGSTAmount = SGSTAmount;
        }

        public double getSGSTRate() {
            return SGSTRate;
        }

        public void setSGSTRate(double SGSTRate) {
            this.SGSTRate = SGSTRate;
        }

        public double getTaxableAmount() {
            return TaxableAmount;
        }

        public void setTaxableAmount(double taxableAmount) {
            TaxableAmount = taxableAmount;
        }
    }

}
