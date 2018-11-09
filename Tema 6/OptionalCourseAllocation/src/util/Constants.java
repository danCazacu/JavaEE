package util;

import java.nio.charset.StandardCharsets;

public final class Constants {
    public static class Database {
        public static String URL1 = "jdbc:postgresql://localhost:5432/javaEE";
        public static String URL2 = "jdbc:postgresql://localhost:5432/JavaEE";
        public static String USER = "postgres";
        public static String hex = "copilotu";
        public static String PASSWORD = "andreea";
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
        public static class Routing{
            public static String EDIT = "editCompulsoryCourse";
            public static String VIEW = "viewCompulsoryCourses";
        }
        public static class SessionKeys{
            public static String EDIT_RECORD_KEY = "courseBean";
        }
    }
}
