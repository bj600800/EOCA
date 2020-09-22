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
	static Map<Double, ArrayList<int[]>> samples = new HashMap<>(); //所有样本
	static ArrayList<String> p_samples = new ArrayList<>();         //正样本集
	static ArrayList<String> n_samples = new ArrayList<>();         //负样本集
	
	static int samples_size=0;                                       //蛋白质条数
	static int[][] samples_probability = new int[21][380];           //所有氨基酸的统计分布
	static double[] positive_p;  //正样本的概率
	static double[] negative_p;  //负样本的概率
	
	public static void main(String[] args) throws Exception{

		//读取GH11所有数据的label和字符串
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\demo\\results\\GH11_R_PH.csv")));
		String lines = br.readLine();
		while((lines=br.readLine())!=null){
			ArrayList<int[]> pars = new ArrayList<>(); //蛋白质序列
			String[] line = lines.split(",");
			Double PH= Double.parseDouble(line[0]);    //label
			//将蛋白质序列转为数字
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
		
		//生成样本的概率
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
	
	//选取两个label
	private static void create_sample() {
		// TODO 自动生成的方法存根
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
	
	//选取两个氨基酸
	private static void create(Double pos, Double neg) {
		// TODO 自动生成的方法存根
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

	//生成正负样本的选取概率
	private static double[] probability(String name) throws Exception {
		// TODO 自动生成的方法存根
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
