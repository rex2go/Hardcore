package eu.rex2go.hardcore.util;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder setStringTag(String key, String value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setString(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setIntTag(String key, int value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setInt(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setDoubleTag(String key, double value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setDouble(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setBooleanTag(String key, boolean value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setBoolean(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setByteTag(String key, byte value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setByte(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setFloatTag(String key, float value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setFloat(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setByteArrayTag(String key, byte[] value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setByteArray(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setFloatTag(String key, int[] value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setIntArray(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setLongTag(String key, long value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setLong(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder setShortTag(String key, short value) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        assert tag != null;
        tag.setShort(key, value);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);

        return this;
    }

    public ItemBuilder hideFlags() {
        setByteTag("HideFlags", (byte) 63);

        return this;
    }

    public ItemBuilder unbreakable() {
        return unbreakable(true);
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        setBooleanTag("Unbreakable", unbreakable);

        return this;
    }

    @Deprecated
    public ItemBuilder addGlow() {
        net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();

        if (tag != null && !tag.hasKey("Enchantments")) {
            tag.set("Enchantments", new NBTTagList());
        }

        nmsItem.setTag(tag);
        itemStack = CraftItemStack.asCraftMirror(nmsItem);

        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("§f" + name);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder addSplitLore(String prefix, String lore) {
        ArrayList<String> lores = new ArrayList<>();

        if (lore.length() > 24) {
            String[] loreSplit = lore.split(" ");
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < loreSplit.length; i++) {
                String part = loreSplit[i];
                stringBuilder.append(part).append(" ");

                if (stringBuilder.length() > 23 && i != loreSplit.length - 1) {
                    lores.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }

            lores.add(stringBuilder.toString());
        } else {
            lores.add(lore);
        }

        for(String lore1 : lores) {
            addLore(prefix + lore1);
        }

        return this;
    }

    public ItemBuilder addLore(String lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        List<String> lores = (itemMeta.getLore() == null) ? new ArrayList() : itemMeta.getLore();
        lores.add(lore);
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        if (itemMeta.getLore() != null) {
            itemMeta.setLore(new ArrayList<>());
        }

        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);

        return this;
    }

    public ItemBuilder setType(Material material) {
        itemStack.setType(material);

        return this;
    }

    public ItemBuilder setDropDisabled(boolean droppable) {
        setBooleanTag("dropDisabled", droppable);

        return this;
    }

    public ItemBuilder setSkullOwner(UUID uuid) {
        if (itemStack.getType() == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            assert skullMeta != null;
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
            itemStack.setItemMeta(skullMeta);
        }

        return this;
    }

    public ItemBuilder setSkullOwner(OfflinePlayer owner) {
        if (itemStack.getType() == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            assert skullMeta != null;
            skullMeta.setOwningPlayer(owner);
            itemStack.setItemMeta(skullMeta);
        }

        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

}
