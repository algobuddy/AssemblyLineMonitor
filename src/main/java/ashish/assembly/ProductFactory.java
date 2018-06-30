package ashish.assembly;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductFactory {

	private static final Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		ProductFactory pc = new ProductFactory();
		System.out.println("Please input number of bolts :");
		final int bolts = pc.getUserInput();
		
		System.out.println("Please input number of machines :");
		final int machines = pc.getUserInput();
		
		System.out.println("Please input product assembly time in seconds :");
		final int assemblyTime = pc.getUserInput();
		
		Boolean isValid = pc.validateUserInput(bolts,machines,assemblyTime);
		
		if(!isValid){
			System.out.println("Invalid Input, please input valid values :");	
		}
		else{
			
			final ProductAssemblyLine pal = new ProductAssemblyLine();
			
			Runnable supRunnable = new Runnable(){
				public void run(){
						try {
							pal.putPartsOnConveyorBelt(bolts, machines);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			};
			
			Runnable empRunnable = new Runnable(){
				public void run(){
					try {
						pal.pickAndAssemble(assemblyTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			
			ExecutorService es = Executors.newFixedThreadPool(3);
			Thread sup = new Thread(supRunnable, "Supervisor");
			
			long startTime = System.nanoTime();
			sup.start();
			
			for(int i=0; i< 3; i++){
				es.execute(empRunnable);
			}
			es.shutdown();
			while(!es.isTerminated()){
				
			}
			
			System.out.println("Total Products Assembled = " + pal.productAssembled);
			System.out.println("Total Time Taken = " + (System.nanoTime()-startTime)/1000000000 + " seconds");
			
		}
	}
	
	public  Boolean validateUserInput(int bolts, int machines, int assemblyTime) {
		
		if(bolts/machines != 2 || bolts % machines != 0){
			return false;
		}
		
		if(assemblyTime < 1){
			return false;
		}
		
		return true;
	}

	public int getUserInput(){
		
		try {
			int n = Integer.parseInt(scan.nextLine().trim());
			return n;
		} catch (NumberFormatException e) {
			System.out.println(e.toString());
			return -1;
		}
	}

}
