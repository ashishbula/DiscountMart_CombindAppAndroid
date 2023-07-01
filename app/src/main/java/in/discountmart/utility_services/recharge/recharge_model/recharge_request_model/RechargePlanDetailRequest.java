package in.discountmart.utility_services.recharge.recharge_model.recharge_request_model;

import in.discountmart.utility_services.model.request_model.DataRequest;

public class RechargePlanDetailRequest extends DataRequest {
      String circle;//": "RJ",
      String ipCode;//": "BGP",
      String plan;//":"Top up"

      public String getCircle() {
            return circle;
      }

      public void setCircle(String circle) {
            this.circle = circle;
      }

      public String getIpCode() {
            return ipCode;
      }

      public void setIpCode(String ipCode) {
            this.ipCode = ipCode;
      }

      public String getPlan() {
            return plan;
      }

      public void setPlan(String plan) {
            this.plan = plan;
      }
}
