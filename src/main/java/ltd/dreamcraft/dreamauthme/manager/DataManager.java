package ltd.dreamcraft.dreamauthme.manager;

import ltd.dreamcraft.dreamauthme.DreamAuthMe;

import java.io.File;

import static org.bukkit.Bukkit.getConsoleSender;

public class DataManager {
    public void init() {
        initScreen();
    }

    private void initScreen() {
        File folder = new File("plugins/DreamAuthMe/screen");

        // 检查文件夹是否存在，如果不存在则创建它
        if (!folder.exists()) {
            if (folder.mkdirs()) {
//                getConsoleSender().sendMessage("§b|> §e成功创建 'plugins/DreamAuthMe/screen' 文件夹!");
            } else {
                getConsoleSender().sendMessage("§c|> §4无法创建 'plugins/DreamAuthMe/screen' 文件夹!");
                return; // 如果无法创建文件夹，退出方法，避免后续复制失败
            }
        }

        // 继续检查并复制配置文件
        File file1 = new File(folder, "DreamAuthMeLogin.yml");
        File file2 = new File(folder, "DreamAuthMeReg.yml");
        File file3 = new File(folder, "DreamAuthMeChangePsd.yml");

        if (!file1.exists()) {
            DreamAuthMe.in().saveResource("screen/DreamAuthMeLogin.yml", false);
        }
        if (!file2.exists()) {
            DreamAuthMe.in().saveResource("screen/DreamAuthMeReg.yml", false);
        }
        if (!file3.exists()) {
            DreamAuthMe.in().saveResource("screen/DreamAuthMeChangePsd.yml", false);
            getConsoleSender().sendMessage("§b|> §e生成默认的screen配置!");
        }

    }

}
