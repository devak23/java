package com.rnd.app.strategyaslist;


import com.rnd.app.strategyaslist.model.OutputType;
import com.rnd.app.strategyaslist.service.DocumentServiceListServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DocumentServiceListServiceImplTest {
    @Autowired
    private DocumentServiceListServiceImpl impl;

    @Test
    public void givenPDF_whenInvokedCreateDocument_thenProducesPDFDoc() {
        assertThat(impl.create(OutputType.PDF)).isEqualTo("PDF document is created");
    }

    @Test
    public void givenExcel_whenInvokedCreateDocument_thenProducesExcelDoc() {
        assertThat(impl.create(OutputType.EXCEL)).isEqualTo("Excel Document is created");
    }

}