package com.bluecine.Blue_Cinema.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.bluecine.Blue_Cinema.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class MainUser implements UserDetails {
        private String documentType;
        private long documentNumber;
        private String names;
        private String surnames;
        private String email;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        
        public MainUser(String documentType, long documentNumber, String names, String surnames, String email, String password, Collection<? extends GrantedAuthority> authorities) {
            this.documentType = documentType;
            this.documentNumber = documentNumber;
            this.names = names;
            this.surnames = surnames;
            this.email = email;
            this.password = password;
            this.authorities = authorities;
        }

        public static MainUser build(User user){
            List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
            return new MainUser(user.getDocumentType(), user.getDocumentNumber(), user.getNames(), user.getSurnames(), user.getEmail(), user.getPassword(), authorities);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities(){
            return authorities;
        }
        
        public String getDocumentType() {
            return documentType;
        }

        public long getDocumentNumber() {
            return documentNumber;
        }

        public String getNames() {
            return names;
        }

        public String getSurnames() {
            return surnames;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        
}
