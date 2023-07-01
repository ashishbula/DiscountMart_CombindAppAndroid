package in.discountmart.utility_services.model;

import java.io.Serializable;

public class NameListModel implements Serializable {
    String name;
    boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
