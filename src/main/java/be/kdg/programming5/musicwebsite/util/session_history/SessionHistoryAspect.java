package be.kdg.programming5.musicwebsite.util.session_history;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class SessionHistoryAspect {
    @Value("${session-history.attribute.name}")
    private String SESSION_HISTORY_ATTRIBUTE_NAME;
    private final HttpSession session;
    private final HttpServletRequest request;

    @Autowired
    public SessionHistoryAspect(HttpSession session, HttpServletRequest request) {
        this.session = session;
        this.request = request;
    }

    @Before("execution(* be.kdg.programming5.musicwebsite.controllers.*.*(..))")
    public void recordSessionHistory() {
        List<SessionHistory> sessionHistoryList = (List<SessionHistory>) session.getAttribute(SESSION_HISTORY_ATTRIBUTE_NAME);
        if(sessionHistoryList == null)
            sessionHistoryList = new ArrayList<>();

        String urlString = request.getRequestURL().toString();
        urlString = removeHostNameFromLink(urlString);
        SessionHistory sessionHistoryEntity = new SessionHistory(urlString, new Timestamp(System.currentTimeMillis()));
        sessionHistoryList.add(sessionHistoryEntity);

        session.setAttribute(SESSION_HISTORY_ATTRIBUTE_NAME, sessionHistoryList);
    }

    private String removeHostNameFromLink(String link) {
            return link.substring(link.indexOf("/", 8));
    }
}
