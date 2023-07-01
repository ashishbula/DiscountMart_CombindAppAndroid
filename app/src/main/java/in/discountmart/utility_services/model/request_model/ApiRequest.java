package in.discountmart.utility_services.model.request_model;

public class ApiRequest {
    public BaseHeaderRequest HEADER;
    public DataRequest DATA;

    public BaseHeaderRequest getHEADER() {
        return HEADER;
    }

    public void setHEADER(BaseHeaderRequest HEADER) {
        this.HEADER = HEADER;
    }

    public DataRequest getDATA() {
        return DATA;
    }

    public void setDATA(DataRequest DATA) {
        this.DATA = DATA;
    }
}
