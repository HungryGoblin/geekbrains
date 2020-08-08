package Animal;

public class Dog extends Animal {

    private static final float RUN_LIMIT = 600;
    private static final float JUMP_LIMIT = 1.2F;
    private static final float SWIM_LIMIT = 50;

    {
        type = "Пёсель";
    }

    public Dog() {
        super(RUN_LIMIT, JUMP_LIMIT, SWIM_LIMIT);
    }

    public Dog(float jL, float rL, float sL) {
        this();
        setLimits(jL, rL, sL);
    }

}


