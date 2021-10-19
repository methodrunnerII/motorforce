package com.mygame.mf.entity.car;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.mygame.mf.entity.Entity;
import com.mygame.mf.entity.Transform;

import static java.lang.Math.abs;

public class Car extends Entity {
    public static final int FWD = 0;
    public static final int RWD = 1;
    public static final int AWD = 2;

    Wheel wlf, wrf, wlb, wrb;
    Wheel[] wheels;
    int driveType;

    boolean accelerating;
    boolean braking;
    boolean turningLeft;
    boolean turningRight;

    float turningFraction;
    float turningRate = 10f;
    float turningRestoreRate = 10f;
    float maxTurnAngle = 30f;

    public Car(Entity parent){
        super(parent);
        Vector3 size = new Vector3(1f, 0.5f, 2f);
        modelInstance = new ModelInstance(new ModelBuilder().createBox(size.x, size.y, size.z, new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

        btCollisionShape collisionShape = new btBoxShape(size);
        Vector3 localInertia = size.cpy();
        collisionShape.calculateLocalInertia(1000, localInertia);
        btRigidBody.btRigidBodyConstructionInfo constructionInfo = new btRigidBody.btRigidBodyConstructionInfo(1000, null, collisionShape, localInertia);
        constructionInfo.setFriction(0);
        rigidBody = new btRigidBody(constructionInfo);

        wlf = new Wheel(this, new Vector3(0.5f, -0.25f, 1f));
        wrf = new Wheel(this, new Vector3(-0.5f, -0.25f, 1f));
        wlb = new Wheel(this, new Vector3(0.5f, -0.25f, -1f));
        wrb = new Wheel(this, new Vector3(-0.5f, -0.25f, -1f));
        wheels = new Wheel[]{wlf, wrf, wlb, wrb};

        driveType = FWD;
        switch (driveType){
            case FWD:
                wlf.setPowered(true);
                wlb.setPowered(true);
                break;
            case RWD:
                wlb.setPowered(true);
                wrb.setPowered(true);
                break;
        }

        wlf.setTurning(true);
        wrf.setTurning(true);

        accelerating = false;
        braking = false;
        turningLeft = false;
        turningRight = false;

        turningFraction = 0;
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

    public btRigidBody getRigidBody(){
        return rigidBody;
    }

    public void update(float delta) {
        super.update(delta);
        rigidBody.activate();
        if(turningLeft){
            turningFraction = Math.min(turningFraction + turningRate*delta, 1);
        } else if(turningRight) {
            turningFraction = Math.max(turningFraction - turningRate * delta, -1);
        } else if(turningFraction != 0){
            int sign = turningFraction < 0 ? -1 : 1;
            turningFraction -= turningRestoreRate*sign*delta;
            //If turningFraction has changed sign, assume the wheels are centered
            if(turningFraction/sign <= 0){
                turningFraction = 0;
            }
        }
        for (Wheel w : wheels) {
            w.update(delta);
        }
    }

    public void render(ModelBatch mb){
        mb.render(modelInstance);
    }

    public boolean isAccelerating() {
        return accelerating;
    }

    public void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
        rigidBody.activate();
    }

    public boolean isBraking() {
        return braking;
    }

    public void setBraking(boolean braking) {
        this.braking = braking;
        rigidBody.activate();
    }

    public void setTurningLeft(boolean turningLeft) {
        this.turningLeft = turningLeft;
        rigidBody.activate();
    }

    public void setTurningRight(boolean turningRight) {
        this.turningRight = turningRight;
        rigidBody.activate();
    }

    public Wheel getWheel(int i){
        return wheels[i];
    }
}
