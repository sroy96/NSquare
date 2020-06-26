package tech.NSquare.N2.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Data
@Getter
@Setter
public class CodeExecutorPayLoadRequest implements Serializable {

    private String accessId;
    private String quesId;
    private String answer;
    private String language;
    
}
