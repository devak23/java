module core {
    requires faker;
    requires java.instrument;
    requires java.management;
    requires java.net.http;
    requires java.sql;
    requires jdk.httpserver;
    requires jdk.incubator.vector;
    requires static lombok;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires org.yaml.snakeyaml;
    exports statemachine.model;
}