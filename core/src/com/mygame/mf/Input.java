package com.mygame.mf;

import com.badlogic.gdx.InputAdapter;

public class Input extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == com.badlogic.gdx.Input.Keys.W){
            MotorForce.car.setAccelerating(true);
        }
        if(keycode == com.badlogic.gdx.Input.Keys.S){
            MotorForce.car.setBraking(true);
        }
        if(keycode == com.badlogic.gdx.Input.Keys.A){
            MotorForce.car.setTurningLeft(true);
        }
        if(keycode == com.badlogic.gdx.Input.Keys.D){
            MotorForce.car.setTurningRight(true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == com.badlogic.gdx.Input.Keys.W){
            MotorForce.car.setAccelerating(false);
        }
        if(keycode == com.badlogic.gdx.Input.Keys.S){
            MotorForce.car.setBraking(false);
        }
        if(keycode == com.badlogic.gdx.Input.Keys.A){
            MotorForce.car.setTurningLeft(false);
        }
        if(keycode == com.badlogic.gdx.Input.Keys.D){
            MotorForce.car.setTurningRight(false);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
