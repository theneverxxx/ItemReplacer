package itemreplacer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import itemreplacer.ItemReplacer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelResolver.class)
public abstract class ItemModelResolverMixin {
    @ModifyVariable(method = "appendItemLayers", at = @At("STORE"), ordinal = 0)
    private ResourceLocation itemreplacer$replaceModel(
            ResourceLocation model,
            @Local(argsOnly = true) ItemStack stack,
            @Local(argsOnly = true) LivingEntity owner
    ) {
        if (model == null) {
            return null;
        }
        String replacement = ItemReplacer.resolve(stack, model.toString(), owner);
        return replacement == null ? model : ResourceLocation.parse(replacement);
    }
}
