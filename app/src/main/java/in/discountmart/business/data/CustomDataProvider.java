package in.discountmart.business.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CustomDataProvider  {

    private static final int MAX_LEVELS = 3;

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static Context context;

   // public  Context context;

    private static List<BaseItem> mMenu = new ArrayList<>();

    public CustomDataProvider(Context cont){
        this.context=cont;
    }

    public static List<BaseItem> getInitialItems() {
        //return getSubItems(new GroupItem("root"));

        List<BaseItem> rootMenu = new ArrayList<>();

       // rootMenu.add(new Item("Logout"));
        rootMenu.add(new Item("Home"));
        rootMenu.add(new Item("Dashboard"));
        //rootMenu.add(new Item("New Registration"));
        rootMenu.add(new GroupItem("Member"));//profile
        rootMenu.add(new GroupItem("Documents"));
        rootMenu.add(new GroupItem("Upload KYC"));
        rootMenu.add(new GroupItem("My Team"));//Geneology
       // rootMenu.add(new GroupItem("E-pin"));
        rootMenu.add(new GroupItem("My Incentive"));// income
        rootMenu.add(new GroupItem("Shopping"));// income
        rootMenu.add(new GroupItem("Wallet"));
        rootMenu.add(new GroupItem("Support"));// complaint

        return rootMenu;
    }

    public static List<BaseItem> getSubItems(BaseItem baseItem) {

        List<BaseItem> result = new ArrayList<>();
        int level = ((GroupItem) baseItem).getLevel() + 1;
        String menuItem = baseItem.getName();

        if (!(baseItem instanceof GroupItem)) {
            throw new IllegalArgumentException("GroupItem required");
        }

        GroupItem groupItem = (GroupItem)baseItem;
        if(groupItem.getLevel() >= MAX_LEVELS){
            return null;
        }

        switch (level){
            case LEVEL_1 :
                switch (menuItem){
                    case "Member"://profile
                        result=getListProfile();
                        break;
                    case "Documents":
                        result=getListDocument();
                        break;
                    case "Upload KYC":
                        result=getListUploadKYC();
                        break;

                    case "My Team":
                        result=getListGenealogy();
                        break;

                    case "E-pin":
                        result= getListEpin();
                        break;
                    case "My Incentive":
                        result=getListIncome();//Income
                        break;
                    case "Shopping":
                        result=getListShopping();//My Shopping
                        break;
                    case "Wallet":
                        result=getListWallet();
                        break;
                    case "Support":
                        result=getListComplaint();
                        break;

                }
                break;

            case LEVEL_2 :
                switch (menuItem){
                    /*case "Wallet":
                        result=getListWallet();
                        break;
                    case "Income":
                        result=getListIncome();
                        break;
                    case "Epin":
                        result=getListEpin();
                        break;*/
                }
                break;
        }

        return result;
    }

    public static boolean isExpandable(BaseItem baseItem) {
        return baseItem instanceof GroupItem;
    }

    /*Shopping Sub menu item*/
    private static List<BaseItem> getListShopping(){
        List<BaseItem> shopList=new ArrayList<>();

        //shopList.add(new Item("My Purchase Detail"));
        shopList.add(new Item("ID Activation"));
        shopList.add(new Item("Downline Purchase"));

        return shopList;
    }

    /*Document Sub menu item*/
    private static List<BaseItem> getListDocument(){
        List<BaseItem> docList=new ArrayList<>();
        docList.add(new Item("Welcome Letter"));
       // docList.add(new Item("Invoice"));
        //docList.add(new Item("Rewards"));

        return docList;
    }

    /*Profile Sub menu item*/
    private static List<BaseItem> getListProfile(){
        List<BaseItem> profileList=new ArrayList<>();
       profileList.add(new Item("New Registration"));
        profileList.add(new Item("Profile"));
        //profileList.add(new Item("Upload KYC"));
        profileList.add(new Item("Change Password"));
        profileList.add(new Item("Change Transaction Password"));
        //profileList.add(new Item("Testimonial"));

        return profileList;
    }

    /*KYC Sub menu item*/
    private static List<BaseItem> getListUploadKYC(){
        List<BaseItem> uploadKyc=new ArrayList<>();
        //uploadKyc.add(new Item("Address Proof"));
        //uploadKyc.add(new Item("Bank Detail"));
        //uploadKyc.add(new Item("Pan Card Detail"));
        uploadKyc.add(new Item("Upload KYC"));
        return uploadKyc;
    }

    /*My Report Sub menu item*/
    private static List<BaseItem> getListMyReport(){
        List<BaseItem> uploadKyc=new ArrayList<>();
        uploadKyc.add(new Item("My Cadre"));
        uploadKyc.add(new Item("My Direct Cadre"));
        uploadKyc.add(new Item("Today Detail Group PV"));
        uploadKyc.add(new Item("Monthwise Group PV/BV"));
        //uploadKyc.add(new Item("Bank Detail"));
        //uploadKyc.add(new Item("Pan Card Detail"));
        return uploadKyc;
    }

    /*Wallet Sub menu Item*/
    private static List<BaseItem> getListWallet(){

        List<BaseItem> walletIncome=new ArrayList<>();

        //walletIncome.add(new Item("My Wallet"));//Wallet Balance
        //walletIncome.add(new Item("Fund Transfer"));//Wallet Balance
        walletIncome.add(new Item("Wallet Transaction Detail"));//Wallet Balance
       // walletIncome.add(new Item("Income Wallet"));//Wallet Balance
        //walletIncome.add(new Item("Main Wallet"));//Shopping wallet
        //walletIncome.add(new Item("Referral Point Wallet"));//Shopping wallet
        walletIncome.add(new Item("Wallet Request"));//Wallet Balance
        walletIncome.add(new Item("Wallet Request Detail"));//Wallet Balance

        //walletIncome.add(new Item("Main to Service Wallet Transfer"));//Wallet Balance
        //walletIncome.add(new Item("Wallet Transfer"));//Wallet Balance
        //walletIncome.add(new Item("Bank Withdrawal"));
        walletIncome.add(new Item("Bank Withdrawal Detail"));
        //walletIncome.add(new Item("Wallet Transfer"));

        return walletIncome;
    }

    /*Geneology/ My neteork Sub Menu item*/
    private static List<BaseItem> getListGenealogy(){
        List<BaseItem> networkList=new ArrayList<>();

        networkList.add(new Item("My Direct"));  //My direct
        //networkList.add(new Item("Generation View"));  //referral tree
        //networkList.add(new Item("Global Pool"));  //universal tree
        //networkList.add(new Item("ID Activation"));
       // networkList.add(new Item("Level Wise Count"));
        //networkList.add(new Item("My Direct"));  //Sponsor, referral tree
        networkList.add(new Item("Level Wise Direct"));
        networkList.add(new Item("Geneology"));//Geneology,memtree,binary
       networkList.add(new Item("Downline Detail"));
       //networkList.add(new Item("Upgrade Report"));
       networkList.add(new Item("My Business"));
       networkList.add(new Item("My Community Detail"));
      // networkList.add(new Item("Downline Rank Report"));

       return networkList;
    }

    /*Reward Sub menu Item*/
    private static List<BaseItem> getRewardList(){

        List<BaseItem> rewardList = new ArrayList<>();
        rewardList.add(new Item("Joining Reward"));
        rewardList.add(new Item("Repurchase Reward"));
        return rewardList;
    }

    /*E-Pin Sub Menu Item*/
    private static List<BaseItem> getListEpin(){
        List<BaseItem> networkList=new ArrayList<>();

        networkList.add(new Item("Pin-Detail"));
        networkList.add(new Item("Pin Transfer"));
        networkList.add(new Item("Pin Transfer Detail"));
        networkList.add(new Item("Pin Receive Detail"));
        networkList.add(new Item("Pin Generate"));

        return networkList;
    }

    /*Complaint  menu Item*/
    private static List<BaseItem> getListComplaint(){

        List<BaseItem> newComplaintList = new ArrayList<>();


        //newComplaintList.add(new Item("Complaint"));
        //newComplaintList.add(new Item("Complaint Detail"));
        newComplaintList.add(new Item("Raise Ticket"));
        newComplaintList.add(new Item("Ticket Status"));



        return newComplaintList;
    }

    /*Income Sub menu Item*/
    private static List<BaseItem> getListIncome(){

        List<BaseItem> eIncome=new ArrayList<>();

        eIncome.add(new Item("Daily Incentive"));//Daily Income
        eIncome.add(new Item("My Reward")); //Weekly Income/Binary Income
        eIncome.add(new Item("Monthly Incentive"));//Daily Income
        eIncome.add(new Item("Community Payout"));
        //eIncome.add(new Item("Weekly Incentive")); //Weekly Income/Binary Income

        //eIncome.add(new Item("Performance & MFA Detail")); //Weekly Income/Binary Income
        //eIncome.add(new Item("Monthly Reward Points")); //Weekly Income/Binary Income



        return eIncome;
    }

    /*Shopping Sub Menu Item*/
    private static List<BaseItem> getListMYshopping(){
        List<BaseItem> shoppingList = new ArrayList<>();
        shoppingList.add(new Item("Free Product Voucher"));
        shoppingList.add(new Item("Camp Coupon"));
        //shoppingList.add(new Item("Stock Report"));
        //shoppingList.add(new Item("Product Sale"));
        //shoppingList.add(new Item("Product Sale Detail"));
       // shoppingList.add(new Item("My Purchase"));

        return shoppingList;
    }

}
