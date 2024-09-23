package functional;

import functional.model.ApplicationDependency;
import functional.model.DependencyManager;

public class LambdaLazinessNonFunctionalMain {

    public static void main(String[] args) {
        ApplicationDependency appdep1 = new ApplicationDependency(1, "app-1");
        ApplicationDependency appdep2 = new ApplicationDependency(2, "app-2");

        DependencyManager dm = new DependencyManager();
        dm.processDependencies(appdep1);
        dm.processDependencies(appdep2);
    }
}
