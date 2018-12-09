package converter;

import dao.OptionalCourseOperations;
import dao.OptionalPackageOperations;
import entity.OptionalCourseEntity;
import entity.OptionalPackageEntity;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=OptionalPackageEntity.class)
public class OptionalPackageEntityConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String submittedValue) {

            if (submittedValue == null || submittedValue.isEmpty()) {
                return null;
            }

            try {

                return OptionalPackageOperations.findByCode(submittedValue);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(submittedValue + " is not a valid OptionalPackage ID"), e);
            }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof OptionalPackageEntity) {
            return String.valueOf(((OptionalPackageEntity) modelValue).getCode());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid OptionalPackageEntity"));
        }
    }
}
