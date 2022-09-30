package me.jerrysmod.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.jerrysmod.events.ReceivePacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
	
	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	private void onReceivePacket(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
		if (MinecraftForge.EVENT_BUS.post(new ReceivePacketEvent(packet))) ci.cancel();
	}
}
