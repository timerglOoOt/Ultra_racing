package ru.itis.game.components;

import ru.itis.game.network.ObjectPosition;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.network.model.NetworkComponentState;

public class SecondCarMove extends Component {
    private Transform transform;
    public SecondCarMove(int id) {
        super(id, true);
    }
    @Override
    public void initialize() {
        transform = getEntity().getTransform();
    }

    @Override
    public void setState(NetworkComponentState state) {
        super.setState(state);
        ObjectPosition position = (ObjectPosition) state;
        transform.setPosition(position.getX(), position.getY() + 1f, 1.f);
    }
}
