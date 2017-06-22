package com.Ezreal;

interface Animal {
	
	abstract void voice();
	
}

class Dog implements Animal {
	
	public void voice(){
		System.out.println("ÍôÍôÍô");
	}
}

class Pig implements Animal {
	
	public void voice(){
		System.out.println("à½~~à½");
	}
}

class Cat implements Animal {
	public void voice() {
		System.out.println("ß÷ß÷ß÷");
	}
}

class mothed{
	static Animal get(String name){
		if(name=="Dog")return new Dog();
		else if(name=="Pig")return new Pig();
		else if(name=="Cat")return new Cat();
		return new Dog();
	}
}

public class Store {
	public static void main(String[] args) {
		Animal a = mothed.get("Dog");
		Animal b = mothed.get("Pig");
		Animal c = mothed.get("Cat");
		a.voice();
		b.voice();
		c.voice();
	}
	
}
