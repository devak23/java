package functional;

import functional.model.ApplicationDependency;
import functional.model.DependencyManager;

public class LambdaLazinessNonFunctionalMain {

    public static void main(String[] args) {
        ApplicationDependency appDep1 = new ApplicationDependency(1, "app-1");
        ApplicationDependency appDep2 = new ApplicationDependency(2, "app-2");

        DependencyManager dm = new DependencyManager();
        dm.processDependencies(appDep1);
        dm.processDependencies(appDep2);
    }
}
