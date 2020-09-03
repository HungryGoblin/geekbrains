package Animal;

public abstract class Animal {

    private static final String MOVEMENT_RUN = "пробежал";
    private static final String MOVEMENT_JUMP = "прыгнул на";
    private static final String MOVEMENT_SWIM = "проплыл";
    private static final String MOVEMENT_INVALID = "смотрит на Вас с недоумением, в его глазах недоверие и обида";
    protected String type = "";
    private float jumpDefLimit = 0;
    private float runDefLimit = 0;
    private float swimDefLimit = 0;
    private float jumpLimit = 0;
    private float runLimit = 0;
    private float swimLimit = 0;

    public Animal(float jL, float rL, float sL) {
        setDefaultLimits(jL, rL, sL);
    }

    // уcтанавливает лимиты по умолчанию
    private void setDefaultLimits(float rL, float jL, float sL) {
        if (rL > 0) runDefLimit = rL;
        if (jL > 0) jumpDefLimit = jL;
        if (sL > 0) swimDefLimit = sL;
        runLimit = runDefLimit;
        jumpLimit = jumpDefLimit;
        swimLimit = swimDefLimit;
    }

    // уcтанавливает лимиты (не могут превышать лимиты по умолчанию)
    public void setLimits(float rL, float jL, float sL) {
        runLimit = (rL > 0 && rL < runDefLimit) ? rL : runDefLimit;
        jumpLimit = (jL > 0 && jL < jumpDefLimit) ? jL : jumpDefLimit;
        swimLimit = (sL > 0 && sL < swimDefLimit) ? sL : swimDefLimit;
    }

    // корректирует величину заданного действия
    protected final float correctRange(float r, float lim) {
        return (r >= 0) ? (r > lim) ? lim : r : 0;
    }

    private String toStringMovement(String m, float r) {
        return String.format("%s %s", type, (r > 0) ? m + " " +
                String.format("%.2f", r) + "(м)" : MOVEMENT_INVALID);
    }

    public String run(float r) {
        return toStringMovement(MOVEMENT_RUN, correctRange(r, runLimit));
    }

    public String swim(float r) {
        return toStringMovement(MOVEMENT_SWIM, correctRange(r, swimLimit));
    }

    public String jump(float r) {
        return toStringMovement(MOVEMENT_JUMP, correctRange(r, jumpLimit));
    }

}
