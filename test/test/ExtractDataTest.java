package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ExtractData;

class ExtractDataTest {

	static ExtractData ex;

	@BeforeEach
	void setUp() throws Exception {
		ex = new ExtractData("data_in\\2024_04_22-11_17.xlsx");
		ex.openFile();
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		ex.closeFile();
	}

	@Test
	void testExtractData() {
		//Do nothing
	}
	
	@Test
	void testGetActivityType() {
		String expected = "Cycling";
		String actual = ex.getActivityType();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetStartHour() {
		String expected = "22/04/2024 11:17";
		String actual = ex.getDate();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetActivityDuration() {
		String expected = "00:00:07";
		String actual = ex.getActivityDuration();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetTotalDistance() {
		float expected = 0.0062940358184278f;
		float actual = ex.getTotalDistance();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetAverageSpeed() {
		float expected = 3.77642130851746f;
		float actual = ex.getAverageSpeed();
		
		assertEquals(expected, actual);
	}

	@Test
	void testGetSpeedArray() {
		float[] expected = {0.0f, 8.69025039672852f, 4.17084932327271f, 0.624298512935638f, 2.67066264152527f, 4.24241876602173f, 2.26004981994629f};
		float[] actual = ex.getSpeedArray();
		
		assertArrayEquals(expected, actual);
	}

	@Test
	void testGetAltitudeArray() {
		float[] expected = {246.109069824219f, 245.398315429687f, 245.425903320312f, 244.929748535156f, 245.287902832031f, 245.134643554687f, 244.886962890625f};
		float[] actual = ex.getAltitudeArray();
		
		assertArrayEquals(expected, actual);
	}

	@Test
	void testGetDistanceArray() {
		float[] expected = {0.0f, 0.0024139583f, 0.0035725273f, 0.0037459435f, 0.0044877944f, 0.005666244f, 0.006294036f};
		float[] actual = ex.getDistanceArray();
		
		assertArrayEquals(expected, actual);
	}

}
