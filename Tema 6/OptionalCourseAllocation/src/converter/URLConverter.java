package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.net.URL;

@FacesConverter(value="urlConverter")
public class URLConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String newValue) {
        try{

           /* if(!newValue.startsWith("http://")){

                newValue = "http://" + newValue;
            }
*/
            return new URL(newValue);
        }catch(Exception e){

            FacesMessage msg =
                    new FacesMessage("URL Conversion error.",
                            "Invalid URL format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ConverterException( msg);

        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        return String.valueOf(value);
    }
}
