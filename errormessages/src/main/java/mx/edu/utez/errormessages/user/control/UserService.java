package mx.edu.utez.errormessages.user.control;

import mx.edu.utez.errormessages.user.model.User;
import mx.edu.utez.errormessages.user.model.UserDto;
import mx.edu.utez.errormessages.user.model.UserRepository;
import mx.edu.utez.errormessages.utils.Message;
import mx.edu.utez.errormessages.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordValidator passwordValidator;

    @Autowired
    AuthenticationManager authenticationManager;

    public Optional<User> findFirstByUsername(String username) {
        return repository.findFirstByUsername(username);
    }

    @Transactional(readOnly = true)
    public ResponseEntity findAll() {
        return new ResponseEntity(new Message("Usuarios registrados", "success", repository.findAll()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity login(UserDto dto) {
        try {
            Optional<User> optionalUser = repository.findFirstByUsername(dto.getUsername());
            if (!optionalUser.isPresent()) {
                return new ResponseEntity(new Message("Credenciales incorrectas", "warning"), HttpStatus.NOT_FOUND);
            }
            if (!optionalUser.get().isStatus()) {
                return new ResponseEntity(new Message("Usuario inactivo", "warning"), HttpStatus.BAD_REQUEST);
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            return new ResponseEntity(new Message("Credenciales correctas", "success", optionalUser.get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Error de credenciales", "error"), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity save(UserDto dto) {
        if (!passwordValidator.isValid(dto.getPassword())) {
            return new ResponseEntity(new Message("La contraseña no cumple con las características de contraseña segura", "error"), HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = repository.findFirstByUsername(dto.getUsername());
        if (optionalUser.isPresent()) {
            return new ResponseEntity(new Message("El correo ingresado ya existe", "warning"), HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.asignValuesRegister(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = repository.saveAndFlush(user);
        if (user == null) {
            return new ResponseEntity(new Message("No se registró el usuario", "error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Message("Usuario registrado", "success", user), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity update(UserDto dto) {
        Optional<User> optionalUser = repository.findById(dto.getId());
        if (!optionalUser.isPresent()) {
            return new ResponseEntity(new Message("Usuario no encontrado", "warning"), HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        user.asignValuesModify(dto);
        user = repository.saveAndFlush(user);
        if (user == null) {
            return new ResponseEntity(new Message("No se modificó el usuario", "error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Message("Usuario modificado", "success", user), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity updatePassword(UserDto dto) {
        try {
            if (!passwordValidator.isValid(dto.getNewPassword())) {
                return new ResponseEntity(new Message("La contraseña no cumple con las características de contraseña segura", "error"), HttpStatus.BAD_REQUEST);
            }
            Optional<User> optionalUser = repository.findById(dto.getId());
            if (!optionalUser.isPresent()) {
                return new ResponseEntity(new Message("Usuario no encontrado", "warning"), HttpStatus.NOT_FOUND);
            }
            if (!optionalUser.get().isStatus()) {
                return new ResponseEntity(new Message("Usuario inactivo", "warning"), HttpStatus.BAD_REQUEST);
            }
            User user = optionalUser.get();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), dto.getPassword()));
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            user = repository.saveAndFlush(user);
            if (user == null) {
                return new ResponseEntity(new Message("No se modificó la contraseña", "error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(new Message("Contraseña modificada", "success", user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Error de credenciales", "error"), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity updateStatus(Long id) {
        Optional<User> optUser = repository.findById(id);
        if (!optUser.isPresent()) {
            return new ResponseEntity(new Message("Usuario no encontrado", "error"), HttpStatus.NOT_FOUND);
        }
        User user = optUser.get();
        user.setStatus(!user.isStatus());
        user = repository.saveAndFlush(user);
        if (user == null) {
            return new ResponseEntity(new Message("Estado no modificado", "error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Message("Estado modificado", "success", user), HttpStatus.OK);
    }
}
