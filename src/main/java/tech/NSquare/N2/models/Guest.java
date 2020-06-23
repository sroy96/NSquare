package tech.NSquare.N2.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Guest {
    private String name;
    private String email;
    private String contactNumber;
}
