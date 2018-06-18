package org.luwenbin888.utils;

public class LazySingleton {
    /**
     * extracted from https://en.wikipedia.org/wiki/Initialization_on_demand_holder_idiom
     *
     * The implementation of the idiom relies on the initialization phase of execution within the Java Virtual Machine (JVM) as specified by the Java Language Specification (JLS).
     * When the class Something is loaded by the JVM, the class goes through initialization. Since the class does not have any static variables to initialize,
     * the initialization completes trivially. The static class definition LazyHolder within it is not initialized until the JVM determines that LazyHolder must be executed.
     * The static class LazyHolder is only executed when the static method getInstance is invoked on the class Something, and the first time this happens the JVM will load and
     * initialize the LazyHolder class. The initialization of the LazyHolder class results in static variable INSTANCE being initialized by
     * executing the (private) constructor for the outer class Something. Since the class initialization phase is guaranteed by the JLS to be sequential,
     * i.e., non-concurrent, no further synchronization is required in the static getInstance method during loading and initialization.
     * And since the initialization phase writes the static variable INSTANCE in a sequential operation, all subsequent concurrent invocations of
     * the getInstance will return the same correctly initialized INSTANCE without incurring any additional synchronization overhead.

     While the implementation is an efficient thread-safe "singleton" cache without synchronization overhead, and better performing than uncontended synchronization,
     the idiom can only be used when the construction of Something can be guaranteed to not fail. In most JVM implementations,
     if construction of Something fails, subsequent attempts to initialize it from the same class-loader will result in a NoClassDefFoundError failure.
     */
    public static class Something {
        private Something() {}

        private static class LazyHolder {
            static final Something INSTANCE = new Something();
        }

        public static Something getInstance() {
            return LazyHolder.INSTANCE;
        }

        public void printMessage() {
            System.out.println("Welcome to Something, using Initialization-on-demand-holder from https://en.wikipedia.org/wiki/Initialization_on_demand_holder_idiom");
        }
    }

    public static void main(String[] args) {
        Something something = Something.getInstance();
        something.printMessage();
    }
}
