package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.Stundenplan;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PresentationMode {
    //TODO: Start Button entfernen, Ghost Busters Schränke zu machen, Login Nachricht entfernen (weil Ausgang nicht offensichtlich), Türen öffnen

    static Location ghostBustersLocation1 = new Location(SchoolWars.WORLD, 38.0, 94.0, 200.0);
    static Location ghostBustersLocation2 = new Location(SchoolWars.WORLD, 38.0, 93.0, 200.0);
    static Block ghostBustersBlock1 = ghostBustersLocation1.getBlock();
    static Block ghostBustersBlock2 = ghostBustersLocation2.getBlock();

    static Location startButtonLocation = new Location(SchoolWars.WORLD, -32.0, 81.0, 179.0);
    static Location startButtonOff = new Location(SchoolWars.WORLD, -32.0, 77.0, 179.0);
    static Location startButtonOn = new Location(SchoolWars.WORLD, -32.0, 78.0, 179.0);

    public static void reloadPresentationMode(){
        if(SchoolWars.presentationMode){
            ghostBustersBlock1.setType(Material.STRIPPED_OAK_LOG);
            ghostBustersBlock2.setType(Material.STRIPPED_OAK_LOG);

            replaceArea(startButtonOff);

            Stundenplan.updateStundenplan(true);
            Stundenplan.updateStundenplan(false);
            Lehrer.updateLehrerPosition(false);

        } else {
            ghostBustersBlock1.setType(Material.AIR);
            ghostBustersBlock2.setType(Material.AIR);

            replaceArea(startButtonOn);
        }
    }

    private static void replaceArea(Location fromLocation) {
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                for(int k = 0; k < 2; k++){
                    Location start = fromLocation;
                    Location destination = startButtonLocation;
                    destination.add(i, 0, j).getBlock().setType(start.add(i, 0, j).getBlock().getType());
                    destination.add(i, 0, j).getBlock().setBlockData(start.add(i, 0, j).getBlock().getBlockData());
                }
            }
        }
    }

}
