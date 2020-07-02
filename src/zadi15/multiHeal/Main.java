package zadi15.multiHeal;
/*  
 *  Copyright https://github.com/zadi15
 *  
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements CommandExecutor{
	public void onEnable() {
	}

	public void onDisable() {
	}

	public boolean onCommand(CommandSender cmdSender, Command chatCmd, String cmdLabel, String[] cmdArgs) {
		/*
		 * Player is restored to full health upon /health or /heal. Other players
		 * can also be healed.
		 * Permissions:
		 * 	mh.heal
		 * 	mh.healothers 
		 */
		if (cmdLabel.equalsIgnoreCase("health") || cmdLabel.equalsIgnoreCase("heal")) {
			if (cmdSender instanceof Player) {//player
				Player player = (Player) cmdSender;
				if (player.hasPermission("mh.heal") == true) {
					if (cmdArgs.length == 0) {
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Healed!");
						player.setHealth(20.0);
						return true;
					} else if (cmdArgs.length == 1){
						if (player.hasPermission("mh.healothers") == true) {
							if (player.getServer().getPlayer(cmdArgs[0]) != null) {
								Player targetPlayer = getServer().getPlayer(cmdArgs[0]);
								targetPlayer.sendMessage(ChatColor.GRAY
										+ "You Have Been Healed By: " + ChatColor.AQUA
										+ player.getDisplayName() + ChatColor.GRAY + ".");
								targetPlayer.setHealth(20.0);
								return true;
							} else {
								player.sendMessage(ChatColor.GRAY + "Player Not Online!");
								return true;
							}
						}
					}
				}
				player.sendMessage(ChatColor.RED + "You do not have the correct perms!");
				return true;

			}
			return true;
		}

		/*
		 * Player is restored to full hunger upon /feed. Other players
		 * can also be fed by other players..
		 * Permissions:
		 * 	mh.healothers
		 */
		if (cmdLabel.equalsIgnoreCase("feed")) {
			if (cmdSender instanceof Player) {//player
				Player player = (Player) cmdSender;
				//if (player.hasPermission("mh.food") == true) {
				if (cmdArgs.length == 0) {
					player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Hunger Has Been Restored!");
					player.setFoodLevel(20);
					return true;
				} else if (cmdArgs.length == 1){
					if (player.hasPermission("mh.healothers") == true) {
						if (player.getServer().getPlayer(cmdArgs[0]) != null) {
							Player targetPlayer = getServer().getPlayer(cmdArgs[0]);
							targetPlayer.sendMessage(ChatColor.GRAY
									+ "You Have Been Fed By: " + ChatColor.AQUA
									+ player.getDisplayName() + ChatColor.GRAY + ".");
							targetPlayer.setFoodLevel(20);
							return true;
						} else {
							player.sendMessage(ChatColor.GRAY + "Player Not Online!");
							return true;
						}
					}
				}
				//}
				player.sendMessage(ChatColor.RED + "You do not have the correct perms!");
				return true;

			}
			return true;
		}

		/*
		 * Player is extinguished when on fire using /extinguish or /ext.
		 * If the player is in the nether however, it also grants them 20
		 * seconds of Fire Resistance (for the new Nether update).
		 * Other players can also be extinguished.
		 * Permissions:
		 * 	mh.healothers 
		 */
		if (cmdLabel.equalsIgnoreCase("extinguish") || cmdLabel.equalsIgnoreCase("ext")) {
			if (cmdSender instanceof Player) {//player
				Player player = (Player) cmdSender;
				if (cmdArgs.length == 0) {
					if (player.getWorld().getEnvironment() == Environment.NETHER) {
						player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Extinguished! (With Fire Resistance!)");
						player.setFireTicks(0);
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 1));
						return true;
					}
					player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Extinguished!");
					player.setFireTicks(0);
					return true;
				} else if (cmdArgs.length == 1){
					if (player.hasPermission("mh.healothers") == true) {
						if (player.getServer().getPlayer(cmdArgs[0]) != null) {
							Player targetPlayer = getServer().getPlayer(cmdArgs[0]);
							if (targetPlayer.getWorld().getEnvironment() == Environment.NETHER) {
								targetPlayer.sendMessage(ChatColor.GRAY
										+ "You Have Been Extingushed (With Fire Resistance!) By: " + ChatColor.AQUA
										+ player.getDisplayName() + ChatColor.GRAY + ".");
								targetPlayer.setFireTicks(0);
								targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 1));
								return true;
							}
							targetPlayer.sendMessage(ChatColor.GRAY
									+ "You Have Been Extinguished By: " + ChatColor.AQUA
									+ player.getDisplayName() + ChatColor.GRAY + ".");
							targetPlayer.setFireTicks(0);
							return true;
						} else {
							player.sendMessage(ChatColor.GRAY + "Player Not Online!");
							return true;
						}
					}
				}
				player.sendMessage(ChatColor.RED + "Too many arguments!");
				return true;

			}
			return true;
		}
		
		/*
		 * This a combination of all of the above commands, for easy
		 * admin convenience.
		 * Permissions:
		 * 	mh.fullheal
		 */
		
		if (cmdLabel.equalsIgnoreCase("fullHealth") || cmdLabel.equalsIgnoreCase("fh")) {
			if (cmdSender instanceof Player) {//player
				Player player = (Player) cmdSender;
				if (player.hasPermission("mh.fullheal") == true) {
					if (cmdArgs.length == 0) {
						if (player.getWorld().getEnvironment() == Environment.NETHER) {
							player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Fully healed!");
							player.setHealth(20.0);
							player.setFireTicks(0);
							player.setFoodLevel(20);
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 1));
							return true;
						}
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Fully healed!");
						player.setHealth(20.0);
						player.setFireTicks(0);
						player.setFoodLevel(20);
						return true;
					}
				}
			}
		}

		return true;
	}
}
