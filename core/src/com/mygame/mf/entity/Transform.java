package com.mygame.mf.entity;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Transform {
    Entity entity;

    Matrix4 mat;

    Vector3 up;
    Vector3 left;
    Vector3 forward;

    public Transform(Entity entity){
        this.entity = entity;

        this.mat = new Matrix4();

        up = new Vector3();
        left = new Vector3();
        forward = new Vector3();
    }

    public void setMatrix(Matrix4 mat){
        this.mat.set(mat);
        update();
    }

    void update(){
        Matrix4 rotOnly = new Matrix4(getAbsoluteMatrix());
        rotOnly.setTranslation(new Vector3());

        left.set(1f, 0, 0).mul(rotOnly);
        up.set(0, 1f, 0).mul(rotOnly);
        forward.set(0, 0, 1f).mul(rotOnly);
    }

    public Matrix4 getRotationMatrix(){
        Matrix4 m = new Matrix4(mat);
        m.setTranslation(0, 0, 0);
        return m;
    }

    public Matrix4 getMatrix(){
        return mat;
    }

    public Matrix4 getAbsoluteMatrix(){
        if(entity == null || entity.parent == null){
            return this.mat.cpy();
        }

        return entity.parent.transform.getAbsoluteMatrix().mul(this.mat);
    }

    public Transform getAbsolute(){
        Transform transform = new Transform(null);
        transform.mat.set(getAbsoluteMatrix());
        transform.update();
        return transform;
    }

    public Vector3 getUp(){
        return up.cpy();
    }

    public Vector3 getDown(){
        return up.cpy().scl(-1);
    }

    public Vector3 getLeft(){
        return left.cpy();
    }

    public Vector3 getRight(){
        return left.cpy().scl(-1);
    }

    public Vector3 getForward(){
        return forward.cpy();
    }

    public Vector3 getBackward(){
        return forward.cpy().scl(-1);
    }

    public Vector3 convertToLocalOrientation(Vector3 v){
        v.mul(getAbsolute().getRotationMatrix().inv());
        return v;
    }

    public Vector3 convertToAbsoluteOrientation(Vector3 v){
        v.mul(getAbsolute().getRotationMatrix());
        return v;
    }

    public Vector3 convertToLocal(Vector3 v){
        v.mul(getAbsoluteMatrix().inv());
        return v;
    }

    public Vector3 convertToAbsolute(Vector3 v){
        v.mul(getAbsoluteMatrix());
        return v;
    }

    public void setPosition(Vector3 position){
        mat.setTranslation(position);
        update();
    }

    Vector3 tempPos = new Vector3();
    public Vector3 getPosition(){
        return mat.getTranslation(tempPos).cpy();
    }

    public void rotate(Vector3 axis, float degrees){
        mat.rotate(axis, degrees);
        update();
    }

    Quaternion tempQuat = new Quaternion();
    public void setRotation(Vector3 axis, float degrees){
        tempQuat.set(axis, degrees);
        mat.set(getPosition(), tempQuat);
        update();
    }

    public void print(){
        System.out.println(mat.toString());
    }
}
