package tech.NSquare.N2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.NSquare.N2.models.Token;

public interface TokenRepository extends MongoRepository<Token,String> {

    Token findByAuthToken(String authToken);
}
