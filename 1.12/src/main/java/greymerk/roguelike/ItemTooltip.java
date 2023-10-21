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
        List<String> itemLocLore = getLocLore(event.getItemStack());
        if(event.isCanceled() || itemLocLore == null || itemLocLore.isEmpty()) return ;

        ItemStack itemStack = event.getItemStack();
        List<String> tooltip = event.getToolTip();
        List<String> itemLore = getLore(itemStack);

        // if item has "Lore" tag AND "rldLocLore" tag, replace Lore texts on tooltip with rldLocLore texts.
        if(itemLore != null && !itemLore.isEmpty()){
            short iTooltip = 1, iLore=0;
            for ( ; iTooltip<tooltip.size() ; iTooltip++){
                if (tooltip.get(iTooltip).equals(itemLore.get(0))){ //Only look for the first Lore text
                    // remove the following lore texts, without comparing each time (for performance)
                    // you can change this to compare every line to be more safe if you want.
                    while(iLore<itemLore.size() && iTooltip<tooltip.size()){ //don't get out of bounds!
                        tooltip.remove(iTooltip);
                        iLore++;
                    }
                    break;
                }
            }
        }
        for(short i = 0 ; i < itemLocLore.size() ; i++){
            tooltip.add(i+1, I18n.format(itemLocLore.get(i)));
        }
    }

    public static List<String> getLocLore(ItemStack itemStack){
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

    public static List<String> getLore(ItemStack itemStack){
        if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("display"))
            return null;

        NBTTagCompound tagCompound = itemStack.getTagCompound();
        NBTTagCompound nbtDisplay = tagCompound.getCompoundTag("display");

        if(!nbtDisplay.hasKey("Lore"))
            return null;
        NBTTagList loreTagList = nbtDisplay.getTagList("Lore", 8);
        List<String> loreStringList = new ArrayList<>();
        for(short i = 0 ; i < loreTagList.tagCount() ; i++){
            loreStringList.add(loreTagList.getStringTagAt(i));
        }

        return loreStringList;
    }
}
