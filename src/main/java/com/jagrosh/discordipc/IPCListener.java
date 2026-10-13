package com.jagrosh.discordipc;

import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.User;
import ua.daamky.utils.JsonObjectWrapper;

public interface IPCListener {
   default void onPacketSent(IPCClient var1, Packet var2) {
   }

   default void onPacketReceived(IPCClient var1, Packet var2) {
   }

   default void onActivityJoin(IPCClient var1, String var2) {
   }

   default void onActivitySpectate(IPCClient var1, String var2) {
   }

   default void onActivityJoinRequest(IPCClient var1, String var2, User var3) {
   }

   default void onReady(IPCClient var1) {
   }

   default void onClose(IPCClient var1, JsonObjectWrapper var2) {
   }

   default void onDisconnect(IPCClient var1, Throwable var2) {
   }
}
