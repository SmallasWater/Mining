package com.Mining.data;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.*;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import com.Mining.Mining;



public class breakData extends baseData{

    @Override
    public Item getBreakItems() {
        return super.handItem;
    }

    @Override
    public Block getBreakBlock() {
        return super.breakBlock;
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (event.isCancelled()) {
            return;
        }
        Item item = player.getInventory().getItemInHand();
        if(!Mining.getMain().getConf().getStringList("ban-world").
                contains(block.getLevel().getFolderName())){
            if(Mining.getMain().open.containsKey(player.getName()) && Mining.getMain().open.get(player.getName())){
                if(this.getBreakItems() != null && this.getBreakItems() != null){
                    super.del = 0;
                    super.breakBlock = null;
                    super.handItem = null;
                    super.dropItem = null;
                    super.dropExp = 0;
                    super.player = null;
                }
                for(Block c:this.getList()){
                    if(Block.equals(block,c)){
                        super.del = 1;
                        super.handItem = item;
                        super.breakBlock = block;
                        super.dropItem = block.getDrops(item);
                        super.dropExp = block.getDropExp();
                        super.player = player;
                        break;
                    }
                }
                if(block instanceof BlockWood){
                    super.del = 2;
                    super.handItem = item;
                    super.breakBlock = block;
                    super.player = player;
                }
                if(this.getBreakItems() != null && this.getBreakItems() != null && this.player != null){
                    if(super.del != 0){
                        if(super.del == 1){
                            if(super.dropItem != null){
                                if(this.getBreakItems().isPickaxe()){
                                    if(this.lookUp(super.dropItem) >= 0){
                                        event.setDrops(new Item[]{});
                                        event.setCancelled();
                                    }
                                }
                            }
                        }else if(this.del == 2){
                            if(this.getBreakItems().isAxe()){
                                if(this.lookUp() >= 0){
                                    event.setDrops(new Item[]{});
                                    event.setCancelled();
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    private Block[] getList(){
        Block[] list = new Block[9];
        list[0] = new BlockOreCoal();
        list[1] = new BlockOreRedstone();
        list[2] = new BlockOreRedstoneGlowing();
        list[3] = new BlockOreDiamond();
        list[4] = new BlockOreEmerald();
        list[5] = new BlockOreGold();
        list[6] = new BlockOreIron();
        list[7] = new BlockOreLapis();
        list[8] = new BlockOreQuartz();
        return list;
    }
    private int lookUp(){
        return this.lookUp(super.dropItem);
    }

    private int lookUp(Item... drops) {
        int cancel = -1;
        if (this.getBreakBlock() != null) {
            if (this.getBreakItems() != null) {
                int x = (int) this.getBreakBlock().getX();
                int y = (int) this.getBreakBlock().getY();
                int z = (int) this.getBreakBlock().getZ();
                Item item = this.getBreakItems();
                int click = item.getMaxDurability();
                int clickDamage = item.getDamage();
                for (int x1 = x - 3; x1 <= x + 3; x1++) {
                    for (int y1 = y - 5; y1 < y + 20; y1++) {
                        for (int z1 = z - 3; z1 <= z + 3; z1++) {
                            Block block = this.getBreakBlock().level.getBlock(x1, y1, z1);
                            if (Block.equals(block, this.getBreakBlock(), true)) {
                                if (click - clickDamage > 1) {
                                    Vector3 vector3 = new Vector3(x1, y1, z1);
                                    if (drops == null) {
                                        this.getBreakBlock().level.addParticle(new DestroyBlockParticle(new Vector3(x1, y1, z1),
                                                block));
                                        cancel++;
                                        clickDamage++;
                                        this.getBreakBlock().level.dropItem(vector3, Item.get(block.getId(), block.getDamage(), 1));
                                        this.getBreakBlock().level.setBlock(vector3, Block.get(0));
                                    } else {
                                        int c = 0;
                                        if (this.dropExp != 0) {
                                            player.getLevel().dropExpOrb(vector3, this.dropExp);
                                        }
                                        for (Item i : drops) {
                                            this.getBreakBlock().level.dropItem(vector3, i);
                                            c++;
                                        }
                                        if(c > 0){
                                            cancel++;
                                            clickDamage++;
                                            this.getBreakBlock().level.addParticle(new DestroyBlockParticle(new Vector3(x1, y1, z1),
                                                    block));
                                            this.getBreakBlock().level.setBlock(vector3, Block.get(0));
                                        }else {
                                            player.sendPopup(TextFormat.YELLOW+"[采矿系统] 抱歉，此工具等级不足....");
                                        }

                                        if(!this.getBreakItems().isUnbreakable()) {
                                            item.setDamage(clickDamage);
                                        }
                                    }
                                    item.setDamage(clickDamage);
                                    super.player.getInventory().setItemInHand(item);
                                }
                            }
                        }
                    }
                }

            }
        }
        return cancel;
    }
}
