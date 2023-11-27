package com.ohuang.okhttp.util.hookSignature;

import com.ohuang.okhttp.util.PackageSignature;

import java.util.List;

public class BiliGameSignature extends PackageSignature {
    @Override
    public String packageName() {
        return "com.bsgamesdk.asdemo";
    }

    @Override
    public byte[] getDefaultSignature() {
        return new byte[]{48, -126, 3, 83, 48, -126, 2, 59, -96, 3, 2, 1, 2, 2, 4, 40, -65, -33, 88, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 48, 89, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49, 11, 48, 9, 6, 3, 85, 4, 8, 19, 2, 83, 72, 49, 11, 48, 9, 6, 3, 85, 4, 7, 19, 2, 83, 72, 49, 13, 48, 11, 6, 3, 85, 4, 10, 19, 4, 103, 97, 109, 101, 49, 13, 48, 11, 6, 3, 85, 4, 11, 19, 4, 103, 97, 109, 101, 49, 18, 48, 16, 6, 3, 85, 4, 3, 12, 9, 103, 97, 109, 101, 95, 100, 101, 109, 111, 48, 32, 23, 13, 49, 54, 48, 56, 50, 50, 48, 51, 48, 50, 53, 53, 90, 24, 15, 50, 49, 49, 54, 48, 55, 50, 57, 48, 51, 48, 50, 53, 53, 90, 48, 89, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49, 11, 48, 9, 6, 3, 85, 4, 8, 19, 2, 83, 72, 49, 11, 48, 9, 6, 3, 85, 4, 7, 19, 2, 83, 72, 49, 13, 48, 11, 6, 3, 85, 4, 10, 19, 4, 103, 97, 109, 101, 49, 13, 48, 11, 6, 3, 85, 4, 11, 19, 4, 103, 97, 109, 101, 49, 18, 48, 16, 6, 3, 85, 4, 3, 12, 9, 103, 97, 109, 101, 95, 100, 101, 109, 111, 48, -126, 1, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 1, 15, 0, 48, -126, 1, 10, 2, -126, 1, 1, 0, -118, 66, -114, 59, -24, 44, -24, -45, 45, 126, -90, 3, -19, 113, 21, -88, 88, 119, 74, -13, 111, -71, 21, 108, 19, -127, -30, -128, -99, 68, 2, 94, 53, -64, -17, -111, -8, -74, 19, 54, 90, -32, 93, 9, 54, 34, 96, -90, 78, -68, 118, 64, -80, 57, 99, -7, 106, -111, -103, -86, 56, 42, 58, -9, 1, 101, -115, 32, 108, -125, -107, 104, -18, 12, 119, -127, -45, -26, 53, -105, 71, 13, 110, 91, -94, -21, 8, 89, 114, -71, 121, -10, -57, 8, 50, 94, 97, 99, -66, 109, 42, 62, -12, -125, 113, -45, 6, 35, -87, -33, -89, -40, 104, -2, -104, -56, 127, -73, 4, 58, 38, 78, -34, -56, 79, 89, -49, -39, 22, -81, -95, 17, -61, 17, 86, 7, -52, 113, -76, 97, -37, 13, 118, 83, 125, -4, -110, -101, 85, 94, 12, 33, 13, -43, -20, 60, 57, 12, 103, -128, -115, 103, -103, -95, 54, 30, 100, -27, -60, -97, 75, -43, 61, 38, 105, -63, 117, 59, -30, -20, 66, 7, 38, 47, 55, 1, -95, 44, 57, 86, -18, -46, -25, 97, 115, -35, -108, 118, -68, 67, -126, -97, -15, 36, 38, 62, 75, -51, -69, 125, 73, 90, -89, -104, -62, -115, -25, 64, 110, 118, 73, 12, -100, -61, -115, 26, 85, -39, 109, -2, -12, -115, -95, 48, 102, 45, -57, 74, 41, -103, 59, -89, 68, -66, -106, -10, 78, 24, 113, 91, -15, 34, 113, 113, -18, -55, 2, 3, 1, 0, 1, -93, 33, 48, 31, 48, 29, 6, 3, 85, 29, 14, 4, 22, 4, 20, 119, -93, 6, -91, 106, -100, -87, 119, 107, -88, -103, 19, -54, 62, -94, -92, 22, -52, 44, -64, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 3, -126, 1, 1, 0, -127, -22, -93, 56, 88, -86, -123, -119, 40, 42, -14, 106, -63, 105, -61, -106, -59, 0, -105, 19, -96, -68, -120, -102, -74, 93, 59, 62, -95, -84, 72, 65, 28, 0, 14, 14, -103, -97, -9, -65, 112, -12, 65, 43, -80, 114, -10, -73, 76, 113, 91, 49, -9, -99, -109, 117, 26, 36, -13, 25, 58, 48, -98, -122, 18, 103, 90, -94, 54, -10, 30, 84, 32, 84, -87, 1, 117, 105, 0, -29, -17, 52, 102, -33, -76, 44, 3, -80, -13, -27, 102, 34, -16, 21, -31, 48, -121, 85, -7, 36, 69, -100, -57, 39, 57, -72, -28, 45, 111, 86, 36, 107, -111, 56, -10, 50, -15, 18, 44, 31, -12, -74, -10, 77, 88, -12, -16, -4, -71, -51, 111, 29, -82, 112, -53, -115, -60, 73, -107, 126, 46, 96, -96, -26, -109, 62, 54, -1, 67, 0, -91, -72, 77, 38, 111, -12, -92, -42, 48, 72, 37, 18, -63, -100, -5, -77, -19, -60, 59, 106, -40, 40, 1, -105, 95, -83, -88, 64, -40, -125, -30, 110, 83, -61, -84, 111, 47, -99, -10, 65, 125, 2, -99, 45, 57, 96, -127, -88, 51, -99, 29, 10, -90, -39, 76, 22, -46, -44, 123, -117, 116, -77, 101, 1, 66, -114, 73, 68, 92, 44, 93, -93, 45, 94, -88, 19, -49, -74, 3, -47, -59, -94, -127, 31, 96, -80, -100, 109, -108, -37, 90, -88, 82, 83, 31, 113, -112, -106, 0, -122, -65, -30, -64, -112, -126, -47};
    }

    @Override
    public void initSignatures(List<byte[]> signatures) {

    }
}