package tech.NSquare.N2.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseHeader {

    private LocalDateTime localDateTime;
    private Transactional transactional;
}
