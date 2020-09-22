
public class main {
	public static void main(String[] args) throws Exception{
//		Split_train_test.main(args);
//		create_samples.main(args);
//		protein_learning learning = new protein_learning();
//		learning.matrix_size = 30;
//		learning.main(args);
//		check_matrix c =new check_matrix();
//		c.matrix_size=30;
//		c.main(args);
//		
//		for(double i=0;i<1;i+=0.1){
//			Loss_function loss = new Loss_function();
//			loss.alpha=i;
//			loss.matrix_size=learning.matrix_size;
//			c = new check_matrix();
//			c.matrix_size=loss.matrix_size;
//			c.main(args);
//		}
		check_obj co = new check_obj();
		for(double i=0;i<1;i+=0.1){
			co.alpha=i;
			co.main(args);
		}
//		check.main(args);
	}
}
