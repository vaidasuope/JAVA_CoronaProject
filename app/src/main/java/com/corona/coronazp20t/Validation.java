package com.corona.coronazp20t;

//cia susikuriam atskira klase (atskira failiuka), kad galetume ja iskviesti,
// cia susirasysim koda, kaip ir C++ panasiai su funkcijomis darome

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    //public yra ribos/matomumas, static priklauso funkciniam programavimui - nereikia kurti naujo objekto,
    // kad pasinaudotume kita biblioteka/kazkokiu objektu, final - dydis galutinis, jo reiksme nesikeis, kokia priskyrem, tokia bus
    //USERNAME_REGEX_PATTERN - sukurem username regular expresion pattern - sablona pasivadinom kaip norim

    public static final String USERNAME_REGEX_PATTERN="^[a-zA-Z]{3,20}$";

    public static boolean isValidUsername(String username){ //cia funkcija mes apsirasom, kaip void, tai cia boolean
        Pattern pattern=Pattern.compile(USERNAME_REGEX_PATTERN); //sukuriamas sablonas pagal musu apsirasytas taisykles (string kur susikurem virsuj)
        Matcher matcher=pattern.matcher(username); //pagal susikurta sablona palyginami vartotojo ivesti duomenys
        return matcher.find(); //grazina true, jeigu atitinka sablona, false - priesingu atveju, kai neatitinka
    }
}
