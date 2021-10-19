package com.mygame.mf.entity;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import java.util.ArrayList;

public class Entity {
    Entity parent;
    ArrayList<Entity> children;

    protected Transform transform;
    protected ModelInstance modelInstance;
    protected btRigidBody rigidBody;

    public Entity(Entity parent){
        children = new ArrayList<Entity>();
        if(parent != null) {
            this.parent = parent;
            parent.children.add(this);
        }
        transform = new Transform(this);
    }

    public Transform getTransform(){
        return transform;
    }

    public void setPosition(Vector3 p){
        transform.setPosition(p);
        if(modelInstance != null){
            modelInstance.transform.setTranslation(p);
        }
        if(rigidBody != null){
            rigidBody.setWorldTransform(transform.getAbsoluteMatrix());
        }
    }

    public void setRotation(Vector3 v, float degrees){
        transform.setRotation(v, degrees);
        if(modelInstance != null){
            modelInstance.transform.setToRotation(v, degrees);
        }
        if(rigidBody != null){
            rigidBody.setWorldTransform(transform.getAbsoluteMatrix());
        }
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public void setModelInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }

    public btRigidBody getRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(btRigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    public void update(float delta){
        if(rigidBody != null){
            transform.setMatrix(rigidBody.getWorldTransform());
        }
        if(modelInstance != null){
            modelInstance.transform.set(transform.getAbsoluteMatrix());
        }

    }

    public void render(ModelBatch b, Environment e){
        if(modelInstance != null){
            b.render(modelInstance, e);
        }
        for(Entity child : children){
            child.render(b, e);
        }
    }
}
