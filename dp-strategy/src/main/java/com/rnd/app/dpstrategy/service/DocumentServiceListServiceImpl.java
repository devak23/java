package com.rnd.app.dpstrategy.service;

import com.rnd.app.dpstrategy.config.DocumentConfig;
import com.rnd.app.dpstrategy.model.OutputType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceListServiceImpl implements DocumentService {
    private final DocumentConfig config;

    @Override
    public String create(OutputType outputType) {
        return config.getCreationStrategies().stream()
                .filter(e -> e.getOutputType().equals(outputType))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new)
                .createDocument();
    }
}
