package ua.daamky.settings;


public class BindSetting extends Setting {
   private String f1;
   private boolean f2;
   private final int f3;

   public BindSetting(String var1, String var2) {
      this(var1, var2, 96);
   }

   public BindSetting(String var1, String var2, int var3) {
      super(var1);
      this.f3 = Math.max(1, var3);
      this.m16(var2);
   }

   public void m16(String var1) {
      String var2 = var1 == null ? "" : var1.replace('\n', ' ').replace('\r', ' ');
      this.f1 = var2.length() > this.f3 ? var2.substring(0, this.f3) : var2;
   }

   public void m4(boolean var1) {
      this.f2 = var1;
   }

   public void m28(char var1) {
      if (this.f1.length() < this.f3 && !Character.isISOControl(var1)) {
         this.m16(this.f1 + var1);
      }
   }

   public void m21(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         for (int var2 = 0; var2 < var1.length(); var2++) {
            this.m28(var1.charAt(var2));
         }
      }
   }

   public void m29() {
      if (!this.f1.isEmpty()) {
         this.f1 = this.f1.substring(0, this.f1.length() - 1);
      }
   }
   public String m30() {
      return this.f1;
   }
   public boolean m31() {
      return this.f2;
   }
   public int m32() {
      return this.f3;
   }
}
