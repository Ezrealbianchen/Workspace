class A {
	
	int a = 1;
	
}

public class TestEquals {
	public static void main(String[] args) {
		
		A obj1 = new A();
		A obj2 = new A();
		String s1,s2,s3 = "abc",s4 = "abc";
		s1 = new String("abc");
		s2 = new String("abc");
		System.out.println("s1.equals(s2)��"+s1.equals(s2));
		System.out.println("s1==s3��"+(s1==s3));
		System.out.println("s1.equals(s3)"+s1.equals(s3));
		System.out.println("s3==s4��"+(s3==s4));
		System.out.println("s2.equals(s3)"+s2.equals(s3));
		System.out.println("s1==s2��"+(s1==s2));
		System.out.println("obj1==obj2��"+(obj1==obj2));
		System.out.println("obj1.equals(obj2)��"+obj1.equals(obj2));
		obj1 = obj2;
		System.out.println("obj1=obj2��obj1==obj2��"+(obj1==obj2));
		System.out.println("obj1=obj2��obj1.equals(obj2)��"+obj1.equals(obj2));
		TestImplement t1 = new TestImplement(2, 1);
		t1.setColor("��ɫ");
		
	}
}