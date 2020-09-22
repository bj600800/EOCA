import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class generate_sequence {
	static int matrix_size = 30;
	static Map<Integer, double[]> matrix = new HashMap<>();
	static ArrayList<String[]> samples = new ArrayList<>();
	static double c =0;
	public static void main(String[] args) throws Exception{
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
		
		BufferedReader read_samples = new BufferedReader(new FileReader(new File("D:\\demo\\results\\GH11_R_PH.csv")));
		String head=read_samples.readLine();
		while((lines=read_samples.readLine())!=null){
			String[] line =lines.split(",");
			samples.add(line);
		}
		read_samples.close();
		
		BufferedWriter bw = new BufferedWriter( new FileWriter(new File("D:\\demo\\results\\new_GH11_R_PH.csv")));
		String label = head.split(",",2)[0];
		String other_lable = head.split(",", 2)[1];
		bw.write(other_lable+","+label+"\r\n");
		for(String[] sample :samples){
			for(int i=0;i<sample.length-1;i++){
				double[] site = matrix.get(i);
				int temp = Integer.parseInt(sample[i+1]);
				double[] amino_acid = matrix.get(temp+380);
				for(double j : site){
					bw.write(j+",");
				}
				for(double j :site){
					bw.write(j+",");
				}
//				bw.write(Mul(site,amino_acid)+",");
				
			}
			bw.write(sample[0]+"\r\n");
		}
		bw.close();
	}
	
	private static double Mul(double[] p, double[] value) {
		// TODO 自动生成的方法存根
		double result =0.0;
		for(int i=0;i<p.length;i++){
			result+=p[i]*value[i];
		}
//		result=Math.exp(result+c);
		return result;
	}
}
