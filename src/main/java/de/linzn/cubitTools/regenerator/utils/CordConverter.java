/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubitTools.regenerator.utils;


import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CordConverter {

    private static final Pattern REGION_FILE = Pattern.compile("r\\.(-?\\d+)\\.(-?\\d+)\\.mca");


    public static int regionToChunk(int region) {
        return region << 5;
    }


    public static int regionToBlock(int region) {
        return region << 9;
    }


    public static int chunkToRegion(int chunk) {
        return chunk >> 5;
    }


    public static int chunkToBlock(int chunk) {
        return chunk << 4;
    }


    public static int blockToRegion(int block) {
        return block >> 9;
    }


    public static int blockToChunk(int block) {
        return block >> 4;
    }


    public static Pair<Integer, Integer> getRegionChunkCoords(String regionFileName) {
        Matcher matcher = REGION_FILE.matcher(regionFileName);
        if (!matcher.find()) {
            throw new IllegalArgumentException(regionFileName + " does not match the region file name format!");
        }

        return new ImmutablePair<>(regionToChunk(Integer.parseInt(matcher.group(1))),
                regionToChunk(Integer.parseInt(matcher.group(2))));
    }

}
