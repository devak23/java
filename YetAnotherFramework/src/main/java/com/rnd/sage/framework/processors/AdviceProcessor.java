package com.rnd.sage.framework.processors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Qualifier("adviceProcessor")
@Service
public class AdviceProcessor extends BaseProcessor {
}
