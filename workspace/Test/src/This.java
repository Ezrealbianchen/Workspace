class ThisDemo{
	
	String name = "123";
    int age;

    public ThisDemo (){ 
    	
        this.age=21;
        
    }    
    
    public void numAdd() {
    	

    	
    	System.out.println(this.equals(new ThisDemo()));
    	
    }
    
    
    
    public ThisDemo(String name,int age){

        this();

        this.name=name;

    }     
    
    public ThisDemo re(){
    	
    	return new ThisDemo("新建",0);
    }
    
 public void print(){

         System.out.println("最终名字="+this.name);

         System.out.println("最终的年龄="+this.age);

    }
}
public class This{
    public static void main(String[] args) {

       ThisDemo tt = new ThisDemo(); //随便传进去的参数
       tt.print();
       ThisDemo t1 = new ThisDemo();
       t1.print();
       System.out.println(tt.equals(t1));
       System.out.println(tt==t1);
    } 
}