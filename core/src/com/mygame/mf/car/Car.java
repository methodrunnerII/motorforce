package com.mygame.mf.car;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Car {
    ModelInstance modelInstance;
    Wheel wlf, wrf, wlb, wrb;

    public Car(){
        modelInstance = new ModelInstance(new ModelBuilder().createBox(1f, 0.5f, 2f, new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

        wlf = new Wheel();
        wrf = new Wheel();
        wlb = new Wheel();
        wrb = new Wheel();

    }

    public Car withModel(Model model){
        setModelInstance(model);
        return this;
    }

    public void setModelInstance(Model model){
        this.modelInstance = new ModelInstance(model);
    }

    public ModelInstance getModelInstance(){
        return modelInstance;
    }

    public void render(ModelBatch mb){
        mb.render(modelInstance);
    }
}
