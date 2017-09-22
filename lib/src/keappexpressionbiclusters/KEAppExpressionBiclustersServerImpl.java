package keappexpressionbiclusters;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

import kbaserelationengine.Bicluster;
import kbaserelationengine.CleanKEAppResultsParams;
import kbaserelationengine.CompendiumDescriptor;
import kbaserelationengine.FeatureTerms;
import kbaserelationengine.GetBiclustersParams;
import kbaserelationengine.GetCompendiumDescriptorsParams;
import kbaserelationengine.GetFeatureTermsParams;
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
import kbkeutil.KbKeUtilServiceClient;
import kbkeutil.TermEnrichment;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.UnauthorizedException;

public class KEAppExpressionBiclustersServerImpl {
    private URL srvWizUrl = null;
    private URL callbackUrl = null;    	
	
	private final String DIST_METRIC = "cityblock";
	private final double DIST_THRESHOLD = 250;
	private final String FCLUSTER_CRITERION = "maxclust";
	private final String LINKAGE_METHOD = "ward";
	private final int MIN_CLUSTER_SIZE = 5;
	private final int MAX_CLUSTER_SIZE = 30;
	
	private final String APP_GUID_BICLUSTER_EXPRESSION = "KEApp1";
	private final String APP_GUID_BICLUSTER_EXPRESSION_GO_ENRICHMENT = "KEApp2";
	private final String APP_GUID_BICLUSTER_FITNESS = "KEApp3";
	private final String APP_GUID_BICLUSTER_FITNESS_GO_ENRICHMENT = "KEApp4";
	
	
    final double PVALUE_CUTOFF = 0.05;
//    final String GO_TERM_SPACE = "molecular_function";
    final String GO_TERM_SPACE = "biological_process";    
	
    
    public KEAppExpressionBiclustersServerImpl(Map<String, String> config) throws MalformedURLException {
        srvWizUrl = new URL(config.get("srv-wiz-url"));
//        callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
	}

	private KbKeUtilServiceClient getKEMathClient(AuthToken authPart) throws UnauthorizedException, IOException{
//        KbKeUtilClient client = new KbKeUtilClient(callbackUrl, authPart);
		KbKeUtilServiceClient client = new KbKeUtilServiceClient(srvWizUrl, authPart);
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

		// TODO remove  when fixed
		params.withAppGuid(APP_GUID_BICLUSTER_EXPRESSION);
		
        final String dataType = "gene expression";        
        return constructBiclusters(
        		params.getAppGuid(), 
        		dataType,         		
        		DIST_METRIC,
        		DIST_THRESHOLD,
        		FCLUSTER_CRITERION,
        		LINKAGE_METHOD,            		        		
        		authPart);
	}
	
	public KEAppOutput enrichGoterms4exprBiclusters(EnrichGoterms4exprBiclustersParams params, AuthToken authPart) throws IOException, JsonClientException {
		
		System.out.println("The app_guid: " + params.getAppGuid());
		
		// TODO remove  when fixed
		params.withAppGuid(APP_GUID_BICLUSTER_EXPRESSION_GO_ENRICHMENT);

		final String dataType = "gene expression";
		return enrichGoterms4Biclusters(params.getAppGuid(), APP_GUID_BICLUSTER_EXPRESSION, dataType, authPart);
	}
		
	public KEAppOutput constructFitnessBiclusters(ConstructFitnessBiclustersParams params, AuthToken authPart) throws IOException, JsonClientException {
		// TODO remove  when fixed
		params.withAppGuid(APP_GUID_BICLUSTER_FITNESS);

		final String dataType = "gene knockout fitness";
        return constructBiclusters(
        		params.getAppGuid(),        		
        		dataType, 
        		DIST_METRIC,
        		DIST_THRESHOLD,
        		FCLUSTER_CRITERION,
        		LINKAGE_METHOD,            		        		        		
        		authPart);
	}

	public KEAppOutput enrichGoterms4fitnessBiclusters(EnrichGoterms4fitnessBiclustersParams params,
			AuthToken authPart) throws IOException, JsonClientException {
		// TODO remove  when fixed
		params.withAppGuid(APP_GUID_BICLUSTER_FITNESS_GO_ENRICHMENT);

		final String dataType = "gene knockout fitness";
		return enrichGoterms4Biclusters(params.getAppGuid(), APP_GUID_BICLUSTER_FITNESS, dataType, authPart);
	}		

	public KEAppOutput orthologsEnrichGoterms4expr(OrthologsEnrichGoterms4ExpressionParams params, AuthToken authPart) {
		// TODO Auto-generated method stub
		return new KEAppOutput()
		.withNewReNodes(10L)
		.withNewReLinks(20L)
		.withUpdatedReNodes(0L)
		.withPropertiesSet(0L)
		.withMessage("");   
	}

	public KEAppOutput orthologsEnrichGoterms4fitness(OrthologsEnrichGoterms4FitnessParams params, AuthToken authPart) {
		// TODO Auto-generated method stub
		return new KEAppOutput()
		.withNewReNodes(10L)
		.withNewReLinks(20L)
		.withUpdatedReNodes(0L)
		.withPropertiesSet(0L)
		.withMessage("");   
	}

	public KEAppOutput orthologsKbaseEnrichGoterms(OrthologsKbaseEnrichGotermsParams params, AuthToken authPart) {
		// TODO Auto-generated method stub
		return new KEAppOutput()
		.withNewReNodes(10L)
		.withNewReLinks(20L)
		.withUpdatedReNodes(0L)
		.withPropertiesSet(0L)
		.withMessage("");   
	}	
    
