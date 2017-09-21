package KEAppExpressionBiclusters::KEAppExpressionBiclustersClient;

use JSON::RPC::Client;
use POSIX;
use strict;
use Data::Dumper;
use URI;
use Bio::KBase::Exceptions;
my $get_time = sub { time, 0 };
eval {
    require Time::HiRes;
    $get_time = sub { Time::HiRes::gettimeofday() };
};

use Bio::KBase::AuthToken;

# Client version should match Impl version
# This is a Semantic Version number,
# http://semver.org
our $VERSION = "0.1.0";

=head1 NAME

KEAppExpressionBiclusters::KEAppExpressionBiclustersClient

=head1 DESCRIPTION


A KBase module: KEAppExpressionBiclusters


=cut

sub new
{
    my($class, $url, @args) = @_;
    

    my $self = {
	client => KEAppExpressionBiclusters::KEAppExpressionBiclustersClient::RpcClient->new,
	url => $url,
	headers => [],
    };

    chomp($self->{hostname} = `hostname`);
    $self->{hostname} ||= 'unknown-host';

    #
    # Set up for propagating KBRPC_TAG and KBRPC_METADATA environment variables through
    # to invoked services. If these values are not set, we create a new tag
    # and a metadata field with basic information about the invoking script.
    #
    if ($ENV{KBRPC_TAG})
    {
	$self->{kbrpc_tag} = $ENV{KBRPC_TAG};
    }
    else
    {
	my ($t, $us) = &$get_time();
	$us = sprintf("%06d", $us);
	my $ts = strftime("%Y-%m-%dT%H:%M:%S.${us}Z", gmtime $t);
	$self->{kbrpc_tag} = "C:$0:$self->{hostname}:$$:$ts";
    }
    push(@{$self->{headers}}, 'Kbrpc-Tag', $self->{kbrpc_tag});

    if ($ENV{KBRPC_METADATA})
    {
	$self->{kbrpc_metadata} = $ENV{KBRPC_METADATA};
	push(@{$self->{headers}}, 'Kbrpc-Metadata', $self->{kbrpc_metadata});
    }

    if ($ENV{KBRPC_ERROR_DEST})
    {
	$self->{kbrpc_error_dest} = $ENV{KBRPC_ERROR_DEST};
	push(@{$self->{headers}}, 'Kbrpc-Errordest', $self->{kbrpc_error_dest});
    }

    #
    # This module requires authentication.
    #
    # We create an auth token, passing through the arguments that we were (hopefully) given.

    {
	my %arg_hash2 = @args;
	if (exists $arg_hash2{"token"}) {
	    $self->{token} = $arg_hash2{"token"};
	} elsif (exists $arg_hash2{"user_id"}) {
	    my $token = Bio::KBase::AuthToken->new(@args);
	    if (!$token->error_message) {
	        $self->{token} = $token->token;
	    }
	}
	
	if (exists $self->{token})
	{
	    $self->{client}->{token} = $self->{token};
	}
    }

    my $ua = $self->{client}->ua;	 
    my $timeout = $ENV{CDMI_TIMEOUT} || (30 * 60);	 
    $ua->timeout($timeout);
    bless $self, $class;
    #    $self->_validate_version();
    return $self;
}




=head2 construct_expr_biclusters

  $return = $obj->construct_expr_biclusters($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.ConstructExprBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
ConstructExprBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.ConstructExprBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
ConstructExprBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub construct_expr_biclusters
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function construct_expr_biclusters (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to construct_expr_biclusters:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'construct_expr_biclusters');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.construct_expr_biclusters",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'construct_expr_biclusters',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method construct_expr_biclusters",
					    status_line => $self->{client}->status_line,
					    method_name => 'construct_expr_biclusters',
				       );
    }
}
 


=head2 enrich_goterms4expr_biclusters

  $return = $obj->enrich_goterms4expr_biclusters($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.EnrichGoterms4exprBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
EnrichGoterms4exprBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.EnrichGoterms4exprBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
EnrichGoterms4exprBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub enrich_goterms4expr_biclusters
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function enrich_goterms4expr_biclusters (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to enrich_goterms4expr_biclusters:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'enrich_goterms4expr_biclusters');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.enrich_goterms4expr_biclusters",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'enrich_goterms4expr_biclusters',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method enrich_goterms4expr_biclusters",
					    status_line => $self->{client}->status_line,
					    method_name => 'enrich_goterms4expr_biclusters',
				       );
    }
}
 


=head2 construct_fitness_biclusters

  $return = $obj->construct_fitness_biclusters($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.ConstructFitnessBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
ConstructFitnessBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.ConstructFitnessBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
ConstructFitnessBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub construct_fitness_biclusters
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function construct_fitness_biclusters (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to construct_fitness_biclusters:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'construct_fitness_biclusters');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.construct_fitness_biclusters",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'construct_fitness_biclusters',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method construct_fitness_biclusters",
					    status_line => $self->{client}->status_line,
					    method_name => 'construct_fitness_biclusters',
				       );
    }
}
 


