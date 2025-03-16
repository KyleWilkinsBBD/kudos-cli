package com.bbdgrads.kudos_cli;

import com.bbdgrads.kudos_cli.auth.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class KudosCliApplication {

	@Autowired
	private static AuthClient authClient;

	/*
	 * TODO:
	 *  Need to make sure cli can talk to api
	 * 	Need to send client keys to cli
	 * 	Need to get auth code from user here
	 */

	public static void main(String[] args) {
		System.out.println("\n\n" +
				"\t\t\t\t██╗  ██╗ ██╗   ██╗ ██████╗   ██████╗  ███████╗\n" +
				"\t\t\t\t██║ ██╔╝ ██║   ██║ ██╔══██╗ ██╔═══██╗ ██╔════╝\n" +
				"\t\t\t\t█████╔╝  ██║   ██║ ██║  ██║ ██║   ██║ ███████╗\n" +
				"\t\t\t\t██╔═██╗  ██║   ██║ ██║  ██║ ██║   ██║ ╚════██║\n" +
				"\t\t\t\t██║  ██╗ ╚██████╔╝ ██████╔╝ ╚██████╔╝ ███████║\n" +
				"\t\t\t\t╚═╝  ╚═╝  ╚═════╝  ╚═════╝   ╚═════╝  ╚══════╝\n\n" +
				"                                          ");
		SpringApplication.run(KudosCliApplication.class, args);
	}

}


