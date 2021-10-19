package com.mygame.mf.loading;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Models {
    public static Model wheelPlaceholder;

    public static void init(){
        wheelPlaceholder = new ModelBuilder().createBox(0.1f, 0.3f, 0.3f, Materials.diffuseBlack,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    public static ModelInstance getInstance(Model model){
        return new ModelInstance(model);
    }
}
