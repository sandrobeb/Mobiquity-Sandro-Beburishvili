import Steps.JsonSteps;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class jsonTests {

	@BeforeTest
	public static void setup() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
	}

	@Test
	public void assertEmailsInCommentsAreInProperFormat() {
		JsonSteps jsonSteps = new JsonSteps();

	jsonSteps.searchForCommentsByUser("Delphine");

	Assert.assertTrue(jsonSteps.checkIfEmailIsValid());
	}
}
