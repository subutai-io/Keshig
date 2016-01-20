package io.subutai.plugin.keshig.api;


import java.util.List;

import io.subutai.plugin.keshig.api.entity.History;
import io.subutai.plugin.keshig.api.entity.KeshigServer;
import io.subutai.plugin.keshig.api.entity.Server;
import io.subutai.plugin.keshig.api.entity.options.DeployOption;
import io.subutai.plugin.keshig.api.entity.options.Option;
import io.subutai.plugin.keshig.api.entity.options.TestOption;


public interface Keshig
{

    /*
    *   Keshig Option handlers
    * */

    void addOption( Option option );

    TestOption getTestOption( String name );

    DeployOption getDeployOption(String name);

    List<TestOption> getAllTestOptions();

    List<DeployOption> getAllDeployOptions();

    void deleteOption(String name);
    //
    void addServer( String server ) throws Exception;

    void removeServer( String serverId );

    Server getServer(String id);

    List<Server> getServers();

    //Keshig Server -> Hosting VM with Peer details

    void addKeshigServer( KeshigServer keshigServer ) throws Exception;

    void removeKeshigServer( String hostname );

    void updateKeshigServer( KeshigServer keshigServer );

    KeshigServer getKeshigServer( String hostname );

    List<KeshigServer> getAllKeshigServers();

    void dropAllServers();

    void updateKeshigServerStatuses();

    void addKeshigServers( List<KeshigServer> servers );

    void updateReserved( String hostName, String serverIp, String usedBy, String comment );

    void runOption(String optionName, String serverId);

    void runProfile( String profileName );

    /*
    *  Keshig History Handlers
    * */

    List<History> listHistory();

    History getHistory( String historyId );

    List<String> getPlaybooks();

    void saveHistory( History history );

    /*
    *  Keshig Profile Handlers
    * */

    List<Profile> listProfiles();

    Profile getProfile( String profileName );

    void updateProfile( Profile profile );

    void deleteProfile( String profileName );

    void addProfile( Profile profile ) throws Exception;

    /*
    *  Release
    * */

    void export( String buildName, String serverId );

    void publish( String boxName, String serverId );

    void freeReserved( String hostname, String serverIp );
}

