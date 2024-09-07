package net.minesprawl.packets.server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

public record SprawlActivityUpdatePacket(String message) implements CustomPayload {
    public static final Id<SprawlActivityUpdatePacket> ID = new Id<>(Identifier.of("minesprawl", "activity"));
    public static final PacketCodec<RegistryByteBuf, SprawlActivityUpdatePacket> CODEC = PacketCodec.tuple(new PacketCodec<>() {
        @Override
        public String decode(RegistryByteBuf buf) {
            int length = buf.readableBytes();
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        }

        @Override
        public void encode(RegistryByteBuf buf, String value) {
            buf.writeString(value);
        }
    }, SprawlActivityUpdatePacket::message, SprawlActivityUpdatePacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
