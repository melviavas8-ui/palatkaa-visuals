package ua.daamky.utils;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.events.EventBus;
import ua.daamky.system.events.EventHandler;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MotionTaskQueue {
   private static final List<ScheduledMotionTask> f1 = new CopyOnWriteArrayList<>();

   public static void m108(ScheduledMotionTask var0) {
      f1.add(var0);
   }

   public static boolean m91() {
      return f1.isEmpty();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      for (ScheduledMotionTask var3 : f1) {
         var3.f1--;
         if (var3.f1 <= 0L) {
            if (var3.f2 != null) {
               var3.f2.run();
            }

            f1.remove(var3);
         }
      }
   }

   static {
      EventBus.register(new MotionTaskQueue());
   }
}
