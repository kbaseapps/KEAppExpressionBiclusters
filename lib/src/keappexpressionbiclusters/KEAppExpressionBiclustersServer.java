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
import java.net.URL;
import kbkeutil.*;
import kbaserelationengine.*;
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
    private static final String gitUrl = "";
    private static final String gitCommitHash = "";

    //BEGIN_CLASS_HEADER
    private URL wsUrl = null;
    private URL srvWizUrl = null;
    private URL callbackUrl = null;
    //END_CLASS_HEADER

    public KEAppExpressionBiclustersServer() throws Exception {
        super("KEAppExpressionBiclusters");
        //BEGIN_CONSTRUCTOR
        wsUrl = new URL(config.get("workspace-url"));
        srvWizUrl = new URL(config.get("srv-wiz-url"));
        callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: construct_expr_biclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link keappexpressionbiclusters.ConstructExprBiclustersInput ConstructExprBiclustersInput}
     * @return   instance of type {@link keappexpressionbiclusters.ConstructExprBiclustersOutput ConstructExprBiclustersOutput}
     */
    @JsonServerMethod(rpc = "KEAppExpressionBiclusters.construct_expr_biclusters", async=true)
    public ConstructExprBiclustersOutput constructExprBiclusters(ConstructExprBiclustersInput params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ConstructExprBiclustersOutput returnVal = null;
        //BEGIN construct_expr_biclusters
        // Sub-calls
        KbKeUtilClient kbKeUtil = new KbKeUtilClient(callbackUrl, authPart);
        kbKeUtil.setIsInsecureHttpConnectionAllowed(true);
        BuildBiclustersOutput bbo = kbKeUtil.buildBiclusters(new BuildBiclustersParams());
        // RE
        KBaseRelationEngineServiceClient re = new KBaseRelationEngineServiceClient(srvWizUrl);
        re.storeBiclusters(new StoreBiclustersParams());
        //END construct_expr_biclusters
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
