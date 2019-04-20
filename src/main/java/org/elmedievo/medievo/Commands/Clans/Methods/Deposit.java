package org.elmedievo.medievo.Commands.Clans.Methods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static org.elmedievo.medievo.Commands.Clans.Mercantilism.Valuables.CURRENCY_NAME_PLURAL;
import static org.elmedievo.medievo.Commands.Clans.Mercantilism.Valuables.CURRENCY_SYMBOL;
import static org.elmedievo.medievo.Commands.Clans.Mercantilism.Valuables.valueInMarket;
import static org.elmedievo.medievo.Database.Getters.PlayerClanGetter.getPlayerClan;
import static org.elmedievo.medievo.Database.Setters.AddColonesToClan.addColonestoClan;
import static org.elmedievo.medievo.Database.Setters.AddGoldToClan.addGoldToClan;
import static org.elmedievo.medievo.util.Generic.CANNOT_DEPOSIT;
import static org.elmedievo.medievo.util.Generic.DEPOSIT_SUCCESS;
import static org.elmedievo.medievo.util.Generic.NOT_IN_A_CLAN;

public class Deposit {
    public static void depositGoldIntoClan(Player player) {
        String playerClan = getPlayerClan(player.getUniqueId());
        if (!Objects.requireNonNull(playerClan).equalsIgnoreCase("none")) {
            Material materialInHand = player.getInventory().getItemInMainHand().getType();
            int materialAmount = player.getInventory().getItemInMainHand().getAmount();
            String material = materialInHand.toString().toLowerCase();
            int colones = valueInMarket(materialInHand);
            if (colones == 0) {
                player.sendMessage(CANNOT_DEPOSIT);
            } else {
                player.setItemInHand(new ItemStack(Material.AIR));
                addColonestoClan(playerClan, colones);
                addGoldToClan(playerClan, material, materialAmount);
                player.sendMessage(DEPOSIT_SUCCESS + ChatColor.AQUA + " » " + ChatColor.WHITE + ChatColor.UNDERLINE + ChatColor.ITALIC + CURRENCY_SYMBOL + colones * materialAmount + " " + CURRENCY_NAME_PLURAL);
            }
        } else {
            player.sendMessage(NOT_IN_A_CLAN);
        }
    }
}