	public KEAppOutput testBiclustering(TestBiclusteringParams params, AuthToken authPart) throws IOException, JsonClientException {

        return constructBiclusters(
        		params.getAppGuid(),        		
        		params.getDataType(), 
        		params.getDistMetric(),
        		params.getDistThreshold(),
        		params.getFclusterCriterion(),
        		params.getLinkageMethod(),            		        		        		
        		authPart);
	}
	
	private KEAppOutput constructBiclusters(
			String appGuid, 
			String dataType,
			String distMetric,
			double distThreshold,
			String fclusterCriterion,
			String linkageMethod,
			AuthToken authPart) throws IOException, JsonClientException {
		
        KBaseRelationEngineServiceClient reClient = getRECleint(authPart);
        KbKeUtilServiceClient kmClient = getKEMathClient(authPart);
        int biclusterId = 1;
        
        // Get KEApp
        KEAppDescriptor app = initApp(reClient, appGuid);
                
        // Get all compendia
        List<CompendiumDescriptor> compendia = reClient.getCompendiumDescriptors(
        		new GetCompendiumDescriptorsParams()
        		.withDataType(dataType));
        
        System.out.println("There are " + compendia.size() + " compendia to process");
        
        // Process each compendium
        for(CompendiumDescriptor cmp: compendia){
        	
        	System.out.println("Doing compendia: " + cmp);
        	
        	try{
            	// Build biclusters
            	BuildBiclustersOutput res = kmClient.buildBiclusters(
            		new BuildBiclustersParams()
            		.withNdarrayRef(cmp.getWsNdarrayId())
            		.withDistMetric(distMetric)
            		.withDistThreshold(distThreshold)
            		.withFclusterCriterion(fclusterCriterion)
            		.withLinkageMethod(linkageMethod));        		
            	
            	// Build list of biclusters
            	List<Bicluster> biclusters = new ArrayList<Bicluster>();
            	for(List<String> biItemGuids: res.getBiclusters()) {
            		int clusterSize = biItemGuids.size();
            		if(clusterSize < MIN_CLUSTER_SIZE || clusterSize > MAX_CLUSTER_SIZE){
            			continue;
            		}
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
        	}catch(Exception e){
        		e.printStackTrace(System.out);
        	}
        }
        
        return builKEAppOutput(app);
	}
	
	private KEAppOutput enrichGoterms4Biclusters(String appGuid, 
			String sourceAppGuid,
			String dataType, AuthToken authPart) throws IOException, JsonClientException {
        KBaseRelationEngineServiceClient reClient = getRECleint(authPart);
        KbKeUtilServiceClient kmClient = getKEMathClient(authPart);
        final String SOURCE_FEATURE_SET_TYPE = "Bicluster";        
        int profileId = 1;
        
        // Get KEApp
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
				// Consider only those features that have at least one term guid
				if(ft.getTermGuids().size() > 0){
					entityTermSet.put(ft.getFeatureGuid(), ft.getTermGuids());
				}
			}
			System.out.println("Total set size:" + entityTermSet.size());
			
        	
			// Get biclusters generated by a given app based on a given compendium
        	List<Bicluster> biclusters = reClient.getBiclusters(new GetBiclustersParams()
        			.withKeappGuid(sourceAppGuid)
        			.withCompendiumGuid(cmp.getGuid()));
        	
        	// Process each bicluster
    		List<String> sampleSet = new ArrayList<String>(); 
        	for(Bicluster b: biclusters){
        		
        		// Do enrichment for features from a bicluster
        		sampleSet.clear();
        		
        		// add feature only if it found in the total list (with go terms) 
        		for(String fguid: b.getFeatureGuids()){
        			if( entityTermSet.containsKey(fguid) ){
        				sampleSet.add(fguid);
        			}
        		}
        		System.out.println("Sample set size:" + sampleSet.size());
        		if(sampleSet.size() == 0) continue;        		        	    
        		
				Long propagation = 0L;
				
//				EnrichOnthologyParams p = new EnrichOnthologyParams()
//				.withSampleSet(sampleSet)
//				.withEntityTermSet(entityTermSet)
//				.withPropagation(propagation);
//				new ObjectMapper().writeValue(new File("EnrichOnthologyParams.json"), p);
        		
        		
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
					
					if(te.getPValue().doubleValue() > PVALUE_CUTOFF) continue;
					kbaserelationengine.TermEnrichment t = 
							new kbaserelationengine.TermEnrichment()
							.withExpectedCount(te.getExpectedCount())
							.withPValue(te.getPValue())
							.withSampleCount(te.getSampleCount())
							.withTermGuid(termGuid)
							.withTotalCount(te.getTotalCount());
					terms.add(t);
				}	
				
				if(terms.size() == 0) continue;
				
				TermEnrichmentProfile profile = new TermEnrichmentProfile()
						.withGuid("GOP:" + System.currentTimeMillis() + "_" + (profileId++))
						.withKeappGuid(app.getGuid())
						.withSourceGeneSetGuid(b.getGuid())
						.withSourceGeneSetType(SOURCE_FEATURE_SET_TYPE)
						.withTermNamespace(GO_TERM_SPACE)
						.withTerms(terms);
				profiles.add(profile);				
        	}
        	if(profiles.size() == 0) continue;
        	GraphUpdateStat graphStat = reClient.storeTermEnrichmentProfiles(new StoreTermEnrichmentProfilesParams()
					.withProfiles(profiles));
        	
//        	System.out.println("app: " + app);
//        	System.out.println("profile: " + );
//        	System.out.println("graphStat: " 
//        			+ graphStat.getNodesCreated()
//        			+ "\t" + graphStat.getRelationshipsCreated()
//        			+ "\t" + graphStat.getNodesCreated()
//        			+ "\t" + graphStat.getPropertiesSet()
//        			);
        	
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
