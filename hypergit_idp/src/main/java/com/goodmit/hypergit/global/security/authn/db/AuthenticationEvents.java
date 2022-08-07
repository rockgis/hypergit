package com.goodmit.hypergit.global.security.authn.db;

import com.goodmit.hypergit.repository.LoginAuditRepo;
import com.goodmit.hypergit.repository.entity.LoginAudit;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Slf4j
public class AuthenticationEvents {

    private LoginAuditRepo loginAuditRepo;

    @Builder
    protected AuthenticationEvents(LoginAuditRepo loginAuditRepo) {
        this.loginAuditRepo = loginAuditRepo;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) success.getSource();
        loginAuditRepo.save(createLoginAudit(token, success.getTimestamp(), null));
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) failures.getSource();
        loginAuditRepo.save(createLoginAudit(token, failures.getTimestamp(), failures.getException().getMessage()));
    }


    private LoginAudit createLoginAudit(UsernamePasswordAuthenticationToken token,long timestamp,String failMessage) {
        String remote = ((WebAuthenticationDetails)token.getDetails()).getRemoteAddress();

        return LoginAudit.builder()
                .name(token.getName())
                .result(token.isAuthenticated())
                .remote(remote)
                .reason(failMessage)
                .time(new LocalDateTime(timestamp))
                .build();
    }

    @EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {

        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
        log.info("Principal " + auditEvent.getPrincipal() + " - " + auditEvent.getType());
        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");
        log.info("Remote IP address: " + details.getRemoteAddress());
        log.info("  Session Id: " + details.getSessionId());
    }
}
