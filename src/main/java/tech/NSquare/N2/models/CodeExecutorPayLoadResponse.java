package tech.NSquare.N2.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Data
@Document
@Getter
@Setter
public class CodeExecutorPayLoadResponse implements Serializable {
    @Id
    private String key;
    private String userToken;
    private String qid;
    private Map<String,Boolean>testCases;
    private Map<String,String>errors;

}
