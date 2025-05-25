module core {
    requires faker;
    requires java.instrument;
    requires java.net.http;
    requires jdk.httpserver;
    requires jdk.incubator.vector;
    requires static lombok;
    requires org.slf4j;
    requires org.yaml.snakeyaml;
    requires org.apache.commons.lang3;
    requires org.checkerframework.checker.qual;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.assertj.core;
//    exports statemachine.model;
    exports functional.model;
    exports functional.spec;
}