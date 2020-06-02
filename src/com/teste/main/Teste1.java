package com.teste.main;

import java.util.Scanner;

import com.teste.apiclient.MovieClient;

public class Teste1 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		MovieClient movieClient = new MovieClient();
		System.out.println("Enter a substr to search movie titles: ");
		String substr = scan.nextLine();
		String[] movieTitles = movieClient.getMovieTitles(substr);
		
		if (movieTitles != null) {
			for (String movie : movieTitles) {
				System.out.println(movie);
			}
		}else {
			System.out.println("--No results found--");
		}
	}

}
