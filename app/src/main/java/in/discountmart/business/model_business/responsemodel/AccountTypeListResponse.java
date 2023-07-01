package in.discountmart.business.model_business.responsemodel;

public class AccountTypeListResponse extends BaseResponseEntity {

    AccountList accounttype[];
   // String response":"OK"


    public AccountList[] getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(AccountList[] accounttype) {
        this.accounttype = accounttype;
    }

    public static class AccountList{
        String accid;//":"0",
        String accountype;//":"CHOOSE ACCOUNT TYPE"

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getAccountype() {
            return accountype;
        }

        public void setAccountype(String accountype) {
            this.accountype = accountype;
        }
    }
}

