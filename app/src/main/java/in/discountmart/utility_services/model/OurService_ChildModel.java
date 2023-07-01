package in.discountmart.utility_services.model;

import java.io.Serializable;

public class OurService_ChildModel implements Serializable {

    private String name;
    private int image;

    public OurService_ChildModel(String name, int url) {
        this.name = name;
        this.image = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
