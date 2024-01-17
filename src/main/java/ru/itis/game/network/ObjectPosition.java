package ru.itis.game.network;
import ru.itis.gengine.network.model.NetworkComponentState;

public class ObjectPosition implements NetworkComponentState {
    private float x;
    private float y;
    private String textureName;
    private boolean direction;

    public ObjectPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public String getTextureName() { return textureName; }
    public void setTextureName(String textureName) { this.textureName = textureName; }
    public void setCarPosition(boolean direction) { this.direction = direction; }
    public boolean getCarPosition() { return direction; }
}