package util;

import java.util.ArrayList;
import java.util.List;

public class FormUtils {
    public List<String> completeYearOfStudy(String query) {
        List<String> results = new ArrayList<String>();
        for (int i = 1; i < 4; i++) {
            results.add(query + i);
        }

        return results;
    }

    public List<String> completeSemester(String query) {
        List<String> results = new ArrayList<String>();
        for (int i = 1; i < 7; i++) {
            results.add(query + i);
        }

        return results;
    }

    public List<String> completeGenders(String query) {
        query = "";
        ArrayList<String> genders = new ArrayList<String>();
        genders.add(query + "Male");
        genders.add(query + "Female");

        return genders;
    }
}
