package minegamers.hu.marciplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Date;


public class Main extends JavaPlugin implements Listener {
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("ls").setExecutor(new command());


        NamespacedKey key = new NamespacedKey(this, "sziv");
        ShapedRecipe heart = new ShapedRecipe(key, command.addItem(2));

        heart.shape("*%*","%B%","*%*");

        heart.setIngredient('*', Material.DIAMOND_BLOCK);
        heart.setIngredient('%', Material.DIAMOND_BLOCK);
        heart.setIngredient('B', Material.NETHERITE_HOE);

        getServer().addRecipe(heart);

    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {


        Player victim = e.getEntity();


        if (victim.getMaxHealth() == 2){
            victim.setMaxHealth(20);
            victim.ban(ChatColor.RED +"Meghaltál végleg", (Date) null, null, true);
        }

        if (e.getEntity().getKiller() instanceof Player) {
            Player attacker = e.getEntity().getKiller();

            attacker.sendMessage(ChatColor.RED + "+1 ❤");
            victim.sendMessage(ChatColor.RED + "-1 ❤");
            victim.setMaxHealth(victim.getMaxHealth() - 2);
            attacker.setMaxHealth(attacker.getMaxHealth() + 2);



        } else{
            victim.setMaxHealth(victim.getMaxHealth() - 2);
            victim.sendMessage(ChatColor.RED + "-1 ❤");
        }

    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){

        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null){
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("❤")){

                if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                    float timeInSeconds = 0.25F;
                    float timeInTicks = 20 * timeInSeconds;
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            //The code inside will be executed in {timeInTicks} ticks.
                            ArrayList<Integer> szamok = new ArrayList<>();
                            for (int i = 0; i < 10; i++) {
                                szamok.add(i);
                            }
                            String kell = "";
                            for (char a:
                                    e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().toCharArray()) {
                                if(szamok.contains(Character.getNumericValue(a))){
                                    kell += a;
                                }

                            }

                            double hp = Double.parseDouble(kell);
                            e.getPlayer().setMaxHealth(e.getPlayer().getMaxHealth() + hp*(e.getPlayer().getInventory().getItemInMainHand().getAmount()));

                            e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));

                        }
                    }.runTaskLater(this, (long)timeInTicks);



                }

            }
        }





    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (e.getItemInHand().getItemMeta() != null) {
            if (e.getItemInHand().getItemMeta().getDisplayName().contains("❤")) {
                e.setCancelled(true);
            }
        }
    }

}
