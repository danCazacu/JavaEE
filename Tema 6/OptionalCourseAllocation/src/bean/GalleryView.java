package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
public class GalleryView {

    private List<String> images;

    @PostConstruct
    public void init() {

        FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        images = new ArrayList<String>();
        for (int i = 1; i < 6; i++) {
            images.add("imageCS" + i + ".jpg");
        }
    }

    public List<String> getImages() {
        return images;
    }
}