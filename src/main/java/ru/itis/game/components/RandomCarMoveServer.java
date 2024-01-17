package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.base.Direction;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.Physics;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

import java.util.ArrayList;
import java.util.List;

public class RandomCarMoveServer extends Component {
    private World world;
    private Texture texture;
    private Shader shader;
    private Transform transform;
    private Physics physics;
    private static List<Entity> playerCars = new ArrayList<>();
    private String textureName;
    private Entity car;
    public float moveSpeed = 3.0f;

    public RandomCarMoveServer(int id, String textureName) {
        super(id, true);
        this.textureName = textureName;
    }

    @Override
    public void initialize() {
        car = getEntity();
        world = getEntity().getWorld();
        transform = getEntity().getTransform();
        physics = getEntity().getPhysics();
    }

    @Override
    public void update(float deltaTime) {
        float speed = deltaTime * moveSpeed;
        car.getTransform().setPosition(
                car.getTransform().getPosition().x,
                car.getTransform().getPosition().y - speed,
                car.getTransform().getPosition().z);

        if (car.getTransform().getPosition().y < -7) {
            world.destroy(car);
        }
        for (int i = 0; i < playerCars.size(); i++) {
            if (physics.intersects(car, playerCars.get(i))) {
                Application.shared.getWindow().setShouldClose(true);
            }
        }
        sendCurrentState();
    }
    @Override
    public NetworkComponentState getState() {
        Vector4f coordinates = transform.getPosition();
        ObjectPosition position = new ObjectPosition(coordinates.x, coordinates.y);
        position.setCarPosition(coordinates.x >= 0);
        position.setTextureName(textureName);
        return position;
    }
    public static void addPlayerCar(Entity car) {
        playerCars.add(car);
    }
}
