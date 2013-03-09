public class GreeterTester {
	public static void main(String[] args) {
		Greeter worldGreeter = new Greeter("World");

		String greeting = worldGreeter.sayHello();
		System.out.println(greeting);

		Greeter daveGreeter = new Greeter("Dave");
		System.out.println(daveGreeter.sayHello());
		
		daveGreeter.setName("Dave, I can't do that");
		System.out.println(daveGreeter.sayHello());
		
		daveGreeter = worldGreeter;
		daveGreeter.setName("Janet");
		System.out.println("Should reference Janet: " + worldGreeter.sayHello());
	}
}
