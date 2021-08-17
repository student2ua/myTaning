package com.tor.commons.cli;

import org.apache.commons.cli.*;

/**
 * <ul>
 * <li> https://commons.apache.org/proper/commons-cli/</li>
 * <li> https://urvanov.ru/2019/06/08/apache-commons-cli/</li>
 * </ul>
 *
 * command line
 */
public class CommandLineExample {

    /**
     * @param args the command line arguments
     * @throws org.apache.commons.cli.ParseException
     *
     */
    public static void main(String[] args) throws ParseException {


        Options options = getOptions();



        // define parser
        CommandLine cmd;
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helper = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("t")) {
                System.out.println("test activated");
            }
            if (cmd.hasOption("t1")) {
                System.out.println("t1 activated");
            }

            if (cmd.hasOption("c")) {
                String opt_config = cmd.getOptionValue("config");
                System.out.println("Config set to " + opt_config);
            }
            if (cmd.hasOption("D")) {
                String opt_config = cmd.getOptionValue("D");
                System.out.println("D activated " + opt_config);
            }
            if (cmd.hasOption("coinInx")) {
                String opt_config = cmd.getOptionValue("index");
                System.out.println("coinInx activated " + opt_config);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Usage:", options);
            System.exit(0);
        }
    }

    private static Options getOptions() {
        Option alpha = new Option("t", "test", false, "Activate feature test");

        Option optionT1 = Option.builder("t1").hasArg().numberOfArgs(1).optionalArg(true).argName("t1_path").build();

        Option config = Option.builder("c").longOpt("config")
                .argName("config")
                .hasArg()
                .required(true)
                .desc("Set config file").build();

        Option property = Option.builder("D")
                .argName("key=value")// "property=value"
                .hasArgs()
                .numberOfArgs(2)
                .valueSeparator()
                .desc("use value for given key")
                .build();

        Option coinbaseOption = Option.builder()
                .longOpt("coinInx").desc("Specify Index")
                .hasArg(true).numberOfArgs(1).optionalArg(false).argName("index").type(Number.class)
                .build();


        // define options
        return new Options()
                .addOption(alpha)
                .addOption(optionT1)
                .addOption(config)
                .addOption(property)
                .addOption(coinbaseOption);
    }
}