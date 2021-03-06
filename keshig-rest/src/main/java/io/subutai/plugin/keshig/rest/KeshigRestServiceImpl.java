package io.subutai.plugin.keshig.rest;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import io.subutai.plugin.keshig.api.Keshig;
import io.subutai.plugin.keshig.api.Profile;
import io.subutai.plugin.keshig.api.entity.options.DeployOption;
import io.subutai.plugin.keshig.api.entity.options.TestOption;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


public class KeshigRestServiceImpl implements KeshigRestService
{

    private static final Logger LOG = LoggerFactory.getLogger( KeshigRestServiceImpl.class );

    private Keshig keshig;


    public KeshigRestServiceImpl()
    {
        LOG.warn( "Init keshig" );
    }


    public Keshig getKeshig()
    {
        return keshig;
    }


    public void setKeshig( Keshig keshig )
    {
        this.keshig = keshig;
    }


    @Override
    public Response listServers()
    {
        return Response.ok( keshig.getServers() ).build();
    }


    @Override
    public Response getServer( final String serverId )
    {
        return Response.ok( keshig.getServer( serverId ) ).build();
    }


    @Override
    public Response addServer( final String serverId )
    {
        try
        {
            keshig.addServer( serverId );
        }
        catch ( Exception e )
        {
            e.printStackTrace();

            return Response.serverError().build();
        }
        return Response.ok().build();
    }


    @Override
    public Response deleteServer( final String id )
    {
        keshig.removeServer( id );

        return Response.ok().build();
    }


    @Override
    public Response updateNightlyBuildStatus( final String hostname, final boolean status )
    {
        keshig.updateNightlyBuild( hostname, status );

        return Response.ok().build();
    }


    @Override
    public Response listOptions()
    {
        List<DeployOption> deployOptions = keshig.getAllDeployOptions();

        List<TestOption> testOptions = keshig.getAllTestOptions();

        Map<String, List<?>> allOptions = new HashMap<>();

        if ( deployOptions.size() > 0 )
        {
            allOptions.put( "DEPLOY", deployOptions );
        }
        if ( testOptions.size() > 0 )
        {
            allOptions.put( "TEST", testOptions );
        }

        return Response.ok().entity( allOptions ).build();
    }


    @Override
    public Response getOptionTypes()
    {
        Set<Object> options = Sets.newHashSet();

        options.add( "DEPLOY" );
        options.add( "TEST" );

        return Response.ok( options ).build();
    }


    @Override
    public Response getOptionsByType( String type )
    {

        switch ( type )
        {
            case "DEPLOY":

                return Response.ok().entity( keshig.getAllDeployOptions() ).build();

            case "TEST":

                return Response.ok().entity( keshig.getAllTestOptions() ).build();

            default:

                return Response.status( BAD_REQUEST ).entity( "Invalid option type" ).build();
        }
    }


    @Override
    public Response getOption( String type, String optionName )
    {

        switch ( type )
        {
            case "DEPLOY":

                return Response.ok().entity( keshig.getDeployOption( optionName ) ).build();

            case "TEST":

                return Response.ok().entity( keshig.getTestOption( optionName ) ).build();

            default:

                return Response.status( BAD_REQUEST ).entity( "Invalid option type" ).build();
        }
    }


    @Override
    public Response runOptionOnTargetServer( String type, String optionName, String serverId )
    {
        keshig.runOption( type, optionName, serverId );
        return Response.ok().build();
    }


    @Override
    public Response runPlaybooks( final String gitId, final String playbooks, final String serverId )
    {
        try
        {
            keshig.runPlaybooks( gitId, Arrays.asList( playbooks.split( "," ) ), serverId );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return Response.ok().build();
    }


    @Override
    public Response export( final String serverId, final String buildName )
    {
        keshig.export( buildName, serverId );

        return Response.ok().build();
    }


    @Override
    public Response addTestOption( TestOption option )
    {

        keshig.addOption( option );

        return Response.ok( keshig.getAllTestOptions() ).build();
    }


    @Override
    public Response addDeployOption( DeployOption option )
    {

        keshig.addOption( option );

        return Response.ok( keshig.getAllDeployOptions() ).build();
    }


    @Override
    public Response updateTestOption( TestOption option )
    {

        keshig.addOption( option );

        return Response.ok( keshig.getAllTestOptions() ).build();
    }


    @Override
    public Response updateDeployOption( DeployOption option )
    {

        keshig.addOption( option );

        return Response.ok( keshig.getAllDeployOptions() ).build();
    }


    @Override
    public Response deleteOption( String type, String optionName )
    {

        if ( Strings.isNullOrEmpty( optionName ) )
        {

            return Response.status( BAD_REQUEST ).entity( "Invalid option name" ).build();
        }

        keshig.deleteOption( type, optionName );

        return Response.ok().build();
    }


    @Override
    public Response getTests()
    {
        return Response.ok().entity( keshig.getPlaybooks() ).build();
    }


    @Override
    public Response updateStatuses()
    {
        keshig.updateKeshigServerStatuses();
        return Response.ok().build();
    }


    @Override
    public Response updateReserved( final String hostName, final String serverIp, final String usedBy,
                                    final String comment )
    {
        try
        {
            keshig.updateReserved( hostName, serverIp, usedBy, comment );
        }
        catch ( Exception e )
        {
            return Response.status( BAD_REQUEST ).entity( "Server is already reserved" ).build();
        }

        return Response.ok( keshig.getAllKeshigServers() ).build();
    }


    @Override
    public Response deleteReservation( final String hostname, final String serverIp )
    {
        keshig.freeReserved( hostname, serverIp );

        return Response.ok( keshig.getAllKeshigServers() ).build();
    }


    @Override
    public Response getStatuses()
    {
        return Response.ok( keshig.getAllKeshigServers() ).build();
    }


    @Override
    public Response listHistory()
    {
        return Response.ok().entity( keshig.listHistory() ).build();
    }


    @Override
    public Response getHistory( String id )
    {
        if ( Strings.isNullOrEmpty( id ) )
        {
            return Response.status( BAD_REQUEST ).entity( "Invalid id" ).build();
        }

        return Response.ok().entity( keshig.getHistory( id ) ).build();
    }


    @Override
    public Response runProfile( String profileName )
    {
        LOG.warn( String.format( "Running Profile:%s", profileName ) );
        keshig.runProfile( profileName );

        return Response.ok().build();
    }


    @Override
    public Response listProfiles()
    {
        return Response.ok().entity( keshig.listProfiles() ).build();
    }


    @Override
    public Response getProfile( String profileName )
    {

        if ( Strings.isNullOrEmpty( profileName ) )
        {
            return Response.status( BAD_REQUEST ).entity( "Invalid profile name" ).build();
        }

        return Response.ok().entity( keshig.getProfile( profileName ) ).build();
    }


    @Override
    public Response addProfile( Profile profile )
    {
        try
        {
            keshig.addProfile( profile );
        }
        catch ( Exception e )
        {

            return Response.status( BAD_REQUEST ).entity( "Error happened while saving profile info" ).build();
        }
        return Response.ok().build();
    }


    @Override
    public Response updateProfile( Profile profile )
    {

        return addProfile( profile );
    }


    @Override
    public Response deleteProfile( String profileName )
    {

        keshig.deleteProfile( profileName );

        return Response.ok().build();
    }
}
