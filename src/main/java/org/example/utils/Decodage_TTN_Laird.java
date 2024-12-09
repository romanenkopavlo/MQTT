package org.example.utils;

import com.google.gson.Gson;
import org.example.pojo.Response;

import java.util.Base64;

public class Decodage_TTN_Laird {
    String json = null;
    Response pojoTTN3;

    public Decodage_TTN_Laird(String json) {
        this.json = json;
        Gson gson = new Gson();
        pojoTTN3 = gson.fromJson(json, Response.class);
    }

    public String getUplinkMessage() {
        byte [] decodedBytes = Base64.getDecoder().decode(pojoTTN3.getUplinkMessage().getFrmPayload().toString());
        String batterie = null;
        switch (decodedBytes[5]) {
            case 0:
                batterie = "0-5%";
                break;
            case 1:
                batterie = "5-20%";
                break;
            case 2:
                batterie = "20-40%";
                break;
            case 3:
                batterie = "40-60%";
                break;
            case 4:
                batterie = "60-80%";
                break;
            case 5:
                batterie = "80-100%";
                break;
        }
        String decodedString = "Temperature: " + decodedBytes[14] + "." + decodedBytes[13] + "°C. " + "Batterie: " + batterie;
        return decodedString;
    }
}
