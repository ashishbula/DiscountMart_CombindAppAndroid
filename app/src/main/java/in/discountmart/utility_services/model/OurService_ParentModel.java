package in.discountmart.utility_services.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OurService_ParentModel implements Serializable {
    private String title;
    private ArrayList<OurService_ChildModel> childList;

    public OurService_ParentModel() {}
    public OurService_ParentModel(String headerTitle, ArrayList<OurService_ChildModel> allItemsInSection) {
        this.title = headerTitle;
        this.childList = allItemsInSection;
    }

    public ArrayList<OurService_ChildModel> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<OurService_ChildModel> childList) {
        this.childList = childList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
