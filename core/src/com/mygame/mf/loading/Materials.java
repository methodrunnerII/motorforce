package com.mygame.mf.loading;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

public class Materials {
    public final static Material diffuseRed = new Material(ColorAttribute.createDiffuse(Color.RED));
    public final static Material diffuseYellow = new Material(ColorAttribute.createDiffuse(Color.YELLOW));
    public final static Material diffuseGreen = new Material(ColorAttribute.createDiffuse(Color.GREEN));
    public final static Material diffuseBlue = new Material(ColorAttribute.createDiffuse(Color.BLUE));
    public final static Material diffuseBlack = new Material(ColorAttribute.createDiffuse(Color.BLACK));
}