=head2 enrich_goterms4fitness_biclusters

  $return = $obj->enrich_goterms4fitness_biclusters($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.EnrichGoterms4fitnessBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
EnrichGoterms4fitnessBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.EnrichGoterms4fitnessBiclustersParams
$return is a KEAppExpressionBiclusters.KEAppOutput
EnrichGoterms4fitnessBiclustersParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub enrich_goterms4fitness_biclusters
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function enrich_goterms4fitness_biclusters (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to enrich_goterms4fitness_biclusters:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'enrich_goterms4fitness_biclusters');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.enrich_goterms4fitness_biclusters",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'enrich_goterms4fitness_biclusters',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method enrich_goterms4fitness_biclusters",
					    status_line => $self->{client}->status_line,
					    method_name => 'enrich_goterms4fitness_biclusters',
				       );
    }
}
 


=head2 orthologs_enrich_goterms4expr

  $return = $obj->orthologs_enrich_goterms4expr($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.OrthologsEnrichGoterms4ExpressionParams
$return is a KEAppExpressionBiclusters.KEAppOutput
OrthologsEnrichGoterms4ExpressionParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.OrthologsEnrichGoterms4ExpressionParams
$return is a KEAppExpressionBiclusters.KEAppOutput
OrthologsEnrichGoterms4ExpressionParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub orthologs_enrich_goterms4expr
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function orthologs_enrich_goterms4expr (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to orthologs_enrich_goterms4expr:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'orthologs_enrich_goterms4expr');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.orthologs_enrich_goterms4expr",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'orthologs_enrich_goterms4expr',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method orthologs_enrich_goterms4expr",
					    status_line => $self->{client}->status_line,
					    method_name => 'orthologs_enrich_goterms4expr',
				       );
    }
}
 


=head2 orthologs_enrich_goterms4fitness

  $return = $obj->orthologs_enrich_goterms4fitness($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.OrthologsEnrichGoterms4FitnessParams
$return is a KEAppExpressionBiclusters.KEAppOutput
OrthologsEnrichGoterms4FitnessParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.OrthologsEnrichGoterms4FitnessParams
$return is a KEAppExpressionBiclusters.KEAppOutput
OrthologsEnrichGoterms4FitnessParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub orthologs_enrich_goterms4fitness
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function orthologs_enrich_goterms4fitness (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to orthologs_enrich_goterms4fitness:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'orthologs_enrich_goterms4fitness');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.orthologs_enrich_goterms4fitness",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'orthologs_enrich_goterms4fitness',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method orthologs_enrich_goterms4fitness",
					    status_line => $self->{client}->status_line,
					    method_name => 'orthologs_enrich_goterms4fitness',
				       );
    }
}
 


=head2 orthologs_kbase_enrich_goterms

  $return = $obj->orthologs_kbase_enrich_goterms($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.OrthologsKbaseEnrichGotermsParams
$return is a KEAppExpressionBiclusters.KEAppOutput
OrthologsKbaseEnrichGotermsParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.OrthologsKbaseEnrichGotermsParams
$return is a KEAppExpressionBiclusters.KEAppOutput
OrthologsKbaseEnrichGotermsParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub orthologs_kbase_enrich_goterms
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function orthologs_kbase_enrich_goterms (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to orthologs_kbase_enrich_goterms:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'orthologs_kbase_enrich_goterms');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.orthologs_kbase_enrich_goterms",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'orthologs_kbase_enrich_goterms',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method orthologs_kbase_enrich_goterms",
					    status_line => $self->{client}->status_line,
					    method_name => 'orthologs_kbase_enrich_goterms',
				       );
    }
}
 


=head2 test_biclustering

  $return = $obj->test_biclustering($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KEAppExpressionBiclusters.TestBiclusteringParams
$return is a KEAppExpressionBiclusters.KEAppOutput
TestBiclusteringParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
	data_type has a value which is a string
	dist_metric has a value which is a string
	dist_threshold has a value which is a float
	fcluster_criterion has a value which is a string
	linkage_method has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string

</pre>

=end html

=begin text

$params is a KEAppExpressionBiclusters.TestBiclusteringParams
$return is a KEAppExpressionBiclusters.KEAppOutput
TestBiclusteringParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
	data_type has a value which is a string
	dist_metric has a value which is a string
	dist_threshold has a value which is a float
	fcluster_criterion has a value which is a string
	linkage_method has a value which is a string
KEAppOutput is a reference to a hash where the following keys are defined:
	new_re_nodes has a value which is an int
	updated_re_nodes has a value which is an int
	new_re_links has a value which is an int
	properties_set has a value which is an int
	message has a value which is a string


=end text

=item Description



=back

=cut

 sub test_biclustering
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function test_biclustering (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to test_biclustering:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'test_biclustering');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KEAppExpressionBiclusters.test_biclustering",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'test_biclustering',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method test_biclustering",
					    status_line => $self->{client}->status_line,
					    method_name => 'test_biclustering',
				       );
    }
}
 
  
sub status
{
    my($self, @args) = @_;
    if ((my $n = @args) != 0) {
        Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
                                   "Invalid argument count for function status (received $n, expecting 0)");
    }
    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
        method => "KEAppExpressionBiclusters.status",
        params => \@args,
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
                           code => $result->content->{error}->{code},
                           method_name => 'status',
                           data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
                          );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method status",
                        status_line => $self->{client}->status_line,
                        method_name => 'status',
                       );
    }
}
   

