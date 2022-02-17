package mx.edu.utez.errormessages.security.controller;
import mx.edu.utez.errormessages.user.control.UserService;
import mx.edu.utez.errormessages.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"*"},methods = {RequestMethod.POST})
public class AuthenticationController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated(UserDto.Login.class) @RequestBody UserDto dto)  {
        return userService.login(dto);
    }
}
