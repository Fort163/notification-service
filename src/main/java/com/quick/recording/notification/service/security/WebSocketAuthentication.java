package com.quick.recording.notification.service.security;

import com.quick.recording.resource.service.security.QROAuth2AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WebSocketAuthentication implements Authentication {

    private final Collection<? extends GrantedAuthority> authorities;
    private final Object details;
    private final Object principal;
    private Boolean authenticated;
    private final String name;

    public WebSocketAuthentication(QROAuth2AuthenticatedPrincipal authenticatedPrincipal){
        this.principal = authenticatedPrincipal;
        this.authorities = authenticatedPrincipal.getAuthorities();
        this.details = authenticatedPrincipal.getAttributes();
        this.authenticated = authenticatedPrincipal.getActive();
        this.name = authenticatedPrincipal.getName();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
