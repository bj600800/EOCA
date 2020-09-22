import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Split_train_test {
	public static void main(String[] args)throws Exception{
		double train_size = 0.8;
		ArrayList<String> all = new ArrayList<>();
		BufferedReader br =new BufferedReader(new FileReader(new File("D:\\demo\\data\\GH11_R_PH.csv")));
		String head = br.readLine();
		String lines ="";
		while((lines = br.readLine())!=null){
			all.add(lines);
		}
		br.close();
		
		ArrayList<String> all_01 = new ArrayList<>();
		BufferedReader read_01 = new BufferedReader(new FileReader(new File("D:\\demo\\data\\GH11_01.csv")));
		String head_01 = read_01.readLine();
		lines ="";
		while((lines=read_01.readLine())!=null){
			all_01.add(lines);
		}
		read_01.close();
		
		
		int train = (int) (train_size*all.size());
		
		BufferedWriter write_train = new BufferedWriter(new FileWriter(new File("D:\\demo\\results\\GH11_train.csv")));
		BufferedWriter write_train_01 = new BufferedWriter(new FileWriter(new File("D:\\demo\\results\\GH11_01_train.csv")));
		BufferedWriter write_test = new BufferedWriter(new FileWriter(new File("D:\\demo\\results\\GH11_test.csv")));
		BufferedWriter write_test_01 = new BufferedWriter(new FileWriter(new File("D:\\demo\\results\\GH11_01_test.csv")));
		
		write_train.write(head+"\r\n");
		write_train_01.write(head_01+"\r\n");
		write_test.write(head+"\r\n");
		write_test_01.write(head_01+"\r\n");

		for(int i=0;i<train;i++){
			int temp = (int) (Math.random()*all.size());
			write_train.write(all.get(temp)+"\r\n");
			write_train_01.write(all_01.get(temp)+"\r\n");
			all.remove(temp);
			all_01.remove(temp);
		}

		for(int i=0;i<all.size();i++){
			write_test.write(all.get(i)+"\r\n");
			write_test_01.write(all_01.get(i)+"\r\n");
		}
		write_train.close();
		write_train_01.close();
		write_test.close();
		write_test_01.close();
//		create_samples.main(args);
	}
}
