package com.mygame.mf.area;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

public class Area {
    Model floorModel;
    ModelInstance floorInstance;
    btCollisionShape collisionShape;
    btCollisionObject collisionObject;

    public Area(){
        Vector3 size = new Vector3(10f, 1f, 10f);

        floorModel = new ModelBuilder().createBox(size.x, size.y, size.z, new Material(ColorAttribute.createDiffuse(Color.RED)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        floorInstance = new ModelInstance(floorModel);
        collisionShape = new btBoxShape(size);
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(collisionShape);
        collisionObject.setWorldTransform(floorInstance.transform);
    }



}
