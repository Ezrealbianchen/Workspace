class Person2 {
	
	static int count = 0;
	protected String name;
	protected int age;
	public Person2(String name,int age){
		this.name = name;
		this.age  = age ;
		this.count++;
	}
	
	public String toString(){
		return this.name+","+this.age;
	}
	
	public void display(){
		System.out.println("本类名="+this.getClass().getName()+";");
		System.out.println("父类名="+this.getClass().getSuperclass().getName()+";");
		System.out.println("Person.count="+Person2.count+" ");
		System.out.println("Student.count="+Student2.count+" ");
		Object obj = this;
		if(obj instanceof Student2)
			System.out.println(obj.toString()+"是Student类对象.");
		else if(obj instanceof Person2)
			System.out.println(obj.toString()+"是Person类对象");
	}
}

class Student2 extends Person2 {
	static int count = 0;
	protected String dept;
	protected Student2(String name,int age,String dept){
		
		super(name,age);
		this.dept = dept;
		this.count++;
	}
	
	public String toString() {
		return super.toString()+","+dept;
	}
	
	public void display() {
		super.display();
		System.out.println("super.count="+super.count);
		System.out.println("this.count="+this.count);
	}
}

public class TestP {
	public static void main(String[] args) {
		
		Person2 per = new Person2("王永涛",23);
		per.display();
		Student2 stu = new Student2("张小三",22,"计算机系");
		stu.display();
	}
}
