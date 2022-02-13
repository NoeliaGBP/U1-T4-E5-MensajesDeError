package mx.edu.utez.errormessages.user.control;

import mx.edu.utez.errormessages.user.model.User;
import mx.edu.utez.errormessages.user.model.UserDto;
import mx.edu.utez.errormessages.user.model.UserRepository;
import mx.edu.utez.errormessages.utils.Message;
import mx.edu.utez.errormessages.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordValidator passwordValidator;


    @Transactional(readOnly = true)
    public ResponseEntity login(UserDto dto) {
        Optional<User> optionalUser = repository.findFirstByEmail(dto.getEmail());
        if (!optionalUser.isPresent()) {
            return new ResponseEntity(new Message("Credenciales incorrectas", "warning"), HttpStatus.NOT_FOUND);
        }
        Optional<User> optUser = repository.findFirstByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (!optUser.isPresent()) {
            return new ResponseEntity(new Message("Datos incorrectos", "error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Message("Credenciales correctas", "success", optUser.get()), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity register(UserDto dto) {
        Optional<User> optionalUser = repository.findFirstByEmail(dto.getEmail());
        if (optionalUser.isPresent()) {
            return new ResponseEntity(new Message("El correo electrónico ingresado ya existe", "warning"), HttpStatus.BAD_REQUEST);
        }
        if (!passwordValidator.isValid(dto.getPassword())){
            return new ResponseEntity(new Message("La contraseña no cumple con las características de contraseña segura", "error"), HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.asignValuesRegister(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = repository.saveAndFlush(user);
        if(user==null){
            return new ResponseEntity(new Message("No se registró el usuario", "error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Message("Usuario registrado", "success", user), HttpStatus.OK);
    }
}
