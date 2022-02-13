package mx.edu.utez.errormessages.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    private String secondLastname;
    private Date birthday;
    @Column(nullable = false, unique = true)
    private String username;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    private boolean status;

    public void asignValuesRegister(UserDto dto){
        this.name = dto.getName();
        this.lastname = dto.getLastname();
        this.secondLastname = dto.getSecondLastname();
        this.birthday = dto.getBirthday();
        this.username = dto.getUsername();
        this.status = true;
    }

    public void asignValuesModify(UserDto dto){
        this.name = dto.getName();
        this.lastname = dto.getLastname();
        this.secondLastname = dto.getSecondLastname();
        this.birthday = dto.getBirthday();
    }
}
