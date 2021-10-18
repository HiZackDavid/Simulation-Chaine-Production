package model;

import java.awt.Point;

public class Component {
    private TypeComposant type;
    private Point position;

    public Component(TypeComposant type, Point position) {
        this.type = type;
        this.position = position;
    }

    public TypeComposant getType() {
        return type;
    }

    public void setType(TypeComposant type) {
        this.type = type;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
