package org.MiragEdge.fwindEmiCore.items;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class CarrotPickAxe implements Listener {

    private final JavaPlugin plugin;
    private boolean isItemsAdderLoaded = false;

    public CarrotPickAxe(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("物品 胡萝卜镐 已加载");
    }

    @EventHandler
    public void onItemsAdderLoad(ItemsAdderLoadDataEvent event) {
        isItemsAdderLoaded = true;
        plugin.getLogger().info("ItemsAdder数据加载完成");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled() || !isItemsAdderLoaded) return;

        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (!ItemsAdder.matchCustomItemName(tool, "items_skin:carrot_pickaxe")) return;

        if (new Random().nextInt(100) >= 25) return;

        event.setDropItems(false);
        event.getBlock().getWorld().dropItemNaturally(
                event.getBlock().getLocation().add(0.5, 0.5, 0.5),
                new ItemStack(Material.CARROT, new Random().nextInt(3) + 1)
        );
    }
}