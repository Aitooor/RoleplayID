package online.starsmc.roleplayid.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> stringList) {
        List<String> arrayList = new ArrayList<>();
        for (String list : stringList) {
            arrayList.add(translate(list));
        }
        return arrayList;
    }

    public static void sendMsgSender(CommandSender sender, String message) {
        sender.sendMessage(translate(message.replace("<player>", sender.getName())));
    }

    public static void sendMsgPlayer(CommandSender player, String message) {
        player.sendMessage(translate(message.replace("<player>", player.getName())));
    }

    public static void sendMsgPlayer(Player player, String message) {
        player.sendMessage(translate(message.replace("<player>", player.getName())));
    }

    public static void sendMsgPlayerPrefix(CommandSender player, String message) {
        sendMsgPlayer(player, getPrefixGame() + message);
    }

    public static void sendMsgPlayerPrefix(Player player, String message) {
        sendMsgPlayer(player, getPrefixGame() + message);
    }

    public static String getPrefixGame() {
        return "&8[&aRoleplayID&8] ";
    }
}
