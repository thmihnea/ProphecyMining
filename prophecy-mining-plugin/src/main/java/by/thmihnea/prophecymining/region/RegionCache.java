package by.thmihnea.prophecymining.region;

import java.util.HashSet;
import java.util.Set;

public class RegionCache {

    private static final Set<CuboidRegion> cachedRegions = new HashSet<>();

    public static void addRegion(CuboidRegion cuboidRegion) {
        cachedRegions.add(cuboidRegion);
    }

    public static void removeRegion(CuboidRegion cuboidRegion) {
        cachedRegions.remove(cuboidRegion);
    }

    public static CuboidRegion getRegionByName(String name) {
        for (CuboidRegion cuboidRegion : cachedRegions) {
            if (cuboidRegion.getName().equalsIgnoreCase(name))
                return cuboidRegion;
        }
        return null;
    }

    public static int getSize() {
        return cachedRegions.size();
    }

    public static Set<CuboidRegion> getCachedRegions() {
        return cachedRegions;
    }
}
