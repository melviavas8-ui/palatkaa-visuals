package ua.daamky.utils.player;

public enum RotationMode {
   f1,
   f2,
   f3;

   public static RotationMode[] m399() {
      return values();
   }

   public static RotationMode m400(String var0) {
      return Enum.valueOf(RotationMode.class, var0);
   }
}
