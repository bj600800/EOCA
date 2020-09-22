import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class create_probability {
	public static void main(String[] args) throws Exception{
		double ave_p = 0.0;
		double ave_n = 0.0;
		ArrayList<Double> p_labels = new ArrayList<>();
		ArrayList<Double> n_labels = new ArrayList<>();
		BufferedReader br =new BufferedReader(new FileReader(new File("D:\\demo\\results\\GH11_R_PH.csv")));
		String lines = br.readLine();
		while((lines = br.readLine())!=null){
			Double p_label = Double.parseDouble(lines.split(",")[0]);
			Double n_label = 14-Double.parseDouble(lines.split(",")[0]);
			if(p_labels.contains(p_label)!=true&&p_label>=6){
				p_labels.add(p_label);
				ave_p+=p_label;
			}
			if(n_labels.contains(n_label)!=true&&(14-n_label)<6){
				n_labels.add(n_label);
				ave_n+=n_label;
			}
		}
		br.close();
		ave_p/=p_labels.size();
		ave_n/=n_labels.size();

		Collections.sort(p_labels);
		Collections.sort(n_labels);
		
		Double sum_p = 0.0;
		Double sum_n = 0.0;
		for(Double i :p_labels){
			sum_p += Math.exp(i-ave_p);
		}
		for(Double i : n_labels){
			sum_n+=Math.exp(i-ave_n);
		}
		
		BufferedWriter p =new BufferedWriter(new FileWriter(new File("p_p.csv")));
		Double temp =0.0;
		for(Double i : p_labels){
			p.write(i+",");
			temp+=Math.exp(i-ave_p)/sum_p;
			p.write(temp+"\r\n");
		}
		p.close();
		
		BufferedWriter n = new BufferedWriter(new FileWriter(new File("n_p.csv")));
		temp =0.0;
		for(int i=n_labels.size()-1;i>=0;i--){
			n.write((14-n_labels.get(i))+",");
			temp+=Math.exp(n_labels.get(i)-ave_n)/sum_n;
			n.write(temp+"\r\n");
		}
		n.close();
		
		
	}
}
