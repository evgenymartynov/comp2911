public class AssembledPart implements WeightedComponent {
    public AssembledPart(WeightedComponent... parts) {
        this.parts = parts;
    }

    @Override
    public int getWeight() {
        int total = 0;
        for (WeightedComponent part : parts)
            total += part.getWeight();
        return total;
    }

    private WeightedComponent[] parts;
}
