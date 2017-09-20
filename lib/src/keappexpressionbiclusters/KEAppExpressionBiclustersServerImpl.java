package keappexpressionbiclusters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kbaserelationengine.Bicluster;
import kbaserelationengine.CleanKEAppResultsParams;
import kbaserelationengine.CompendiumDescriptor;
import kbaserelationengine.FeatureTerms;
import kbaserelationengine.GetBiclustersParams;
import kbaserelationengine.GetCompendiumDescriptorsParams;
import kbaserelationengine.GetFeatureTermsParams;
import kbaserelationengine.GetKEAppDescriptorParams;
import kbaserelationengine.GraphUpdateStat;
import kbaserelationengine.KBaseRelationEngineServiceClient;
import kbaserelationengine.KEAppDescriptor;
import kbaserelationengine.StoreBiclustersParams;
import kbaserelationengine.StoreKEAppDescriptorParams;
import kbaserelationengine.StoreTermEnrichmentProfilesParams;
import kbaserelationengine.TermEnrichmentProfile;
import kbkeutil.BuildBiclustersOutput;
import kbkeutil.BuildBiclustersParams;
import kbkeutil.EnrichOnthologyOutput;
import kbkeutil.EnrichOnthologyParams;
import kbkeutil.KbKeUtilClient;
import kbkeutil.TermEnrichment;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.UnauthorizedException;

public class KEAppExpressionBiclustersServerImpl {
    private URL srvWizUrl = null;
    private URL callbackUrl = null;    	
	
    public KEAppExpressionBiclustersServerImpl(Map<String, String> config) throws MalformedURLException {
        srvWizUrl = new URL(config.get("srv-wiz-url"));
        callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
	}

	private KbKeUtilClient getKEMathClient(AuthToken authPart) throws UnauthorizedException, IOException{
        KbKeUtilClient client = new KbKeUtilClient(callbackUrl, authPart);
        client.setIsInsecureHttpConnectionAllowed(true);
        client.setServiceVersion("dev");
        return client;
    }    
    
    private KBaseRelationEngineServiceClient getRECleint(AuthToken authPart) throws UnauthorizedException, IOException{
        KBaseRelationEngineServiceClient client = new KBaseRelationEngineServiceClient(srvWizUrl, authPart);
        client.setIsInsecureHttpConnectionAllowed(true);
        client.setServiceVersion("dev");
        return client;    	
    }

	public KEAppOutput constructExprBiclusters(ConstructExprBiclustersParams params,
			AuthToken authPart) throws IOException, JsonClientException {		
        final String dataType = "gene expression";        
        return constructBiclusters(params.getAppGuid(), dataType, authPart);
	}
	
	public KEAppOutput enrichGoterms4exprBiclusters(EnrichGoterms4exprBiclustersParams params, AuthToken authPart) throws IOException, JsonClientException {
        final String dataType = "gene expression";
		return enrichGoterms4Biclusters(params.getAppGuid(), dataType, authPart);
	}
		
	public KEAppOutput constructFitnessBiclusters(ConstructFitnessBiclustersParams params, AuthToken authPart) throws IOException, JsonClientException {
		final String dataType = "gene knockout fitness";
        return constructBiclusters(params.getAppGuid(), dataType, authPart);
	}

	public KEAppOutput enrichGoterms4fitnessBiclusters(EnrichGoterms4fitnessBiclustersParams params,
			AuthToken authPart) throws IOException, JsonClientException {
		final String dataType = "gene knockout fitness";
		return enrichGoterms4Biclusters(params.getAppGuid(), dataType, authPart);
	}		

	public KEAppOutput orthologsEnrichGoterms4expr(OrthologsEnrichGoterms4ExpressionParams params, AuthToken authPart) {
		// TODO Auto-generated method stub
		return new KEAppOutput()
		.withNewReNodes(0L)
		.withNewReLinks(0L)
		.withUpdatedReNodes(0L)
		.withPropertiesSet(0L)
		.withMessage("");   
	}

	public KEAppOutput orthologsEnrichGoterms4fitness(OrthologsEnrichGoterms4FitnessParams params, AuthToken authPart) {
		// TODO Auto-generated method stub
		return new KEAppOutput()
		.withNewReNodes(0L)
		.withNewReLinks(0L)
		.withUpdatedReNodes(0L)
		.withPropertiesSet(0L)
		.withMessage("");   
	}

