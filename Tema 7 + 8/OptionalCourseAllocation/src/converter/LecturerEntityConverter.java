package converter;

import dao.LecturerOperations;
import entity.LecturerEntity;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass= LecturerEntityConverter.class, value = "lecturerEntityConverter")
public class LecturerEntityConverter implements Converter {



    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String submittedValue) {

            if (submittedValue == null || submittedValue.isEmpty()) {
                return null;
            }

            try {

                return LecturerOperations.findByName(submittedValue);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Lecturer Entity Name"), e);
            }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof LecturerEntity) {
            return String.valueOf(((LecturerEntity) modelValue).getName());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid Lecturer Entity"));
        }
    }
}
