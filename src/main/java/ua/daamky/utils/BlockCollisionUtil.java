package ua.daamky.utils;

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public final class BlockCollisionUtil {
   private static final MinecraftClient f1 = MinecraftClient.getInstance();

   private BlockCollisionUtil() {
   }

   public static boolean m95(Block var0) {
      return f1.player != null && f1.world != null ? m96(f1.player.getBoundingBox().contract(0.001), var0) : false;
   }

   public static boolean m96(Box var0, Block var1) {
      return m97(var0, var1x -> f1.world.getBlockState(var1x).isOf(var1));
   }

   public static boolean m97(Box var0, Predicate<BlockPos> var1) {
      return m98(var0).anyMatch(var1);
   }

   private static Stream<BlockPos> m98(Box var0) {
      int var1 = (int)Math.floor(var0.minX);
      int var2 = (int)Math.floor(var0.minY);
      int var3 = (int)Math.floor(var0.minZ);
      int var4 = (int)Math.floor(var0.maxX);
      int var5 = (int)Math.floor(var0.maxY);
      int var6 = (int)Math.floor(var0.maxZ);
      Iterable var7 = () -> BlockPos.iterate(var1, var2, var3, var4, var5, var6).iterator();
      return StreamSupport.stream(var7.spliterator(), false);
   }
}
