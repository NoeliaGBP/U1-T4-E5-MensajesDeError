package mx.edu.utez.errormessages.security.service;

import mx.edu.utez.errormessages.security.model.MyUserDetails;
import mx.edu.utez.errormessages.user.control.UserService;
import mx.edu.utez.errormessages.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findFirstByUsername(s).get();
        return MyUserDetails.build(user);
    }
}
