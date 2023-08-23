//package com.team.gallexiv.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//
//import java.sql.Timestamp;
//
//@Getter
//@Entity
//@Table(name = "session", schema = "gallexiv")
//public class Session {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "sessionId")
//    private int sessionId;
//    @Basic
//    @Column(name = "clientSessionId")
//    private String clientSessionId;
//    @Basic
//    @Column(name = "sessionUserAgent")
//    private String sessionUserAgent;
//    @Basic
//    @Column(name = "sessionData")
//    private String sessionData;
//    @Basic
//    @Column(name = "expirationTime")
//    private Timestamp expirationTime;
//    @ManyToOne
//    @JoinColumn(name = "sessionUserId", referencedColumnName = "userId", nullable = false)
//    private Userinfo userinfoBySessionUserId;
//
//    public void setSessionId(int sessionId) {
//        this.sessionId = sessionId;
//    }
//
//    public void setClientSessionId(String clientSessionId) {
//        this.clientSessionId = clientSessionId;
//    }
//
//    public void setSessionUserAgent(String sessionUserAgent) {
//        this.sessionUserAgent = sessionUserAgent;
//    }
//
//    public void setSessionData(String sessionData) {
//        this.sessionData = sessionData;
//    }
//
//    public void setExpirationTime(Timestamp expirationTime) {
//        this.expirationTime = expirationTime;
//    }
//
//    public void setUserinfoBySessionUserId(Userinfo userinfoBySessionUserId) {
//        this.userinfoBySessionUserId = userinfoBySessionUserId;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Session session = (Session) o;
//        return sessionId == session.sessionId && Objects.equals(clientSessionId, session.clientSessionId) && Objects.equals(sessionUserAgent, session.sessionUserAgent) && Objects.equals(sessionData, session.sessionData) && Objects.equals(expirationTime, session.expirationTime);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(sessionId, clientSessionId, sessionUserAgent, sessionData, expirationTime);
//    }


//}
