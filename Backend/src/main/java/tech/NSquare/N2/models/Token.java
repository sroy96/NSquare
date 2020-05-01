package tech.NSquare.N2.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Token {
    @Id
    private String _id;
    private String authToken;
}
