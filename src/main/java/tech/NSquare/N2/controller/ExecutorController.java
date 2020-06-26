package tech.NSquare.N2.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.NSquare.N2.constants.URLConstants;
import tech.NSquare.N2.models.CodeExecutorPayLoadRequest;
import tech.NSquare.N2.models.CodeExecutorPayLoadResponse;
import tech.NSquare.N2.serviceImpl.ExecutorServiceImpl;
import tech.NSquare.N2.util.NsquareException;

@Slf4j
@RestController
@RequestMapping(URLConstants.BASE_URL)
public class ExecutorController {

    @Autowired
    private ExecutorServiceImpl executorService;

    @PostMapping(URLConstants.SEND_PAYLOAD)
    public ResponseEntity<CodeExecutorPayLoadResponse>submitCode(@RequestBody CodeExecutorPayLoadRequest codeExecutorPayLoadRequest){
        CodeExecutorPayLoadResponse codeExecutorPayLoadResponse=null;
       try {
           codeExecutorPayLoadResponse = executorService.sendPayLoad(codeExecutorPayLoadRequest);
           return ResponseEntity.status(HttpStatus.OK).body(codeExecutorPayLoadResponse);
       }
       catch (NsquareException ex){
           log.error("Execution Controller Dispatch ERROR");
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
       }

    }



    @GetMapping(URLConstants.GET_PAYLOAD)
    public ResponseEntity<CodeExecutorPayLoadResponse>getOutput(@RequestParam String key){
        CodeExecutorPayLoadResponse codeExecutorPayLoadResponse = null;
        try{
            codeExecutorPayLoadResponse =  executorService.dataBridge(key);
            return ResponseEntity.status(HttpStatus.OK).body(codeExecutorPayLoadResponse);
        }
        catch(NsquareException ex){
            log.error("Execution Controller Dispatch ERROR");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

}
