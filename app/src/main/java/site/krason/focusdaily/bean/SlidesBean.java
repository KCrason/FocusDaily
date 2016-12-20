package site.krason.focusdaily.bean;

import java.io.Serializable;

/**
 * @author Created by KCrason on 2016/12/20.
 * @email 535089696@qq.com
 */

public class SlidesBean implements Serializable {

    private String image;
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
