package tech.NSquare.N2.service;

import tech.NSquare.N2.models.CodeExecutorPayLoadRequest;
import tech.NSquare.N2.models.CodeExecutorPayLoadResponse;

public interface ExecutorService {

    CodeExecutorPayLoadResponse sendPayLoad(CodeExecutorPayLoadRequest codeExecutorPayLoadRequest);

    CodeExecutorPayLoadResponse dataBridge(String key);
}
