package com.didi.dts.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class SharedPref {
    // ===========================================================
    // Constants
    // ===========================================================

    public static final String HANDLED_FIRST_TIME = "handled_first_time";
    private static final String PREF_NAME = "com.didi.dts";
    // ===========================================================
    // Fields
    // ===========================================================
    private static SharedPreferences sPref;
    private static PBEParameterSpec sPBEParamSpec;

    // ===========================================================
    // Initialization
    // ===========================================================

    public static void init(Context context) {
        try {
            sPBEParamSpec = new PBEParameterSpec(Settings.Secure.getString(context.getContentResolver(), Settings.System.ANDROID_ID).getBytes("utf-8"), 20);
        } catch (Exception e) {
            Logger.l(e);
        }
        sPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ===========================================================
    // Public methods
    // ===========================================================

    public static void setListener(OnSharedPreferenceChangeListener listener) {
        sPref.registerOnSharedPreferenceChangeListener(listener);
    }

    public static boolean isEncKey(String encKey, String key) {
        return encrypt(key).equals(encKey);
    }

    public static void putString(String name, String value) {
        Logger.l("putString " + name + "=" + value);
        putStringEnc(name, value);
    }

    public static void putLong(String name, long value) {
        putString(name, String.valueOf(value));
    }

    public static void putBoolean(String name, boolean value) {
        putString(name, String.valueOf(value));
    }

    public static void putInt(String name, int value) {
        putString(name, String.valueOf(value));
    }

    public static String getString(String name) {
        return getStringEnc(name);
    }

    public static boolean getBoolean(String name, Boolean def) {
        String value = getStringEnc(name);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Boolean.parseBoolean(value);
            } catch (Exception e) {
                Logger.l(e);
            }
        }
        return def;
    }

    public static long getLong(String name) {
        String value = getStringEnc(name);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
                Logger.l(e);
            }
        }
        return 0;
    }

    public static int getInt(String name, int defaultValue) {
        String value = getStringEnc(name);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                Logger.l(e);
            }
        }
        return defaultValue;
    }

    // ===========================================================
    // Security methods
    // ===========================================================

    private static void putStringEnc(String name, String value) {
        name = encrypt(name);
        value = encrypt(value);

        Editor editor = sPref.edit();
        editor.putString(name, value);
        editor.commit();
    }

    private static String getStringEnc(String name) {
        String key = encrypt(name);
        String value = sPref.getString(key, null);
        if (null != value) {
            value = decrypt(value);
        }
        return value;
    }


    private static String encrypt(String value) {
        try {
            char[] sod = new char[32];
            for (int i = 0; i < sod.length; i++) {
                sod[i] = (char) (40 + i * 2 - i / 2);
            }

            final byte[] bytes = value != null ? value.getBytes("utf-8") : new byte[0];
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(sod));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, sPBEParamSpec);
            return new String(Base64.encode(pbeCipher.doFinal(bytes), Base64.NO_WRAP), "utf-8");
        } catch (Exception e) {
            Logger.l(e);
        }
        return null;
    }

    private static String decrypt(String value) {
        try {
            char[] sod = new char[32];
            for (int i = 0; i < sod.length; i++) {
                sod[i] = (char) (40 + i * 2 - i / 2);
            }

            final byte[] bytes = value != null ? Base64.decode(value, Base64.DEFAULT) : new byte[0];
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(sod));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, sPBEParamSpec);
            return new String(pbeCipher.doFinal(bytes), "utf-8");
        } catch (Exception e) {
            Logger.l(e);
        }
        return null;
    }
}
