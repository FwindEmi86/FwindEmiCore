package org.MiragEdge.fwindEmiCore;

import org.bukkit.plugin.java.JavaPlugin;

public final class FwindEmiCore extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("FwindEmiCore 插件已加载！");
    }

    @Override
    public void onDisable() {
        getLogger().info("FwindEmiCore 插件已卸载！");
    }
}