	public KEAppOutput orthologsKbaseEnrichGoterms(OrthologsKbaseEnrichGotermsParams params, AuthToken authPart) {
		// TODO Auto-generated method stub
		return new KEAppOutput()
		.withNewReNodes(0L)
		.withNewReLinks(0L)
		.withUpdatedReNodes(0L)
		.withPropertiesSet(0L)
		.withMessage("");   
	}	
    
	private KEAppOutput constructBiclusters(String appGuid, String dataType,
			AuthToken authPart) throws IOException, JsonClientException {
		
        KBaseRelationEngineServiceClient reClient = getRECleint(authPart);
        KbKeUtilClient kmClient = getKEMathClient(authPart);
        int biclusterId = 1;
        
        // Get KEApp
//        KEAppDescriptor app = getApp(reClient, appGuid); 
        KEAppDescriptor app = initApp(reClient, appGuid);
                
        // Get all compendia
        List<CompendiumDescriptor> compendia = reClient.getCompendiumDescriptors(
        		new GetCompendiumDescriptorsParams()
        		.withDataType(dataType));
        
        // Process each compendium
        for(CompendiumDescriptor cmp: compendia){
        	
        	// Build biclusters
        	BuildBiclustersOutput res = kmClient.buildBiclusters(
        		new BuildBiclustersParams()
        		.withNdarrayRef(cmp.getWsNdarrayId())
        		.withDistMetric("euclidean")
        		.withDistThreshold(50.0)
        		.withFclusterCriterion("distance")
        		.withLinkageMethod("ward"));        		
        	
        	// Build list of biclusters
        	List<Bicluster> biclusters = new ArrayList<Bicluster>();
        	for(List<String> biItemGuids: res.getBiclusters()) {
        		Bicluster bic = new Bicluster()
        				.withCompendiumGuid(cmp.getGuid())
        				.withConditionGuids(null)
        				.withFeatureGuids(biItemGuids)
        				.withGuid("BIC:" + System.currentTimeMillis() + "_" + (biclusterId++))
        				.withKeappGuid(app.getGuid());
        		biclusters.add(bic);
        	}
        	
        	// Store biclusters
        	GraphUpdateStat graphStat = reClient.storeBiclusters(
        			new StoreBiclustersParams()
        			.withBiclusters(biclusters));
        	
        	updateAppState(reClient, app, graphStat);
        	// Update app state        	
        }
        
        return builKEAppOutput(app);
	}
	
