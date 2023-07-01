package in.discountmart.business.model_business.responsemodel;

/**
 * Created by The Rock on 3/22/2018.
 */

public class BaseResponseEntity {

    String response;//":"OK"
    String msg;
    String name;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
