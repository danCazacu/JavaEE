package laborator1;

import java.io.PrintWriter;

public class Entry {
    
    private String key ;
    private String name;
    private String email;
    private String timestamp;
    
    public Entry(String key, String name, String email, String timestamp) {
        
        this.key = key;
        this.name = name;
        this.email = email;
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public void doPrintEntry(PrintWriter out){
    
        out.println("<pre>" + "   (entry) key:  " + this.key + " </pre>");
        out.println("<pre>" + "   (entry) Name:  " + this.name + " </pre>");
        out.println("<pre>" + "   (entry) Email:  " + this.email + " </pre>");
        out.println("<pre>" + "   (entry) TimeStamp:  " + this.timestamp + " </pre>");
    }
}
