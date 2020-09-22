import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class create_samples {
	static double split = 6;
	static int alp_size = 21;
	static int length_size =380;
	static double[][] statistic = new double[alp_size][length_size];
	static double[][] statistic_small = new double[alp_size][length_size];
	static double[][] statistic_big= new double[alp_size][length_size];
	static double protein_size_big=0.0;
	static double protein_size_small=0.0;
	static double protein_size=0.0;
	static ArrayList<String> p_sample = new ArrayList<>();
	static ArrayList<String> n_sample = new ArrayList<>();	
	public static void main(String[] args) throws Exception{
		statistics("D:\\demo\\results\\GH11_R_PH.csv");
//		use_all_protein("D:\\demo\\results\\GH11_R_PH.csv");
		
		System.out.println(protein_size);
		System.out.println(protein_size_big);
		System.out.println(protein_size_small);
		create();
	}
	private static void use_all_protein(String path) throws Exception {
		// TODO 自动生成的方法存根
		BufferedReader br =new BufferedReader(new FileReader(new File(path)));
		String lines = br.readLine();
		protein_size=0.0;
		statistic = new double[alp_size][length_size];
		while((lines=br.readLine())!=null){
			protein_size++;
			String[] line = lines.split(",");
			for(int i=0;i<(line.length-1);i++){
				statistic[Integer.parseInt(line[i+1])][i]+=1;
				statistic_big[Integer.parseInt(line[i+1])][i]+=1;
			}
		}
		for(int i=0;i<length_size;i++){
			for(int j=0;j<alp_size;j++){
				statistic[j][i] = statistic[j][i]/protein_size;
			}
		}
		br.close();
		
	}
	private static void create() throws Exception {
		// TODO 自动生成的方法存根
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("samples")));
		for(int i=0;i<1000000;i++){
			int location = (int) (Math.random()*length_size);
			double p = Math.random();
			double n = Math.random();
			int loc_p = 0;
			int loc_n = 0;
			for(int j=0;j<alp_size;j++){
				if(statistic_small[j][location]>=n){
					loc_n = j;
					break;
				}
			}
			for(int j=0;j<alp_size;j++){
				if(statistic_big[j][location]>=p){
					loc_p = j;
					break;
				}
			}
			if(loc_p!=loc_n){
				p_sample.add(location+","+(loc_p+length_size)+";"+statistic[loc_p][location]+":");
				n_sample.add(location+","+(loc_n+length_size)+";"+statistic[loc_n][location]+"\r\n");
			}else{
				i--;
			}
		}

		for(int i=0;i<10e5;i++){
			int chose_p = (int)(Math.random()*p_sample.size());
			int chose_n = (int)(Math.random()*n_sample.size());
			if(p_sample.get(chose_p).equals(n_sample.get(chose_n))!=true){
				bw.write(p_sample.get(chose_p)+n_sample.get(chose_n));
			}else{
				i--;
			}
		}
		
		bw.close();
	}
	private static void statistics(String path) throws Exception {
		// TODO 自动生成的方法存根
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String lines = br.readLine();
		while((lines = br.readLine())!=null){
			protein_size++;
			String[] line = lines.split(",");
			double label = Double.parseDouble(line[0]);
			if(label>split){
				protein_size_big++;
				for(int i=0;i<(line.length-1);i++){
					statistic[Integer.parseInt(line[i+1])][i]+=1;
					statistic_big[Integer.parseInt(line[i+1])][i]+=1;
				}
			}else{
				protein_size_small++;
				for(int i=0;i<(line.length-1);i++){
					statistic[Integer.parseInt(line[i+1])][i]+=1;
					statistic_small[Integer.parseInt(line[i+1])][i]+=1;
				}
			}
		}
		br.close();
		
		for(int i=0;i<length_size;i++){
			for(int j=0;j<alp_size;j++){
				statistic[j][i] = statistic[j][i]/protein_size;
				if(j==0){
					statistic_big[j][i]=statistic_big[j][i]/protein_size_big;
					statistic_small[j][i] = statistic_small[j][i]/protein_size_small;
				}else{
					statistic_big[j][i]=statistic_big[j-1][i]+(statistic_big[j][i]/protein_size_big);
					statistic_small[j][i] = statistic_small[j-1][i]+(statistic_small[j][i]/protein_size_small);
				}
			}
		}
		
		
	}


}
