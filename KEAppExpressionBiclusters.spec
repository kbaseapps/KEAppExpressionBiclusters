/*
A KBase module: KEAppExpressionBiclusters
*/

module KEAppExpressionBiclusters {

    typedef structure {
    } ConstructExprBiclustersInput;

    typedef structure {
    } ConstructExprBiclustersOutput;

    funcdef construct_expr_biclusters(ConstructExprBiclustersInput params)
        returns (ConstructExprBiclustersOutput) authentication required;
};
