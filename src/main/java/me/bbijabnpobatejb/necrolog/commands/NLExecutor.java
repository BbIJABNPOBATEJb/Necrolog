package me.bbijabnpobatejb.necrolog.commands;

import me.bbijabnpobatejb.necrolog.object.DeathInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.Permission;
import java.util.*;
import java.util.List;

import static me.bbijabnpobatejb.necrolog.Necrolog.LIST_DEATH_INFO;
import static me.bbijabnpobatejb.necrolog.Necrolog.getPlugin;
import static me.bbijabnpobatejb.necrolog.enums.Message.*;
import static me.bbijabnpobatejb.necrolog.events.PluginEventHandler.colorText;
import static me.bbijabnpobatejb.necrolog.object.PluginHelper.PERM_USE;

public class NLExecutor implements CommandExecutor {


    public final CommandList commandList = new CommandList();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        int page = 1;

        boolean op = sender.isOp();
        if (args.length >= 1) {
            String sArg = args[0];
            if (op) {
                if (sArg.equalsIgnoreCase(commandList.admin.get(0))) {
                    for (String s : getPlugin().configHandler.reloadCfg()) {
                        sender.sendMessage(s);
                    }
                    return true;
                } else {
                    String sClear = commandList.admin.get(1);
                    if (sArg.equalsIgnoreCase(sClear)) {
                        String confirm = "confirm";
                        if (args.length > 1) {
                            String s = args[1];
                            if (s.equalsIgnoreCase(confirm)) {
                                sender.sendMessage(CLEARED.getMessage());
                                clearList();
                                return true;
                            }
                        }

                        sender.sendMessage(DO_YOU_WANT.getMessage() + LIST_DEATH_INFO.size() + ")");
                        sender.sendMessage(THEN_USAGE.getMessage()+ " §4/nl " + sClear + " " + confirm);

                        return true;
                    }
                }
            }
            try {
                page = Integer.parseInt(sArg);
                page = Math.max(1, page);
            } catch (NumberFormatException ignored) {
            }
        }


        if (!sender.hasPermission(PERM_USE) && !op) {
            sender.sendMessage(DONT_HAVE_PERMS.getMessage());
            return true;
        }

        getNecrolog(sender, page);

        return true;
    }

    private void clearList() {
        LIST_DEATH_INFO.clear();
        getPlugin().configHandler.saveData();
    }

    public static final int redColor = 0xFF2C2C;
    public static final int redColor2 = 0xFF612C;
    public static final int orangeColor = 0xFFCA2C;
    public static final int yellowColor = 0xFFFF2C;

    private void getNecrolog(final CommandSender sender, final int page) {
        List<DeathInfo> list = new ArrayList<>(LIST_DEATH_INFO);
        int totalSize = list.size();
        int infoPerPage = 10;
        int maxPages = (int) Math.ceil((double) totalSize / infoPerPage);
        if (totalSize == 0) {
            sender.sendMessage(NECROLOG_IS_EMPTY.getMessage());
            return;
        }
        if (page > maxPages) {
            sender.sendMessage(PAGE_NOT_FOUND.getMessage() + maxPages);
            return;
        }
        int size = LIST_DEATH_INFO.size();
        if (sender instanceof Player) {
            int spaceCount = 6;
            for (int i = 0; i < infoPerPage; i++) {
                int number = i + ((page - 1) * infoPerPage);
                if (number >= size) {
                    spaceCount += 10 - i;
                    break;
                }
            }
            sendEmpty(sender, spaceCount);
        }

        String lineTop = "───────────────Necrolog──────────────";
        String line = "──────────────────────────────────";
        Component lineC = colorText(line, redColor);
        Component lineCTop = colorText(lineTop, redColor);
        sender.sendMessage(lineCTop);

        for (int i = 0; i < infoPerPage; i++) {
            int number = i + ((page - 1) * infoPerPage);
            if (number >= size) break;
            DeathInfo deathInfo = LIST_DEATH_INFO.get(number);
            int textNumber = number + 1;
            Component message = Component.text("[" + textNumber + "] ").color(TextColor.color(redColor2)).append(deathInfo.getText());

            sender.sendMessage(message);

        }
        sender.sendMessage(lineC);
        Component component = getComponent(page, totalSize, maxPages);
        sender.sendMessage(component);
        sender.sendMessage(lineC);
    }

    @NotNull
    private Component getComponent(int page, int totalSize, int maxPages) {
        Component component = Component.text(DEATH_LIST.getMessage() + totalSize + "). ").color(TextColor.color(redColor));
        component = component.append(Component.text(PAGE.getMessage() + " " + page + " из " + maxPages).color(TextColor.color(orangeColor)));

        Component left = Component.text("[☜]").color(TextColor.color(yellowColor));
        Component right = Component.text("[☞]").color(TextColor.color(yellowColor));
        int leftPage = Math.max(page - 1, 1);
        int rightPage = Math.min(page + 1, maxPages);
        final String leftCommand = "/nl " + leftPage;
        final String rightCommand = "/nl " + rightPage;

        HoverEvent<Component> hoverEvent = HoverEvent.showText(Component.text(PREV_PAGE.getMessage()).color(TextColor.color(yellowColor)));

        ClickEvent clickEvent = ClickEvent.runCommand(leftCommand);
        left = left.clickEvent(clickEvent).hoverEvent(hoverEvent);

        hoverEvent = HoverEvent.showText(Component.text(NEXT_PAGE.getMessage()).color(TextColor.color(yellowColor)));
        clickEvent = ClickEvent.runCommand(rightCommand);
        right = right.clickEvent(clickEvent).hoverEvent(hoverEvent);

        component = component.append(Component.text("   "));
        Component center = Component.text("  |  ").color(TextColor.color(yellowColor));
        component = component.append(left).append(center).append(right);
        return component;
    }

    public void sendEmpty(CommandSender sender, int count) {
        for (int i = 0; i < count; i++) {
            sender.sendMessage(Component.text(" "));
        }
    }

}