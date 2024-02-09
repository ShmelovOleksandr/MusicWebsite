package be.kdg.programming5.musicwebsite.util.session_history;

import java.io.Serializable;
import java.sql.Timestamp;

public class SessionHistory implements Serializable {
    private String link;
    private Timestamp timestamp;

    public SessionHistory(String link, Timestamp timestamp) {
        this.link = link;
        this.timestamp = timestamp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
