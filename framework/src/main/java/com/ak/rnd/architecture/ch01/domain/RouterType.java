package com.ak.rnd.architecture.ch01.domain;


/**
 * Value objects help us complement our code's expressiveness when there is no need to identify something uniquely, as
 * well as when we are more concerned about the object's attributes than its identity. We can use value objects to
 * compose an entity object, so we must make value objects immutable to avoid unforeseen inconsistencies throughout the
 * Domain. In the router example presented previously, we can represent the Type router as a value object attribute
 * from the Router entity
 */
public enum RouterType {
    EDGE,
    CORE
}
