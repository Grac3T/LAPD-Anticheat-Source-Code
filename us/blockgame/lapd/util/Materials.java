// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util;

import org.bukkit.Material;

public class Materials
{
    private static final int[] MATERIAL_FLAGS;
    public static final int SOLID = 1;
    public static final int LIQUID = 2;
    public static final int LADDER = 4;
    public static final int WALL = 8;
    private static final int STAIRS = 16;
    
    public Materials() {
        for (int i = 0; i < Materials.MATERIAL_FLAGS.length; ++i) {
            final Material material = Material.values()[i];
            if (material.isSolid()) {
                final int[] material_FLAGS = Materials.MATERIAL_FLAGS;
                final int n = i;
                material_FLAGS[n] |= 0x1;
            }
            if (material.name().endsWith("_STAIRS")) {
                final int[] material_FLAGS2 = Materials.MATERIAL_FLAGS;
                final int n2 = i;
                material_FLAGS2[n2] |= 0x10;
            }
            if (material.name().toLowerCase().contains("clay")) {
                final int[] material_FLAGS3 = Materials.MATERIAL_FLAGS;
                final int n3 = i;
                material_FLAGS3[n3] |= 0x1;
            }
        }
        Materials.MATERIAL_FLAGS[Material.SIGN_POST.getId()] = 0;
        Materials.MATERIAL_FLAGS[Material.WALL_SIGN.getId()] = 0;
        Materials.MATERIAL_FLAGS[Material.DIODE_BLOCK_OFF.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.DIODE_BLOCK_ON.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.CARPET.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.SNOW.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.ANVIL.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.STAINED_CLAY.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.TRAP_DOOR.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.ENCHANTMENT_TABLE.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.ENDER_PORTAL_FRAME.getId()] = 1;
        Materials.MATERIAL_FLAGS[172] = 1;
        Materials.MATERIAL_FLAGS[Material.IRON_TRAPDOOR.getId()] = 1;
        final int[] material_FLAGS4 = Materials.MATERIAL_FLAGS;
        final int id = Material.WATER.getId();
        material_FLAGS4[id] |= 0x2;
        final int[] material_FLAGS5 = Materials.MATERIAL_FLAGS;
        final int id2 = Material.STATIONARY_WATER.getId();
        material_FLAGS5[id2] |= 0x2;
        final int[] material_FLAGS6 = Materials.MATERIAL_FLAGS;
        final int id3 = Material.LAVA.getId();
        material_FLAGS6[id3] |= 0x2;
        final int[] material_FLAGS7 = Materials.MATERIAL_FLAGS;
        final int id4 = Material.STATIONARY_LAVA.getId();
        material_FLAGS7[id4] |= 0x2;
        final int[] material_FLAGS8 = Materials.MATERIAL_FLAGS;
        final int id5 = Material.LADDER.getId();
        material_FLAGS8[id5] |= 0x5;
        final int[] material_FLAGS9 = Materials.MATERIAL_FLAGS;
        final int id6 = Material.VINE.getId();
        material_FLAGS9[id6] |= 0x5;
        final int[] material_FLAGS10 = Materials.MATERIAL_FLAGS;
        final int id7 = Material.FENCE.getId();
        material_FLAGS10[id7] |= 0x8;
        final int[] material_FLAGS11 = Materials.MATERIAL_FLAGS;
        final int id8 = Material.SKULL.getId();
        material_FLAGS11[id8] |= 0x8;
        final int[] material_FLAGS12 = Materials.MATERIAL_FLAGS;
        final int id9 = Material.FENCE_GATE.getId();
        material_FLAGS12[id9] |= 0x8;
        final int[] material_FLAGS13 = Materials.MATERIAL_FLAGS;
        final int id10 = Material.COBBLE_WALL.getId();
        material_FLAGS13[id10] |= 0x8;
        final int[] material_FLAGS14 = Materials.MATERIAL_FLAGS;
        final int id11 = Material.NETHER_FENCE.getId();
        material_FLAGS14[id11] |= 0x8;
    }
    
    public static boolean checkFlag(final Material material, final int flag) {
        return (Materials.MATERIAL_FLAGS[material.getId()] & flag) == flag;
    }
    
    static {
        MATERIAL_FLAGS = new int[256];
    }
}
