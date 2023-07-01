package in.discountmart.utility_services.model;

public class NavigationItemModel {

    public String menuName, url;
    public boolean hasChildren, isGroup;

    public NavigationItemModel(String menuName, boolean isGroup, boolean hasChildren) {

        this.menuName = menuName;
        //this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
