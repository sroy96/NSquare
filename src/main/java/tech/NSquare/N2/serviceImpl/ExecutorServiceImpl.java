package tech.NSquare.N2.serviceImpl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import tech.NSquare.N2.models.CodeExecutorPayLoadRequest;
import tech.NSquare.N2.models.CodeExecutorPayLoadResponse;
import tech.NSquare.N2.repository.ExecutorRepository;
import tech.NSquare.N2.service.ExecutorService;
import tech.NSquare.N2.util.NsquareException;
import java.util.Locale;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.*;


@Service
@Slf4j
public class ExecutorServiceImpl implements ExecutorService {

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    private ExecutorRepository executorRepository;


    @Override
    public CodeExecutorPayLoadResponse sendPayLoad(CodeExecutorPayLoadRequest codeExecutorPayLoadRequest) {
        try {
            queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload(codeExecutorPayLoadRequest).build());
            CodeExecutorPayLoadResponse codeExecutorPayLoadResponse = new CodeExecutorPayLoadResponse();
            codeExecutorPayLoadResponse.setQid(codeExecutorPayLoadRequest.getQuesId());
            codeExecutorPayLoadResponse.setKey(codeExecutorPayLoadRequest.getAccessId().toUpperCase(Locale.ENGLISH).substring(1,5).concat(codeExecutorPayLoadRequest.getQuesId()));
            codeExecutorPayLoadResponse.setUserToken(codeExecutorPayLoadRequest.getAccessId());
            try {
                executorRepository.save(codeExecutorPayLoadResponse);
            }
            catch (NsquareException ex){
                throw new NsquareException(DATABASE_INSERTION_ERROR.getErrorCode(), DATABASE_INSERTION_ERROR.getErrorMessage());
            }
            return codeExecutorPayLoadResponse;
        }
        catch(NsquareException ex){
            log.error("Payload not dispatch ERROR", ex.getMessage());
            throw new NsquareException(PAYLOAD_SQS_DISPATCH_FAILED.getErrorCode(), PAYLOAD_SQS_DISPATCH_FAILED.getErrorMessage());
        }
    }

    @Override
    public CodeExecutorPayLoadResponse dataBridge(String key) {
        if(null!=key){
            CodeExecutorPayLoadResponse execOutput = validateKey(key);
            return execOutput;
        }
        else{
            throw new NsquareException(UNKNOWN.getErrorCode(),UNKNOWN.getErrorMessage());
        }
    }

    private  CodeExecutorPayLoadResponse validateKey(String key) {
       try {
          return executorRepository.findByKey(key);
       }
       catch (NullPointerException ex){
           log.error("Execution Key ERROR");
           throw  new NsquareException(FAILED.getErrorCode(),FAILED.getErrorMessage());
       }
    }
}
