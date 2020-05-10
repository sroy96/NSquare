package tech.NSquare.N2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinUs {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String countryCode;
    private String phoneNumber;
}
