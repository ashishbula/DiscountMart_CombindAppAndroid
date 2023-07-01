package in.discountmart.business.model_business.responsemodel;

public class MemberRelationListResponse extends BaseResponseEntity {
     MemberRelation memrels[];

    public MemberRelation[] getMemrels() {
        return memrels;
    }

    public void setMemrels(MemberRelation[] memrels) {
        this.memrels = memrels;
    }

    public static class MemberRelation{
        String memrel;//": "S/O"

         public String getMemrel() {
             return memrel;
         }

         public void setMemrel(String memrel) {
             this.memrel = memrel;
         }
     }
}
