import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AssemblyTest {
    @Test
    public void testGetWeight() {
        PrimitivePart wheel = new PrimitivePart(3);
        PrimitivePart engineCasing = new PrimitivePart(50);
        PrimitivePart combustionChamber = new PrimitivePart(1);

        PrimitivePart pew = new PrimitivePart(0);
        AssembledPart rocketLauncher = new AssembledPart(pew, pew, pew, pew,
                pew, pew, pew, pew, pew, pew, pew, pew, pew, pew, pew, pew,
                pew, pew, pew, pew, pew, pew, pew, pew, pew, pew, pew, pew);

        AssembledPart engine = new AssembledPart(engineCasing,
                combustionChamber, combustionChamber);

        AssembledPart car = new AssembledPart(engine, wheel, wheel, wheel,
                wheel, rocketLauncher);

        assertEquals(12 + 50 + 2, car.getWeight());
    }

    private class WroomWroom extends PrimitivePart {
        public WroomWroom() {
            super(100);
        }
    }

    private class Wheel extends PrimitivePart {
        public Wheel() {
            super(40);
        }
    }

    private class Frame extends PrimitivePart {
        public Frame() {
            super(600);
        }
    }

    private class Pew extends PrimitivePart {
        public Pew() {
            super(0);
        }
    }

    private class RocketLauncher extends AssembledPart {
        public RocketLauncher() {
            super(new Pew(), new Pew(), new Pew(), new Pew(), new Pew(),
                    new Pew(), new Pew(), new Pew(), new Pew(), new Pew());
        }
    }

    private class Chassis extends AssembledPart {
        public Chassis() {
            super(new Wheel(), new Wheel(), new Wheel(), new Wheel(), new Frame());
        }
    }

    private class Car extends AssembledPart {
        public Car() {
            super(new Chassis(), new RocketLauncher(), new WroomWroom());
        }
    }

    @Test
    public void testCompositeClasses() {
        Car car = new Car();
        assertEquals(860, car.getWeight());
    }
}
