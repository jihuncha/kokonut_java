package com.huni.engineer.kokonutjava.common;

public class MediaUtil {

    public static class MyImage {
        public long id;
        public String name;
        public String path;

        public MyImage() { }
        public MyImage(long id, String name, String path) {
            this.id = id;
            this.name = name;
            this.path = path;
        }

        @Override
        public String toString() {
            return "MyImage{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }
    }
}