sub version {
    my ($self) = @_;
    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
        method => "KEAppExpressionBiclusters.version",
        params => [],
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(
                error => $result->error_message,
                code => $result->content->{code},
                method_name => 'test_biclustering',
            );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(
            error => "Error invoking method test_biclustering",
            status_line => $self->{client}->status_line,
            method_name => 'test_biclustering',
        );
    }
}

sub _validate_version {
    my ($self) = @_;
    my $svr_version = $self->version();
    my $client_version = $VERSION;
    my ($cMajor, $cMinor) = split(/\./, $client_version);
    my ($sMajor, $sMinor) = split(/\./, $svr_version);
    if ($sMajor != $cMajor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Major version numbers differ.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor < $cMinor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Client minor version greater than Server minor version.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor > $cMinor) {
        warn "New client version available for KEAppExpressionBiclusters::KEAppExpressionBiclustersClient\n";
    }
    if ($sMajor == 0) {
        warn "KEAppExpressionBiclusters::KEAppExpressionBiclustersClient version is $svr_version. API subject to change.\n";
    }
}

=head1 TYPES



=head2 KEAppOutput

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
new_re_nodes has a value which is an int
updated_re_nodes has a value which is an int
new_re_links has a value which is an int
properties_set has a value which is an int
message has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
new_re_nodes has a value which is an int
updated_re_nodes has a value which is an int
new_re_links has a value which is an int
properties_set has a value which is an int
message has a value which is a string


=end text

=back



=head2 ConstructExprBiclustersParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 EnrichGoterms4exprBiclustersParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 ConstructFitnessBiclustersParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 EnrichGoterms4fitnessBiclustersParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 OrthologsEnrichGoterms4ExpressionParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 OrthologsEnrichGoterms4FitnessParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 OrthologsKbaseEnrichGotermsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 TestBiclusteringParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string
data_type has a value which is a string
dist_metric has a value which is a string
dist_threshold has a value which is a float
fcluster_criterion has a value which is a string
linkage_method has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string
data_type has a value which is a string
dist_metric has a value which is a string
dist_threshold has a value which is a float
fcluster_criterion has a value which is a string
linkage_method has a value which is a string


=end text

=back



=cut

package KEAppExpressionBiclusters::KEAppExpressionBiclustersClient::RpcClient;
use base 'JSON::RPC::Client';
use POSIX;
use strict;

#
# Override JSON::RPC::Client::call because it doesn't handle error returns properly.
#

sub call {
    my ($self, $uri, $headers, $obj) = @_;
    my $result;


    {
	if ($uri =~ /\?/) {
	    $result = $self->_get($uri);
	}
	else {
	    Carp::croak "not hashref." unless (ref $obj eq 'HASH');
	    $result = $self->_post($uri, $headers, $obj);
	}

    }

    my $service = $obj->{method} =~ /^system\./ if ( $obj );

    $self->status_line($result->status_line);

    if ($result->is_success) {

        return unless($result->content); # notification?

        if ($service) {
            return JSON::RPC::ServiceObject->new($result, $self->json);
        }

        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    elsif ($result->content_type eq 'application/json')
    {
        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    else {
        return;
    }
}


sub _post {
    my ($self, $uri, $headers, $obj) = @_;
    my $json = $self->json;

    $obj->{version} ||= $self->{version} || '1.1';

    if ($obj->{version} eq '1.0') {
        delete $obj->{version};
        if (exists $obj->{id}) {
            $self->id($obj->{id}) if ($obj->{id}); # if undef, it is notification.
        }
        else {
            $obj->{id} = $self->id || ($self->id('JSON::RPC::Client'));
        }
    }
    else {
        # $obj->{id} = $self->id if (defined $self->id);
	# Assign a random number to the id if one hasn't been set
	$obj->{id} = (defined $self->id) ? $self->id : substr(rand(),2);
    }

    my $content = $json->encode($obj);

    $self->ua->post(
        $uri,
        Content_Type   => $self->{content_type},
        Content        => $content,
        Accept         => 'application/json',
	@$headers,
	($self->{token} ? (Authorization => $self->{token}) : ()),
    );
}



1;
