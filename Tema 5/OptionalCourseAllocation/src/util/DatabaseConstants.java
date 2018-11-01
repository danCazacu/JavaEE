package util;

import java.nio.charset.StandardCharsets;

public final class DatabaseConstants {
    public static String URL = "jdbc:postgresql://localhost:5432/JavaEE";
    public static String USER = "postgres";
    public static String hex = "636f70696c6f7475";
    public static String PASSWORD = new String(hex.getBytes(), StandardCharsets.UTF_8);
}
