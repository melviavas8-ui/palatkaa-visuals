package ua.daamky.system.events;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
   private static final Map<Class<? extends CancellableEvent>, List<EventBus$1>> f1 = new ConcurrentHashMap<>();
   private static final Set<Object> f2 = Collections.newSetFromMap(new ConcurrentHashMap<>());

   public static void register(Object var0) {
      if (!f2.contains(var0)) {
         f2.add(var0);

         for (Method var4 : var0.getClass().getDeclaredMethods()) {
            if (var4.isAnnotationPresent(EventHandler.class) && var4.getParameterCount() == 1) {
               Class var5 = var4.getParameterTypes()[0];
               if (CancellableEvent.class.isAssignableFrom(var5)) {
                  EventHandler var7 = var4.getAnnotation(EventHandler.class);
                  EventPriority var8 = var7.I0();
                  var4.setAccessible(true);
                  f1.computeIfAbsent(var5, var0x -> new CopyOnWriteArrayList<>()).add(new EventBus$1(var0, var4, var8));
                  f1.get(var5).sort(Comparator.comparingInt(var0x -> var0x.f3.getLevel()));
               }
            }
         }
      }
   }

   public static void unregister(Object var0) {
      if (f2.contains(var0)) {
         f2.remove(var0);

         for (List<EventBus$1> var2 : f1.values()) {
            var2.removeIf(var1 -> var1.f1 == var0);
         }
      }
   }

   public static <T extends CancellableEvent> T post(T var0) {
      List<EventBus$1> var1 = f1.get(var0.getClass());
      if (var1 != null && !var1.isEmpty()) {
         for (EventBus$1 var3 : var1) {
            if (!var0.isCancelled() || var3.f3.getLevel() >= EventPriority.HIGH.getLevel()) {
               try {
                  var3.f2.invoke(var3.f1, var0);
               } catch (Exception var5) {
                  var5.printStackTrace();
               }
            }
         }

         return (T)var0;
      } else {
         return (T)var0;
      }
   }

   public static boolean isRegistered(Object var0) {
      return f2.contains(var0);
   }

   public static void clear() {
      f1.clear();
      f2.clear();
   }

   public static int m1462(Class<? extends CancellableEvent> var0) {
      List var1 = f1.get(var0);
      return var1 == null ? 0 : var1.size();
   }
}
