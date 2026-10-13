package com.jagrosh.discordipc.entities;

import ua.daamky.utils.JsonObjectWrapper;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Packet {
   private final Packet.OpCode op;
   private final JsonObjectWrapper data;

   public Packet(Packet.OpCode var1, JsonObjectWrapper var2) {
      this.op = var1;
      this.data = var2;
   }

   public byte[] toBytes() {
      byte[] var1 = this.data.toString().getBytes(StandardCharsets.UTF_8);
      ByteBuffer var2 = ByteBuffer.allocate(var1.length + 8);
      var2.putInt(Integer.reverseBytes(this.op.ordinal()));
      var2.putInt(Integer.reverseBytes(var1.length));
      var2.put(var1);
      return var2.array();
   }

   public Packet.OpCode getOp() {
      return this.op;
   }

   public JsonObjectWrapper getJson() {
      return this.data;
   }

   @Override
   public String toString() {
      return "Pkt:" + this.getOp() + this.getJson().toString();
   }

   public static enum OpCode {
      HANDSHAKE,
      FRAME,
      CLOSE,
      PING,
      PONG;
   }
}
