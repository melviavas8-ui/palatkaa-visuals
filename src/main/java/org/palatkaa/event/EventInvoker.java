package org.palatkaa.event;

@FunctionalInterface
public interface EventInvoker {
   void invoke(Object var1, Event var2);
}
