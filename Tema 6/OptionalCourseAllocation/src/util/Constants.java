package util;

import java.nio.charset.StandardCharsets;

public final class Constants {
    public static class Database {
        public static String URL = "jdbc:postgresql://localhost:5432/JavaEE";
        public static String USER = "postgres";
        public static String PASSWORD = "copilotu";
    }

    public static class Lecturer{

        public static class Table {
            public static String COLUMN_NAME = "name";
            public static String COLUMN_EMAIL = "email";
            public static String COLUMN_GENDER = "gender";
            public static String COLUMN_URL = "url";
        }
        public static class SessionKeys{
            public static String EDIT_RECORD_KEY = "lecturerBean";
        }
        public static class Routing{
            public static String EDIT = "editLecturer";
            public static String VIEW = "viewLecturers";
        }
    }

    public static class Course{

        public static class Table{

            public static String COLUMN_ID = "id";
            public static String COLUMN_CODE = "code";
            public static String COLUMN_SHORT_NAME = "short_name";
            public static String COLUMN_NAME = "name";
            public static String COLUMN_YEAR = "year";
            public static String COLUMN_SEMESTER = "semester";
            public static String COLUMN_CREDITS = "credits";
            public static String COLUMN_URL = "url";
            public static String COLUMN_FK_LECTURER_NAME = "lecturer_name";
            public static String COLUMN_FK_OPTIONAL_PACKAGES_CODE = "optpackages_code";

        }

        public static class RoutingCompulsory{
            public static String EDIT = "editCompulsoryCourse";
            public static String VIEW = "viewCompulsoryCourses";
        }

        public static class RoutingOptional{
            public static String EDIT = "editOptionalCourse";
            public static String VIEW = "viewOptionalCourses";
        }

        public static class SessionKeysCompulsory{
            public static String EDIT_RECORD_KEY = "courseBean";
        }

        public static class SessionKeysOptional{
            public static String EDIT_RECORD_KEY = "optionalCourseBean";
        }
    }

    public static class OptionalPackage{

        public static class Table{

            public static String COLUMN_CODE = "code";
            public static String COLUMN_YEAR = "year";
            public static String COLUMN_SEMESTER = "semester";
            public static String COLUMN_DISCIPLINE_NUMBER = "disciplineno";
        }

        public static class SessionKeys{

            public static String EDIT_RECORD_KEY = "optionalPackageBean";
        }

        public static class Routing{

            public static String EDIT = "editOptionalPackage";
            public static String VIEW = "viewOptionalPackages";
        }

    }
}
