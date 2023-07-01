package in.discountmart.utility_services.model.response_model;

public class TokenResponse {

    String ID;//":0,"
    String TokenId;//":"a8bf6b88-092b-472d-bcd5-bb2177f4d888","
    String GenerateDate;//":null,"
    String IsLive;//":false

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }

    public String getGenerateDate() {
        return GenerateDate;
    }

    public void setGenerateDate(String generateDate) {
        GenerateDate = generateDate;
    }

    public String getIsLive() {
        return IsLive;
    }

    public void setIsLive(String isLive) {
        IsLive = isLive;
    }
}
