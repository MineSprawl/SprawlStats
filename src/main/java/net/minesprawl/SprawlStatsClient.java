package net.minesprawl;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;

public class SprawlStatsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBoolean(true);

		ClientPlayNetworking.send(NetworkHandler.SPRAWLSTATS_STATUS_PACKET_ID, buf);
	}
}