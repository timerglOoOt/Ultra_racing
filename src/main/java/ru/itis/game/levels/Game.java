package ru.itis.game.levels;

import ru.itis.game.Entities;
import ru.itis.game.components.*;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.BoxCollider;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.network.model.NetworkEntity;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

public class Game extends LevelBase {
    Shader baseShader;
    public void startServer(World world) {
        GSize window = Application.shared.getWindow().getWindowSize();
        System.out.println("SERVER LEVEL START!");
        world.getRenderer().setClearColor(0.2f, 0.5f, 1.0f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/vertex_shader.glsl",
                "resources/shaders/fragment_shader.glsl"
        );

        createCamera(world);

        Texture firstCartexture = new Texture("resources/textures/racing_car_1.png");
        MeshData meshData = Primitives.createRectangler(1.5f, 1.3f);
        Mesh mesh = new Mesh(meshData, false, firstCartexture, baseShader);
        Entity carEntity = world.instantiateEntity(Entities.FIRST_CAR.id, true, "firstCar");
        carEntity.addComponent(mesh);
        carEntity.addComponent(new BoxCollider());
        carEntity.addComponent(new FirstCarMove(5));
        RandomCarMoveServer.addPlayerCar(carEntity);
        carEntity.getTransform().setPosition(
                carEntity.getTransform().getPosition().x + 1f,
                -window.height / 200,
                carEntity.getTransform().getPosition().z
        );
        carEntity.addComponent(new CameraMove(carEntity.getTransform()));
        carEntity.sendCurrentState();

        Entity randomCars = world.instantiateEntity(Entities.RANDOM_CARS.id, true, "randomCars");

        RandomCarCreate randomCarCreate = new RandomCarCreate(20);
        randomCarCreate.setShader(baseShader);
        randomCars.addComponent(randomCarCreate);
        randomCars.addComponent(new BoxCollider());
        randomCars.sendCurrentState();

        Texture roadTexture = new Texture("resources/textures/road.png");
        MeshData roadMeshData = Primitives.createRectangler(window.height / 80, 7);
        Mesh roadMesh = new Mesh(roadMeshData, false, roadTexture, baseShader);
        Entity roadEntity = world.instantiateEntity(-2, false, "road");
        roadEntity.getTransform().translate(roadEntity.getTransform().getPosition().x, roadEntity.getTransform().getPosition().y, -1);
        roadEntity.addComponent(roadMesh);
    }

    @Override
    public void startClient(World world) {
        GSize window = Application.shared.getWindow().getWindowSize();
        System.out.println("CLIENT LEVEL START!");
        world.getRenderer().setClearColor(0.2f, 0.5f, 1.0f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/vertex_shader.glsl",
                "resources/shaders/fragment_shader.glsl"
        );

        createCamera(world);

        Texture secondCartexture = new Texture("resources/textures/racing_car_2.png");
        MeshData meshData = Primitives.createRectangler(1.5f, 1.3f);
        Mesh mesh = new Mesh(meshData, false, secondCartexture, baseShader);
        Entity carEntity = world.instantiateEntity(Entities.SECOND_CAR.id, true, "secondCar");
        carEntity.addComponent(mesh);
        carEntity.addComponent(new BoxCollider());
        carEntity.getTransform().setPosition(
                carEntity.getTransform().getPosition().x - 1f,
                -window.height / 200,
                carEntity.getTransform().getPosition().z
                );
        carEntity.addComponent(new FirstCarMove(5));
        carEntity.addComponent(new CameraMove(carEntity.getTransform()));
        carEntity.sendCurrentState();

        Texture roadTexture = new Texture("resources/textures/road.png");
        MeshData roadMeshData = Primitives.createRectangler(window.height / 80, 7);
        Mesh roadMesh = new Mesh(roadMeshData, false, roadTexture, baseShader);
        Entity roadEntity = world.instantiateEntity(-1, false, "road");
        roadEntity.getTransform().translate(roadEntity.getTransform().getPosition().x, roadEntity.getTransform().getPosition().y, -1);
        roadEntity.addComponent(roadMesh);
    }

    @Override
    public void createEntityNetworkEvent(NetworkEntity entity) {
        World world = Application.shared.getWorld();
        int id = entity.getId();
        if(world.findEntityById(id).isPresent()) {
            System.out.println("ENTITY WITH ID " + id + " ALREADY EXISTS");
            return;
        }
        System.out.println("CREATE ENTITY WITH ID: " + id);

        if (id == Entities.FIRST_CAR.id) {
            Texture firstCartexture = new Texture("resources/textures/racing_car_1.png");
            MeshData meshData = Primitives.createRectangler(1.5f, 1.3f);
            Mesh mesh = new Mesh(meshData, false, firstCartexture, baseShader);
            Entity carEntity = world.instantiateEntity(Entities.FIRST_CAR.id, true, "firstCar");
            carEntity.addComponent(mesh);
            carEntity.addComponent(new BoxCollider());
            carEntity.addComponent(new SecondCarMove(5));
            carEntity.applyEntity(entity);
        }
        if (id == Entities.SECOND_CAR.id) {
            Texture secondCartexture = new Texture("resources/textures/racing_car_2.png");
            MeshData meshData = Primitives.createRectangler(1.5f, 1.3f);
            Mesh mesh = new Mesh(meshData, false, secondCartexture, baseShader);
            Entity carEntity = world.instantiateEntity(Entities.SECOND_CAR.id, true, "secondCar");
            carEntity.addComponent(mesh);
            carEntity.addComponent(new BoxCollider());
            carEntity.addComponent(new SecondCarMove(5));
            carEntity.applyEntity(entity);
            RandomCarMoveServer.addPlayerCar(carEntity);
        }
        if (id >= 100) {
            Texture texture = new Texture("resources/textures/bus.png");
            MeshData meshData = Primitives.createRectangler(1.4f, 1.4f);
            Mesh mesh = new Mesh(meshData, false, texture, baseShader);
            Entity randomCar = world.instantiateEntity(id, true, "randomCars");
            randomCar.addComponent(new RandomCarMoveClient(1000 + id));
            randomCar.addComponent(mesh);
            randomCar.addComponent(new BoxCollider());
            randomCar.applyEntity(entity);
        }
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }

    private void createCamera(World world) {
        Entity cameraEntity = world.instantiateEntity(30, false,"camera");
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);

        camera.setFieldOfView(60.f);
        camera.setShader(baseShader);
        cameraEntity.getTransform().setPosition(0.f, 0.f, 10.f);
    }
}
