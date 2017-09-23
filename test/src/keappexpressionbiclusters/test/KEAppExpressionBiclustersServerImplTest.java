package keappexpressionbiclusters.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import keappexpressionbiclusters.EnrichGoterms4exprBiclustersParams;
import keappexpressionbiclusters.KEAppExpressionBiclustersServerImpl;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientException;

public class KEAppExpressionBiclustersServerImplTest {

	KEAppExpressionBiclustersServerImpl impl;
	String token = System.getenv("token");
	String user = System.getenv("user");
	@Before
	public void setUp() throws Exception {
		Map<String, String> config = new Hashtable<String,String>();		
		config.put("srv-wiz-url", System.getenv("srv-wiz-url"));			
		impl = new KEAppExpressionBiclustersServerImpl(config);
	}
	
//	@Test
	public void testConstructExprBiclusters() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnrichGoterms4exprBiclusters() throws IOException, JsonClientException {
		impl.enrichGoterms4exprBiclusters(new EnrichGoterms4exprBiclustersParams(), new AuthToken(token, user));
	}

//	@Test
	public void testConstructFitnessBiclusters() {
		fail("Not yet implemented");
	}

//	@Test
	public void testEnrichGoterms4fitnessBiclusters() {
		fail("Not yet implemented");
	}

//	@Test
	public void testOrthologsEnrichGoterms4expr() {
		fail("Not yet implemented");
	}

//	@Test
	public void testOrthologsEnrichGoterms4fitness() {
		fail("Not yet implemented");
	}

//	@Test
	public void testOrthologsKbaseEnrichGoterms() {
		fail("Not yet implemented");
	}

}
