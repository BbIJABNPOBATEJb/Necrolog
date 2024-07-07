package me.bbijabnpobatejb.necrolog.object;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class DeathMessage implements ConfigurationSerializable {

    public String translationKey;
    public List<String> dopArgs;

    static final String dopS = "$$";

    public DeathMessage() {
    }

    public DeathMessage(String translationKey, List<String> dopArgs) {
        this.translationKey = translationKey;
        this.dopArgs = dopArgs;
    }

    @Nullable
    public static DeathMessage createDeathMessage(PlayerDeathEvent event) {
        Component component = event.deathMessage();
        if (!(component instanceof TranslatableComponent)) return null;
        TranslatableComponent t = (TranslatableComponent) component;
        DeathMessage deathMessage = new DeathMessage();
        deathMessage.translationKey = t.key();
        List<String> list = new ArrayList<>();

        for (Component arg : t.args()) {
            if (arg instanceof TranslatableComponent) {
                String key = ((TranslatableComponent) arg).key();
                key = dopS + key;
                list.add(key);
            } else {
                list.add(arg.insertion());
            }
        }
        deathMessage.dopArgs = list;
        return deathMessage;

    }


    public List<Component> getArgsComponent() {
        List<Component> list = new ArrayList<>();
        for (String s : dopArgs) {
            if (s.startsWith(dopS)) {
                s = s.substring(2);
                list.add(Component.translatable(s));
            } else {
                list.add(Component.text(s));
            }
        }
        return list;
    }

    public Component getFullMessage() {
        return Component.translatable(translationKey, getArgsComponent());
    }

    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("translationKey", this.translationKey);
        data.put("dopArgs", this.dopArgs);

        return data;
    }

    public static DeathMessage deserialize(Map<String, Object> args) {
        return new DeathMessage(
                (String) args.get("translationKey"),
                (List<String>) args.get("dopArgs")
        );
    }
}
