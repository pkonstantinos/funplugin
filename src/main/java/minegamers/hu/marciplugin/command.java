package minegamers.hu.marciplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class command implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player){
            Player p = ((Player) sender).getPlayer();
            if (args.length == 0){
                p.sendMessage("/ls reset\n/ls withdraw <mennyiség>");
                return true;
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("withdraw")){
                    p.sendMessage("/ls withdraw <mennyiség>");
                    return true;

                } if (args[0].equalsIgnoreCase("reset")) {
                    for (Player pl:
                            Bukkit.getOnlinePlayers()) {
                        pl.setMaxHealth(20);
                    }
                    return true;
                }
            }
            if (args.length == 2){
                if (args[0].equalsIgnoreCase("withdraw")){
                    double hp = Double.parseDouble(args[1]);
                    if(p.getMaxHealth() > hp){
                        p.setMaxHealth(p.getMaxHealth() - hp);
                        p.getInventory().addItem(addItem(hp));


                    }
                    return true;

                }
            }
        }
        return true;
    }

    public static ItemStack addItem(double sziv){
        ItemStack i = new ItemStack(Material.POPPY);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.RED + ( "+" + (int) sziv + "❤"));
        i.setItemMeta(meta);
        return i;
    }
}
