package com.rnd.app.framework.ch01.domain;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * STEP1:
 * Entities help us build more expressive code. What characterizes an entity is its sense of continuity and identity,
 * as described by Domain-Driven Design: Tackling Complexity in the Heart of Software. That continuity is related to the
 * life cycle and mutable characteristics of the object. For example, in our network and topology inventory scenario, we
 * mentioned the existence of routers. For a router, we can define whether its state is enabled or disabled.
 * Also, we can assign some properties describing the relationship that a router has with different routers and other
 * network equipment. All those properties may change over time, so we can see that the router is not a static thing and
 * that its characteristics inside the problem domain can change. Because of that, we can state that the router has a
 * life cycle. Apart from that, every router should be unique in an inventory, so it must have an identity. So, this
 * sense of continuity and identity are the elements that determine an entity.
 * The following code shows a Router entity class composed of the RouterType and RouterId value objects
 */
public class Router {
    private final RouterType routerType;
    private final RouterId routerId;

    public Router(RouterType routerType, RouterId routerId) {
        this.routerType = routerType;
        this.routerId = routerId;
    }

    public static Predicate<Router> filterRouterByType(RouterType routerType) {
        return routerType.equals(RouterType.CORE) ? isCore() : isEdge();
    }

    private static Predicate<Router> isCore() {
        return p -> p.getRouterType() == RouterType.CORE;
    }

    private static Predicate<Router> isEdge() {
        return p -> p.getRouterType() == RouterType.EDGE;
    }

    public static List<Router> fetchRouter(List<Router> routers, Predicate<Router> predicate) {
        return routers.stream().filter(predicate).collect(Collectors.<Router>toList());
    }

    private RouterType getRouterType() {
        return routerType;
    }
}
