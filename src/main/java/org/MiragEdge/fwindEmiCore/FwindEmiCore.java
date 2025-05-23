package org.MiragEdge.fwindEmiCore;

import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CarrotHoePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (!ItemsAdder.matchCustomItemName(tool, "your_namespace:carrot_hoe")) return;

        // 25% 概率触发
        if (new Random().nextInt(100) >= 25) return;

        // 处理掉落物（兼容所有版本）
        event.setDropItems(false);
        List<ItemStack> drops = new ArrayList<>(event.getBlock().getDrops());
        drops.add(new ItemStack(Material.CARROT, new Random().nextInt(3) + 1));

        // 生成掉落
        drops.forEach(item ->
                event.getBlock().getWorld().dropItemNaturally(
                        event.getBlock().getLocation(),
                        item
                )
        );
    }
}