package in.discountmart.utility_services.recharge.recharge_model.recharge_response_model;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class GetServiceProviderResponse extends BaseResponse implements Serializable {
     String SNo;//": 0,
    String CompanyName;//": "Airtel",
    String Code;//": 2,
    String ActiveStatus;//": null,
    String service;//": null,
    String ComissionGroupId;//": 0,
    String disableservice;//": null,
    String ActiveApiId;//": 7,
    String TracerCode;//": null,
    String IPcode;//": null,
    String ipsts;//": null,
    String PostPaidDed;//": 0,
    String IsDeduct;//": null,
    String PPCode;//": null,
    String IsBBPS;//": null
    String Logo;

    public String getSNo() {
        return SNo;
    }

    public void setSNo(String SNo) {
        this.SNo = SNo;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getComissionGroupId() {
        return ComissionGroupId;
    }

    public void setComissionGroupId(String comissionGroupId) {
        ComissionGroupId = comissionGroupId;
    }

    public String getDisableservice() {
        return disableservice;
    }

    public void setDisableservice(String disableservice) {
        this.disableservice = disableservice;
    }

    public String getActiveApiId() {
        return ActiveApiId;
    }

    public void setActiveApiId(String activeApiId) {
        ActiveApiId = activeApiId;
    }

    public String getTracerCode() {
        return TracerCode;
    }

    public void setTracerCode(String tracerCode) {
        TracerCode = tracerCode;
    }

    public String getIPcode() {
        return IPcode;
    }

    public void setIPcode(String IPcode) {
        this.IPcode = IPcode;
    }

    public String getIpsts() {
        return ipsts;
    }

    public void setIpsts(String ipsts) {
        this.ipsts = ipsts;
    }

    public String getPostPaidDed() {
        return PostPaidDed;
    }

    public void setPostPaidDed(String postPaidDed) {
        PostPaidDed = postPaidDed;
    }

    public String getIsDeduct() {
        return IsDeduct;
    }

    public void setIsDeduct(String isDeduct) {
        IsDeduct = isDeduct;
    }

    public String getPPCode() {
        return PPCode;
    }

    public void setPPCode(String PPCode) {
        this.PPCode = PPCode;
    }

    public String getIsBBPS() {
        return IsBBPS;
    }

    public void setIsBBPS(String isBBPS) {
        IsBBPS = isBBPS;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }
}
