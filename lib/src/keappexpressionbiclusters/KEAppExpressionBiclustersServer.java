package keappexpressionbiclusters;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
//END_HEADER

/**
 * <p>Original spec-file module name: KEAppExpressionBiclusters</p>
 * <pre>
 * A KBase module: KEAppExpressionBiclusters
 * </pre>
 */
public class KEAppExpressionBiclustersServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;
    private static final String version = "0.0.1";
    private static final String gitUrl = "https://github.com/psnovichkov/KEAppExpressionBiclusters.git";
    private static final String gitCommitHash = "7a8ea9df5754b92dc8ce159810303cae6b0ea2d3";

    //BEGIN_CLASS_HEADER
    KEAppExpressionBiclustersServerImpl impl;
    //END_CLASS_HEADER

    public KEAppExpressionBiclustersServer() throws Exception {
        super("KEAppExpressionBiclusters");
        //BEGIN_CONSTRUCTOR
        impl = new KEAppExpressionBiclustersServerImpl(config);
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: construct_expr_biclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link keappexpressionbiclusters.ConstructExprBiclustersParams ConstructExprBiclustersParams}
     * @return   instance of type {@link keappexpressionbiclusters.KEAppOutput KEAppOutput}
     */
    @JsonServerMethod(rpc = "KEAppExpressionBiclusters.construct_expr_biclusters", async=true)
    public KEAppOutput constructExprBiclusters(ConstructExprBiclustersParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        KEAppOutput returnVal = null;
        //BEGIN construct_expr_biclusters
        returnVal = impl.constructExprBiclusters( params,  authPart);
        //END construct_expr_biclusters
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: enrich_goterms4expr_biclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link keappexpressionbiclusters.EnrichGoterms4exprBiclustersParams EnrichGoterms4exprBiclustersParams}
     * @return   instance of type {@link keappexpressionbiclusters.KEAppOutput KEAppOutput}
     */
    @JsonServerMethod(rpc = "KEAppExpressionBiclusters.enrich_goterms4expr_biclusters", async=true)
    public KEAppOutput enrichGoterms4exprBiclusters(EnrichGoterms4exprBiclustersParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        KEAppOutput returnVal = null;
        //BEGIN enrich_goterms4expr_biclusters
        returnVal = impl.enrichGoterms4exprBiclusters(params, authPart);
        //END enrich_goterms4expr_biclusters
        return returnVal;
    }
    @JsonServerMethod(rpc = "KEAppExpressionBiclusters.status")
    public Map<String, Object> status() {
        Map<String, Object> returnVal = null;
        //BEGIN_STATUS
        returnVal = new LinkedHashMap<String, Object>();
        returnVal.put("state", "OK");
        returnVal.put("message", "");
        returnVal.put("version", version);
        returnVal.put("git_url", gitUrl);
        returnVal.put("git_commit_hash", gitCommitHash);
        //END_STATUS
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new KEAppExpressionBiclustersServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new KEAppExpressionBiclustersServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
