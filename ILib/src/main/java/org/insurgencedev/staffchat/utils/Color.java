package org.insurgencedev.staffchat.utils;

        import net.kyori.adventure.text.Component;
        import net.kyori.adventure.text.minimessage.MiniMessage;
        import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

        public class Color {

            public static String colorize(String message) {
                MiniMessage mm = MiniMessage.miniMessage();
                
                // Convert legacy text to Component
                Component legacy = LegacyComponentSerializer.legacyAmpersand().deserialize(message);

                // Parse MiniMessage format
                String minimessage = mm.serialize(legacy).replace("\\", "");

                return LegacyComponentSerializer.legacySection().serialize(mm.deserialize(minimessage));
            }
        }