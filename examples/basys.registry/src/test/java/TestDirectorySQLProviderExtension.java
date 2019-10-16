import org.eclipse.basyx.regression.directory.sql.TestDirectorySQLProvider;

/**
 * Test queries to SQL directory provider. Tomcat server and postgres db is
 * started by docker compose
 * 
 * @author kuhn, ps
 *
 */
public class TestDirectorySQLProviderExtension extends TestDirectorySQLProvider {

	// Directory web service URL
	@Override
	public void setUp() {
		this.wsURL = "http://localhost:8081/registry";

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public TestDirectorySQLProviderExtension() {
		super();
	}
}
