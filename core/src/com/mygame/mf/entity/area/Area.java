package com.mygame.mf.entity.area;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public class Area {
    Model floorModel;
    ModelInstance floorInstance;
    btCollisionShape collisionShape;

    btRigidBody.btRigidBodyConstructionInfo constructionInfo;
    btRigidBody rigidBody;

    public Area() {
        Vector3 size = new Vector3(50f, 1f, 50f);

        floorModel = new ModelBuilder().createBox(size.x, size.y, size.z, new Material(ColorAttribute.createDiffuse(Color.DARK_GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        floorInstance = new ModelInstance(floorModel);
        collisionShape = new btBoxShape(size);

        Vector3 localInertia = new Vector3(0, 0, 0);
        constructionInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, collisionShape, localInertia);
        rigidBody = new btRigidBody(constructionInfo);
        rigidBody.translate(new Vector3(0, -5f, 0));
    }

    public ModelInstance getModelInstance(){
        return this.floorInstance;
    }

    public btRigidBody getRigidBody(){
        return this.rigidBody;
    }
}
