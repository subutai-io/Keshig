package io.subutai.plugin.keshigqd.cli;


import java.util.HashMap;
import java.util.Map;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import io.subutai.plugin.keshigqd.api.KeshigQD;


@Command( scope = "keshigqd", name = "build", description = "build specific branch/tag/commit " )
public class BuildCommand extends OsgiCommandSupport
{
    @Argument( index = 0, name = "tests", description = "run with tests true/false", required = false, multiValued =
            false )
    String tests;

    @Argument( index = 1, name = "clean", description = "clean run true/false", required = false, multiValued = false )
    String clean;

    private KeshigQD keshigQD;


    public KeshigQD getKeshigQD()
    {
        return keshigQD;
    }


    public void setKeshigQD( final KeshigQD keshigQD )
    {
        this.keshigQD = keshigQD;
    }


    @Override
    protected Object doExecute() throws Exception
    {
        Map<String, String> args = new HashMap<>();
        args.put( io.subutai.plugin.keshigqd.api.entity.Command.tests, tests );
        args.put( io.subutai.plugin.keshigqd.api.entity.Command.clean, clean );
        keshigQD.build( args );

        return null;
    }
}