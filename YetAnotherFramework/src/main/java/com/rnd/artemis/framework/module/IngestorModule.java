package com.rnd.artemis.framework.module;

import com.rnd.artemis.framework.model.DataRow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("ingestorModule")
@RequiredArgsConstructor
public class IngestorModule extends AbstractBaseModule {

    @Override
    public void runModule(DataRow dataRow) {

    }
}
