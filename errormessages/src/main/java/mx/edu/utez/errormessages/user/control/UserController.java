package mx.edu.utez.errormessages.user.control;

import mx.edu.utez.errormessages.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/login")
    public ResponseEntity<Object> login (@Validated(UserDto.Login.class) @RequestBody UserDto dto){
        return service.login(dto);
    }
}
