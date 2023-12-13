import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
* ���ر�Ҷ˹������
*/
public class BayesClassifier extends JFrame implements ActionListener
{
	static PrintWriter pw; // ��־��¼��
	ArrayList ls=new ArrayList();
	ArrayList conclusion=new ArrayList();
//	ArrayList pWord=new ArrayList();

	List<String> pResult;
	private static TrainingDataManager tdm;//ѵ����������
	private String trainnigDataPath;//ѵ����·��

	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem openSourceFile, openDicItem, closeItem;
	private JRadioButtonMenuItem fmmItem, bmmItem;
	private JMenuItem openTrainFileItem, saveDicItem, aboutItem;
	private JButton btSeg1, btSeg2, btSeg3,btSeg4;
	private JTextArea taOutput;
	private JTextArea taInput;
//	private JTextField taInputFile;
	private JPanel panel;
//	JLabel infoDic, infoAlgo;
	public String FilePath;

	private void initFrame()
	{
		setTitle("�Ϸ��ձ����ŷ���ϵͳ");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("�ļ�");
		JMenu algorithmMenu =  new JMenu("ѵ��");
		JMenu trainMenu =  new JMenu("����");
		JMenu helpMenu =  new JMenu("����");
		 
		openSourceFile = fileMenu.add("������ִ��ļ�");
		openDicItem = fileMenu.add("����ʵ�");
		fileMenu.addSeparator();
		closeItem = fileMenu.add("�˳�");
		
		algorithmMenu.add(fmmItem = new JRadioButtonMenuItem("�������ƥ��", true));
		algorithmMenu.add(bmmItem = new JRadioButtonMenuItem("�������ƥ��", false));
		ButtonGroup algorithms = new ButtonGroup();
		algorithms.add(fmmItem);
		algorithms.add(bmmItem);
		
		openTrainFileItem = trainMenu.add("���벢ѵ������");
		saveDicItem = trainMenu.add("����ʵ�");
		
		aboutItem = helpMenu.add("����Word Segment Demo");		
		
		menuBar.add(fileMenu);
		menuBar.add(algorithmMenu);
		menuBar.add(trainMenu);
		menuBar.add(helpMenu);
		openDicItem.addActionListener(this);
		openSourceFile.addActionListener(this);
		closeItem.addActionListener(this);
		openTrainFileItem.addActionListener(this);
		saveDicItem.addActionListener(this);
		aboutItem.addActionListener(this);	
		fmmItem.addActionListener(this);
		bmmItem.addActionListener(this);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
	//	topPanel.setLayout(new GridLayout(2,3,10,10));
	//	JPanel centerPanel = new JPanel();
	//	centerPanel.setLayout(new GridLayout(2,1));//GridLayout
		JPanel bottomPanel = new JPanel();
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
	//    this.getContentPane().add(centerPanel, BorderLayout.CENTER);
	    this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

	    
	    btSeg1 = new JButton("�ı�����");
	    
	    btSeg2 = new JButton("���ż�����");
	    btSeg3 = new JButton("ѵ������");
	    btSeg4 = new JButton("����Ŀ¼");
	    //btSeg1 = new JButton("�������ƥ��ִ�");
	    //btSeg2 = new JButton("�������ƥ��ִ�");
	    //btSeg3 = new JButton("JE-analyzer�ִ�(Lucene)");
		//tfInput = new JTextField("", 30);
		taInput = new JTextArea();   taInput.setLineWrap( true );
		taOutput = new JTextArea();  taOutput.setLineWrap( true );
		
	    this.getContentPane().add(new JScrollPane(taInput), BorderLayout.CENTER); 
	    this.getContentPane().add(new JScrollPane(taOutput), BorderLayout.CENTER); 
//		taInputFile=new JTextField(20);
		//topPanel.add(tfInput);
		topPanel.add(btSeg4);
	//	topPanel.add(btSeg1);
		topPanel.add(btSeg2);
		topPanel.add(btSeg3);


		//topPanel.add(btSeg3);
//		taInputFile.setText("����Ŀ¼");
//		centerPanel.add(taInput );  //taInput.setText( "aaa");
//		centerPanel.add(taOutput);  //taOutput.setText( "bbb");
		
//		infoDic = new JLabel();
//		infoAlgo = new JLabel();
//		bottomPanel.add(taInputFile);
//		bottomPanel.add(infoAlgo);
		saveDicItem.setEnabled(false);
		btSeg1.addActionListener(this);
		btSeg2.addActionListener(this);
		btSeg3.addActionListener(this);
		btSeg4.addActionListener(this);
		//btSeg3.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e) {
		//�ı�����
		if(e.getSource() == btSeg1)
		{/*
			String[] Classes =tdm.getTraningClassifications();//����
			List<Buffer> dbase;
			DataBase d=new DataBase();
			
			d.creatTable("һ������");
			for(int i=0;i<Classes.length;i++){
				d.addRow("һ������", "����"+Classes[i]);
			}
			
			String[] rowName=new String[Classes.length] ;//����
			for (int k = 0; k <Classes.length; k++) {
				rowName[k]="����_"+Classes[k];
			}
			dbase=d.getData("һ����������", rowName);
			for(int i=0;i<dbase.size();i++){
				dbase.get(i).word=dbase.get(i).word.replaceAll("\\s", "");
			}
			d.insertData(dbase, Classes.length, "һ������");
			String s="yayn      m";
			System.out.println(s.replaceAll("\\s", ""));
			for(int i=0;i<Classes.length;i++){
				String[] Classes2 = tdm.get2ClassificationsName(Classes[i]);
				String tableName="��"+Classes[i];
				d.creatTable(tableName);
				for(int m=0;m<Classes2.length;m++){
					d.addRow(tableName, "����"+Classes2[m]);
				}
				String[] rowName=new String[Classes2.length] ;//����
				for (int k = 0; k <Classes2.length; k++) {
					rowName[k]="����"+Classes2[k];
				}
				dbase=d.getData("����"+Classes[i], rowName);
				for(int k=0;k<dbase.size();k++){
					dbase.get(k).word=dbase.get(k).word.replaceAll("\\s", "");
				}
				d.insertData(dbase, Classes2.length, tableName);
				System.out.println(i);
			}*/
		//	application app=new application();
		//	app.classify("..\\trs2txt\\nf1012_test\\2\\sample\\05_����Ͷ������¹�\\03","05_����Ͷ������¹�","03");
			TrainingDataManager tdm=new TrainingDataManager();
			float Nxc = tdm.getCountContainKeyOfClassification("01_����", "̰��");
		//	float Nc = tdm.getTrainingFileCount2("01_����");
		//	float V = tdm.getTraningClassifications().length;
			System.out.println(Nxc);
		}
		
		//���ż�����
		if(e.getSource() == btSeg2)
		{	
		//	test();
			
			
			String classes="1����";
			String[] Classes2 = get2ClassificationsName(classes);
			application app=new application();
			
			app.classify(this.FilePath,"1����","01");
			
			/*
			for(int i=0;i<Classes2.length;i++){//***********
				//String path=".\\sample\\"+classes+"\\"+Classes2[i];////*****
				File traningTextDir = new File(this.FilePath);
				//File traningTextDir = new File(path);
				if (traningTextDir.isDirectory()) 
				{
				//	app.classify(this.FilePath,classes,Classes2[0]);
					app.classify(this.FilePath,classes,Classes2[i]);
				}
			}//**
			*/
	//		String s=taOutput.getText();
	//		taOutput.setText(s+"\n"+app.getResult());
			taOutput.setText(app.getResult());
		}
		
		//ѵ������
		if(e.getSource() == btSeg3)
		{
		//	Training t=new Training();
		//	t.classify1();
		//	t.classify2();
			
			mThread m1=new mThread("һ������");
			Thread t1=new Thread(m1);
			t1.start();
			
			
			mThread m2=new mThread("��������");
			Thread t2=new Thread(m2);
			t2.start();
			
			
			mThread m6=new mThread("CHI");
			Thread t6=new Thread(m6);
			t6.start();
			
			
			
			mThread m4=new mThread("CHI2");
			Thread t4=new Thread(m4);
			t4.start();
			
			/*
			mThread m7=new mThread("TFIDF");
			Thread t7=new Thread(m7);
			t7.start();
			
			mThread m5=new mThread("TFIDF2");
			Thread t5=new Thread(m5);
			t5.start();*/
			
			
		}
		
		//����Ŀ¼
		if(e.getSource() == btSeg4){
	//		System.out.println("test");
	//		read();
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//        JFileChooser.FILES_ONLY
//        JFileChooser.DIRECTORIES_ONLY
			int returnVal = fc.showOpenDialog(this);
			File file_choosed = fc.getSelectedFile();
			this.FilePath=file_choosed.getPath();
			System.out.println(this.FilePath);
			taOutput.setText("����Ŀ¼��"+this.FilePath);
		}
	}
		
