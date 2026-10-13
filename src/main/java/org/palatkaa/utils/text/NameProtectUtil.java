package org.palatkaa.utils.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

/**
 * NameProtect feature removed — identity helpers so callers still compile.
 */
public final class NameProtectUtil {
   private NameProtectUtil() {}

   public static String protect(String text) {
      return text;
   }

   public static Component protect(Component component) {
      return component;
   }

   public static FormattedText protect(FormattedText text) {
      return text;
   }

   public static FormattedCharSequence protect(FormattedCharSequence sequence) {
      return sequence;
   }

   public static MutableComponent protectMutable(MutableComponent component) {
      return component;
   }
}
