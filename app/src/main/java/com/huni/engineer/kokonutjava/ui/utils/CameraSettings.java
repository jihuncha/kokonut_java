package com.huni.engineer.kokonutjava.ui.utils;

public class CameraSettings {

    public static class Resolution {
        public static final int I_2160p = 0;
        public static final int I_1440p = 1;
        public static final int I_1080p = 2;
        public static final int I_720p  = 3;
        public static final int I_480p  = 4;

        public static final int I_MAX = 999;
        public static final int I_MIN =  -1;

        public static int getWidth(int code) {
            switch (code) {
                case I_2160p: return 2160;
                case I_1440p: return 1440;
                case I_1080p: return 1080;
                case I_720p : return 720;
                case I_480p : return 480;
            }

            return 480;
        }
        public static int getHeight(int code) {
            switch (code) {
                case I_2160p: return 3840;
                case I_1440p: return 2560;
                case I_1080p: return 1920;
                case I_720p : return 1280;
                case I_480p : return 854;
            }

            return 854;
        }
        public static int getX(int code) {
            switch (code) {
                case I_2160p: return 9;
                case I_1440p: return 9;
                case I_1080p: return 9;
                case I_720p : return 9;
                case I_480p : return 4;
            }

            return 9;
        }

        public static int getY(int code) {
            switch (code) {
                case I_2160p: return 16;
                case I_1440p: return 16;
                case I_1080p: return 16;
                case I_720p : return 16;
                case I_480p : return 5;
            }

            return 16;
        }

        public static String valueOf(int code) {
            switch (code) {
                case I_2160p: return "4K(2160x3840)";
                case I_1440p: return "2K(1440x2560)";
                case I_1080p: return "FHD(1080x1920)";
                case I_720p : return "HD(720x1280)";
                case I_480p : return "WVGA(480x800)";
            }

            return "N/A(" + code + ")";
        }
    }
}
