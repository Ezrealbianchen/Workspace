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
    	
    	return new ThisDemo("�½�",0);
    }
    
 public void print(){

         System.out.println("��������="+this.name);

         System.out.println("���յ�����="+this.age);

    }
}
public class This{
    public static void main(String[] args) {

       ThisDemo tt = new ThisDemo(); //��㴫��ȥ�Ĳ���
       tt.print();
       ThisDemo t1 = new ThisDemo();
       t1.print();
       System.out.println(tt.equals(t1));
       System.out.println(tt==t1);
    } 
}