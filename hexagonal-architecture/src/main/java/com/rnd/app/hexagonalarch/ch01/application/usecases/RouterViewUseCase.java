package com.rnd.app.hexagonalarch.ch01.application.usecases;


import com.rnd.app.hexagonalarch.ch01.domain.Router;

import java.util.List;
import java.util.function.Predicate;

/**
 * STEP 2:
 * Use cases represent the system's behavior through application-specific operations, which exist within the software
 * realm to support the domain's constraints. Use cases may interact directly with entities and other use cases, making
 * them flexible components. In Java, we represent use cases as abstractions defined by interfaces expressing what the
 * software can do. The following code shows a use case that provides an operation to get a filtered list of routers:
 */
public interface RouterViewUseCase {
    List<Router> getRouters(Predicate<Router> predicate);
}

/*
 * Note the Predicate filter. We're going to use it to filter the router list when implementing that use case with an
 * input port.
 */