package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.User;
import ua.daamky.utils.JsonObjectWrapper;
import ua.daamky.utils.JsonParseException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class WindowsPipe extends Pipe {
   private RandomAccessFile file;

   WindowsPipe(IPCClient var1, HashMap<String, Callback> var2, String var3) {
      super(var1, var2);

      try {
         this.file = new RandomAccessFile(var3, "rw");
      } catch (FileNotFoundException var5) {
      }
   }

   @Override
   public void write(byte[] var1) throws IOException {
      this.file.write(var1);
   }

   @Override
   public Packet read() throws IOException, JsonParseException {
      while (this.file.length() == 0L && this.status == PipeStatus.CONNECTED) {
         try {
            Thread.sleep(50L);
         } catch (InterruptedException var8) {
         }
      }

      if (this.status == PipeStatus.DISCONNECTED) {
         throw new IOException("Disconnected!");
      } else if (this.status == PipeStatus.CLOSED) {
         return new Packet(Packet.OpCode.CLOSE, null);
      } else {
         Packet.OpCode var1 = Packet.OpCode.values()[Integer.reverseBytes(this.file.readInt())];
         int var2 = Integer.reverseBytes(this.file.readInt());
         byte[] var3 = new byte[var2];
         this.file.readFully(var3);
         Packet var4 = new Packet(var1, new JsonObjectWrapper(new String(var3)));
         if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, var4);
         }

         String var5 = var4.getJson().m150("evt", null);
         if ("READY".equals(var5)) {
            try {
               JsonObjectWrapper var6 = var4.getJson().m148("data");
               JsonObjectWrapper var7 = var6.m148("user");
               IPCClient.connectedUser = new User(
                  var7.m150("username", "?"),
                  "0",
                  var7.m146("id"),
                  var7.m150("avatar", null)
               );
            } catch (Exception var9) {
            }
         }

         return var4;
      }
   }

   @Override
   public void close() throws IOException {
      this.send(Packet.OpCode.CLOSE, new JsonObjectWrapper(), null);
      this.status = PipeStatus.CLOSED;
      this.file.close();
   }
}
