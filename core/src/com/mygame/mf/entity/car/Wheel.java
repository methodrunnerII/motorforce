package com.mygame.mf.entity.car;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.mygame.mf.Physics;
import com.mygame.mf.entity.Entity;
import com.mygame.mf.loading.Models;

public class Wheel extends Entity {
    Car car;
    Spring spring;

    boolean powered;
    boolean turning;

    boolean gripping;
    float slideThreshold = 3000f;
    float gripThreshold = 100f;

    float gripLossFactor = 0.1f;

    public Vector3 targetForce;

    Vector3 currentAbsolutePosition;
    Vector3 previousAbsolutePosition;
    Vector3 absoluteVelocity;
    Vector3 localVelocity;

    public Wheel(Car car, Vector3 position){
        super(car);

        this.car = car;
        this.transform.setPosition(position);
        this.spring = new Spring(this);
        modelInstance = Models.getInstance(Models.wheelPlaceholder);

        powered = false;
        turning = false;
        gripping = true;

        targetForce = new Vector3(0, 0, 0);

        currentAbsolutePosition = new Vector3();
        previousAbsolutePosition = new Vector3();
        absoluteVelocity = new Vector3();
        localVelocity = new Vector3();
    }

    float gripFactor;
    public void update(float delta){
        super.update(delta);
        currentAbsolutePosition.set(transform.getAbsolute().getPosition());
        absoluteVelocity = currentAbsolutePosition.cpy().sub(previousAbsolutePosition).scl(1/delta);
        localVelocity = transform.convertToLocalOrientation(absoluteVelocity.cpy());

        if(turning) {
            transform.setRotation(transform.getUp(), car.turningFraction * car.maxTurnAngle);
        }

        spring.update(delta);

        gripFactor = 1;
        if(!gripping){
            gripFactor *= 0.1f;
        }

        targetForce.set(0, 0, 0);
        if(spring.isGrounded()){

            targetForce.set(-localVelocity.x*2000f*gripFactor, 0, 0);

            if(car.accelerating){
                targetForce.add(0, 0, 2000f*gripFactor);
            }
            if(car.braking){
                targetForce.add(0, 0, Math.max(Math.min(-localVelocity.z, 1), -1)*2000*gripFactor);
            }

            if(gripping && targetForce.len() > gripThreshold){
               gripping = false;
            } else if (!gripping && targetForce.len() < slideThreshold){
                gripping = true;
            }

            car.getTransform().convertToAbsoluteOrientation(targetForce);

            car.getRigidBody().applyForce(targetForce, car.getTransform().convertToAbsoluteOrientation(transform.getPosition()));
        }

        previousAbsolutePosition.set(currentAbsolutePosition);
    }

    //relative to car
    public Vector3 getContactPoint(){
        return transform.getPosition().add(transform.getDown().scl(spring.getCurrentExtent()));
    }

    public Vector3 getContactPointAbsolute(){
        return getContactPoint().mul(transform.getAbsoluteMatrix());
    }

    public void setPowered(boolean powered){
        this.powered = powered;
    }

    public void setTurning(boolean turning){
        this.turning = turning;
    }

    public Vector3 getAbsoluteVelocity(){
        return absoluteVelocity;
    }

    public Vector3 getLocalVelocity(){
        return localVelocity;
    }

    class Spring{
        Wheel wheel;

        float currentExtent;
        float previousExtent;
        float minExtent;
        float maxExtent;

        float f0;
        float k;
        float dampingForce;

        float currentForce;
        boolean grounded;

        public Spring(Wheel wheel){
            this.wheel = wheel;
            minExtent = -0.1f;
            maxExtent = 0.5f;

            f0 = 20000;
            k = f0;
            dampingForce = 5000;

            grounded = false;
        }

        public void update(float delta){
            previousExtent = currentExtent;
            float d = testRayCollision();
            grounded = d != -1;

            if(!grounded){
                currentExtent = getMaxLength();
                return;
            }

            currentForce = (f0 + (getMaxLength() - d)*k + (previousExtent - d)/delta*dampingForce)*delta;

            wheel.car.getRigidBody().applyForce(wheel.getTransform().getUp().scl(currentForce), wheel.transform.getPosition().mul(car.getTransform().getRotationMatrix()));

        }

        public float getCurrentExtent(){
            return currentExtent;
        }

        public float getMaxLength(){
            return maxExtent - minExtent;
        }

        Vector3 colFrom;
        Vector3 colTo;
        float testRayCollision(){
            float l = spring.getMaxLength();
            colFrom = wheel.transform.getAbsolute().getPosition();
            colTo = colFrom.cpy().add(car.getTransform().getAbsolute().getDown().scl(l));
            ClosestRayResultCallback callback = new ClosestRayResultCallback(colFrom, colTo);
            Physics.getDynamicsWorld().rayTest(colFrom, colTo, callback);
            if(callback.hasHit()){
                return callback.getClosestHitFraction()*l;
            } else {
                return -1;
            }
        }

        public boolean isGrounded(){
            return grounded;
        }
    }
}
