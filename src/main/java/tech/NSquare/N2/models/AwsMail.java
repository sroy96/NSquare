package tech.NSquare.N2.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

/**
 * AWS SES Mail configuration
 */
@Getter
@Setter
@Data
public class AwsMail {
    private String from;
    private String to;
    private String subject;
    private Map<String, Object> model;
}
