package donggukthon.volunmate.security;

import donggukthon.volunmate.repository.UserRepository;
import donggukthon.volunmate.type.EUserType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter private final String socialId;
    @Getter private final EUserType type;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails create(UserRepository.UserSecurityForm form) {
        return CustomUserDetails.builder()
                .socialId(form.getId())
                .type(form.getType())
                .password(form.getPassword())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return socialId;
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
