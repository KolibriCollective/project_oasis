package com.fs.starfarer.api.campaign;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.BaseHazardCondition;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.econ.impl.PopulationAndInfrastructure;
import com.fs.starfarer.api.OS_txt;
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


public class thin_athmosphere extends BaseHazardCondition{

    public final float ACCESSIBILITY_BONUS = 5f;
    public final float ORE_BONUS = 1;
    public final float HEAVY_MACHINERY_DEMAND = 1;
    public final float METALS_DEMAND = 1; 


    public void apply(String id) {

        .getAccessibilityBonus().modifyFlat(id,ACCESSIBILITY_BONUS/100, txt("thin_atmosphere_ACCESSIBILTY_BONUS"));

        Industry industry = market.getIndustry(Industries.POPULATION);
        if(industry!=null){
            industry.getDemand(Commodities.METALS).getQuantity().modifyMult(id + "_0", Commodities.METALS, METALS_DEMAND, txt("thin_athmophere_HEAVY_MACHINERUY_DEMAND"));
            industry.getDemand(Commodities.HEAVY_MACHINERY).getQuantity().modifyMult(id + "_0", Commodities.HEAVY_MACHINERY, HEAVY_MACHINERY_DEMAND, txt("thin_athmophere_METAL_DEMAND"));
        }
        Industry industry2 = market.getIndustry(Industries.MINING);
        if(industry2!=null){
            if (industry2.isFunctional()) {
                industry2.Supply(id + "_0",Commodities.ORE, ORE_BONUS,"");
                industry2.Supply(id + "_0",Commodities.RARE_ORE, ORE_BONUS,"");
                industry2.Supply(id + "_0",Commodities.ORGANICS, ORE_BONUS,"");
                industry2.Supply(id + "_0",Commodities.VOLATILES, ORE_BONUS,"");
            } 
            
            else {
                industry2.getSupply(Commodities.ORE).getQuantity().unmodifyFlat(id + "_0");
                industry2.getSupply(Commodities.RARE_ORE).getQuantity().unmodifyFlat(id + "_0");
                industry2.getSupply(Commodities.ORGANICS).getQuantity().unmodifyFlat(id + "_0");
                industry2.getSupply(Commodities.VOLATILES).getQuantity().unmodifyFlat(id + "_0");
            }
        }

    }
    public void unapply(String id) {
        this.market.getAccessbilityMod().unmodifyFlat(id);
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
 */