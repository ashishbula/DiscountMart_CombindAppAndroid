package in.discountmart.business.model_business.responsemodel;

public class StateListResponse extends BaseResponseEntity {
    StateList states[];

    public StateList[] getStates() {
        return states;
    }

    public void setStates(StateList[] states) {
        this.states = states;
    }

    public static class StateList{

        String statecode;//": "0",
        String statename;//": "--Choose State Name--"

        public String getStatecode() {
            return statecode;
        }

        public void setStatecode(String statecode) {
            this.statecode = statecode;
        }

        public String getStatename() {
            return statename;
        }

        public void setStatename(String statename) {
            this.statename = statename;
        }
    }
}
