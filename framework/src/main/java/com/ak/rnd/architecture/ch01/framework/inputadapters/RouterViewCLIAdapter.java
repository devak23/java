package com.ak.rnd.architecture.ch01.framework.inputadapters;

import com.ak.rnd.architecture.ch01.application.ports.input.RouterViewInputPort;
import com.ak.rnd.architecture.ch01.application.usecases.RouterViewUseCase;
import com.ak.rnd.architecture.ch01.domain.Router;
import com.ak.rnd.architecture.ch01.domain.RouterType;
import com.ak.rnd.architecture.ch01.framework.outputadapters.RouterViewFileAdapter;

import java.util.List;

/**
 * This class illustrates the creation of an input adapter that gets data from the STDIN. Note the use of the input port
 * through its use case interface. Here, we passed the command that encapsulates input data that's used on the
 * Application hexagon to deal with the Domain hexagon's constraints. If we want to enable other communication forms in
 * our system, such as REST, we just have to create a new REST adapter containing the dependencies to expose a REST
 * communication's endpoint
 */
public class RouterViewCLIAdapter {
    private RouterViewUseCase routerViewUseCase;

    public List<Router> obtainRelatedRouters(String type) {
        return routerViewUseCase.getRouters(Router.filterRouterByType(RouterType.valueOf(type)));
    }

    public RouterViewCLIAdapter() {
        this.routerViewUseCase = new RouterViewInputPort(RouterViewFileAdapter.getInstance());
    }
}