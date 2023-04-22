package net.pulga22.notified.mixin;

import net.minecraft.nbt.NbtCompound;
import net.pulga22.notified.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData(){
        if (this.persistentData == null){
            this.persistentData = new NbtCompound();
        }

        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir){
        if (persistentData != null){
            nbt.put("NotifiedData", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readMethod(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("NotifiedData", 10)){
            persistentData = nbt.getCompound("NotifiedData");
        }
    }

}
