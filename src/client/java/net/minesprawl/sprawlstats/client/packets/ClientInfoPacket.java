package net.minesprawl.sprawlstats.client.packets;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ClientInfoPacket(String version) implements CustomPayload {

    public static final Id<ClientInfoPacket> ID = new Id<>(Identifier.of("sprawlstats", "init")); // sprawlstats:init
    public static final PacketCodec<RegistryByteBuf, ClientInfoPacket> CODEC = PacketCodec.tuple(PacketCodecs.STRING, ClientInfoPacket::version, ClientInfoPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
