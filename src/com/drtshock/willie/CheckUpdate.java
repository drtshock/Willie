/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drtshock.willie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author drtshock
 */
public class CheckUpdate {

    public static String[] getUpdate(String slug) throws IOException {
        slug = slug.toLowerCase();
        String[] ret = new String[2];
        URL url = new URL("http://api.bukget.org/3/plugins/bukkit/" + slug + "/latest");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
        }
        catch(UnknownHostException e) {
            throw new IOException();
        }
        JSONObject json;
        try {
            json = new JSONObject(in.readLine()).getJSONArray("versions").getJSONObject(0);
            ret[0] = json.getString("dbo_version");
            ret[1] = json.getString("link");
        } catch(JSONException e) {
            throw new IOException();
        }
        in.close();
        return ret;
    }
}
