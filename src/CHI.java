import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CHI {
	TrainingDataManager tdm=new TrainingDataManager();
	StopWordsHandler s=new StopWordsHandler(".\\ͣ�ôʱ�.txt");
	String[] Classes = tdm.getTraningClassifications();//����
	/**
	 * һ������CHIѵ������
	 */
	
	public void Chi_square(){
		List<Buffer> Dbase = new ArrayList<Buffer>();
		DataBase db=new DataBase();
		System.out.println("��ʼһ������CHIѵ��...");
		double N = tdm.getTrainingFileCount();
		double Nc[] =new double[Classes.length];
		double Nxc[] =new double[Classes.length];
		for(int a=0;a<Classes.length;a++){
			System.out.println("����һ���ı��ļ�..."+Classes[a]);
			String text=getText1(Classes[a]);
			System.out.println("��ʼ�ִ�һ...");
			text=cutNumber(text);
			String[] terms = ChineseSpliter.split(text, " ").split(" ");
			terms = DropStopWords(terms);
			
			for(int w=0;w<terms.length;w++){
				if(terms[w].length()>1){
					double sumNxc=0;
					for(int i=0;i<Classes.length;i++){
						Nxc[i] = tdm.getCountContainKeyOfClassification(Classes[i], terms[w]);
						sumNxc+=Nxc[i];
						Nc[i] = tdm.getTrainingFileCountOfClassification(Classes[i]);
					}
					double px;
					double pxc[] =new double[Classes.length];
					double pnxnc[] =new double[Classes.length];
					double pnxc[] =new double[Classes.length];
					double pxnc[] =new double[Classes.length];
					double pc[] =new double[Classes.length];

					
					for(int i=0;i<Classes.length;i++){
						pc[i]=(Nc[i]+1)/(N+1);
						pxc[i]=(Nxc[i]+1)/(N+1);
						pnxc[i]=(Nc[i]-Nxc[i]+1)/(N+1);
						pxnc[i]=(sumNxc-Nxc[i])/(N+1);
						pnxnc[i]=(N-Nc[i]-sumNxc+Nxc[i]+1)/(N+1);
					}
					px=(sumNxc+1)/(N+1);
					double chixc[] =new double[Classes.length];
					Buffer b=new Buffer(Classes.length);
					b.word=terms[w];
					for(int i=0;i<Classes.length;i++){
			//			chixc[i]=N*(pxc[i]*pnxnc[i]-pxnc[i]*pnxc[i])/(px*pc[i]*(1-px)*(1-pc[i]));
						chixc[i]=(pxc[i]*pnxnc[i]-pxnc[i]*pnxc[i])/(px*pc[i]*(1-px)*(1-pc[i]));
						if(chixc[i]>0){
							b.pr[i]=(float) chixc[i];
						}
						else{
							b.pr[i]=0;
						}
					}
					Dbase.add(b);
				}
			}
			
		}
		
		//д�����ݿ�
		System.out.println("ѵ������...\n");
		System.out.println("д�����ݿ�...");
		db.creatTable("����CHI");
		for(int i=0;i<Classes.length;i++){
			db.addRow("����CHI", "CHI"+Classes[i]);
		}
		db.insertData(mThread.Dbase, Classes.length, "����CHI");
//		Dbase.clear();
		System.out.println("һ������ѵ������...\n");
		
		
	}
	
	
	/**
	 * ��ȡһ�����������ı�
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getText1(String Classes){
		
		String text=null;
	//	for(int i=0;i<Classes.length;i++){
			String Classes2[]=tdm.get2ClassificationsName(Classes);
			for(int j=0;j<tdm.get2ClassificationsName(Classes).length;j++){
				String path[]=tdm.get2FilesPath(Classes, Classes2[j]);
				for(int k=0;k<path.length;k++){
					try {
						text+=tdm.getText(path[k])+"\n";
					} catch (FileNotFoundException e) {
						//  Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						//  Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
	//	}
		System.out.println("����һ���ı�����...");
		return text;
	}
	/**
	 * ��ȡ������������
	 * @param Classification
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getText2(String Classification,String Classes2){
		System.out.println("��������ı��ļ�...");
		String text=null;
	//	String Classes2[]=tdm.get2ClassificationsName(Classification);
	//	for(int i=0;i<Classes2.length;i++){
			String path[]=tdm.get2FilesPath(Classification, Classes2);
			for(int k=0;k<path.length;k++){
				try {
					text+=tdm.getText(path[k])+"\n";
				} catch (FileNotFoundException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
			}	
	//	}
		System.out.println("��������ı�����...");
		return text;
	}
	/**
	 * ͣ�ôʴ���
	 * @param oldWords
	 * @return
	 */
	public String[] DropStopWords(String[] oldWords)
	{
		Vector<String> v1 = new Vector<String>();
		for(int i=0;i<oldWords.length;++i)
		{
		//	if(StopWordsHandler.IsStopWord(oldWords[i])==false)
			if(s.IsStopWord(oldWords[i])==false&& oldWords[i].length()>1)
			{//����ͣ�ô�
				v1.add(oldWords[i]);
			}
		}
		String[] newWords = new String[v1.size()];
		v1.toArray(newWords);
		return newWords;
	}
	/**
	 * ȥ������
	 * @param text
	 * @return
	 */
	public static String cutNumber(String text){

		String reg="[\\d]";
		Pattern p=Pattern.compile(reg);
		Matcher m=p.matcher(text);
	//	String sr=null;
		while (m.find()){    
		    text = m.replaceAll(" "); 
		    }    
		  //  if (sr == null) {    
		   //   System.out.println("NO MATCHES: "); 
		  //  }

		return text;
	}
	
}
