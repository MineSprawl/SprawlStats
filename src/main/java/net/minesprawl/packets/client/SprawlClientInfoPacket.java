package net.minesprawl.packets.client;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SprawlClientInfoPacket(String version) implements CustomPayload {
    public static final Id<SprawlClientInfoPacket> ID = new Id<>(Identifier.of("minesprawl", "init"));
    public static final PacketCodec<RegistryByteBuf, SprawlClientInfoPacket> CODEC = PacketCodec.tuple(PacketCodecs.STRING, SprawlClientInfoPacket::version, SprawlClientInfoPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
