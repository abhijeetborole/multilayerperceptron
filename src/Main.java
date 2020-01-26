import java.util.*;
import java.io.*;
//import tech.tablesaw.api.Table;
//import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.io.csv.CsvReadOptions.Builder;
import java.nio.*;
import java.nio.file.*;
import java.math.*;
import java.util.Random;
public class Main {
	Random rand = new Random();
	public static void main(String[] args) throws Exception {
		double err = 0.0;
		int i=0,j=0,c=0, iteration = 0,no_class_haber = 2,no_class_jet = 7,correct = 0, wrong = 0;
		String fileName = "haberman.data";
		String fileName1 = "shuttle.trn";
		File file = new File(fileName);
		File file1 = new File(fileName1);
		
            double[][] h = new double[3][306];
    		double[][] ah = new double[306][3];
    		double[][] ho = new double[no_class_haber][306];
    		double[] hotest = new double[306];
//    		double[][] ahotest = new double[306][1];
    		double[][] aho = new double[306][no_class_haber];

             double[][] jet = new double[9][43806];
     		 double[][] ajet = new double[43806][9];
     		 double[] jettest = new double[43806];
     		 double[][] jeto = new double[no_class_jet][43806];
     		 double[][] ajeto = new double[43806][no_class_jet];
		
		try {
			Scanner inputStream = new Scanner(file);
			do {
				String data  = inputStream.next();
				//System.out.println(data);
			    String[] d = data.split(",");
			    for(int q = 0;q<d.length;q++) {
			    	if(q<3) {
						h[j][i] = Double.parseDouble(d[q]);
						j++;
					}else {
						hotest[i] = Integer.parseInt(d[q]);
						if(Integer.parseInt(d[q])==1) {
							ho[0][i] = 1.0;
							ho[1][i] = 0.0;
						}
						else {
							ho[0][i] = 0.0;
							ho[1][i] = 1.0;
						}
					}
			    }
			    j = 0;
			    i++;
			}while (inputStream.hasNext());
			//System.out.println(Arrays.toString(h[2]));
			inputStream.close();
		}

		catch(Exception e) {
			System.out.println(e.getMessage());
		}
//		try {
			Scanner inputStream1 = new Scanner(file1);
			do {
				String data1  = inputStream1.next();
			    String[] d1 = data1.split(",");
				//System.out.println(data1);
			    for(int q = 0;q<d1.length;q++) {
			    	if(q<d1.length-1) {
			    		//System.out.println(i+","+j+","+q);
						jet[j][i] = Double.parseDouble(d1[q]);
						j++;
					}else {
						int jetout = Integer.parseInt(d1[q]);
						jettest[i] = jetout;
							for(int jetiter = 0;jetiter < no_class_jet;jetiter++ ) {
								if(jetiter == jetout)
									jeto[jetiter][i] = 1.0;
								else 
									jeto[jetiter][i] = 0.0;
							}
						}
					}
			    j = 0;
			    i++;
			}while (inputStream1.hasNext());
			//System.out.println(Arrays.toString(h[2]));
			inputStream1.close();
//		}

//		catch(Exception e) {
//			System.out.println(e.getMessage());
	//	}
		
		for(int a=0;a<306;a++) {
			for(int b=0;b<3;b++)
				ah[a][b] = h[b][a];
			for(int b=0;b<2;b++)
				aho[a][b] = ho[b][a];
			//if(aho[a][0]==2)
				//aho[a][0] = 0;
			//System.out.println(Arrays.toString(ah[a]));
			//System.out.println(Arrays.toString(aho[a]));
		}
		for(int a=0;a<43500;a++) {
			for(int b=0;b<9;b++)
				ajet[a][b] = jet[b][a];
			for(int b=0;b<7;b++)
				ajeto[a][b] = jeto[b][a];
			//if(aho[a][0]==2)
				//aho[a][0] = 0;
			//System.out.println(Arrays.toString(ah[a]));
			//System.out.println(Arrays.toString(aho[a]));
		}

		int[] layers = {3,15,2};
		MLP mhaber = new MLP(layers, 0.001,new tanh());
		for(int o = 0; o<10000; o++) {
			for(int n=0;n<306;n++) {
				double in[] = ah[n];
				double out[] = aho[n];
				//System.out.println("in"+in.length+" ,out"+out.length);
				err = mhaber.backProp(in, out);
			}
			System.out.println("Error at step "+o+" is "+err);
			double tryin[] = {35,67,10};
			System.out.println("output for 35,67,10 = "+Arrays.toString(mhaber.calcOutput(tryin)));
		}
		for(int oc =0;oc<306;oc++) {
			double in [] = ah[oc];
			double out[] = mhaber.calcOutput(in);
			System.out.println("Predicted is "+arrayMax(out)+", Actual is "+hotest[oc]);
			if(arrayMax(out)==hotest[oc])
				correct++;
			else
				wrong++;
		}
		System.out.println("Accuracy is "+correct*100.0/306+"%");
 		
		int[] layersjet = {9,18,7};
		MLP mjet = new MLP(layersjet, 0.01,new tanh());
		for(int o = 0; o<10000; o++) {
			for(int n=0;n<43806;n++) {
				double in[] = ajet[n];
				double out[] = ajeto[n];
				//System.out.println("in"+in.length+" ,out"+out.length);
				err = mjet.backProp(in, out);
			}
			System.out.println("Error at step "+o+" is "+err);
			double tryin[] = {50,21,77,0,28,0,27,48,22};
			//System.out.println("output for 50,21,77,0,28,0,27,48,22 = "+Arrays.toString(mjet.calcOutput(tryin)));
		}

		
	}
	public static double arrayMax(double a[]) {
		double max = a[0];
		int index = 0;

		for (int i = 0; i < a.length; i++) 
		{
			if (max < a[i]) 
			{
				max = a[i];
				index = i;
			}
		}
		return index+1;
	}
}
