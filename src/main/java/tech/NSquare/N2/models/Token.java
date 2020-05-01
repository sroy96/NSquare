package tech.NSquare.N2.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document (value = "userTokens")
public class Token {
    @Id
    private String _id;
    private String authToken;
}
