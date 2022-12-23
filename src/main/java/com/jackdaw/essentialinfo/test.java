package com.jackdaw.essentialinfo;

import net.kyori.adventure.text.Component;


public class test {
    public static void main(String[] args){
        String str = "&7%player%: Connect to [%server%].";
        Component component =EssentialInfo.miniMessageBuilder().deserialize(str);
        System.out.println(component);
    }
}
