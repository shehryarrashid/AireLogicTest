public enum Consciousness {
    ALERT(0),
    CVPU(1); // You can assign any non-zero value for CVPU

    private final int value;

    Consciousness(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
