package world.bentobox.epichooks.events;


import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.eclipse.jdt.annotation.NonNull;

import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.util.Util;
import world.bentobox.epichooks.EpicHooksAddon;


/**
 * This listener checks every player event and acts if event name is "HopperAccessEvent".
 * As EpicHoppers plugin is premium with hidden under protected repository, it is not
 * possible to use proper event class. Need to use this workaround.
 */
public class HopperAccessListener implements Listener
{
    /**
     * Constructor HopperAccessListener creates a new HopperAccessListener instance.
     *
     * @param addon of type EpicHooksAddon
     */
    public HopperAccessListener(@NonNull EpicHooksAddon addon)
    {
        this.addon = addon;
    }


    /**
     * This method cache all PlayerEvents and checks if it's name is HopperAccessEvent.
     * If correct event is found, then it checks if player have access to hoppers in
     * this world.
     * @param event instance of PlayerEvent.
     */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onHopperAccess(PlayerEvent event)
    {
        if (event.getEventName().equals("HopperAccessEvent"))
        {
            Player player = event.getPlayer();

            if (!this.addon.getPlugin().getIWM().inWorld(Util.getWorld(player.getWorld())))
            {
                // Ignore non-bentobox worlds.
                return;
            }

            if (!this.addon.getIslands().getIslandAt(player.getLocation()).
                map(i -> i.isAllowed(User.getInstance(player),
                    EpicHooksAddon.EPIC_HOPPER_ISLAND_PROTECTION)).
                orElse(false))
            {
                // Player does not have access to hoppers in this island
                // I know it can be simplified in single line, but then it is hard
                // to read!
                ((Cancellable) event).setCancelled(true);
            }
        }
    }


// This is proper event handling, however, without access to jar file, it cannot be
// compiled in public JENKINS server.
//    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
//    public void onHopperAccess(HopperAccessEvent event)
//    {
//        Player player = event.getPlayer();
//
//        if (!this.addon.getPlugin().getIWM().inWorld(Util.getWorld(player.getWorld())))
//        {
//            // Ignore non-bentobox worlds.
//            return;
//        }
//
//        if (!this.addon.getIslands().getIslandAt(player.getLocation()).
//            map(i -> i.isAllowed(User.getInstance(player),
//                EpicHooksAddon.EPIC_HOPPER_ISLAND_PROTECTION)).
//            orElse(false))
//        {
//            // Player does not have access to hoppers in this island
//            event.setCancelled(true);
//        }
//    }


    /**
     * Current addon.
     */
    private EpicHooksAddon addon;
}
