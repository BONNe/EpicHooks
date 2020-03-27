package world.bentobox.epichooks.events;


import com.songoda.epichoppers.api.events.HopperAccessEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.eclipse.jdt.annotation.NonNull;

import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.lists.Flags;
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


    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onHopperAccess(HopperAccessEvent event)
    {
        Player player = event.getPlayer();

        if (!this.addon.getPlugin().getIWM().inWorld(Util.getWorld(player.getWorld())))
        {
            // Ignore non-bentobox worlds.
            return;
        }

        if (!this.addon.getIslands().getIslandAt(player.getLocation()).
            map(i -> i.isAllowed(User.getInstance(player), Flags.HOPPER)).
            orElse(false))
        {
            // Player does not have access to hoppers in this island
            event.setCancelled(true);
        }
    }


    /**
     * Current addon.
     */
    private EpicHooksAddon addon;
}
