import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class check_matrix {
	static Map<Integer, double[]> matrix = new HashMap<>();
	static ArrayList<Double[]> samples = new ArrayList<>();
	static ArrayList<Integer> IG = new ArrayList<>();
	static double c;
	static int matrix_size=30;
	public static void main(String[] args) throws Exception{		
		//读取样本
		BufferedReader read_samples = new BufferedReader(new FileReader(new File("D:\\demo\\results\\GH11_test.csv")));
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
		double r = 0.0;
		double s =0.0;
		double out =0;
		while(out<=0.14){
			BufferedReader read_IG =new BufferedReader(new FileReader(new File("IG.csv")));
			lines = "";
			while((lines = read_IG.readLine())!=null){
				String[] line = lines.split(",");
				Integer label = Integer.parseInt(line[0]);
				double value = Double.parseDouble(line[1]);
				if(value>out){
					IG.add(label);
				}
			}
			read_IG.close();
			double temp = check();
			if(temp>s){
				r=out;
				s=temp;
			}
			out+=0.001;
			IG = new ArrayList<>();
		}
		
		System.out.println(r);
		System.out.println(s);
	}
	public static double check() throws Exception {
		// TODO 自动生成的方法存根
		//读取表示学习后的结果
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\demo\\results\\matrix_PH_alp_"+matrix_size+".csv")));
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
		
		double sum = 0;
		double  t = 0;
		BufferedWriter  res = new BufferedWriter(new FileWriter(new File("result.csv")));
		for(int i=0;i<samples.size()-1;i++){
			for(int j=i+1;j<samples.size();j++){
				if(i!=j){
					double result = caculate(samples.get(i),samples.get(j));
//					res.write(result+","+samples.get(i)[0]+","+samples.get(j)[0]+"\r\n");
					if((samples.get(i)[0]-samples.get(j)[0])!=0){
						sum++;
						if((samples.get(i)[0]-samples.get(j)[0])*result>0){
							t++;
						}
					}
				}
			}
		}
		res.close();
//		System.out.println(t);
//		System.out.println(sum);
//		System.out.println((double)t/(double)sum);
		return ((double)t/(double)sum);
		
	}
	private static double caculate(Double[] sp1, Double[] sp2) {
		// TODO 自动生成的方法存根
		double result = 0.0;
		for(int i=0;i<sp1.length-1;i++){
			if(sp1[i]-sp2[i]!=0){
				int num_1 = (int) (sp1[i+1]+380);
				int num_2 = (int) (sp2[i+1]+380);
				double[] c_1 = matrix.get(num_1);
				double[] c_2 = matrix.get(num_2);
				double[] v = matrix.get(i);
				if(IG.contains(i)){
//					if((Mul(c_1, v))>(Mul(c_2, v))){
//						result++;
//					}else if((Mul(c_1, v))<(Mul(c_2, v))){
//						result--;
//					}
					result+=((Mul(c_1, v))-(Mul(c_2, v)));
				}
			}
		}
		return result;
	}
	private static double Mul(double[] p, double[] value) {
		// TODO 自动生成的方法存根
		double result =0.0;
		for(int i=0;i<p.length;i++){
			result+=p[i]*value[i];
		}
		result=Math.exp(result+c);
		return result;
	}
}
