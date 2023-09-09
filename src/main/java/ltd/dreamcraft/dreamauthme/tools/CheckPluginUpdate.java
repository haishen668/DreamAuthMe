package ltd.dreamcraft.dreamauthme.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ltd.dreamcraft.dreamauthme.DreamAuthMe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.bukkit.Bukkit.getConsoleSender;

public class CheckPluginUpdate {
    /**
     * 检查插件是否需要更新，访问API获取插件json数据
     *
     * @param apiUrl API链接
     * @return 如果有新版本就返回字符串 “最新版本&下载地址” 没有新版本就返回"NOTREQUIRED" 发生错误返回"ERROR"
     */
    public static String checkPluginUpdate(String apiUrl) {
        getConsoleSender().sendMessage("§b|> §6正在检查更新....");
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 解析API返回的JSON数据
            String apiResponse = response.toString();
            try {
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(apiResponse).getAsJsonArray();

                // 遍历JSON数组，查找名为"DragonAuthMe"的插件信息
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject pluginInfo = jsonArray.get(i).getAsJsonObject();
                    String name = pluginInfo.get("name").getAsString();
                    if ("DreamAuthMe".equals(name)) {
                        String latestVersion = pluginInfo.get("version").getAsString(); // 获取最新版本号
                        String downloadUrl = pluginInfo.get("url").getAsString(); // 获取下载链接

                        // 检查是否有新版本可用
                        if (isNeedUpdate(DreamAuthMe.in().getDescription().getVersion(), latestVersion)) {
                            // 告诉控制台插件有了最新版本，需要你的更新
                            getConsoleSender().sendMessage("§b|> §c发现新版本：" + latestVersion);
                            getConsoleSender().sendMessage("§b|> §c新版本下载地址：" + downloadUrl);
                            return latestVersion + "&" + downloadUrl;
                        } else {
                            getConsoleSender().sendMessage("§b|> §aDreamAuthMe§e插件已经是§c最新版本§e无需更新。");
                            // 如果不需要更新就返回字符串
                            return "NOTREQUIRED";
                        }

                        // 只需要检查一个名为"DragonAuthMe"的插件信息，因此退出循环
                        // break;
                    }
                }
                return null;
            } catch (Exception e) {
                DreamAuthMe.in().getLogger().severe("解析API返回的JSON数据时发生错误：" + e.getMessage());
                return "ERROR";
            }
        } catch (Exception e) {
            DreamAuthMe.in().getLogger().severe("更新DreamAuthMe插件时发生错误：" + e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 检查插件是否需要更新
     *
     * @param oldVersion 旧版本号
     * @param newVersion 新版本号
     * @return 如果需要更新，返回 true；否则返回 false
     */
    public static boolean isNeedUpdate(String oldVersion, String newVersion) {
        // 将版本号按照点号分割为数组，方便逐个部分进行比较
        String[] version1Parts = oldVersion.split("\\.");
        String[] version2Parts = newVersion.split("\\.");

        // 获取版本号部分的最大长度，避免数组越界
        int length = Math.max(version1Parts.length, version2Parts.length);

        // 逐个部分进行比较
        for (int i = 0; i < length; i++) {
            // 将版本号的每个部分转换为整数
            int part1 = (i < version1Parts.length) ? Integer.parseInt(version1Parts[i]) : 0;
            int part2 = (i < version2Parts.length) ? Integer.parseInt(version2Parts[i]) : 0;

            // 如果旧版本的当前部分小于新版本的当前部分，则表示需要更新，返回 true
            if (part1 < part2) {
                return true;
            }
            // 如果旧版本的当前部分大于新版本的当前部分，则表示不需要更新，返回 false
            else if (part1 > part2) {
                return false;
            }
        }

        // 如果版本号相同，则不需要更新，返回 false
        return false;
    }
}
