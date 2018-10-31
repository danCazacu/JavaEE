package tags;

import Util.PropertiesManager;
import internal.MovieDetails;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.TreeMap;

public class ResponseTagHandler extends SimpleTagSupport {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        PropertiesManager prop = PropertiesManager.getPropertiesManagerInstance();
        Integer id = null;
        id = prop.getMovieId(key);
        if(id!=null && id>0) {
            TreeMap<Integer, MovieDetails> allMovies = prop.getPropertiesAsMap();
            MovieDetails movie = allMovies.get(id);
            out.println("Database returned \"" +movie.getDescription() + "\" as description for movie title \""+movie.getName()+"\"");
        }else{
            out.println("No match found for movie title \""+key+"\"");
        }
    }
}
