# -*- coding: utf-8 -*-
############################################################
#
# Autogenerated by the KBase type compiler -
# any changes made here will be overwritten
#
############################################################

from __future__ import print_function
# the following is a hack to get the baseclient to import whether we're in a
# package or not. This makes pep8 unhappy hence the annotations.
try:
    # baseclient and this client are in a package
    from .baseclient import BaseClient as _BaseClient  # @UnusedImport
except:
    # no they aren't
    from baseclient import BaseClient as _BaseClient  # @Reimport


class KEAppExpressionBiclusters(object):

    def __init__(
            self, url=None, timeout=30 * 60, user_id=None,
            password=None, token=None, ignore_authrc=False,
            trust_all_ssl_certificates=False,
            auth_svc='https://kbase.us/services/authorization/Sessions/Login'):
        if url is None:
            raise ValueError('A url is required')
        self._service_ver = None
        self._client = _BaseClient(
            url, timeout=timeout, user_id=user_id, password=password,
            token=token, ignore_authrc=ignore_authrc,
            trust_all_ssl_certificates=trust_all_ssl_certificates,
            auth_svc=auth_svc)

    def construct_expr_biclusters(self, params, context=None):
        """
        :param params: instance of type "ConstructExprBiclustersParams" ->
           structure: parameter "app_guid" of String
        :returns: instance of type "KEAppOutput" -> structure: parameter
           "new_re_nodes" of Long, parameter "updated_re_nodes" of Long,
           parameter "new_re_links" of Long, parameter "properties_set" of
           Long, parameter "message" of String
        """
        return self._client.call_method(
            'KEAppExpressionBiclusters.construct_expr_biclusters',
            [params], self._service_ver, context)

    def enrich_goterms4expr_biclusters(self, params, context=None):
        """
        :param params: instance of type "EnrichGoterms4exprBiclustersParams"
           -> structure: parameter "app_guid" of String
        :returns: instance of type "KEAppOutput" -> structure: parameter
           "new_re_nodes" of Long, parameter "updated_re_nodes" of Long,
           parameter "new_re_links" of Long, parameter "properties_set" of
           Long, parameter "message" of String
        """
        return self._client.call_method(
            'KEAppExpressionBiclusters.enrich_goterms4expr_biclusters',
            [params], self._service_ver, context)

    def construct_fitness_biclusters(self, params, context=None):
        """
        :param params: instance of type "ConstructFitnessBiclustersParams" ->
           structure: parameter "app_guid" of String
        :returns: instance of type "KEAppOutput" -> structure: parameter
           "new_re_nodes" of Long, parameter "updated_re_nodes" of Long,
           parameter "new_re_links" of Long, parameter "properties_set" of
           Long, parameter "message" of String
        """
        return self._client.call_method(
            'KEAppExpressionBiclusters.construct_fitness_biclusters',
            [params], self._service_ver, context)

    def enrich_goterms4fitness_biclusters(self, params, context=None):
        """
        :param params: instance of type
           "EnrichGoterms4fitnessBiclustersParams" -> structure: parameter
           "app_guid" of String
        :returns: instance of type "KEAppOutput" -> structure: parameter
           "new_re_nodes" of Long, parameter "updated_re_nodes" of Long,
           parameter "new_re_links" of Long, parameter "properties_set" of
           Long, parameter "message" of String
        """
        return self._client.call_method(
            'KEAppExpressionBiclusters.enrich_goterms4fitness_biclusters',
            [params], self._service_ver, context)

    def orthologs_enrich_goterms4expr(self, params, context=None):
        """
        :param params: instance of type
           "OrthologsEnrichGoterms4ExpressionParams" -> structure: parameter
           "app_guid" of String
        :returns: instance of type "KEAppOutput" -> structure: parameter
           "new_re_nodes" of Long, parameter "updated_re_nodes" of Long,
           parameter "new_re_links" of Long, parameter "properties_set" of
           Long, parameter "message" of String
        """
        return self._client.call_method(
            'KEAppExpressionBiclusters.orthologs_enrich_goterms4expr',
            [params], self._service_ver, context)

    def orthologs_enrich_goterms4fitness(self, params, context=None):
        """
        :param params: instance of type
           "OrthologsEnrichGoterms4FitnessParams" -> structure: parameter
           "app_guid" of String
        :returns: instance of type "KEAppOutput" -> structure: parameter
           "new_re_nodes" of Long, parameter "updated_re_nodes" of Long,
           parameter "new_re_links" of Long, parameter "properties_set" of
           Long, parameter "message" of String
        """
        return self._client.call_method(
            'KEAppExpressionBiclusters.orthologs_enrich_goterms4fitness',
            [params], self._service_ver, context)

    def orthologs_kbase_enrich_goterms(self, params, context=None):
        """
        :param params: instance of type "OrthologsKbaseEnrichGotermsParams"
           -> structure: parameter "app_guid" of String
        :returns: instance of type "KEAppOutput" -> structure: parameter
           "new_re_nodes" of Long, parameter "updated_re_nodes" of Long,
           parameter "new_re_links" of Long, parameter "properties_set" of
           Long, parameter "message" of String
        """
        return self._client.call_method(
            'KEAppExpressionBiclusters.orthologs_kbase_enrich_goterms',
            [params], self._service_ver, context)

    def status(self, context=None):
        return self._client.call_method('KEAppExpressionBiclusters.status',
                                        [], self._service_ver, context)