	private KEAppOutput enrichGoterms4Biclusters(String appGuid, 
			String dataType, AuthToken authPart) throws IOException, JsonClientException {
        KBaseRelationEngineServiceClient reClient = getRECleint(authPart);
        KbKeUtilClient kmClient = getKEMathClient(authPart);
        final String GO_TERM_SPACE = "molecular_function";
        final String SOURCE_FEATURE_SET_TYPE = "Bicluster";
        int profileId = 1;
        
        // Get KEApp
//        KEAppDescriptor app = getApp(reClient, appGuid); 
        KEAppDescriptor app = initApp(reClient, appGuid);
        
        // Get all compendia
        List<CompendiumDescriptor> compendia = reClient.getCompendiumDescriptors(
        		new GetCompendiumDescriptorsParams()
        		.withDataType(dataType));
        
        // Process biclusters for each compendium
        for(CompendiumDescriptor cmp: compendia){
        	// GO term profiles to be generated
			List<TermEnrichmentProfile> profiles = new ArrayList<TermEnrichmentProfile>();
        				
			// Build total set
        	List<FeatureTerms> featureTerms = reClient.getFeatureTerms(new GetFeatureTermsParams()
        			.withTaxonGuid(cmp.getTaxonomyGuid())
        			.withTermSpace(GO_TERM_SPACE));        	
			Map<String, List<String>> entityTermSet = new Hashtable<String,List<String>>();
			for(FeatureTerms ft: featureTerms){
				entityTermSet.put(ft.getFeatureGuid(), ft.getTermGuids());
			}
        	
			// Get biclusters
        	List<Bicluster> biclusters = reClient.getBiclusters(new GetBiclustersParams()
        			.withCompendiumGuid(cmp.getGuid()));
        	
        	int index = 0;
        	// Process each bicluster
        	for(Bicluster b: biclusters){
        		if(index++ >= 3) break;        	
        		
        		// Do enrichment for features from a bicluster
        		List<String> sampleSet = b.getFeatureGuids();
        		if(sampleSet.size() == 0) continue;
        		        	        		        		
				Long propagation = 1L;
				EnrichOnthologyOutput res = kmClient.enrichOnthology(
        				new EnrichOnthologyParams()
        				.withSampleSet(sampleSet)
        				.withEntityTermSet(entityTermSet)
        				.withPropagation(propagation)
        				);
				
				// Do not do anything if there are no enriched terms
				if(res.getEnrichmentProfile().size() == 0) continue;								
				
				// Build profile
				List<kbaserelationengine.TermEnrichment> terms = new ArrayList<kbaserelationengine.TermEnrichment>();				
				for(Entry<String, TermEnrichment> tte : res.getEnrichmentProfile().entrySet()){
					String termGuid = tte.getKey();
					TermEnrichment te = tte.getValue();
					kbaserelationengine.TermEnrichment t = 
							new kbaserelationengine.TermEnrichment()
							.withExpectedCount(te.getExpectedCount())
							.withPValue(te.getPValue())
							.withSampleCount(te.getSampleCount())
							.withTermGuid(termGuid)
							.withTotalCount(te.getTotalCount());
					terms.add(t);
				}				
				TermEnrichmentProfile profile = new TermEnrichmentProfile()
						.withGuid("GOP:" + System.currentTimeMillis() + "_" + (profileId++))
						.withKeappGuid(app.getGuid())
						.withSourceGeneSetGuid(b.getGuid())
						.withSourceGeneSetType(SOURCE_FEATURE_SET_TYPE)
						.withTermNamespace(GO_TERM_SPACE)
						.withTerms(terms);
				profiles.add(profile);				
        	}
        	GraphUpdateStat graphStat = reClient.storeTermEnrichmentProfiles(new StoreTermEnrichmentProfilesParams()
					.withProfiles(profiles));
        	
        	updateAppState(reClient, app, graphStat);
        }
        
        return builKEAppOutput(app);   
	}
	
	


	private KEAppOutput builKEAppOutput(KEAppDescriptor app) {
		return new KEAppOutput()
		.withNewReNodes(app.getNodesCreated())
		.withNewReLinks(app.getRelationsCreated())
		.withUpdatedReNodes(0L)
		.withPropertiesSet(app.getPropertiesSet())
		.withMessage("");        
	}

	private void updateAppState(KBaseRelationEngineServiceClient reClient, KEAppDescriptor app,
			GraphUpdateStat graphStat) throws IOException, JsonClientException {
        app
    	.withNodesCreated(app.getNodesCreated() + graphStat.getNodesCreated())
    	.withRelationsCreated(app.getRelationsCreated() + graphStat.getRelationshipsCreated())
    	.withPropertiesSet(app.getPropertiesSet() + graphStat.getPropertiesSet());
        
        reClient.storeKEAppDescriptor(new StoreKEAppDescriptorParams()
        		.withApp(app));            
	}

	private KEAppDescriptor initApp(KBaseRelationEngineServiceClient reClient, String appGuid) throws IOException, JsonClientException {
		KEAppDescriptor app = new KEAppDescriptor()
	        	.withNodesCreated(0L)
	        	.withRelationsCreated(0L)
	        	.withPropertiesSet(0L)
	        	.withGuid(appGuid)
	        	.withLastRunEpoch(System.currentTimeMillis());
		
        reClient.storeKEAppDescriptor(new StoreKEAppDescriptorParams()
        		.withApp(app));        
		
		// Clean all results generated by this app before
        reClient.cleanKEAppResults(
        		new CleanKEAppResultsParams()
        		.withAppGuid(app.getGuid()));
        return app;
	}

	
//	private KEAppDescriptor getApp(KBaseRelationEngineServiceClient reClient, String appGuid) throws IOException, JsonClientException {
//        return reClient.getKEAppDescriptor(
//        		new GetKEAppDescriptorParams()
//        		.withAppGuid(appGuid));
//	}

}
