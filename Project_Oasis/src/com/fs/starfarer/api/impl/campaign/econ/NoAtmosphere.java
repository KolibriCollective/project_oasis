package com.fs.starfarer.api.impl.campaign.econ;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.util.Misc;

public class NoAtmosphere extends BaseHazardCondition {
    public float noAtmosphere_ACCESSIBLITY_BONUS = 10f;
    public int noAtmosphere_CREW_BONUS = 1;
    public int noAtmosphere_suplies_demand = 1 ;
    public int noAtmosphere_metal_demand = 1;
    public int noAtmosphere_heavyMachinery_demenad = 1; 
    public int NoAtmosphere_drugs_demand = 1; 

    public String no_atmosphere_desc = "No Atmosphere";


    public void apply(String id) {
        market.getHazard().modifyFlat(id,50/100f,no_atmosphere_desc);
        market.getAccessibilityMod().modifyFlat(id,noAtmosphere_ACCESSIBLITY_BONUS/100,no_atmosphere_desc);

        Industry industry = market.getIndustry(Industries.POPULATION);

        if (industry != null) {
            int size = market.getSize();
            
             int noAtmosphere_CREW_BONUS = (size - 3);
             int noAtmosphere_suplies_demand = (size - 3);
             int noAtmosphere_metal_demand = (size - 3);
             int noAtmosphere_heavyMachinery_demenad = (size - 3); 
             int NoAtmosphere_drugs_demand = (size - 3);

            industry.getSupply(Commodities.CREW).getQuantity().modifyFlat(id + "_0", noAtmosphere_CREW_BONUS,no_atmosphere_desc);
            industry.getDemand(Commodities.SUPPLIES).getQuantity().modifyFlat(id + "_0", noAtmosphere_suplies_demand, no_atmosphere_desc);
            industry.getDemand(Commodities.METALS).getQuantity().modifyFlat(id + "_0", noAtmosphere_metal_demand, no_atmosphere_desc);
            industry.getDemand(Commodities.HEAVY_MACHINERY).getQuantity().modifyFlat(id + "_0", noAtmosphere_heavyMachinery_demenad, no_atmosphere_desc);
            industry.getDemand(Commodities.DRUGS).getQuantity().modifyFlat(id + "_0", NoAtmosphere_drugs_demand, no_atmosphere_desc);
        }
        industry = market.getIndustry(Industries.PATROLHQ);
        if (industry != null) { 
            industry.getSupply(Commodities.CREW).getQuantity().modifyFlat(id + "_0", noAtmosphere_CREW_BONUS,no_atmosphere_desc);
        }
        industry = market.getIndustry(Industries.WAYSTATION);
        if (industry!=null){
             market.getAccessibilityMod().modifyFlat(id,noAtmosphere_ACCESSIBLITY_BONUS/100,no_atmosphere_desc);
        }

        
    }

    public void unapply(String id) {
        market.getAccessibilityMod().unmodifyFlat(id);
    }

    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
         super.createTooltipAfterDescription(tooltip, expanded);
         tooltip.addPara("%s accessibility", 10.0F, Misc.getHighlightColor(), new String[]{"+10%"});
         tooltip.addPara("%s Heavy Machinery (Population & Infrastructure)", 1, Misc.getHighlightColor(), new String[]{"+1"});
         tooltip.addPara("%s Metal (Population & Infrastructure)", 1, Misc.getHighlightColor(), new String[]{"+1"});
         tooltip.addPara("%s Supplies (Population & Infrastructure)",1,Misc.getHighlightColor(),new String[]{"+1"});
         tooltip.addPara("%s Drugs (Population & Infrastructure)",1,Misc.getHighlightColor(),new String[]{"+1"});
    }

}
