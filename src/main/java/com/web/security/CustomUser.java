package com.web.security;

import com.web.domain.User;
import com.web.domain.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomUser extends org.springframework.security.core.userdetails.User {
    private static final String ROLE_PREFIX="ROLE_";
    private Long uNum;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String profilePhoto;
    private String thumbnailUrl;
    private String userRole;
    private User user;

    public CustomUser(User user){
        super(user.getUserId(),user.getUserPw(),makeGrantedAuthority(user.getRoles()));
        this.uNum=user.getUNum();
        this.userName=user.getUserName();
        this.userPhone=user.getUserPhone();
        this.userEmail=user.getUserEmail();
        this.profilePhoto=user.getProfilePhoto();
        this.thumbnailUrl=user.getThumbnailUrl();
        this.userRole=user.getRoles().toString();
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<UserRole> roles) {
        List<GrantedAuthority> list=new ArrayList<>();
        roles.forEach(role->list.add(new SimpleGrantedAuthority(ROLE_PREFIX+role.getRoleName())));
        return list;
    }
}
