public class PrimitivePart implements WeightedComponent {
    public PrimitivePart(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    private int weight;
}
