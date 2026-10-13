package ua.daamky.utils.player;

public enum RotationPriority {
   f1,
   f2,
   f3;

   public static RotationPriority[] m403() {
      return values();
   }

   public static RotationPriority m404(String var0) {
      return Enum.valueOf(RotationPriority.class, var0);
   }
}
