package mx.edu.utez.errormessages.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    @NotNull(groups = {Modify.class, UpdatePassword.class})
    private Long id;
    @NotBlank(groups = {Register.class, Modify.class})
    private String name;
    @NotBlank(groups = {Register.class, Modify.class})
    private String lastname;
    private String secondLastname;
    private Date birthday;
    @NotBlank(groups = Login.class)
    private String username;
    @NotBlank(groups = {Login.class, UpdatePassword.class})
    private String password;
    @NotBlank(groups = UpdatePassword.class)
    private String newPassword;

    public interface Login {
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface UpdatePassword {
    }
}
