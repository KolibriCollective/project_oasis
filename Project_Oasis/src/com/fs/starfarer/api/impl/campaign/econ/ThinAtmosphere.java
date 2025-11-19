package com.fs.starfarer.api.impl.campaign.econ;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.util.Misc;

/*
 * those are the package that I believe we need 
 * I think we need to add them to our jar file for them to function properly tho
 * or maybe not ? 
 * we might need more or some of the one I got aren't usefull
 * alright those package name are their location in the jar file 
 * so for exemple BaseHazardCondition is in com / fs / starfarer / api / impl / campaign / econ 
 * has a script file (if I remember corectly)
 * this manage the function for hazard rating for a market 
 * MarketImmigrationModifer change the the speed of market growth
 * Industries has access to all of the vanilla industrie and make it so you can change production and demmand (hopefully)
 * Commodities has all of the Commodiites defined needed for changing the industrie production / demand and such  
 * I don't know what Misc does 
 */

public class ThinAtmosphere extends BaseHazardCondition {
    
    
    public final float ACCESSIBILITY_BONUS = 5f;
    public final int ORE_BONUS = 1;

    
    public void apply(String id) {

        market.getHazard().modifyFlat(id,25/100f,"Thin Atmosphere");
        market.getAccessibilityMod().modifyFlat(id,ACCESSIBILITY_BONUS/100,"Thin Atmosphere");

        Industry industry = market.getIndustry(Industries.POPULATION);
        if(industry!=null){
            int  size = market.getSize();
            float HEAVY_MACHINERY_DEMAND = (size - 3);
            float METALS_DEMAND = (size - 3);
            industry.getDemand(Commodities.METALS).getQuantity().modifyFlat(id + "_0", METALS_DEMAND,"Thin Atmosphere");
            industry.getDemand(Commodities.HEAVY_MACHINERY).getQuantity().modifyFlat(id + "_0", HEAVY_MACHINERY_DEMAND, "Thin Atmosphere");
        }
        industry = market.getIndustry(Industries.MINING);
        if(industry!=null){
            if (industry.isFunctional()) {
                if (market.getCommoditiesWithTag(Commodities.VOLATILES) !=null) { 
                    industry.supply(id + "_0",Commodities.VOLATILES, ORE_BONUS,"Thin Atmosphere");
                }
            } 
            
            else {
                industry.getSupply(Commodities.VOLATILES).getQuantity().unmodifyFlat(id + "_0");
            }
        }

        industry = market.getIndustry(Industries.WAYSTATION);
        if (industry!=null){
             market.getAccessibilityMod().modifyFlat(id,ACCESSIBILITY_BONUS/100,"Thin Atmosphere");
        }
    }
    public void unapply(String id) {
        market.getAccessibilityMod().unmodifyFlat(id);
    }

    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
         super.createTooltipAfterDescription(tooltip, expanded);
         tooltip.addPara("%s accessibility", 5.0F, Misc.getHighlightColor(), new String[]{"+5%"});
         tooltip.addPara("%s Heavy Machinery (Population & Infrastructure)", 1, Misc.getHighlightColor(), new String[]{"+1"});
         tooltip.addPara("%s Metal (Population & Infrastructure)", 1, Misc.getHighlightColor(), new String[]{"+1"});
         tooltip.addPara("%s Volatiles (Mining)", 1, Misc.getHighlightColor(), new String[]{"+1"});
    }
}

/*
 * 2025-11-16 (21:19)
 * 1. let's figure out what the public void unapply (string id) part of the function need to do for the code to work 
 * 
 * (22:18)
 * my best guess is that after changing certain values you need to make the change happen by unapplying it ? 
 * doesn't seem to be needed for industry 
 * I have yet to understand why US doesn't have this. but the bases game does 
 * if my guess are correct for unapply then it should be good to go 
 * but this is a assumption and we won't be sure it does infact work until trying to get the mod to work in the game 
 * 
 * 2. we will make sure that the mod has access to the function and figure out if there is any probleme 
 * 
 * the mod access this file trought the planetary_condition.csv file 
 * to get the mod to actually do anything we need to make it so we overwrite that file 
 * possible way to do that : 
 * currently we don't know where and when the file is called but it should be a safe asumption to think it's in the Modplugin file and 
 * at onNewGame has that is the function that does all of the stuff when creating a new game 
 * 
 * 
 * 
 * 3. add a string file to store all of the text instead of having them in the file 
 * 
 * (23:35)
 * created and imported the package that should be able to call the text that will be stored in a other file 
 * it is not yet implemented but it is a start
 * (1:43)
 * I believe that the reason US_txt is called has US_txt.txt is so it call the function txt directly
 * added the function call to this file hopefully it work
 * now we just have to make simaler addiction for the ore, rare ore, organics, and volatiles (line 54-57)
 * 
 * 4. and finally we will debug everything we have done so far, so we make the process smth we can not only replicate but make sure that it 
 * is good
 * 
 * (10:49)
 * fixed the package error 
 * (15:46)
 * now that all of the package are present and working we can finally actually work on seeing where we messed up in the code 
 * so 
 * getAccessibilityMod isn't actually a function 
 * Supply isn't a commmand for industry ??
 * txt still does not work 
 * 
 * 2025-11-19 (00:04)
 * 
 * fixed both issue (getAccessibilityMod and Supply not functioning)
 * txt doesn't really work still trying to understand why 
 * added createTooltipAfterDescription to the file, I believe this is the command that make the modification show in game
 * 
 * 2025-11-19 (5:45)
 * 
 * the test is done 
 * we have created the first modification in the game and it work 
 * what now 
 * well we need to figure out how to actually get all of the file to work in .class / jar file 
 * and expend the mod to encompass to all of the condition 
 * the hardest part to 0.10 is done and it shouldn't take long to get to 0.1.0
 * 
 */