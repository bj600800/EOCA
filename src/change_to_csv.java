import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class change_to_csv {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("GH11.fas")));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("GH11.csv")));
		
		change(br,bw);
	}

	private static void change(BufferedReader br, BufferedWriter bw) throws Exception {
		// TODO automatically generated method stub
		bw.write("PH,T,");
		for(int i=1;i<=380;i++){
			bw.write("Val"+i+",");
		}
		bw.write("\r\n");
		
		String lines= "";
		while((lines = br.readLine())!=null){
			String[] line = lines.split("_");
			String PH = line[line.length-3];
			String T = line[line.length-1];
			bw.write(PH+","+T+",");
			lines = br.readLine();
			char[] temp = lines.toCharArray();
			for(char p : temp){
				bw.write(p+",");
			}
			bw.write("\r\n");
		}
		br.close();
		bw.close();
		
	}
	
}
