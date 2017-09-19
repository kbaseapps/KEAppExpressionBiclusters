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
    } ConstructExprBiclustersParams;
    funcdef construct_expr_biclusters(ConstructExprBiclustersParams params)
        returns (KEAppOutput) authentication required;
        
    typedef structure {
    } EnrichGoterms4exprBiclustersParams;
    funcdef enrich_goterms4expr_biclusters(EnrichGoterms4exprBiclustersParams params)
        returns (KEAppOutput) authentication required;


};
