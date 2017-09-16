/*
A KBase module: KEAppExpressionBiclusters
*/

module KEAppExpressionBiclusters {

    typedef structure {
    } ConstructExprBiclustersInput;

    typedef structure {
		int new_re_nodes;    
		int updated_re_nodes;
		int new_re_links;
		int properties_set;			
		string message;    
    } ConstructExprBiclustersOutput;

    funcdef construct_expr_biclusters(ConstructExprBiclustersInput params)
        returns (ConstructExprBiclustersOutput) authentication required;
};
