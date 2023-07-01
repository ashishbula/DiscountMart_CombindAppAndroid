package in.discountmart.model_business.responsemodel;

public class IdProofListResponse extends BaseResponseEntity {

    IdProofList idtype[];

    public IdProofList[] getIdprooflist() {
        return idtype;
    }

    public void setIdprooflist(IdProofList[] idprooflist) {
        this.idtype = idprooflist;
    }

    public static class IdProofList
    {
        String id;//": "0",
        String Idtype;//": "--Choose Id Proof--"

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdtype() {
            return Idtype;
        }

        public void setIdtype(String idtype) {
            this.Idtype = idtype;
        }
    }


}
