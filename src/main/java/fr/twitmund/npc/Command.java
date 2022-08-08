package fr.twitmund.npc;

import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.mojang.authlib.GameProfile;
import fr.twitmund.Main;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;


import java.util.UUID;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (arg.equalsIgnoreCase("mod")){
            if (!(player.hasPermission("*"))){
                player.sendMessage(Main.getInstance().elementa + ChatColor.RED + "Vous n'avez pas la permission de faire cela");
                return false;
            }
        }
        String npcName = (String) args[0];

        switch (arg){
            case "spawnnpc":

                addNPCPacket(createNPC(args[0], player.getLocation()), player);
                player.sendMessage(Main.getInstance().elementa+ "Vous avez fait spawn un npc"
                + Main.getInstance().entreCrochet(npcName,ChatColor.DARK_BLUE, true , false));



                break;

            case "delNPC":


        }



        return false;
    }




    public EntityPlayer createNPC(String npcName , Location location){
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorld("world")).getHandle(); // Change "world" to the world the NPC should be spawned in.
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), npcName); // Change "playername" to the name the NPC should have, max 16 characters.
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld)); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        return npc;
    }

    public void addNPCPacket(EntityPlayer npc , Player player){
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc)); // Spawns the NPC for the player client.
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
    }
}
