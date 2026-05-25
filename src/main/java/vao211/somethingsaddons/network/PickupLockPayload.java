package vao211.somethingsaddons.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PickupLockPayload(boolean isLocked) implements CustomPayload {
    public static final Id<PickupLockPayload> ID = new Id<>(Identifier.of("somethingsaddons", "pickup_lock"));

    public static final PacketCodec<RegistryByteBuf, PickupLockPayload> CODEC = PacketCodec.tuple(
            PacketCodec.ofStatic((buf, val) -> buf.writeBoolean(val), buf -> buf.readBoolean()),
            PickupLockPayload::isLocked,
            PickupLockPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}