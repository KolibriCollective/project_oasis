/*
 * this a been taken from the mod UnkownSkies and slightly modified
 * this function was made by Tartiflette
 */
import com.fs.starfarer.api.Global;

public class OS_txt { 
    private final String category = "OS_Planetary_Condition_desc";
    
    public static String txt(String id){
        return Global.getSettings().getString(category, id);
    }
}