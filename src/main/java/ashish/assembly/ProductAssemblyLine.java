package ashish.assembly;

import java.util.LinkedList;

public class ProductAssemblyLine {

	LinkedList<String> conveyorBelt = new LinkedList<>();
	int capacity = 100;
	Boolean allLoaded = false;
	Integer productAssembled = 0;

	public int putPartsOnConveyorBelt(int bolts, int machines) throws InterruptedException {

		while (bolts > 0 || machines > 0) {
			synchronized (this) {
				while (conveyorBelt.size() == capacity)
					wait();

				if (bolts > 0) {
					conveyorBelt.add("B");
					conveyorBelt.add("B");
					bolts -= 2;
				}

				if (machines > 0) {
					conveyorBelt.add("M");
					machines--;
				}

				System.out.println(Thread.currentThread().getName() + " added bolts and machines");
				notifyAll();
			}
		}
		System.out.println(Thread.currentThread().getName() + " has no bolts and machine lefts");
		allLoaded = true;
		return conveyorBelt.size();
	}

	public int pickAndAssemble(int prodAsseblyTime) throws InterruptedException {
		int boltsCollected = 0;
		int machineCollected = 0;

		long startTime = System.nanoTime();
		while (conveyorBelt.size() > 0) {
			synchronized (this) {
				if (conveyorBelt.size() == 0)
					wait();

				if (conveyorBelt.element() == "B" && boltsCollected < 2) {
					conveyorBelt.removeFirst();
					boltsCollected++;
					System.out.println(Thread.currentThread().getName() + " picked bolt " + boltsCollected);
				}

				if (conveyorBelt.element() == "M" && machineCollected < 1) {
					conveyorBelt.removeFirst();
					machineCollected++;
					System.out.println(Thread.currentThread().getName() + " picked machine " + machineCollected);
				}
				notifyAll();
			}

				if (boltsCollected == 2 && machineCollected == 1) {
					Thread.sleep(1000 * prodAsseblyTime);
					boltsCollected = 0;
					machineCollected = 0;
					synchronized (this) {
						productAssembled++;
						System.out.println(Thread.currentThread().getName() + " completed product " + productAssembled);
					}
				}
		}
		
		return (int) ((System.nanoTime()-startTime) / 1000000000);
	}
}
