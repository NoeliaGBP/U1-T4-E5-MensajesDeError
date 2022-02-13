package mx.edu.utez.errormessages.utils;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @NonNull
    private String text;
    @NonNull
    private String type;
    private Object result;
}
