package tech.NSquare.N2.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.NSquare.N2.models.enums.Course;
import tech.NSquare.N2.models.enums.ServiceLevel;
import java.util.Map;


@Getter
@Setter
@Data
@Document (value = "UserDetails")
public class User {

    @Id
    private String _id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String countryCode;
    private String phoneNumber;
    private boolean active;
    private Map<Course, Boolean> courseEnrolled;
    private Map<ServiceLevel, Boolean> authorized;


    public void User() {
        this._id = RandomStringUtils.random(8, true, true);
        this.active=true;
    }


}
