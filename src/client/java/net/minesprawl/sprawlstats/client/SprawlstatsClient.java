package net.minesprawl.sprawlstats.client;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minesprawl.sprawlstats.client.packets.ClientInfoPacket;
import net.minesprawl.sprawlstats.packets.ServerActivityUpdatePacket;

import java.time.Instant;

public class SprawlstatsClient implements ClientModInitializer {

    private static final String MOD_ID = "sprawlstats";

    private Core core;
    private final Activity discordActivity = new Activity();

    @Override
    public void onInitializeClient() {
        try (CreateParams params = new CreateParams()) {
            params.setClientID(1271149887882264599L);
            params.setFlags(CreateParams.getDefaultFlags());

            this.core = new Core(params);
        }

        PayloadTypeRegistry.playC2S().register(ClientInfoPacket.ID, ClientInfoPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ServerActivityUpdatePacket.ID, ServerActivityUpdatePacket.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(ServerActivityUpdatePacket.ID, (payload, context) -> {
            setDiscordActivity(payload.message(), false);
        });

        ClientPlayConnectionEvents.JOIN.register(((handler, packetSender, client) -> {
            core.activityManager().clearActivity();
            if (!client.getServer().getServerIp().equals("mc.minesprawl.net")) return;

            this.setDiscordActivity("", true);
            ClientPlayNetworking.send(new ClientInfoPacket(MOD_ID));
        }));

        ClientPlayConnectionEvents.DISCONNECT.register(((handler, client) -> {
            core.activityManager().clearActivity();
        }));
    }

    public void setDiscordActivity(String message, boolean resetTime) {
        ServerInfo serverInfo = MinecraftClient.getInstance().getNetworkHandler().getServerInfo();

        int onlinePlayers = 0;
        int maxPlayers = 0;

        if (serverInfo != null && serverInfo.players != null) {
            onlinePlayers = serverInfo.players.online();
            maxPlayers = serverInfo.players.max();
        }

        this.discordActivity.setType(ActivityType.PLAYING);
        this.discordActivity.assets().setLargeImage("logo");
        this.discordActivity.setState("Playing mc.minesprawl.net");
        this.discordActivity.setDetails(message);
        this.discordActivity.party().size().setCurrentSize(onlinePlayers);
        this.discordActivity.party().size().setMaxSize(maxPlayers);

        if (resetTime) this.discordActivity.timestamps().setStart(Instant.now());

        this.core.activityManager().updateActivity(this.discordActivity);
    }

}
