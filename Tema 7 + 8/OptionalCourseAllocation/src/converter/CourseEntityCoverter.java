package converter;

import dao.CourseOperations;
import dao.LecturerOperations;
import entity.CourseEntity;
import entity.LecturerEntity;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass= CourseEntityCoverter.class, value = "courseEntityConverter")
public class CourseEntityCoverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String submittedValue) {

            if (submittedValue == null || submittedValue.isEmpty()) {
                return null;
            }

            try {

                return CourseOperations.findByName(submittedValue);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Lecturer Entity Name"), e);
            }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof CourseEntity) {
            return String.valueOf(((CourseEntity) modelValue).getName());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid Course Entity"));
        }
    }
}
