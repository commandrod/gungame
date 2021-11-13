package me.commandrod.gungame.levels;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RegisterLevels {

    private static ItemStack enchant(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack enchantWeapon(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static void setLevel(int level, String armorType, String weaponMat){

        int minusNumber = 1;
        LevelManager.createLevel(level, new ItemStack[] {
                LevelManager.getLevel(level-minusNumber).getArmor()[0],
                LevelManager.getLevel(level-minusNumber).getArmor()[1],
                LevelManager.getLevel(level-minusNumber).getArmor()[2],
                LevelManager.getLevel(level-minusNumber).getArmor()[3]
        }, new ItemStack(Material.valueOf(weaponMat + "_AXE")));

        LevelManager.createLevel(level + 1, new ItemStack[] {
                LevelManager.getLevel(level-minusNumber).getArmor()[0],
                LevelManager.getLevel(level-minusNumber).getArmor()[1],
                LevelManager.getLevel(level-minusNumber).getArmor()[2],
                LevelManager.getLevel(level-minusNumber).getArmor()[3]
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 2, new ItemStack[] {
                LevelManager.getLevel(level-minusNumber).getArmor()[0],
                LevelManager.getLevel(level-minusNumber).getArmor()[1],
                LevelManager.getLevel(level-minusNumber).getArmor()[2],
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 3, new ItemStack[] {
                LevelManager.getLevel(level-minusNumber).getArmor()[0],
                LevelManager.getLevel(level-minusNumber).getArmor()[1],
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 4, new ItemStack[] {
                LevelManager.getLevel(level-minusNumber).getArmor()[0],
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 5, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 6, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_AXE"))));

        LevelManager.createLevel(level + 7, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 8, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 9, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                enchant(new ItemStack(Material.valueOf(armorType + "_CHESTPLATE"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 10, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                enchant(new ItemStack(Material.valueOf(armorType + "_LEGGINGS"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_CHESTPLATE"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 11, new ItemStack[] {
                enchant(new ItemStack(Material.valueOf(armorType + "_BOOTS"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_LEGGINGS"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_CHESTPLATE"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

    }

    private static void firstArmor(){
        int level = 1;
        String armorType = "LEATHER";
        String weaponMat = "WOODEN";
        LevelManager.createLevel(level, new ItemStack[] {
                null,
                null,
                null,
                null
        }, new ItemStack(Material.valueOf(weaponMat + "_AXE")));

        LevelManager.createLevel(level + 1, new ItemStack[] {
                null,
                null,
                null,
                null
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 2, new ItemStack[] {
                null,
                null,
                null,
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 3, new ItemStack[] {
                null,
                null,
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 4, new ItemStack[] {
                null,
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 5, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, new ItemStack(Material.valueOf(weaponMat + "_SWORD")));

        LevelManager.createLevel(level + 6, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_AXE"))));

        LevelManager.createLevel(level + 7, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                new ItemStack(Material.valueOf(armorType + "_HELMET"))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 8, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                new ItemStack(Material.valueOf(armorType + "_CHESTPLATE")),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 9, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                new ItemStack(Material.valueOf(armorType + "_LEGGINGS")),
                enchant(new ItemStack(Material.valueOf(armorType + "_CHESTPLATE"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 10, new ItemStack[] {
                new ItemStack(Material.valueOf(armorType + "_BOOTS")),
                enchant(new ItemStack(Material.valueOf(armorType + "_LEGGINGS"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_CHESTPLATE"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));

        LevelManager.createLevel(level + 11, new ItemStack[] {
                enchant(new ItemStack(Material.valueOf(armorType + "_BOOTS"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_LEGGINGS"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_CHESTPLATE"))),
                enchant(new ItemStack(Material.valueOf(armorType + "_HELMET")))
        }, enchantWeapon(new ItemStack(Material.valueOf(weaponMat + "_SWORD"))));
    }

    public static void iWantToUnaliveThisTookSoLongAHGDAGSD(){
        firstArmor();
        //setLevel(1, "LEATHER", "WOODEN");
        setLevel(13, "CHAINMAIL", "STONE");
        setLevel(25, "GOLDEN", "GOLDEN");
        setLevel(37, "IRON", "IRON");
        setLevel(49, "DIAMOND", "DIAMOND");
    }
}