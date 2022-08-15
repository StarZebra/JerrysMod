package me.jerrysmod.mixin;

import me.jerrysmod.JerrysMod;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public abstract class NickHiderMixin {
	
	protected NickHiderMixin() {
	}
	
	@Shadow
	protected abstract void renderStringAtPos(String paramString, boolean paramBoolean);
	
	@Shadow
	public abstract int getStringWidth(String paramString);
	
	@Shadow
	public abstract int getCharWidth(char paramChar);
	
	
	@Inject(method = {"renderStringAtPos"}, at = {@At("HEAD")}, cancellable = true)
	private void renderString(String text, boolean shadow, CallbackInfo ci) {
		if (text != null && text.contains(JerrysMod.mc.getSession().getUsername())) {
			ci.cancel();
			renderStringAtPos(text.replaceAll(JerrysMod.mc.getSession().getUsername(), "Jerry"), shadow);
		}
	}
	
	@Inject(method = {"getStringWidth"}, at = {@At("RETURN")}, cancellable = true)
	private void getStringWidth(String text, CallbackInfoReturnable<Integer> cir) {
		if (text != null && text.contains(JerrysMod.mc.getSession().getUsername())) {
			cir.setReturnValue(getStringWidth(text.replaceAll(JerrysMod.mc.getSession().getUsername(), "Jerry")));
		}
	}
}