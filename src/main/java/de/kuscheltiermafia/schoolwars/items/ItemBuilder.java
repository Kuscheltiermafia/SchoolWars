package de.kuscheltiermafia.schoolwars.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private Material material;
    private String id;
    private String displayName;
    private int customModelData;
    private int stackSize = 64;
    private List<String> lore;
    private boolean glint;
    private boolean hideTooltip;
    private boolean hideAdditionalTooltip;
    private boolean showInItemList;

    private double attackDamage;
    private double attackSpeed;

    public ItemBuilder(String id, Material material) {
        this.id = id;
        this.material = material;
        this.showInItemList = true;

        this.lore = new ArrayList<>();
    }

    public ItemBuilder setID(String id) {
        this.id = id;
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public ItemBuilder setStackSize(int stackSize) {
        this.stackSize = stackSize;
        return this;
    }

    public ItemBuilder setLore(String... loreLines) {
        this.lore.addAll(Arrays.asList(loreLines));
        return this;
    }

    public ItemBuilder hasGlint() {
        this.glint = true;
        return this;
    }

    public ItemBuilder hideTooltip() {
        this.hideTooltip = true;
        return this;
    }

    public ItemBuilder hideAdditionalTooltip() {
        this.hideAdditionalTooltip = true;
        return this;
    }

    public ItemBuilder hideInItemList() {
        this.showInItemList = false;
        return this;
    }

    public ItemBuilder setAttackStats(double attackDamage, double attackSpeed) {
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;

        return this;
    }

    public ItemStack build() {
        ItemStack genItem = new ItemStack(material);
        ItemMeta meta = genItem.getItemMeta();

        meta.getPersistentDataContainer().set(Items.itemIDKey, PersistentDataType.STRING, this.id);

        meta.setMaxStackSize(this.stackSize);
        meta.setCustomModelData(this.customModelData);
        meta.setDisplayName(this.displayName);
        meta.setLore(this.lore);
        meta.setEnchantmentGlintOverride(this.glint);
        meta.setHideTooltip(this.hideTooltip);
        meta.setUnbreakable(true);


        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        if (this.hideAdditionalTooltip) {
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        }

        if(this.attackDamage != 0.0) {
            AttributeModifier damageModifier = new AttributeModifier(Items.dmgAttributeKey, this.attackDamage, AttributeModifier.Operation.ADD_NUMBER);
            AttributeModifier speedModifier = new AttributeModifier(Items.spdAttributeKey, this.attackSpeed, AttributeModifier.Operation.ADD_NUMBER);

            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifier);
        }

        genItem.setItemMeta(meta);

        Items.itemList.put(this.id, genItem);
        if (!this.showInItemList) Items.hiddenItems.add(genItem);

        return genItem;
    }
}
