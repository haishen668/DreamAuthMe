package ltd.dreamcraft.dreamauthme.tools;

import java.util.ArrayList;
import java.util.List;

public class Lang {
    /**
     * 发送一条前缀为[DreamAuthMe] [ERROR]为前缀的信息
     *
     * @param s 传入的信息
     * @return 返回带有错误前缀的信息，并且将§替换为&
     */
    public static String error(String s) {
        String error = "§f[§eDreamAuthMe§f] §f[§cERROR§f] §f- §7" + s;
        return error.replaceAll("&", "§");
    }

    /**
     * 发送一条前缀为[DreamAuthMe] [SUCCESS]为前缀的信息
     *
     * @param s 传入的信息
     * @return 返回带有成功前缀的信息，并且将§替换为&
     */
    public static String success(String s) {
        String success = "§f[§eDreamAuthMe§f] §f[§aSUCCESS§f] §f- §7" + s;
        return success.replaceAll("&", "§");
    }

    /**
     * 发送一条前缀为[DreamAuthMe] [WARN]为前缀的信息
     *
     * @param s 传入的信息
     * @return 返回带有警告前缀的信息，并且将§替换为&
     */
    public static String warn(String s) {
        String warn = "§f[§eDreamAuthMe§f] §f[§eWARN§f] §f- §7" + s;
        return warn.replaceAll("&", "§");
    }

    public static String prefix(String s) {
        return "§f[§eDreamAuthMe§f] " + s.replaceAll("&", "§");
    }

    public static String ChatColor(String message) {
        return message.replaceAll("&", "§");
    }

    public static List<String> ChatColor(List<String> message) {
        List<String> list = new ArrayList();

        for (String s : message) {
            list.add(s.replaceAll("&", "§"));
        }

        return list;
    }
}
