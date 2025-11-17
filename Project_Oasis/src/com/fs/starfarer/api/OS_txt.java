package com.fs.starfarer.api;
/*
 * this a been taken from the mod UnkownSkies and slightly modified
 * this function was made by Tartiflette
 */
//import com.fs.starfarer.api.Global; // what the Global function is clearly called at line 12 buh

public class OS_txt { 
    public static String category = "OS_Planetary_Condition_desc";
    
    public static String txt(String id){
        return Global.getSettings().getString(category, id);
    }
}

