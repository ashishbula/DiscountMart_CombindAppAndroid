package in.discountmart.model_business.responsemodel;

public class KycDetailResponse extends BaseResponse {
    String idno;//": "LP223344",
    String addrsverf;//": "R",
    String idverf;//": "Rejected",
    String rejectreason;//": "",
    String rejectremark;//": "",
    String vaerifydate;//": "10-Apr-2021",
    AddressDetail addressdetail;
    BankDetail bankdetail;
    PanDetail pandetail;

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getAddrsverf() {
        return addrsverf;
    }

    public void setAddrsverf(String addrsverf) {
        this.addrsverf = addrsverf;
    }

    public String getIdverf() {
        return idverf;
    }

    public void setIdverf(String idverf) {
        this.idverf = idverf;
    }

    public String getRejectreason() {
        return rejectreason;
    }

    public void setRejectreason(String rejectreason) {
        this.rejectreason = rejectreason;
    }

    public String getRejectremark() {
        return rejectremark;
    }

    public void setRejectremark(String rejectremark) {
        this.rejectremark = rejectremark;
    }

    public String getVaerifydate() {
        return vaerifydate;
    }

    public void setVaerifydate(String vaerifydate) {
        this.vaerifydate = vaerifydate;
    }

    public AddressDetail getAddressdetail() {
        return addressdetail;
    }

    public void setAddressdetail(AddressDetail addressdetail) {
        this.addressdetail = addressdetail;
    }

    public BankDetail getBankdetail() {
        return bankdetail;
    }

    public void setBankdetail(BankDetail bankdetail) {
        this.bankdetail = bankdetail;
    }

    public PanDetail getPandetail() {
        return pandetail;
    }

    public void setPandetail(PanDetail pandetail) {
        this.pandetail = pandetail;
    }

    public static class AddressDetail{
        String idproof;//": "21 POOJA TOW",
        String address;//": "87 TEGOR NAGAR, AJMER ROAD, JAIPUR",
        String pincode;//": "311001",
        String city;//": "BHILWARA",
        String district;//": "BHILWARA",
        String statecode;//": "21",
        String statename;//": "Rajasthan",
        String addrproof;//": "https://cpanel.life4ever.co.in/images/UploadImage/202102101631060911030.jpg",
        String IdproofNo;//": "21 POOJA TOW",
        String backaddressproof;//": "https://cpanel.life4ever.co.in/images/UploadImage/202102101631061071030.jpg",
        String backaddressdate;//": "10-02-2021 16:31:08",
        String idtype;//": "Voter ID",
        String areacode;//": "247455"

        public String getIdproof() {
            return idproof;
        }

        public void setIdproof(String idproof) {
            this.idproof = idproof;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
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

        public String getAddrproof() {
            return addrproof;
        }

        public void setAddrproof(String addrproof) {
            this.addrproof = addrproof;
        }

        public String getIdproofNo() {
            return IdproofNo;
        }

        public void setIdproofNo(String idproofNo) {
            IdproofNo = idproofNo;
        }

        public String getBackaddressproof() {
            return backaddressproof;
        }

        public void setBackaddressproof(String backaddressproof) {
            this.backaddressproof = backaddressproof;
        }

        public String getBackaddressdate() {
            return backaddressdate;
        }

        public void setBackaddressdate(String backaddressdate) {
            this.backaddressdate = backaddressdate;
        }

        public String getIdtype() {
            return idtype;
        }

        public void setIdtype(String idtype) {
            this.idtype = idtype;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }
    }

    public static class BankDetail{
        String bankid;//": "0",
        String acno;//": "",
        String ifscode;//": "",
        String accounttype;//": "CHOOSE ACCOUNT TYPE",
        String branchname;//": "",
        String bankproof;//": ""


        public String getBankid() {
            return bankid;
        }

        public void setBankid(String bankid) {
            this.bankid = bankid;
        }

        public String getAcno() {
            return acno;
        }

        public void setAcno(String acno) {
            this.acno = acno;
        }

        public String getIfscode() {
            return ifscode;
        }

        public void setIfscode(String ifscode) {
            this.ifscode = ifscode;
        }

        public String getAccounttype() {
            return accounttype;
        }

        public void setAccounttype(String accounttype) {
            this.accounttype = accounttype;
        }

        public String getBranchname() {
            return branchname;
        }

        public void setBranchname(String branchname) {
            this.branchname = branchname;
        }

        public String getBankproof() {
            return bankproof;
        }

        public void setBankproof(String bankproof) {
            this.bankproof = bankproof;
        }
    }
    public static class PanDetail{
        String panno;//": "",
        String panimage;//": ""

        public String getPanno() {
            return panno;
        }

        public void setPanno(String panno) {
            this.panno = panno;
        }

        public String getPanimage() {
            return panimage;
        }

        public void setPanimage(String panimage) {
            this.panimage = panimage;
        }
    }

}
