package functional.model;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DependencyManager {

    private Map<Long,String> apps = new HashMap<>();

    public void processDependencies(ApplicationDependency app) {
        log.info("Processing app: {}" , app.getName());
        log.info("Dependencies: {}" , app.getDependencies());

        apps.put(app.getId(),app.getDependencies());
    }
}