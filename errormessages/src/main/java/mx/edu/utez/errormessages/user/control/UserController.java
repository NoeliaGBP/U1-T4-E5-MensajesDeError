package mx.edu.utez.errormessages.user.control;

import mx.edu.utez.errormessages.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("")
    public ResponseEntity<Object> findAll() {
        return service.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Object> save(@Validated(UserDto.Register.class) @RequestBody UserDto dto)  {
        return service.save(dto);
    }

    @PutMapping("")
    public ResponseEntity<Object> update(@Validated(UserDto.Modify.class) @RequestBody UserDto dto)  {
        return service.update(dto);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Object> updatePassword(@Validated(UserDto.UpdatePassword.class) @RequestBody UserDto dto)  {
        return service.updatePassword(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStatus(@PathVariable("id") Long id)  {
        return service.updateStatus(id);
    }
}
