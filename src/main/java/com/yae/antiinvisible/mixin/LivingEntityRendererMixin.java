package com.yae.antiinvisible.mixin;

import com.yae.antiinvisible.client.AntiinvisibleConfig;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

    @ModifyConstant(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", constant = @Constant(intValue = 654311423))
    private int useConfiguredInvisibleOpacity(int originalColor) {
        return AntiinvisibleConfig.getInvisibleEntityColor();
    }
}
