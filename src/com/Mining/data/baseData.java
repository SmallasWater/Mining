package com.Mining.data;



import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Listener;
import cn.nukkit.item.Item;


abstract public class baseData implements Listener{
    Item handItem = null;

    Block breakBlock = null;

    int del = 0;

    Player player = null;

    Item[] dropItem = null;

    int dropExp = 0;

    abstract public Item getBreakItems();

    abstract public Block getBreakBlock();



}
