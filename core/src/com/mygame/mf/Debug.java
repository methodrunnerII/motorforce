package com.mygame.mf;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Debug {
    static String text;
    static BitmapFont font = new BitmapFont();
    public static void first(){
        text = "";
    }

    public static void addLineToText(String t){
        text += t + "\n";
    }

    public static String getText(){
        return text;
    }

    public static void drawText(SpriteBatch batch){
        font.draw(batch, Debug.getText(), 20, 700);
    }

    public static void drawDirection(ShapeRenderer r, float x0, float y0, float x1, float y1){
        r.circle(x0, y0, 30);
        r.line(x0, y0, x0+x1, y0+y1);
    }
}
