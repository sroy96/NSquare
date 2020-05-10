package tech.NSquare.N2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tech.NSquare.N2.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmailAddress(String emailAddress);

    User findBy_id(String user_id);
}
