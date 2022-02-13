package mx.edu.utez.errormessages.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    @NotNull(groups = Modify.class)
    private Long id;
    @NotBlank(groups = Register.class)
    private String name;
    @NotBlank(groups = Register.class)
    private String lastname;
    private String secondLastname;
    private Date birthday;
    @NotBlank(groups = Login.class)
    private String email;
    @NotBlank(groups = Login.class)
    private String password;

    public interface Login {
    }

    public interface Register {
    }

    public interface Modify {
    }
}
