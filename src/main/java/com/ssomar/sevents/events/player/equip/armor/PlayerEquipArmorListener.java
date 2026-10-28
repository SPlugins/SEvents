package com.ssomar.sevents.events.player.equip.armor;

import com.ssomar.sevents.events.player.click.right.PlayerRightClickEvent;
import com.ssomar.sevents.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public class PlayerEquipArmorListener implements Listener {

    private final List<String> blockedMaterials;

    public PlayerEquipArmorListener() {
        blockedMaterials = new ArrayList<>();
        blockedMaterials.add("BEACON");
        blockedMaterials.add("CRAFTING_TABLE");
        blockedMaterials.add("ENCHANTMENT_TABLE");
        blockedMaterials.add("ENDER_CHEST");
        blockedMaterials.add("DIODE_BLOCK_OFF");
        blockedMaterials.add("DIODE_BLOCK_ON");
        blockedMaterials.add("REDSTONE_COMPARATOR_OFF");
        blockedMaterials.add("REDSTONE_COMPARATOR_ON");
        blockedMaterials.add("LEVER");
        blockedMaterials.add("DAYLIGHT_DETECTOR_INVERTED");
        blockedMaterials.add("DAYLIGHT_DETECTOR");
        blockedMaterials.add("CARTOGRAPHY_TABLE");
        blockedMaterials.add("ANVIL");
        blockedMaterials.add("CHIPPED_ANVIL");
        blockedMaterials.add("DAMAGED_ANVIL");
        blockedMaterials.add("GRINDSTONE");
        blockedMaterials.add("LOOM");
        blockedMaterials.add("STONECUTTER");
        blockedMaterials.add("BELL");
        blockedMaterials.add("SMITHING_TABLE");
    }
    //Event Priority is highest because other plugins might cancel the events before we check. (EX : LOCKED IN INVENTORY EI)

    /* THIS EVENT MUST BE LOW, because idk why CMI cancel it for no reason */
    /* seems fixed 05/23 */
    @EventHandler(priority = EventPriority.HIGH)
    public final void inventoryClick(final InventoryClickEvent e) {
        boolean shift = false, numberkey = false;

        /* NEED TO IGNORE if only CMI cancel the event
        HandlerList handlers = PlayerEquipArmorEvent.getHandlerList();
        RegisteredListener[] listeners = handlers.getRegisteredListeners();
        boolean checkCancel = false;
        for (RegisteredListener registeredListener : listeners) {
            registeredListener.
        }

        if (checkCancel) {
            return;
        }*/

        if(e.isCancelled()) return;

        if (e.getAction() == InventoryAction.NOTHING) {
            return;// Why does this get called if nothing happens??
        }
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            shift = true;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            numberkey = true;
        }
        if (e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER) {
            return;
        }
        if (e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (!e.getInventory().getType().equals(InventoryType.CRAFTING) && !e.getInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor(), false);
        if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
            // Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot.
            return;
        }
        if (shift) {
            newArmorType = ArmorType.matchType(e.getCurrentItem(), false);
            if (newArmorType != null) {
                boolean equipping = e.getRawSlot() != newArmorType.getSlot();
                PlayerInventory inv = e.getWhoClicked().getInventory();
                if (newArmorType.equals(ArmorType.HELMET) && (equipping == isAirOrNull(inv.getHelmet()))
                        || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping == isAirOrNull(inv.getChestplate()))
                        || newArmorType.equals(ArmorType.LEGGINGS) && (equipping == isAirOrNull(inv.getLeggings()))
                        || newArmorType.equals(ArmorType.BOOTS) && (equipping == isAirOrNull(inv.getBoots()))) {
                    PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent((Player) e.getWhoClicked(), PlayerEquipArmorEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
                    armorEquipEvent.setCancelled(e.isCancelled());
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                    }
                }
            }
        } else {
            ItemStack newArmorPiece = e.getCursor();
            ItemStack oldArmorPiece = e.getCurrentItem();
            if (numberkey) {
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {// Prevents shit in the 2by2 crafting
                    // e.getClickedInventory() == The players inventory
                    // e.getHotBarButton() == key people are pressing to equip or unequip the item to or from.
                    // e.getRawSlot() == The slot the item is going to.
                    // e.getSlot() == Armor slot, can't use e.getRawSlot() as that gives a hotbar slot ;-;
                    ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                    if (!isAirOrNull(hotbarItem)) {// Equipping
                        newArmorType = ArmorType.matchType(hotbarItem, false);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                    } else {// Unequipping
                        newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor(), false);
                    }
                }
            } else {
                if (isAirOrNull(e.getCursor()) && !isAirOrNull(e.getCurrentItem())) {// unequip with no new item going into the slot.
                    newArmorType = ArmorType.matchType(e.getCurrentItem(), false);
                }
                // e.getCurrentItem() == Unequip
                // e.getCursor() == Equip
                // newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
            }
            if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                PlayerEquipArmorEvent.EquipMethod method = PlayerEquipArmorEvent.EquipMethod.PICK_DROP;
                if (e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) {
                    method = PlayerEquipArmorEvent.EquipMethod.HOTBAR_SWAP;
                }
                PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent((Player) e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                armorEquipEvent.setCancelled(e.isCancelled());
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    /* REQUIRE TO REGISTRY PLAYER RIGHT CLICK EVENT */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerRightClickEvent(PlayerRightClickEvent e) {
        Player player = e.getPlayer();
        ItemStack item = ((PlayerInteractEvent) e.getSourceEvent()).getItem();

        // Not armor: nothing to do. Checked first because getState() below copies the whole block entity (a full barrel costs seconds on Paper)
        ArmorType newArmorType = ArmorType.matchType(item, true);
        if (newArmorType == null) return;

        if (e.hasBlock()) {
            Block block = e.getBlock();
            // Some blocks have actions when you right click them which stops the client from equipping the armor in hand.
            if (!Version.is1v11Less() && block.getState() instanceof Container) {
                return;
            }
            Material mat = block.getType();
            if (mat.toString().contains("SIGN")
                    || mat.toString().contains("TRAPDOOR")
                    || mat.toString().contains("DOOR")
                    || mat.toString().contains("BUTTON")
                    || mat.toString().contains("FENCE_GATE")
                    || mat.toString().contains("BED")
                    || mat.toString().contains("CANDLE_CAKES")
                    || mat.toString().contains("BUTTONS")
                    || mat.toString().contains("FLOWER_POT")) {
                return;
            }
            for (String s : blockedMaterials) {
                if (mat.name().equalsIgnoreCase(s)) {
                    return;
                }
            }
        }

        if (ArmorType.canReplaceUsingHotBar(item) &&
                ((newArmorType.equals(ArmorType.HELMET) && isAirOrNull(e.getPlayer().getInventory().getHelmet()))
                        || (newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(e.getPlayer().getInventory().getChestplate()))
                        || (newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(e.getPlayer().getInventory().getLeggings()))
                        || (newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(e.getPlayer().getInventory().getBoots())))) {
            PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent(e.getPlayer(), PlayerEquipArmorEvent.EquipMethod.HOTBAR, ArmorType.matchType(item, true), null, item);
            armorEquipEvent.setCancelled(e.isCancelled());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                e.setCancelled(true);
                player.updateInventory();
            }
        } else if (Version.is1v19v4Plus()) {
            ItemStack currentArmorPiece = null;
            if (newArmorType.equals(ArmorType.HELMET) && !isAirOrNull(e.getPlayer().getInventory().getHelmet())) {
                currentArmorPiece = e.getPlayer().getInventory().getHelmet();
            } else if (newArmorType.equals(ArmorType.CHESTPLATE) && !isAirOrNull(e.getPlayer().getInventory().getChestplate())) {
                currentArmorPiece = e.getPlayer().getInventory().getChestplate();
            } else if (newArmorType.equals(ArmorType.LEGGINGS) && !isAirOrNull(e.getPlayer().getInventory().getLeggings())) {
                currentArmorPiece = e.getPlayer().getInventory().getLeggings();
            } else if (newArmorType.equals(ArmorType.BOOTS) && !isAirOrNull(e.getPlayer().getInventory().getBoots())) {
                currentArmorPiece = e.getPlayer().getInventory().getBoots();
            }
            if (currentArmorPiece != null && ArmorType.canReplaceUsingHotBar(item)) {
                PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent(e.getPlayer(), PlayerEquipArmorEvent.EquipMethod.HOTBAR, ArmorType.matchType(item, true), currentArmorPiece, item);
                armorEquipEvent.setCancelled(e.isCancelled());
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    e.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void inventoryDrag(InventoryDragEvent event) {
        // getType() seems to always be even.
        // Old Cursor gives the item you are equipping
        // Raw slot is the ArmorType slot
        // Can't replace armor using this method making getCursor() useless.
        ArmorType type = ArmorType.matchType(event.getOldCursor(), false);
        if (event.getRawSlots().isEmpty()) {
            return;// Idk if this will ever happen
        }
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)) {
            PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent((Player) event.getWhoClicked(), PlayerEquipArmorEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
            armorEquipEvent.setCancelled(event.isCancelled());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setResult(Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e) {
        ArmorType type = ArmorType.matchType(e.getBrokenItem(), false);
        if (type != null) {
            Player p = e.getPlayer();
            PlayerEquipArmorEvent armorEquipEvent = new PlayerEquipArmorEvent(p, PlayerEquipArmorEvent.EquipMethod.BROKE, type, e.getBrokenItem(), null);
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short) (i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                } else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                } else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                } else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (e.getKeepInventory()) {
            return;
        }
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (!isAirOrNull(i)) {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipArmorEvent(p, PlayerEquipArmorEvent.EquipMethod.DEATH, ArmorType.matchType(i, false), i, null));
                // No way to cancel a death event.
            }
        }
    }

    /**
     * A utility method to support versions that use null or air ItemStacks.
     */
    public static boolean isAirOrNull(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
}
