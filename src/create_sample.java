import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class create_sample {
	static Map<Double, ArrayList<int[]>> samples = new HashMap<>(); //��������
	static ArrayList<String> p_samples = new ArrayList<>();         //��������
	static ArrayList<String> n_samples = new ArrayList<>();         //��������
	
	static int samples_size=0;                                       //����������
	static int[][] samples_probability = new int[21][380];           //���а������ͳ�Ʒֲ�
	static double[] positive_p;  //�������ĸ���
	static double[] negative_p;  //�������ĸ���
	
	public static void main(String[] args) throws Exception{

		//��ȡGH11�������ݵ�label���ַ���
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\demo\\results\\GH11_R_PH.csv")));
		String lines = br.readLine();
		while((lines=br.readLine())!=null){
			ArrayList<int[]> pars = new ArrayList<>(); //����������
			String[] line = lines.split(",");
			Double PH= Double.parseDouble(line[0]);    //label
			//������������תΪ����
			int[] par = new int[line.length-1];        
			for(int i=0;i<par.length;i++){
				int x = Integer.parseInt(line[i+1]);
				samples_probability[x][i]+=1;
				par[i] = x;
			}
			pars.add(par);
			
			if(samples.containsKey(PH)){
				pars = samples.get(PH);
				pars.add(par);
				samples.put(PH, pars);
			}else{
				samples.put(PH, pars);
			}
			
			samples_size++;
		}
		br.close();
		
		//���������ĸ���
		positive_p = probability("p_p.csv");
		negative_p = probability("n_p.csv");
		
		
		for(int i=0;i<10e6;i++){
			create_sample();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("samples")));
		for(int i=0;i<10e5;i++){
			int num_p = (int)(Math.random()*p_samples.size());
			int num_n = (int)(Math.random()*n_samples.size());
			if(p_samples.get(num_p).equals(n_samples.get(num_n))!=true){
				bw.write(p_samples.get(num_p)+":");
				bw.write(n_samples.get(num_n)+"\r\n");
			}else{
				i--;
			}

		}
		bw.close();
		
		

	}
	
	//ѡȡ����label
	private static void create_sample() {
		// TODO �Զ����ɵķ������
		ArrayList<Double> key = new ArrayList<>(samples.keySet());
		double p  = Math.random();   
		double n = Math.random();
		Double pos = 0.0;
		Double neg = 0.0;
		for(int i=0;i<positive_p.length;i++){
			if(p<positive_p[i]){
				pos=key.get(i);
				break;
			}
		}
		for(int i=0;i<negative_p.length;i++){
			if(n<negative_p[i]){
				neg=key.get(i);
				break;
			}
		}
		if(pos<=neg){
			create_sample();
		}else{
			create(pos,neg);
		}
	}
	
	//ѡȡ����������
	private static void create(Double pos, Double neg) {
		// TODO �Զ����ɵķ������
		String result ="";
		ArrayList<Integer> dif = new ArrayList<>();
		ArrayList<int[]> p = samples.get(pos);
		ArrayList<int[]> n = samples.get(neg);
		
		int temp_p = (int) (Math.random()*p.size());
		int temp_n = (int) (Math.random()*n.size());
		
		int[] result_p = p.get(temp_p);
		int[] result_n = n.get(temp_n);
		for(int i=0;i<result_p.length;i++){
			if(result_p[i]!=result_n[i]){
				dif.add(i);
			}
		}
		int num = dif.get((int) (Math.random()*dif.size()));
		double temp =0.0;
		
		result+=num+",";
		result+=(result_p[num]+380)+";";
		temp =(double)samples_probability[result_p[num]][num]/(double)samples_size;
		result+=temp;
		
		p_samples.add(result);
		
		result="";
		result+=num+",";
		result+=(result_n[num]+380)+";";
		temp = (double)samples_probability[result_n[num]][num]/(double)samples_size;
		result+=temp;
		n_samples.add(result);
		
	}

	//��������������ѡȡ����
	private static double[] probability(String name) throws Exception {
		// TODO �Զ����ɵķ������
		Map<Double, Double> p = new LinkedHashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(new File(name)));
		String lines = "";
		while((lines=br.readLine())!=null){
			String[] line = lines.split(",");
			Double label = Double.parseDouble(line[0]);
			Double par =Double.parseDouble(line[1]);
			p.put(label, par);
			
		}
		double[] result = new double[p.size()];
		int temp =0;
		for(double i : p.keySet()){
			result[temp]=p.get(i);
			temp++;
		}
		return result;
	}
}
