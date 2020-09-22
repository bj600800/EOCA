import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class change_to_number {
	public static void main(String[] args) throws Exception{

		write_R("wild.csv","wild_R.csv");
		
		write_R_01("GH11_R_PH_mutation3.csv","GH11_R_PH_mutation3_01.csv");
	}

	private static void write_R_01(String read, String write) throws Exception {
		// TODO automatically generated method stub
		BufferedReader br =new BufferedReader(new FileReader(new File(read)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(write)));
		String lines = br.readLine();
//		String lines = "";
//		bw.write("PH,T,");
		for(int i=1;i<=(lines.split(",").length-1)*20;i++){
			bw.write("Val"+i+",");
		}
		bw.write("PH\r\n");
		while((lines=br.readLine())!=null){
			String[] line = lines.split(",");
			for(int i=0;i<line.length-1;i++){
				String l = line[i];
				switch (l) {
				case "0":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "1":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,");
					break;
				case "2":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,");
					break;
				case "3":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,");
					break;
				case "4":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,");
					break;
				case "5":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,");
					break;
				case "6":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,");
					break;
				case "7":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,");
					break;
				case "8":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,");
					break;
				case "9":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,");
					break;
				case "10":
					bw.write("0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,");
					break;
				case "11":
					bw.write("0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "12":
					bw.write("0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "13":
					bw.write("0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "14":
					bw.write("0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "15":
					bw.write("0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "16":
					bw.write("0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "17":
					bw.write("0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "18":
					bw.write("0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "19":
					bw.write("0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "20":
					bw.write("1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				default:
					bw.write(l+",");
					break;
				}
			}
			bw.write(line[line.length-1]+"\r\n");
		}
		bw.close();
		br.close();
	}

	private static void write_R(String read, String write) throws Exception {
		// TODO automatically generated method stub

		BufferedReader br =new BufferedReader(new FileReader(new File(read)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(write)));
		String lines = br.readLine();
		bw.write(lines);
		bw.write("\r\n");
		while((lines=br.readLine())!=null){
			String[] line = lines.split(",");
			for(String l :line){
				switch (l) {
				case "-":
					bw.write("0,");
					break;
				case "A":
					bw.write("1,");
					break;
				case "C":
					bw.write("2,");
					break;
				case "D":
					bw.write("3,");
					break;
				case "E":
					bw.write("4,");
					break;
				case "F":
					bw.write("5,");
					break;
				case "G":
					bw.write("6,");
					break;
				case "H":
					bw.write("7,");
					break;
				case "I":
					bw.write("8,");
					break;
				case "K":
					bw.write("9,");
					break;
				case "L":
					bw.write("10,");
					break;
				case "M":
					bw.write("11,");
					break;
				case "N":
					bw.write("12,");
					break;
				case "P":
					bw.write("13,");
					break;
				case "Q":
					bw.write("14,");
					break;
				case "R":
					bw.write("15,");
					break;
				case "S":
					bw.write("16,");
					break;
				case "T":
					bw.write("17,");
					break;
				case "V":
					bw.write("18,");
					break;
				case "W":
					bw.write("19,");
					break;
				case "Y":
					bw.write("20,");
					break;
				default:
					bw.write("");
					break;
				}
			}
			bw.write("\r\n");
		}
		bw.close();
		br.close();	
	}

	private static void write_01(String read, String write) throws Exception {
		// TODO automatically generated method stub

		BufferedReader br =new BufferedReader(new FileReader(new File(read)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(write)));
//		String lines = br.readLine();
		String lines = "";
//		bw.write("PH,T,");
		for(int i=1;i<=7600;i++){
			bw.write("Val"+i);
			if(i!=7600){
				bw.write(",");
			}
		}
		bw.write("\r\n");
		while((lines=br.readLine())!=null){
			String[] line = lines.split(",");
			for(String l :line){
				switch (l) {
				case "-":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "A":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,");
					break;
				case "C":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,");
					break;
				case "D":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,");
					break;
				case "E":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,");
					break;
				case "F":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,");
					break;
				case "G":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,");
					break;
				case "H":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,");
					break;
				case "I":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,");
					break;
				case "K":
					bw.write("0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,");
					break;
				case "L":
					bw.write("0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,");
					break;
				case "M":
					bw.write("0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "N":
					bw.write("0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "P":
					bw.write("0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "Q":
					bw.write("0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "R":
					bw.write("0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "S":
					bw.write("0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "T":
					bw.write("0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "V":
					bw.write("0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "W":
					bw.write("0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				case "Y":
					bw.write("1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,");
					break;
				default:
					bw.write(l+",");
					break;
				}
			}
			bw.write("\r\n");
		}
		bw.close();
		br.close();
		
	
		
	}
}
