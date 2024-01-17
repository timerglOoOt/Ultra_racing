package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.BoxCollider;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.network.model.NetworkComponentState;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

import java.util.Random;

public class RandomCarCreate extends Component {
    private World world;
    private Texture texture;
    private Shader shader;
    private Transform transform;
    private GSize window = Application.shared.getWindow().getWindowSize();
    private float timer = 0f;
    private int count = 100;
    private String textureName;
    private String[] carsImageName = {"bus", "car", "car_2"};
    public RandomCarCreate(int id) {
        super(id, true);
    }
    public void setTexture() {
        textureName = "resources/textures/" + carsImageName[new Random().nextInt(carsImageName.length)] + ".png";
        this.texture = new Texture(textureName);
    }
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    @Override
    public void initialize() {
        world = getEntity().getWorld();
        transform = getEntity().getTransform();
        setTexture();
    }

    @Override
    public void update(float deltaTime) {
        timer += deltaTime;
        if (timer > 10) {
            timer = 0f;
            Entity entity = world.instantiateEntity(count++, true, "car");
            MeshData meshData = Primitives.createRectangler(1.4f, 1.4f);
            Mesh mesh = new Mesh(meshData, false, texture, shader);
            entity.addComponent(mesh);
            entity.addComponent(new BoxCollider());
            setTexture();
            entity.getTransform().translate(-1.5f + new Random().nextFloat() * 3f, window.height / 200 , 1.f);
            if (entity.getTransform().getPosition().x < 0) {
                entity.getTransform().rotate((float) Math.PI, 0, 0);
            }
            entity.addComponent(new RandomCarMoveServer(999 + count, textureName));
            entity.sendCurrentState();
        }
    }

    @Override
    public NetworkComponentState getState() {
        Vector4f coordinates = transform.getPosition();
        ObjectPosition position = new ObjectPosition(coordinates.x, coordinates.y);
        return position;
    }
}
