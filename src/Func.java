import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

class PlusPlus extends Thread {
    private int id;
  
    public PlusPlus(int idInput) {
    	this.id=idInput;
    }

    @Override
    public void run() {
        for (int i = id; i <Func.sizesOfArray[Func.c2] ; i+=Func.numberOfThreadsArray[Func.c1]) {
        	Func.answerMatrix[i]=Func.arr1[i]*Func.arr2[i];
        }
    }
}

public class Func {
	
	static int[] arr1=new int[1000000];
	static int[] arr2=new int[1000000];
	static int[] numberOfThreadsArray= {1,2,5,10,20,50,100,150};
	static int[] sizesOfArray={100,1000,10000,100000,1000000};
	static int[] answerMatrix;
	static int c2;
	static int c1;
	
	static int sum;
	static File file ;
	static FileWriter writer ;
	public static void createFile() throws IOException {
		file = new File("D://eclipse for java//projects//OSLab22//files//testFile2.txt");
		//Create the file
		if (file.createNewFile())
		{
		    System.out.println("File is created!");
		} else {
		    System.out.println("File already exists.");
		}
		writer = new FileWriter(file);
	}
	static Thread[] threadsArray=new Thread[150];
	static long[] periodHolderArray = new long[11];

    public static void main(String[] args) throws IOException {
    	//createFile();
    	
    	for ( c2 = 0; c2 < sizesOfArray.length; c2++)
    	 {
    		System.out.println("----------------------------------------------------");
    		System.out.println("size of array in this try: " +Integer.valueOf(sizesOfArray[c2]));

    		for ( c1 = 0; c1 < numberOfThreadsArray.length; c1++){
				System.out.println("");
				System.out.println("number of threads in this try: " +Integer.valueOf(numberOfThreadsArray[c1]));
				
				for (int c3 = 0; c3 < 10; c3++) {
					for (int i = 0; i < sizesOfArray[c2]; i++) {
			    		arr1[i]=(int)(ThreadLocalRandom.current().nextInt());
			    		arr2[i]=(int)(ThreadLocalRandom.current().nextInt());
					}
					answerMatrix=new int[sizesOfArray[c2]];
					threadsArray=new Thread[numberOfThreadsArray[c1]];
					long startTime = System.nanoTime();

			        for (int i = 0; i < numberOfThreadsArray[c1]; i++) {
			        	PlusPlus p = new PlusPlus(i);
			        	threadsArray[i]=p;
					}
			        
			        for (int i = 0; i < numberOfThreadsArray[c1]; i++) {
			        	threadsArray[i].start();
					}
			        
			        try {
			        	for (int i = 0; i < numberOfThreadsArray[c1]; i++) {
			        		threadsArray[i].join();
			        		int mainRes = 0;
			        		for (int j = 0; j < sizesOfArray[c2]; j++) {
								mainRes+=answerMatrix[j];
							}
			    		}
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
			        
			        long endTime = System.nanoTime();
			        long totalTime = endTime - startTime;
			        periodHolderArray[c3]=totalTime/1000;
			       
				}
				long sumsum=0;
				for (int k = 0; k < 10; k++) {
					sumsum+= periodHolderArray[k];
				}
				System.out.print("\r");
				System.out.println("average time taken: " +sumsum/10);
				
			}
		}
    }
    
}
