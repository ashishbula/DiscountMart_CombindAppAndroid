package in.discountmart.utility_services.recharge.recharge_model.recharge_response_model;

import java.io.Serializable;

import in.discountmart.utility_services.model.response_model.BaseResponse;

public class RechargePlanDetail extends BaseResponse implements Serializable {
    String id;//": "3197782",
    String sp_key;//": "BGP",
    String sp_circle;//": null,
    String recharge_value;//": "10",
    String recharge_talktime;//": "7.47",
    String Details;//": null,
    String recharge_validity;//": "NA",
    String recharge_short_description;//": "Top up",
    String recharge_description;//": "(MRP is inclusive of 18% GST).",
    String last_updated_dt;//": null
    String planName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSp_key() {
        return sp_key;
    }

    public void setSp_key(String sp_key) {
        this.sp_key = sp_key;
    }

    public String getSp_circle() {
        return sp_circle;
    }

    public void setSp_circle(String sp_circle) {
        this.sp_circle = sp_circle;
    }

    public String getRecharge_value() {
        return recharge_value;
    }

    public void setRecharge_value(String recharge_value) {
        this.recharge_value = recharge_value;
    }

    public String getRecharge_talktime() {
        return recharge_talktime;
    }

    public void setRecharge_talktime(String recharge_talktime) {
        this.recharge_talktime = recharge_talktime;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getRecharge_validity() {
        return recharge_validity;
    }

    public void setRecharge_validity(String recharge_validity) {
        this.recharge_validity = recharge_validity;
    }

    public String getRecharge_short_description() {
        return recharge_short_description;
    }

    public void setRecharge_short_description(String recharge_short_description) {
        this.recharge_short_description = recharge_short_description;
    }

    public String getRecharge_description() {
        return recharge_description;
    }

    public void setRecharge_description(String recharge_description) {
        this.recharge_description = recharge_description;
    }

    public String getLast_updated_dt() {
        return last_updated_dt;
    }

    public void setLast_updated_dt(String last_updated_dt) {
        this.last_updated_dt = last_updated_dt;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
