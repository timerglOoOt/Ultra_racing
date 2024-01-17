package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.base.Direction;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;

public class CameraMove extends Component {
    public float moveSpeed = 6.5f;

    private Transform transform;
    private World world;
    private Transform carTransform;
    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        world = getEntity().getWorld();
    }

    public CameraMove (Transform carTransform) {
        super(-10, false);
        this.carTransform = carTransform;
    }
    @Override
    public void update(float deltaTime) {
        float speed = moveSpeed * deltaTime;

        if (carTransform.getPosition().y  > Application.shared.getWindow().getWindowSize().height / 200 - 0.2) {
            carTransform.translate(Direction.Down, speed);
        } else if (carTransform.getPosition().y < -4.8) {
            carTransform.translate(Direction.Up, speed);
        }
    }
}