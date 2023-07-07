package greymerk.roguelike;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemTooltip {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onItemTooltip(ItemTooltipEvent event){
        List<String> itemLore = getRLDLore(event.getItemStack());
        if(event.isCanceled() || itemLore == null) return ;

        ItemStack itemStack = event.getItemStack();
        List<String> tooltip = event.getToolTip();
        for(short i = 0 ; i < itemLore.size() ; i++){
            tooltip.add(i+1, I18n.format(itemLore.get(i)));
        }
    }

    public static List<String> getRLDLore(ItemStack itemStack){
        if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("display"))
            return null;

        NBTTagCompound tagCompound = itemStack.getTagCompound();
        NBTTagCompound nbtDisplay = tagCompound.getCompoundTag("display");

        if(!nbtDisplay.hasKey("rldLocLore"))
            return null;
        NBTTagList loreTagList = nbtDisplay.getTagList("rldLocLore", 8);
        List<String> loreStringList = new ArrayList<>();
        for(short i = 0 ; i < loreTagList.tagCount() ; i++){
            loreStringList.add(loreTagList.getStringTagAt(i));
        }


        return loreStringList;
    }
}
