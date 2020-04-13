package com.Mining;

import cn.nukkit.plugin.PluginBase;

import cn.nukkit.utils.Config;
import com.Mining.data.breakData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * @sine Java 1.8
 * @since NukkitX 支持 MineCraft 1.9 api 1.0.0
 * 连锁采矿
 * @author 若水
 */
public class Mining extends PluginBase{

    private static Mining main;
    public static Mining getMain(){
        return main;
    }
    public HashMap<String,Boolean> open = new HashMap<>();

    @Override
    public void onEnable() {
        main = this;
        this.getServer().getPluginManager().registerEvents(new breakData(),this);
        this.getServer().getCommandMap().register("",new clickCommand());
       this.init();
        this.getServer().getLogger().info("连锁采矿插件加载成功");

    }


    public Config getConf(){
        return new Config(this.getDataFolder()+"/config.yml",Config.YAML);
    }


    private void init(){
        if(!new File(this.getDataFolder()+"/config.yml").exists())
            saveDefaultConfig();
        reloadConfig();
    }

}
