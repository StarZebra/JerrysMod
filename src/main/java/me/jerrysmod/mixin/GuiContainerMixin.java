package me.jerrysmod.mixin;

import me.jerrysmod.events.DrawSlotEvent;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class GuiContainerMixin {
	
	@Shadow
	public Container inventorySlots;
	
	@Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
	private void beforeDrawSlot(Slot slot, CallbackInfo callbackInfo) {
		if(MinecraftForge.EVENT_BUS.post(new DrawSlotEvent(inventorySlots, slot))) {
			callbackInfo.cancel();
		}
	}
}
