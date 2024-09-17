package net.minesprawl.sprawlstats.packets;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

public record ServerActivityUpdatePacket(String message) implements CustomPayload {

    public static final Id<ServerActivityUpdatePacket> ID = new Id<>(Identifier.of("sprawlstats", "update")); // sprawlstats:update
    public static final PacketCodec<RegistryByteBuf, ServerActivityUpdatePacket> CODEC = PacketCodec.tuple(new PacketCodec<>() {
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
    }, ServerActivityUpdatePacket::message, ServerActivityUpdatePacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
