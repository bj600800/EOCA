import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class create_samplesbysplit {
	static double size = 0.7;
	static double split = 6;
	static int alp_size = 21;
	static int length_size =380;
	static double[][] statistic = new double[alp_size][length_size];
	static double[][] statistic_small = new double[alp_size][length_size];
	static double[][] statistic_big= new double[alp_size][length_size];
	static double protein_size_big=0.0;
	static double protein_size_small=0.0;
	static double protein_size=0.0;
	static ArrayList<String> big_sampe = new ArrayList<>();
	static ArrayList<String> small_sampe = new ArrayList<>();
	static ArrayList<String> test = new ArrayList<>();
	static ArrayList<String> p_sample = new ArrayList<>();
	static ArrayList<String> n_sample = new ArrayList<>();	
	public static void main(String[] args) throws Exception{
		statistics("D:\\demo\\results\\GH11_R_PH.csv");
		create();
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
		//读取GH11家族所有蛋白质序列及其功能项的值。并按照功能项值的大小分成big_sampe和small_sampe两个数据集。
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String lines = br.readLine();
		while((lines = br.readLine())!=null){
			protein_size++;
			String[] line = lines.split(",");
			double label = Double.parseDouble(line[0]);
			if(label>=split){
				protein_size_big++;
				big_sampe.add(lines);
			}else{
				protein_size_small++;
				small_sampe.add(lines);
			}
		}
		br.close();	
		//在big_sampe和small_sampe中随机选取（1-size）比例的数据用作测试集，并将其存入D:\\demo\\results\\GH11_test.csv中
		for(int i=0;i<(1-size)*protein_size_big;i++){
			int ramdom  = (int) (Math.random()*big_sampe.size());
			test.add(big_sampe.get(ramdom));
			big_sampe.remove(ramdom);
		}
		for(int i=0;i<(1-size)*protein_size_small;i++){
			int ramdom  = (int) (Math.random()*small_sampe.size());
			test.add(small_sampe.get(ramdom));
			small_sampe.remove(ramdom);
		}

		BufferedWriter bw =new BufferedWriter(new FileWriter(new File("D:\\demo\\results\\GH11_test.csv")));
		while(test.size()>0){
			int random = (int)(Math.random()*test.size());
			bw.write(test.get(random)+"\r\n");
			test.remove(random);
		}
		bw.close();
		//更新big size；small size；all size。
		protein_size_big = big_sampe.size();
		protein_size_small = small_sampe.size();
		protein_size = protein_size_big+protein_size_small;
		
		for(int i=0;i<protein_size_big;i++){
			String[] line = big_sampe.get(i).split(",");
			for(int j=0;j<(line.length-1);j++){
				statistic[Integer.parseInt(line[j+1])][j]+=1;
				statistic_big[Integer.parseInt(line[j+1])][j]+=1;
			}
		}
		
		for(int i=0;i<protein_size_small;i++){
			String[] line = small_sampe.get(i).split(",");
			for(int j=0;j<(line.length-1);j++){
				statistic[Integer.parseInt(line[j+1])][j]+=1;
				statistic_small[Integer.parseInt(line[j+1])][j]+=1;
			}
		}
		
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
