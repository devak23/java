package com.ak.rnd.architecture.ch01.application.ports.input;

import com.ak.rnd.architecture.ch01.application.ports.output.RouterViewOuputPort;
import com.ak.rnd.architecture.ch01.application.usecases.RouterViewUseCase;
import com.ak.rnd.architecture.ch01.domain.Router;

import java.util.List;
import java.util.function.Predicate;

/**
 * If use cases are just interfaces describing what the software does, we still need to implement the use case interface.
 * That's the role of the input port. By being a component that's directly attached to use cases, at the Application
 * level, input ports allow us to implement software intent on domain terms. Here is an input port providing an
 * implementation that fulfills the software intent stated in the use case:
 */

public class RouterViewInputPort implements RouterViewUseCase {
    private final RouterViewOuputPort routerListOutputPort;

    public RouterViewInputPort(RouterViewOuputPort ouputPort) {
        this.routerListOutputPort = ouputPort;
    }

    @Override
    public List<Router> getRouters(Predicate<Router> filter) {
        var routers = routerListOutputPort.fetchRouters();
        return Router.fetchRouter(routers, filter);
    }
}
