package br.com.extractor.ygops.util;

import java.util.ArrayList;
import java.util.List;

public class ColorGenerator {

    private List<Color> colorsList;

    public ColorGenerator() {
        colorsList = getList();
    }

    public int getColor(int key) {
        return colorsList.get(key).getColor();
    }

    public List<Color> getList() {
        colorsList = new ArrayList<>();
        colorsList.add(new Color(0, 0xff90a4ae));
        colorsList.add(new Color(1, 0xffe57373));
        colorsList.add(new Color(2, 0xfff06292));
        colorsList.add(new Color(3, 0xffba68c8));
        colorsList.add(new Color(4, 0xff9575cd));
        colorsList.add(new Color(5, 0xff7986cb));
        colorsList.add(new Color(6, 0xff64b5f6));
        colorsList.add(new Color(7, 0xff4fc3f7));
        colorsList.add(new Color(8, 0xff4dd0e1));
        colorsList.add(new Color(9, 0xff4db6ac));
        colorsList.add(new Color(10, 0xff81c784));
        colorsList.add(new Color(11, 0xffaed581));
        colorsList.add(new Color(12, 0xffff8a65));
        colorsList.add(new Color(13, 0xffd4e157));
        colorsList.add(new Color(14, 0xffffd54f));
        colorsList.add(new Color(15, 0xffffb74d));
        colorsList.add(new Color(16, 0xffa1887f));
        return colorsList;
    }

    public int[] getColors(){
        int[] colors = {0xff90a4ae, 0xffe57373, 0xfff06292, 0xffba68c8, 0xff9575cd, 0xff7986cb, 0xff64b5f6, 0xff4fc3f7, 0xff4dd0e1, 0xff4db6ac};
        return colors;
    }

    public class Color {
        private int id;
        private int color;

        public Color() {
        }

        public Color(int id, int color) {
            this.color = color;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
