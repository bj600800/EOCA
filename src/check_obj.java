import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class check_obj {
	static double alpha =0.7;
	static Map<Integer, double[]> matrix = new HashMap<>();
	static ArrayList<Double[]> samples = new ArrayList<>();
	static ArrayList<Integer> IG = new ArrayList<>();
	static double c;
	static int matrix_size=30;
	public static void main(String[] args) throws Exception{
		int num=5;
		//读取样本
		read_samples("D:\\demo\\results\\0.8\\"+num+"\\GH11_test.csv");
		//读取矩阵
		read_matrix("D:\\demo\\results\\0.8\\"+num+"\\matrix_PH_alp_"+matrix_size+".csv");
		//读取信息增益
		read_IG("IG.csv",0.002);
		double sum=0;
		double time=0;
		for(int i=0;i<samples.size()-1;i++){
			for(int j=i+1;j<samples.size();j++){
				double score_i = calculate(samples.get(i));
				double score_j = calculate(samples.get(j));
				if((samples.get(i)[0]-samples.get(j)[0])!=0){
					sum++;
					if((samples.get(i)[0]-samples.get(j)[0])*(score_i-score_j)>0){
						time++;
					}
				}
			}
		}
		System.out.println(time/sum);
	}
	private static double calculate(Double[] sample) {
		// TODO 计算score
		double result_1=0;
		double result_2=0;
		
		for(int i=0;i<sample.length-1;i++){
			int site = (int)(sample[i+1]+380);
			double[] amino_acid = matrix.get(site);
			double[] v_site =matrix.get(i);
			if(IG.contains(i)){
				result_1+=Mul(v_site, amino_acid);
			}
		}
		
		for(int i=0;i<sample.length-2;i++){
			for(int j=i+1;j<sample.length-1;j++){
				int aa_1 = (int) (sample[i+1]+380);
				int aa_2 = (int) (sample[j+1]+380);
				double[] amino_acid_1 = matrix.get(aa_1);
				double[] amino_acid_2 = matrix.get(aa_2);
				if(IG.contains(i)&&IG.contains(j)){
					result_2+=Mul(amino_acid_1,amino_acid_2);
				}
			}
		}
		return alpha*result_1+(1-alpha)*result_2/(IG.size()-1);
	}
	private static void read_matrix(String path) throws Exception {
		// TODO 读取矩阵
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String lines =br.readLine();
		c = Double.parseDouble(lines.split(",")[0]);
		while((lines=br.readLine())!=null){
			String[] line = lines.split(",");
			Integer label = (int) Double.parseDouble(line[0]);
			double[] value = new double[line.length-1];
			for(int i=0;i<value.length;i++){
				value[i] = Double.parseDouble(line[i+1]);
			}
			matrix.put(label, value);
		}
		br.close();
	}
	private static void read_IG(String path,double threshold) throws Exception {
		// TODO 读取信息增益
		BufferedReader read_IG =new BufferedReader(new FileReader(new File(path)));
		IG = new ArrayList<>();
		String lines = "";
		while((lines = read_IG.readLine())!=null){
			String[] line = lines.split(",");
			Integer label = Integer.parseInt(line[0]);
			double value = Double.parseDouble(line[1]);
			if(value>threshold){
				IG.add(label);
			}
		}
		read_IG.close();
		
	}
	private static void read_samples(String path) throws Exception {
		// TODO 读取样本
		BufferedReader read_samples = new BufferedReader(new FileReader(new File(path)));
		String lines=read_samples.readLine();
		while((lines=read_samples.readLine())!=null){
			String[] line =lines.split(",");
			Double[] value = new Double[line.length];
			for(int i=0;i<line.length;i++){
				value[i] = Double.parseDouble(line[i]);
			}
			samples.add(value);
		}
		read_samples.close();
	}
	private static double Mul(double[] p, double[] value) {
		// TODO 向量点乘
		double result =0.0;
		for(int i=0;i<p.length;i++){
			result+=p[i]*value[i];
		}
//		result=Math.exp(result+c);
		return result;
	}

}
