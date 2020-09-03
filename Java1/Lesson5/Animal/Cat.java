package Animal;

public class Cat extends Animal {

    private static final float RUN_LIMIT = 200;
    private static final float JUMP_LIMIT = 2;
    private static final float SWIM_LIMIT = 0;

    {
        type = "Котик";
    }

    public Cat() {
        super(RUN_LIMIT, JUMP_LIMIT, SWIM_LIMIT);
    }

    public Cat(float jL, float rL) {
        this();
        setLimits(jL, rL, SWIM_LIMIT);
    }

    public final void setLimits(float rL, float jL) {
        setLimits(rL, jL, SWIM_LIMIT);
    }

    @Override
    public final void setLimits(float rL, float jL, float sL) {
        super.setLimits(rL, jL, SWIM_LIMIT);
    }

}
