package tech.NSquare.N2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.NSquare.N2.models.CodeExecutorPayLoadResponse;

public interface ExecutorRepository extends MongoRepository<CodeExecutorPayLoadResponse,String> {

    CodeExecutorPayLoadResponse findByKey(String key);
}
