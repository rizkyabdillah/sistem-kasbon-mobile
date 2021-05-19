package com.android.kasbon.sistem.utilitas;

import java.util.Random;

public class UtilsSingleton {

    private static final String NUMBER = "123456789";
    private static final Random random = new Random();

    public static String getRandom(String prefix, int length) {
        StringBuilder character = new StringBuilder(prefix);
        for(int i = 0; i < length; i++) {
            character.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        }
        return character.toString().toString();
    }
}
