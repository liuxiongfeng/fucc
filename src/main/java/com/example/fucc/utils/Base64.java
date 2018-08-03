package com.example.fucc.utils;

/**
 * Created by QP on 2017/10/17.
 */
public class Base64 {
    private static final byte[] a = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] b = new byte[128];

    static {
        int var0;
        for(var0 = 0; var0 < 128; ++var0) {
            b[var0] = -1;
        }

        for(var0 = 65; var0 <= 90; ++var0) {
            b[var0] = (byte)(var0 - 65);
        }

        for(var0 = 97; var0 <= 122; ++var0) {
            b[var0] = (byte)(var0 - 97 + 26);
        }

        for(var0 = 48; var0 <= 57; ++var0) {
            b[var0] = (byte)(var0 - 48 + 52);
        }

        b[43] = 62;
        b[47] = 63;
    }

    public Base64() {
    }

    public static byte[] encode(byte[] var0) {
        int var2 = var0.length % 3;
        byte[] var1;
        if(var2 == 0) {
            var1 = new byte[4 * var0.length / 3];
        } else {
            var1 = new byte[4 * (var0.length / 3 + 1)];
        }

        int var3 = var0.length - var2;
        int var7 = 0;

        int var8;
        for(var8 = 0; var7 < var3; var8 += 4) {
            int var4 = var0[var7] & 255;
            int var5 = var0[var7 + 1] & 255;
            int var6 = var0[var7 + 2] & 255;
            var1[var8] = a[var4 >>> 2 & 63];
            var1[var8 + 1] = a[(var4 << 4 | var5 >>> 4) & 63];
            var1[var8 + 2] = a[(var5 << 2 | var6 >>> 6) & 63];
            var1[var8 + 3] = a[var6 & 63];
            var7 += 3;
        }

        int var10;
        switch(var2) {
            case 0:
            default:
                break;
            case 1:
                var10 = var0[var0.length - 1] & 255;
                var7 = var10 >>> 2 & 63;
                var8 = var10 << 4 & 63;
                var1[var1.length - 4] = a[var7];
                var1[var1.length - 3] = a[var8];
                var1[var1.length - 2] = 61;
                var1[var1.length - 1] = 61;
                break;
            case 2:
                var10 = var0[var0.length - 2] & 255;
                int var11 = var0[var0.length - 1] & 255;
                var7 = var10 >>> 2 & 63;
                var8 = (var10 << 4 | var11 >>> 4) & 63;
                int var9 = var11 << 2 & 63;
                var1[var1.length - 4] = a[var7];
                var1[var1.length - 3] = a[var8];
                var1[var1.length - 2] = a[var9];
                var1[var1.length - 1] = 61;
        }

        return var1;
    }

    public static byte[] decode(byte[] var0) {
        var0 = a(var0);
        byte[] var1;
        if(var0[var0.length - 2] == 61) {
            var1 = new byte[(var0.length / 4 - 1) * 3 + 1];
        } else if(var0[var0.length - 1] == 61) {
            var1 = new byte[(var0.length / 4 - 1) * 3 + 2];
        } else {
            var1 = new byte[var0.length / 4 * 3];
        }

        int var6 = 0;

        byte var2;
        byte var3;
        byte var4;
        byte var5;
        for(int var7 = 0; var6 < var0.length - 4; var7 += 3) {
            var2 = b[var0[var6]];
            var3 = b[var0[var6 + 1]];
            var4 = b[var0[var6 + 2]];
            var5 = b[var0[var6 + 3]];
            var1[var7] = (byte)(var2 << 2 | var3 >> 4);
            var1[var7 + 1] = (byte)(var3 << 4 | var4 >> 2);
            var1[var7 + 2] = (byte)(var4 << 6 | var5);
            var6 += 4;
        }

        if(var0[var0.length - 2] == 61) {
            var2 = b[var0[var0.length - 4]];
            var3 = b[var0[var0.length - 3]];
            var1[var1.length - 1] = (byte)(var2 << 2 | var3 >> 4);
        } else if(var0[var0.length - 1] == 61) {
            var2 = b[var0[var0.length - 4]];
            var3 = b[var0[var0.length - 3]];
            var4 = b[var0[var0.length - 2]];
            var1[var1.length - 2] = (byte)(var2 << 2 | var3 >> 4);
            var1[var1.length - 1] = (byte)(var3 << 4 | var4 >> 2);
        } else {
            var2 = b[var0[var0.length - 4]];
            var3 = b[var0[var0.length - 3]];
            var4 = b[var0[var0.length - 2]];
            var5 = b[var0[var0.length - 1]];
            var1[var1.length - 3] = (byte)(var2 << 2 | var3 >> 4);
            var1[var1.length - 2] = (byte)(var3 << 4 | var4 >> 2);
            var1[var1.length - 1] = (byte)(var4 << 6 | var5);
        }

        return var1;
    }

    public static byte[] decode(String var0) {
        var0 = a(var0);
        byte[] var1;
        if(var0.charAt(var0.length() - 2) == 61) {
            var1 = new byte[(var0.length() / 4 - 1) * 3 + 1];
        } else if(var0.charAt(var0.length() - 1) == 61) {
            var1 = new byte[(var0.length() / 4 - 1) * 3 + 2];
        } else {
            var1 = new byte[var0.length() / 4 * 3];
        }

        int var6 = 0;

        byte var2;
        byte var3;
        byte var4;
        byte var5;
        for(int var7 = 0; var6 < var0.length() - 4; var7 += 3) {
            var2 = b[var0.charAt(var6)];
            var3 = b[var0.charAt(var6 + 1)];
            var4 = b[var0.charAt(var6 + 2)];
            var5 = b[var0.charAt(var6 + 3)];
            var1[var7] = (byte)(var2 << 2 | var3 >> 4);
            var1[var7 + 1] = (byte)(var3 << 4 | var4 >> 2);
            var1[var7 + 2] = (byte)(var4 << 6 | var5);
            var6 += 4;
        }

        if(var0.charAt(var0.length() - 2) == 61) {
            var2 = b[var0.charAt(var0.length() - 4)];
            var3 = b[var0.charAt(var0.length() - 3)];
            var1[var1.length - 1] = (byte)(var2 << 2 | var3 >> 4);
        } else if(var0.charAt(var0.length() - 1) == 61) {
            var2 = b[var0.charAt(var0.length() - 4)];
            var3 = b[var0.charAt(var0.length() - 3)];
            var4 = b[var0.charAt(var0.length() - 2)];
            var1[var1.length - 2] = (byte)(var2 << 2 | var3 >> 4);
            var1[var1.length - 1] = (byte)(var3 << 4 | var4 >> 2);
        } else {
            var2 = b[var0.charAt(var0.length() - 4)];
            var3 = b[var0.charAt(var0.length() - 3)];
            var4 = b[var0.charAt(var0.length() - 2)];
            var5 = b[var0.charAt(var0.length() - 1)];
            var1[var1.length - 3] = (byte)(var2 << 2 | var3 >> 4);
            var1[var1.length - 2] = (byte)(var3 << 4 | var4 >> 2);
            var1[var1.length - 1] = (byte)(var4 << 6 | var5);
        }

        return var1;
    }

    private static byte[] a(byte[] var0) {
        byte[] var1 = new byte[var0.length];
        int var2 = 0;

        for(int var3 = 0; var3 < var0.length; ++var3) {
            if(a(var0[var3])) {
                var1[var2++] = var0[var3];
            }
        }

        byte[] var4 = new byte[var2];
        System.arraycopy(var1, 0, var4, 0, var2);
        return var4;
    }

    private static String a(String var0) {
        StringBuffer var1 = new StringBuffer();
        int var2 = var0.length();

        for(int var3 = 0; var3 < var2; ++var3) {
            if(a((byte)var0.charAt(var3))) {
                var1.append(var0.charAt(var3));
            }
        }

        return var1.toString();
    }

    private static boolean a(byte var0) {
        return var0 == 61?true:(var0 >= 0 && var0 < 128?b[var0] != -1:false);
    }
}
