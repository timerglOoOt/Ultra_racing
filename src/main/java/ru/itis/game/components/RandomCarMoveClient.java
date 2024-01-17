package ru.itis.game.components;

import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;
import ru.itis.gengine.renderer.Texture;

public class RandomCarMoveClient extends Component {
    private Transform transform;
    private String textureName;
    private boolean flag = true;
    public RandomCarMoveClient(int id) {
        super(id, true);
    }
    @Override
    public void initialize() {
        transform = getEntity().getTransform();
//        texture = new Texture("resources/textures/bus.png");
    }
    public String getTextureName() { return textureName; }
    @Override
    public void setState(NetworkComponentState state) {
        super.setState(state);
        ObjectPosition position = (ObjectPosition) state;
        transform.setPosition(position.getX(), position.getY(), 1.f);
        if (flag && !position.getCarPosition()) {
//            texture = new Texture(position.getTextureName());
            flag = false;
            getEntity().getTransform().rotate((float) Math.PI, 0, 0);
        }
    }
}
