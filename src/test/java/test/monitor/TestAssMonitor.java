package test.monitor;

import static org.junit.Assert.*;

import org.junit.Test;

import ashish.assembly.ProductAssemblyLine;
import ashish.assembly.ProductFactory;


public class TestAssMonitor {

	@Test
	public void testConveyorBeltLoading() throws InterruptedException
	{
		ProductAssemblyLine pal = new ProductAssemblyLine();
		assertEquals(15, pal.putPartsOnConveyorBelt(10, 5));
	}
	
	@Test
	public void testProductAssemblyLine() throws InterruptedException
	{
		ProductAssemblyLine pal = new ProductAssemblyLine();
		pal.putPartsOnConveyorBelt(2, 1);
		assertEquals(2, pal.pickAndAssemble(2));
		assertFalse(4 == pal.pickAndAssemble(2));
		
	}
	
	@Test
	public void testInputValues() {

		ProductFactory pc = new ProductFactory();
		assertTrue(pc.validateUserInput(6, 3, 10));
		assertFalse(pc.validateUserInput(6, 3, 0));
		assertFalse(pc.validateUserInput(1, 1, 10));
	}

}
