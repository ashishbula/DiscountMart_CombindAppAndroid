package in.discountmart.utility_services.model.response_model;

public class BaseResponse {

    String RESP_CODE;//": "0",
    String RESPONSE;//": "SUCCESS",
    String RESP_MSG;//": "Transaction Success",
    String RESP_VALUE;


    public String getRESP_CODE() {
        return RESP_CODE;
    }

    public void setRESP_CODE(String RESP_CODE) {
        this.RESP_CODE = RESP_CODE;
    }

    public String getRESPONSE() {
        return RESPONSE;
    }

    public void setRESPONSE(String RESPONSE) {
        this.RESPONSE = RESPONSE;
    }

    public String getRESP_MSG() {
        return RESP_MSG;
    }

    public void setRESP_MSG(String RESP_MSG) {
        this.RESP_MSG = RESP_MSG;
    }

    public String getRESP_VALUE() {
        return RESP_VALUE;
    }

    public void setRESP_VALUE(String RESP_VALUE) {
        this.RESP_VALUE = RESP_VALUE;
    }
}
