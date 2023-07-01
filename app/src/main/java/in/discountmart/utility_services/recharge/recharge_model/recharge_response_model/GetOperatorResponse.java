package in.discountmart.utility_services.recharge.recharge_model.recharge_response_model;

public class GetOperatorResponse {
    String operator;//": "Jio",
    String circle;//": "Rajasthan"
    String status;//": "error",
    String msg;//": "incorrect mobile number"

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
