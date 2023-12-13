import java.util.ArrayList;
import java.util.List;

public class mThread implements Runnable{
	private String name;
	static int id=0,count=0;
	static List<Buffer> Dbase = new ArrayList<Buffer>();
	Training t=new Training();
	public mThread(String name){
		this.name=name;
	}
	public void run(){
		try {
		if(name.equals("һ������")){
			t.classify1(0,13);
		}
		/*if(name.equals("һ������2")){
			t.classify1(11,23);
		}*/
		if(name.equals("��������")){
			t.classify2(0,13);
		}
		/*if(name.equals("��������2")){
			t.classify2(16,17);
		}*/
		if(name.equals("CHI")){
			t.Chi_square();
		}
		if(name.equals("TFIDF")){
			t.TFIDF();
		}
		if(name.equals("TFIDF2")){
			t.TFIDF2();
		}
		if(name.equals("CHI2")){
			t.CHI2();
		}
		
		}catch (Exception e) {
              // �쳣����
        	System.out.println("����");
        }
		finally{
			id++;
			System.out.println("�߳�"+id+"��������" + name);
		}
	}
}
