package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

public class FirstCarMove extends Component {
    private Events events;
    private Transform transform;
    private Entity carEntity;

    @Override
    public void initialize() {
        events = getEntity().getEvents();
        transform = getEntity().getTransform();
        carEntity = getEntity();
    }

    public FirstCarMove(int id) {
        super(id, true);
    }
    @Override
    public void update(float deltaTime) {
        boolean isMoved = false;

        if (events.isKeyPressed(Key.W) ) {
            isMoved = true;
            carEntity.getTransform().setPosition(
                    carEntity.getTransform().getPosition().x,
                    carEntity.getTransform().getPosition().y + 0.1f,
                    carEntity.getTransform().getPosition().z);
        }
        if (events.isKeyPressed(Key.S)) {
            isMoved = true;
            carEntity.getTransform().setPosition(
                    carEntity.getTransform().getPosition().x,
                    carEntity.getTransform().getPosition().y - 0.1f,
                    carEntity.getTransform().getPosition().z);
        }
        if (events.isKeyPressed(Key.A)) {
            isMoved = true;
            carEntity.getTransform().setPosition(
                    carEntity.getTransform().getPosition().x - 0.1f,
                    carEntity.getTransform().getPosition().y,
                    carEntity.getTransform().getPosition().z);
        }
        if (events.isKeyPressed(Key.D)) {
            isMoved = true;
            carEntity.getTransform().setPosition(
                    carEntity.getTransform().getPosition().x + 0.1f,
                    carEntity.getTransform().getPosition().y,
                    carEntity.getTransform().getPosition().z);
        }

        if (Math.abs(carEntity.getTransform().getPosition().x) > 2.5f) {
            Application.shared.getWindow().setShouldClose(true);
        }
        if (isMoved) {
            sendCurrentState();
        }
    }
    @Override
    public NetworkComponentState getState() {
        Vector4f coordinates = transform.getPosition();
        return new ObjectPosition(coordinates.x, coordinates.y);
    }
}
