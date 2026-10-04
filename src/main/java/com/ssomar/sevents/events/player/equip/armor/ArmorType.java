package com.ssomar.sevents.events.player.equip.armor;

import com.ssomar.sevents.version.Version;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public enum ArmorType {
    HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8);

    private final int slot;

    ArmorType(int slot) {
        this.slot = slot;
    }

    /**
     * Attempts to match the ArmorType for the specified ItemStack.
     *
     * @param itemStack The ItemStack to parse the type of.
     * @return The parsed ArmorType, or null if not found.
     */
    public static ArmorType matchType(final ItemStack itemStack, boolean noHead) {
        if (PlayerEquipArmorListener.isAirOrNull(itemStack)) return null;
        /* The equippable component decides first (1.21.3+): an item can be worn in another slot than its material's one
         * (e.g. a CARVED_PUMPKIN equippable on the LEGS was seen as a helmet, Discord 1451945672751190189). */
        if (Version.is1v21v3Plus()) {
            ArmorType fromComponent = fromEquippable(itemStack);
            if (fromComponent != null) return fromComponent;
        }
        String type = itemStack.getType().name();
        //System.out.println("type: "+type);
        if (!noHead && (type.endsWith("_SKULL") || type.endsWith("_HEAD"))) return HELMET;
        else if (type.endsWith("_HELMET")) return HELMET;
        else if (type.endsWith("_CHESTPLATE") || type.equals("ELYTRA")) return CHESTPLATE;
        else if (type.endsWith("_LEGGINGS")) return LEGGINGS;
        else if (type.endsWith("_BOOTS")) return BOOTS;
        else if (type.equals("CARVED_PUMPKIN")) return HELMET;
        else if (Version.is1v21v3Plus()) {
            if (itemStack.hasItemMeta()) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.hasEquippable()) {
                    EquippableComponent equippable = itemMeta.getEquippable();
                    switch (equippable.getSlot()) {
                        case HEAD:
                            return HELMET;
                        case CHEST:
                            return CHESTPLATE;
                        case LEGS:
                            return LEGGINGS;
                        case FEET:
                            return BOOTS;
                        default:
                            return null;
                    }
                } else return null;
            } else return null;
        } else return null;
    }

    public static boolean canReplaceUsingHotBar(final ItemStack itemStack) {
        if (PlayerEquipArmorListener.isAirOrNull(itemStack)) return false;
        /* 1.21.3+: an item with an equippable component is put on with a right click only if the component is swappable
         * (a carved pumpkin made swappable can, an item worn on the head with swappable: false can't) */
        if (Version.is1v21v3Plus() && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null && itemMeta.hasEquippable()) return itemMeta.getEquippable().isSwappable();
        }
        String type = itemStack.getType().name();
        /* a vanilla carved pumpkin can't be worn with a right click */
        if (type.equals("CARVED_PUMPKIN")) return false;
        return true;
    }

    /** ArmorType of the equippable component of the item, or null (no component, or a non armor slot). 1.21.3+ only. */
    private static ArmorType fromEquippable(final ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || !itemMeta.hasEquippable()) return null;
        EquippableComponent equippable = itemMeta.getEquippable();
        switch (equippable.getSlot()) {
            case HEAD:
                return HELMET;
            case CHEST:
                return CHESTPLATE;
            case LEGS:
                return LEGGINGS;
            case FEET:
                return BOOTS;
            default:
                return null;
        }
    }

    public int getSlot() {
        return slot;
    }
}