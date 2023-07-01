package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class DashboardResponse extends BaseResponse implements Serializable {
    //MyTeam myteam;
    //Member_profile profile;
    //Main_Wallet mainwallet;
    //Shop_Wallet shoppewallet;
    //Income income;
   // Rank rank;
    String referalurlleft;
    String referalurlright;
    String profilepic;
    String idno;
    String name;
    String rank;
    String club;

    //"response": "OK"
    String referalurl;//": "https://discountmania.in/Newjoining1.aspx?ref=NTA3NjI5Ni8w",
    @SerializedName("news")
    @Expose
    ArrayList<Map<String,String>> news;

    @SerializedName("myteam")
    @Expose
    ArrayList<Map<String,String>> myteam;

    @SerializedName("income")
    @Expose
    ArrayList<Map<String,String>> income;

    @SerializedName("wallet")
    @Expose
    ArrayList<Map<String,String>> wallet;

    @SerializedName("reward")
    @Expose
    ArrayList<Map<String,String>> reward;

    @SerializedName("directs")
    @Expose
    ArrayList<Map<String,String>> directs;
    @SerializedName("mfabusiness")
    @Expose
    ArrayList<Map<String,String>> mfabusiness;

    public String getReferalurlleft() {
        return referalurlleft;
    }

    public void setReferalurlleft(String referalurlleft) {
        this.referalurlleft = referalurlleft;
    }

    public String getReferalurlright() {
        return referalurlright;
    }

    public void setReferalurlright(String referalurlright) {
        this.referalurlright = referalurlright;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getReferalurl() {
        return referalurl;
    }

    public void setReferalurl(String referalurl) {
        this.referalurl = referalurl;
    }

    public ArrayList<Map<String, String>> getNews() {
        return news;
    }

    public void setNews(ArrayList<Map<String, String>> news) {
        this.news = news;
    }

    public ArrayList<Map<String, String>> getMyteam() {
        return myteam;
    }

    public void setMyteam(ArrayList<Map<String, String>> myteam) {
        this.myteam = myteam;
    }

    public ArrayList<Map<String, String>> getIncome() {
        return income;
    }

    public void setIncome(ArrayList<Map<String, String>> income) {
        this.income = income;
    }

    public ArrayList<Map<String, String>> getWallet() {
        return wallet;
    }

    public void setWallet(ArrayList<Map<String, String>> wallet) {
        this.wallet = wallet;
    }

    public ArrayList<Map<String, String>> getReward() {
        return reward;
    }

    public void setReward(ArrayList<Map<String, String>> reward) {
        this.reward = reward;
    }

    public ArrayList<Map<String, String>> getDirects() {
        return directs;
    }

    public void setDirects(ArrayList<Map<String, String>> directs) {
        this.directs = directs;
    }

    public ArrayList<Map<String, String>> getMfabusiness() {
        return mfabusiness;
    }

    public void setMfabusiness(ArrayList<Map<String, String>> mfabusiness) {
        this.mfabusiness = mfabusiness;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
}
