package mx.edu.utez.errormessages.security.model;

import java.util.Set;

public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String nickname;
    private String name;
    private Long identKey;
    private Set<String> roles;

    public JwtDto(String token, String nickname, Set<String> roles) {
        this.token = token;
        this.nickname = nickname;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdentKey() {
        return identKey;
    }

    public void setIdentKey(Long identKey) {
        this.identKey = identKey;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