	public void test(){
		TrainingDataManager tdm=new TrainingDataManager();
		String[] Classes =tdm.getTraningClassifications();//����
		for (int j=0;j<Classes.length;j++){
			String classes=Classes[j];
			String[] Classes2 = get2ClassificationsName(classes);
			application app=new application();
			
			for(int i=0;i<Classes2.length;i++){
				String path="..\\trs2txt\\nf1012_test\\Old\\sample\\"+classes+"\\"+Classes2[i];
				File traningTextDir = new File(path);
				if (traningTextDir.isDirectory()) 
				{
					app.classify(path,classes,Classes2[i]);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void read(){
		String[] Classes =tdm.getTraningClassifications();//����
		DataBase d=new DataBase();
		List<Buffer> dbase;
		System.out.println("����һ�����ݿ�...");
		String[] rowName=new String[Classes.length] ;//����
		for (int k = 0; k <Classes.length; k++) {
			rowName[k]="TFIDF"+Classes[k];
		}
		dbase=d.getData("����TFIDF", rowName);
		
	/*	
		java.util.Collections.sort(dbase,new Comparator() 
		{
			public int compare(final Object o1,final Object o2) 
			{
				final Buffer m1 = (Buffer) o1;
				final Buffer m2 = (Buffer) o2;
				final double ret = m1.pr[21] - m2.pr[21];
		//		final double ret = m1.TFIDF[21] - m2.TFIDF[21];
				if (ret < 0) 
				{
					return 1;
				} 
				else 
				{
					return -1;
				}
			}
		});
		*/
	//	for(int i=0;i<dbase.size();i++){
		for(int i=0;i<100;i++){	
	//		String s=dbase.get(i).word+": "+dbase.get(i).pr[21];
			String s=dbase.get(i).word;
			for (int k = 0; k <Classes.length; k++) {
		//		s+=Classes[k]+":"+dbase.get(i).pr[k];
			}
			System.out.println(s);
		}
		
	}
	
	/**
	* Ĭ�ϵĹ���������ʼ��ѵ����
	*/
	public BayesClassifier() 
	{
		tdm =new TrainingDataManager();
	}

	/**
	* ����������ı���������X�ڸ����ķ���Cj�е�����������
	* <code>ClassConditionalProbability</code>����ֵ
	* @param X �������ı���������
	* @param Cj ���������
	* @return ����������������ֵ����<br>
	*/
	
	
	public static void main(String[] args) throws IOException
	{
		BayesClassifier classifier = new BayesClassifier();//����Bayes������
		classifier.initFrame();
		Toolkit theKit = classifier.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		
		classifier.setBounds(wndSize.width/4, wndSize.height/4,
						wndSize.width/2, wndSize.height/2);
		classifier.setVisible(true);
		/*
		String s[]=tdm.get2FilesPath("01_����","03");
		for(int i=0;i<s.length;i++){
			System.out.println(s[i]);
		}*/
	//	DataBase d=new DataBase();
	//	d.insertData("tcxp", 5,"sogou");
	//	System.out.println(tdm.getTrainingFileCount2("01_����"));

	}
	
	public String[] get2ClassificationsName(String classification){
		String Path=".\\sample";
		File traningTextDir= new File(Path);
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		String[] ret = classDir.list();
		return ret;
	}
	
}