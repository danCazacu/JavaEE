package listener;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class SessionCounter implements HttpSessionListener {
    private static int users = 0;
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        users ++;
    }
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        users --;
    }
    public static int getConcurrentUsers() {
        return users;
    }
}
