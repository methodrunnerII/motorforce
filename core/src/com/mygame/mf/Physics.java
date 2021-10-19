package com.mygame.mf;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;

public class Physics {
    final static short GROUND_FLAG = 1<<8;
    final static short OBJECT_FLAG = 1<<9;
    final static short ALL_FLAG = -1;

    static class MFContactListener extends ContactListener {
        @Override
        public boolean onContactAdded(btCollisionObjectWrapper colObj0Wrap, int partId0, int index0,
                                      btCollisionObjectWrapper colObj1Wrap, int partId1, int index1){
            return true;
        }
    }

    static btCollisionConfiguration collisionConfig;
    static btDispatcher dispatcher;
    static btBroadphaseInterface broadphase;
    static btConstraintSolver constraintSolver;
    static btDynamicsWorld dynamicsWorld;

    static MFContactListener contactListener;

    static void init(){
        Bullet.init();

        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadphase = new btDbvtBroadphase();
        constraintSolver = new btSequentialImpulseConstraintSolver();
        dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
        dynamicsWorld.setGravity(new Vector3(0, -10, 0));
        contactListener = new MFContactListener();
    }

    public static btDynamicsWorld getDynamicsWorld(){
        return dynamicsWorld;
    }

    public static void step(float delta){
        dynamicsWorld.stepSimulation(delta);
    }
}
