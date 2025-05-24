package org.MiragEdge.fwindEmiCore;

import org.MiragEdge.fwindEmiCore.items.CarrotPickAxe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FwindEmiCore extends JavaPlugin {

    private CarrotPickAxe carrotPickAxeModule;

    @Override
    public void onEnable() {
        // 首次初始化
        initializePlugin();
        getLogger().info("[核心] 插件已启用！");
    }

    @Override
    public void onDisable() {
        // 关闭插件
        shutdownPlugin();
        getLogger().info("[核心] 插件已关闭！");
    }

    // 首次初始化
    private void initializePlugin() {
        saveDefaultConfig();
        reloadConfig();
        carrotPickAxeModule = new CarrotPickAxe(this);
        carrotPickAxeModule.loadConfig();
        carrotPickAxeModule.register();
    }

    // 关闭插件
    private void shutdownPlugin() {
        if (carrotPickAxeModule != null) {
            carrotPickAxeModule.unregister();
            carrotPickAxeModule = null;
        }
    }

    // 重载插件
    private void reloadPlugin() {
        shutdownPlugin(); // 清理旧模块
        initializePlugin(); // 重新初始化
        getLogger().info("[核心] 配置重载完成！");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fwindemicore") || cmd.getName().equalsIgnoreCase("fec")) {
            if (args.length == 0) {
                sender.sendMessage("§l§eFwindEmi-Core §f版本: §a" + getPluginMeta().getVersion());
                sender.sendMessage("§f使用 §b/fec reload §f重载配置");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("fwindemicore.reload")) {
                    sender.sendMessage("§c你没有权限执行此操作！");
                    return true;
                }
                reloadPlugin();
                sender.sendMessage("§a[核心] 配置重载完成！");
                return true;
            }
        }
        return false;
    }
}