/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zly.deskclock.worldclock;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Cities {

    public static final String WORLDCLOCK_UPDATE_INTENT = "com.zly.deskclock.worldclock.update";
    private static final String NUMBER_OF_CITIES = "number_of_cities";

    public static void saveCitiesToSharedPrefs(
            SharedPreferences prefs, HashMap<String, com.zly.deskclock.worldclock.CityObj> cities) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUMBER_OF_CITIES, cities.size());
        Collection<com.zly.deskclock.worldclock.CityObj> col = cities.values();
        Iterator<com.zly.deskclock.worldclock.CityObj> i = col.iterator();
        int count = 0;
        while (i.hasNext()) {
            com.zly.deskclock.worldclock.CityObj c = i.next();
            c.saveCityToSharedPrefs(editor, count);
            count++;
        }
        editor.apply();
    }

    public static  HashMap<String, com.zly.deskclock.worldclock.CityObj> readCitiesFromSharedPrefs(SharedPreferences prefs) {
        int size = prefs.getInt(NUMBER_OF_CITIES, -1);
        HashMap<String, com.zly.deskclock.worldclock.CityObj> c = new HashMap<String, com.zly.deskclock.worldclock.CityObj>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                com.zly.deskclock.worldclock.CityObj o = new CityObj(prefs, i);
                if (o.mCityName != null && o.mTimeZone != null) {
                    c.put(o.mCityId, o);
                }
            }
        }
        return c;
    }

    private static void dumpCities(SharedPreferences prefs, String title) {
        int size = prefs.getInt(NUMBER_OF_CITIES, -1);
        Log.d("Cities", "Selected Cities List " + title);
        Log.d("Cities", "Number of cities " + size);
        HashMap<String, com.zly.deskclock.worldclock.CityObj> c = new HashMap<String, com.zly.deskclock.worldclock.CityObj>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                com.zly.deskclock.worldclock.CityObj o = new CityObj(prefs, i);
                if (o.mCityName != null && o.mTimeZone != null) {
                    Log.d("Cities", "Name " + o.mCityName + " tz " + o.mTimeZone);
                }
            }
        }
    }
}
