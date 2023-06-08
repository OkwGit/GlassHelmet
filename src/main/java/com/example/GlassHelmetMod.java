package com.example;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = "glasshelmetmod", version = "1.0.0")
public class GlassHelmetMod {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack helmet = player.inventory.armorItemInSlot(3);
        if (helmet != null && helmet.getItem() instanceof ItemArmor) {
            ItemArmor itemArmor = (ItemArmor) helmet.getItem();
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                ModelBiped modelBiped = ((RenderPlayer) event.renderer).getMainModel();
                ModelResourceLocation modelResourceLocation = new ModelResourceLocation("minecraft:glass", "inventory");
                IBakedModel bakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(modelResourceLocation);

                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.translate(0.0F, -0.25F, 0.0F);
                modelBiped.bipedHead.showModel = true;
                modelBiped.bipedHeadwear.showModel = false;
                Minecraft.getMinecraft().getRenderItem().renderItem(helmet, bakedModel);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
}