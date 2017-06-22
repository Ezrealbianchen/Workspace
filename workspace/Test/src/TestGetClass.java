class Person1 {
	protected String name;
	public Person1(String name) {
		this.name = name;
	}
}
public class TestGetClass {
	public static void main(String[] args) {
		
		Person1 per = new Person1("уехЩ");
		Class obj = per.getClass();
		System.out.println(obj);
		System.out.println(obj.isInterface());
	}
}
