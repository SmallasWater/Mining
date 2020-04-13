package com.Mining;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class clickCommand extends Command{

    clickCommand() {
        super("采矿","连锁采矿开关");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(Mining.getMain().open.containsKey(commandSender.getName())){
                if(Mining.getMain().open.get(commandSender.getName())){
                    commandSender.sendMessage("连锁采矿已关闭");
                    Mining.getMain().open.put(commandSender.getName(),false);
                }else {
                    commandSender.sendMessage("连锁采矿已开启");
                    Mining.getMain().open.put(commandSender.getName(),true);
                }
            }else {
                Mining.getMain().open.put(commandSender.getName(),true);
                commandSender.sendMessage("连锁采矿已开启");
            }
        }else{
            commandSender.sendMessage("控制台去死");
        }
        return false;
    }
}
