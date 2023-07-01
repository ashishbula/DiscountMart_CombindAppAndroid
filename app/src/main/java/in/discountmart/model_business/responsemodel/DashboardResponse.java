package in.discountmart.model_business.responsemodel;

import java.io.Serializable;
import java.util.ArrayList;

public class DashboardResponse extends BaseResponse implements Serializable {
    ArrayList<MyTeam> myteam;
    //ArrayList<Member_profile> profile;
    ArrayList<News> news;
    ArrayList<Wallet> wallet;
    //Shop_Wallet shoppewallet;
    ArrayList<Income> income;
   // Rank rank;
    String referalurlleft;
    String referalurlright;
    String profilepic;
    String idno;
    String name;
    String rank;
    //MyGeneration generationdetail;
    ArrayList<Reward> reward;
    //ArrayList<LoginHistory>loginhistory;
   // ArrayList<ActiveDirect>activedirects;
    ArrayList<Directs>directs;

    public ArrayList<MyTeam> getMyteam() {
        return myteam;
    }

    public void setMyteam(ArrayList<MyTeam> myteam) {
        this.myteam = myteam;
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public ArrayList<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(ArrayList<Wallet> wallet) {
        this.wallet = wallet;
    }

    public ArrayList<Income> getIncome() {
        return income;
    }

    public void setIncome(ArrayList<Income> income) {
        this.income = income;
    }

    public ArrayList<Reward> getReward() {
        return reward;
    }

    public void setReward(ArrayList<Reward> reward) {
        this.reward = reward;
    }

    public ArrayList<Directs> getDirects() {
        return directs;
    }

    public void setDirects(ArrayList<Directs> directs) {
        this.directs = directs;
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

    public static class MyTeam{
       String name;//": "Today's Registration",
        String left;//": "0.00",
        String right;//": "686.00",
        String total;//": "686.00"

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public static class Member_profile{
       String profilepic;//": "images/no_photo.jpg",
        String idno;//": "GW223344",
        String name;//": "GOLDWINGS GLOBAL BUSINESS PVT LTD "



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

        public String getProfilepic() {
            return profilepic;
        }

        public void setProfilepic(String profilepic) {
            this.profilepic = profilepic;
        }
    }

    public static class News{}

    public static class Wallet{
       String walletname;//": "Main Wallet",
        String credit;//": "18381.00",
        String debit;//": "18456.00",
        String balance;//": "-75.00"

        public String getWalletname() {
            return walletname;
        }

        public void setWalletname(String walletname) {
            this.walletname = walletname;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getDebit() {
            return debit;
        }

        public void setDebit(String debit) {
            this.debit = debit;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
    public static class Reward{}

    public static class Income{
         String name;//": "Matching Income",
        String amount;//": "30300.00"

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
    public static class Rank{

        String rank;

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }

    public static class ActiveDirect implements Serializable{
        String idno;//": "PC11019735",
        String membername;//": "VINOD KUMAR PHULWARIYA",
        String position;//": "TEAM2"

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public String getMembername() {
            return membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
    public static class Directs implements Serializable{
       String idno;//": "goldwingsglobal",
        String name;//": "MEHRAJ KHAN",
        String activation;//": "26-Feb-2020"

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

        public String getActivation() {
            return activation;
        }

        public void setActivation(String activation) {
            this.activation = activation;
        }
    }

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
}
