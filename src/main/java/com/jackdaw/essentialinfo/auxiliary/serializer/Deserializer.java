package com.jackdaw.essentialinfo.auxiliary.serializer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class Deserializer {

    /**
     * Deserialize the normal String to a message component.
     * This method can only identify the color &-code.
     *
     * @param str the raw message.
     * @return The component that can be sent in Minecraft.
     */
    public static @NotNull Component deserialize(String str) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(str);
    }

    /**
     * Deserialize the normal String to a message component.
     * This method uses MiniMessage to deserialize.
     *
     * @param str the raw message.
     * @return The component that can be sent in Minecraft.
     */
    public static @NotNull Component miniMessage(String str) {
        return MiniMessage.miniMessage().deserialize(str);
    }
}
