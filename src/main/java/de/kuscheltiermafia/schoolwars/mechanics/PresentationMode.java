package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.Stundenplan;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;

public class PresentationMode {

    static Location ghostBustersLocation1 = new Location(SchoolWars.WORLD, 38.0, 95.0, 200.0);
    static Location ghostBustersLocation2 = new Location(SchoolWars.WORLD, 38.0, 94.0, 200.0);
    static Block ghostBustersBlock1 = ghostBustersLocation1.getBlock();
    static Block ghostBustersBlock2 = ghostBustersLocation2.getBlock();

    static Location startButtonLocation = new Location(SchoolWars.WORLD, -32.0, 81.0, 179.0);
    static Location startButtonOff = new Location(SchoolWars.WORLD, -32.0, 77.0, 179.0);
    static Location startButtonOn = new Location(SchoolWars.WORLD, -32.0, 78.0, 179.0);

    public static void reloadPresentationMode(){
        if(SchoolWars.presentationMode){
            ghostBustersBlock1.setType(Material.STRIPPED_OAK_LOG);
            ghostBustersBlock2.setType(Material.STRIPPED_OAK_LOG);

            replaceButtonArea(startButtonOff);

            Stundenplan.updateStundenplan(true);
            Stundenplan.updateStundenplan(false);
            Lehrer.updateLehrerPosition(false);

        } else {
            ghostBustersBlock1.setType(Material.AIR);
            ghostBustersBlock2.setType(Material.AIR);

            replaceButtonArea(startButtonOn);

            Lehrer.removeAllLehrer();
        }
    }

    private static void replaceButtonArea(Location fromLocation) {
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                Location offset = new Location(SchoolWars.WORLD, i, 0, j);
                Location destination = startButtonLocation.clone();
                destination.add(offset);
                Location from = fromLocation.clone();
                from.add(offset);
                destination.getBlock().setType(from.getBlock().getType());
                destination.getBlock().setBlockData(from.getBlock().getBlockData());
                if (from.getBlock().getState() instanceof Sign fromSign && destination.getBlock().getState() instanceof Sign toSign) {
                    for (int k = 0; k < 4; k++) {
                        // Zeile als Component holen
                        Component line = fromSign.getSide(Side.FRONT).line(k);
                        // Zeile auf das Ziel-Schild setzen
                        toSign.getSide(Side.FRONT).line(k, line);
                    }
                    toSign.update();
                }

            }
        }
    }

}
