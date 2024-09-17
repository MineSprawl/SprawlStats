package net.minesprawl.sprawlstats.client.mixin;

import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerList.class)
public class MultiplayerScreenMixin {

    @Inject(at = @At("RETURN"), method = "loadFile()V")
    private void init(CallbackInfo info) {
        ServerList screen = (ServerList) (Object) this;

        ServerInfo serverInfo = new ServerInfo("MineSprawl", "mc.minesprawl.net", ServerInfo.ServerType.OTHER);

        screen.add(serverInfo, false);
    }

}
