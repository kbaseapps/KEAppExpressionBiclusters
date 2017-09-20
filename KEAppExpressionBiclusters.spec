/*
A KBase module: KEAppExpressionBiclusters
*/

module KEAppExpressionBiclusters {

    typedef structure {
		int new_re_nodes;    
		int updated_re_nodes;
		int new_re_links;
		int properties_set;			
		string message;    
    } KEAppOutput;

    typedef structure {
    	string app_guid;
    } ConstructExprBiclustersParams;
    funcdef construct_expr_biclusters(ConstructExprBiclustersParams params)
        returns (KEAppOutput) authentication required;
        
    typedef structure {
    	string app_guid;
    } EnrichGoterms4exprBiclustersParams;
    funcdef enrich_goterms4expr_biclusters(EnrichGoterms4exprBiclustersParams params)
        returns (KEAppOutput) authentication required;


    typedef structure {
    	string app_guid;
    } ConstructFitnessBiclustersParams;
    funcdef construct_fitness_biclusters(ConstructFitnessBiclustersParams params)
        returns (KEAppOutput) authentication required;
        
    typedef structure {
    	string app_guid;
    } EnrichGoterms4fitnessBiclustersParams;
    funcdef enrich_goterms4fitness_biclusters(EnrichGoterms4fitnessBiclustersParams params)
        returns (KEAppOutput) authentication required;
        

    typedef structure {
    	string app_guid;
    } OrthologsEnrichGoterms4ExpressionParams;
    funcdef orthologs_enrich_goterms4expr(OrthologsEnrichGoterms4ExpressionParams params)
        returns (KEAppOutput) authentication required;

    typedef structure {
    	string app_guid;
    } OrthologsEnrichGoterms4FitnessParams;
    funcdef orthologs_enrich_goterms4fitness(OrthologsEnrichGoterms4FitnessParams params)
        returns (KEAppOutput) authentication required;

    typedef structure {
    	string app_guid;
    } OrthologsKbaseEnrichGotermsParams;
    funcdef orthologs_kbase_enrich_goterms(OrthologsKbaseEnrichGotermsParams params)
        returns (KEAppOutput) authentication required;        

};
