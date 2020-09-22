import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class protein_learning {
	static int matrix_size = 30;
	static int num =0;                                          //��������
	static double c =-50.0;                                      //����c
	static double c_mt = 0.0;                                    //��c��Ӧ��mt
	static double c_vt = 0.0;										 //��c��Ӧ��vt
	static ArrayList<Double> alp = new ArrayList<>();	   		// �ֵ�
	static ArrayList<String> positive_sample = new ArrayList<>();//��������
	static ArrayList<String> negative_sample = new ArrayList<>();//��������
	static Map<Double, Double[]> matrix = new HashMap<>();         //��������
	static Map<Double, Double[]> All_mt = new HashMap<>();          //����������Ӧ��mt����
	static Map<Double, Double[]> All_vt = new HashMap<>();          //����������Ӧ��vt����
	/*************************************************************/
	static final Double beta1 = (double) 0.9;
	static final Double beta2 = (double) 0.999;
	static final Double epsilon = (double) Math.pow(10, -8);
	static final Double eta = (double) 0.001;
	/*************************************************************/
	public static void main(String[] args) throws Exception{
		BufferedReader br =new BufferedReader(new FileReader(new File("samples")));
		String lines ="";
		while((lines=br.readLine())!=null){
			String[] line = lines.split(":");
			positive_sample.add(line[0]);
			negative_sample.add(line[1]);
			
		}
		br.close();
	
		for(int i=0;i<401;i++){
			alp.add((double) i);
		}
		
		//���ɾ���
		for(int i=0;i<alp.size();i++){
			Double[] m =new Double[matrix_size];
			for(int j=0;j<matrix_size;j++){
				Double r = Math.random();
				int a = 1;
				if(r>=0.5){
					a = -1;
				}
				m[j] = Math.random()*0.01*a;
			}
			matrix.put(alp.get(i), m);
		}
		
		//��ʼ��mt,vt����
		for(int i=0;i<alp.size();i++){
			Double[] temp_mt =new Double[matrix_size];
			Double[] temp_vt =new Double[matrix_size];
			for(int j=0;j<matrix_size;j++){
				temp_mt[j] = 0.0;
				temp_vt[j] = 0.0;
			}
			All_mt.put(alp.get(i), temp_mt);
			All_vt.put(alp.get(i), temp_vt);
		}
		//batch=10������10w��
		for(int i=1;i<100000;i++){
			num++;
			ArrayList<String> positive = new ArrayList<>();
			ArrayList<String> negative = new ArrayList<>();
			positive.addAll(positive_sample.subList((i-1)*10,i*10));
			negative.addAll(negative_sample.subList((i-1)*10,i*10));
			update(positive,negative);
			
//			if(i % 10000== 0||i==1){
//				System.out.println(caculate());
//			}
			if(i==99999){
				BufferedWriter bw =new BufferedWriter(new FileWriter(new File("D:\\demo\\results\\matrix_PH_alp_"+matrix_size+".csv")));
				bw.write(c+"\r\n");
				for(int j=0;j<alp.size();j++){
					Double[] n = matrix.get(alp.get(j));
					bw.write(alp.get(j)+",");
					for(int s=0;s<n.length;s++){
						bw.write(n[s]+",");
					}
					bw.write("\r\n");
				}
				bw.close();
			}
		}
	}
	//����Ŀ�꺯��
	private static Double caculate() {
		// TODO �Զ����ɵķ������
		Double res = 0.0;
		for(int i=0;i<positive_sample.size();i++){
			//�ĸ�node
			Double topic_n1i = Double.parseDouble(positive_sample.get(i).split(";")[0].split(",")[0]);
			Double topic_n1j = Double.parseDouble(positive_sample.get(i).split(";")[0].split(",")[1]);
			Double topic_n2i = Double.parseDouble(negative_sample.get(i).split(";")[0].split(",")[0]);
			Double topic_n2j = Double.parseDouble(negative_sample.get(i).split(";")[0].split(",")[1]);
			//��������Ƶ�ʺ͸�������Ƶ��
			Double Pn1 = Math.pow(Double.parseDouble(positive_sample.get(i).split(";")[1]), 0.75);
			Double Pn2 = Math.pow(Double.parseDouble(negative_sample.get(i).split(";")[1]), 0.75);
			//�ĸ�node������
			Double[] n1i = new Double[matrix_size];
			Double[] n1j = new Double[matrix_size];
			Double[] n2i = new Double[matrix_size];
			Double[] n2j = new Double[matrix_size];	
			for(int j=0;j<matrix_size;j++){
				n1i[j]=matrix.get(topic_n1i)[j];
				n1j[j]=matrix.get(topic_n1j)[j];
				n2i[j]=matrix.get(topic_n2i)[j];
				n2j[j]=matrix.get(topic_n2j)[j];
			}
			Double temp = 0.0;
			temp = (Math.log(sigma(Math.log(Pij(n1i,n1j))-Math.log(Pn1)))+Math.log(1-sigma(Math.log(Pij(n2i,n2j))-Math.log(Pn2))));
			res = res+temp;
			
		}
		
		return res;
	}
	//����sigmoid����
	private static double sigma(double d) {
		// TODO �Զ����ɵķ������
		return  1/(1+Math.exp(-d));
	}
	//�����ݶ�
	private static void update(ArrayList<String> positive, ArrayList<String> negative) {
		// TODO �Զ����ɵķ������
		
		// ��ʼ���ݶȾ���
		Map<Double, Double[]> All_gt = new HashMap<>();
		for (int i = 0; i < alp.size(); i++) {
			Double[] temp = new Double[matrix_size];
			for (int j = 0; j < matrix_size; j++) {
				temp[j] = 0.0;
			}
			All_gt.put(alp.get(i), temp);
		}

		Double gt_c = 0.0; // �洢������c���ݶȵ��ۼӺ�
		for (int i = 0; i < positive.size(); i++) {
			// �ĸ�ID��2��λ�ã�2��������
			Double C_1i = Double.parseDouble(positive.get(i).split(";")[0].split(",")[0]);
			Double V_1j = Double.parseDouble(positive.get(i).split(";")[0].split(",")[1]);
			Double C_2i = Double.parseDouble(negative.get(i).split(";")[0].split(",")[0]);
			Double V_2j = Double.parseDouble(negative.get(i).split(";")[0].split(",")[1]);
			// ��������Ƶ�ʺ͸�������Ƶ��
			Double Pn1 = Math.pow(Double.parseDouble(positive.get(i).split(";")[1]), 0.75);
			Double Pn2 = Math.pow(Double.parseDouble(negative.get(i).split(";")[1]), 0.75);
			// 4��ID��Ӧ������
			Double[] c1i = new Double[matrix_size];
			Double[] v1j = new Double[matrix_size];
			Double[] c2i = new Double[matrix_size];
			Double[] v2j = new Double[matrix_size];
			for (int j = 0; j < matrix_size; j++) {
				c1i[j] = matrix.get(C_1i)[j];
				v1j[j] = matrix.get(V_1j)[j];
				c2i[j] = matrix.get(C_2i)[j];
				v2j[j] = matrix.get(V_2j)[j];
			}

			// //�洢�ĸ�node�������ݶ�
			Double[] temp_c1i = new Double[matrix_size];
			Double[] temp_v1j = new Double[matrix_size];
			Double[] temp_c2i = new Double[matrix_size];
			Double[] temp_v2j = new Double[matrix_size];

			for (int j = 0; j < matrix_size; j++) {
				temp_c1i[j] = 0.0;
				temp_v1j[j] = 0.0;
				temp_c2i[j] = 0.0;
				temp_v2j[j] = 0.0;
			}

			// �����ݶ�
			Double theta_n1 = 0.0;
			if ((Pn1 + Pij(c1i, v1j)) != 0) {
				theta_n1 = Pn1 / (Pn1 + Pij(c1i, v1j));
			}
			Double theta_n2 = 0.0;
			if ((Pn2 + Pij(c2i, v2j)) != 0) {
				theta_n2 = -Pij(c2i, v2j) / (Pn2 + Pij(c2i, v2j));
			}
			for (int j = 0; j < matrix_size; j++) {
				temp_c1i[j] = v1j[j] * theta_n1;
				temp_v1j[j] = c1i[j] * theta_n1;
				temp_c2i[j] = v2j[j] * theta_n2;
				temp_v2j[j] = c2i[j] * theta_n2;
			}
			Double temp_c = 0.0;
			temp_c = theta_n1 + theta_n2;

			// ��c���ݶȽ����ۼ�
			gt_c += temp_c;
			// ���������ݶȽ����ۼ�
			Double[] to_n1i = new Double[matrix_size];
			Double[] to_n1j = new Double[matrix_size];
			Double[] to_n2i = new Double[matrix_size];
			Double[] to_n2j = new Double[matrix_size];
			if(C_1i==C_2i){
				to_n1i=to_n2i;
			}
			for (int j = 0; j < matrix_size; j++) {
				to_n1i[j] = All_gt.get(C_1i)[j];
				to_n1j[j] = All_gt.get(V_1j)[j];
				to_n2i[j] = All_gt.get(C_2i)[j];
				to_n2j[j] = All_gt.get(V_2j)[j];
			}
			for (int j = 0; j < matrix_size; j++) {
				to_n1i[j] += temp_c1i[j];
				to_n1j[j] += temp_v1j[j];
				to_n2i[j] += temp_c2i[j];
				to_n2j[j] += temp_v2j[j];

			}
			All_gt.remove(C_1i);
			All_gt.remove(V_1j);
			All_gt.remove(C_2i);
			All_gt.remove(V_2j);
			All_gt.put(C_1i, to_n1i);
			All_gt.put(V_1j, to_n1j);
			All_gt.put(C_2i, to_n2i);
			All_gt.put(V_2j, to_n2j);

		}
		for (int i = 0; i < alp.size(); i++) {
			Double[] result_n = new Double[matrix_size]; // �洢��������
			for (int j = 0; j < matrix_size; j++) {
				result_n[j] = 0.0;
			}
			Double[] temp_ni_gt = store(All_gt.get(alp.get(i)), alp.get(i)); // ��������������
			for (int j = 0; j < matrix_size; j++) {
				result_n[j] = matrix.get(alp.get(i))[j] + temp_ni_gt[j];
			}
			matrix.remove(alp.get(i));
			matrix.put(alp.get(i), result_n);
		}
		store(gt_c);

	}
    //���������������ݶ�
	private static Double[] store(Double[] ni, Double n) {
		// TODO �Զ����ɵķ������
		Double[] deta = new Double[matrix_size];
		Double[] mt = new Double[matrix_size];
		Double[] vt = new Double[matrix_size];

		for(int i=0;i<deta.length;i++){
			deta[i] = 0.0;
			mt[i] = All_mt.get(n)[i];
			vt[i] = All_vt.get(n)[i];
			
		}
		
		for(int i=0;i<matrix_size;i++){
			mt[i] = beta1*mt[i]+(1-beta1)*ni[i];
			vt[i] = beta2*vt[i]+(1-beta2)*Math.pow(ni[i], 2);
		}
		All_mt.remove(n);
		All_vt.remove(n);
		All_mt.put(n, mt);
		All_vt.put(n, vt);
		
		Double[] temp_mt = new Double[matrix_size];
		Double[] temp_vt = new Double[matrix_size];
		for(int i=0;i<matrix_size;i++){
			temp_mt[i]=mt[i]/(1-Math.pow(beta1, num));
			temp_vt[i]=vt[i]/(1-Math.pow(beta2, num));
		}
		for(int i=0;i<matrix_size;i++){
			deta[i]=(eta*temp_mt[i])/(Math.sqrt(temp_vt[i])+epsilon);
		}
		return deta;
	}
	//����c���ݶ�
	private static void store(double i) {
		// TODO �Զ����ɵķ������
		c_mt=beta1*c_mt+(1-beta1)*i;
		c_vt=beta2*c_vt+(1-beta2)*i*i;
		Double temp_mt =c_mt/(1-Math.pow(beta1,num));
		Double temp_vt =c_vt/(1-Math.pow(beta2,num));
		c = c+eta*temp_mt/(Math.sqrt(temp_vt)+epsilon);
	}
	//����P(V,C)
	private static Double Pij(Double[] ni, Double[] nj) {
		// TODO �Զ����ɵķ������
		Double res = (double) 0;
		for(int i=0;i<ni.length;i++){
			res+=ni[i]*nj[i];
		}
		return Math.exp(res+c);
	}
	
}
