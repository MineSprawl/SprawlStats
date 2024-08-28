package net.minesprawl.mixin;

import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.option.ServerList;
import net.minecraft.client.network.ServerInfo.ServerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MultiplayerScreen.class)
public class SprawlStatsMixin {

	@Inject(method = "init", at = @At("RETURN"))
	private void onInit(CallbackInfo ci) {
		MultiplayerScreen screen = (MultiplayerScreen) (Object) this;
		ServerList serverList = screen.getServerList();

		ServerList newServerList = new ServerList(screen.getMinecraft());

		List<ServerInfo> servers = new ArrayList<>();

		ServerInfo mineSprawl = new ServerInfo("MineSprawl", "play.minesprawl.net", ServerType.OTHER);
		newServerList.add(mineSprawl, false);

		for (int i = 0; i < serverList.size(); i++) {
			ServerInfo server = serverList.get(i);
			if (!server.address.equals("play.minesprawl.net")) {
				servers.add(server);
			}
		}

		screen.setServerList(newServerList);
	}
}
