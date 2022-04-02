package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		System.out.println("Hello! Welcome to Film Query!");
		System.out.println("Please select a menu option.");
		menu(input);

	}

	private void menu(Scanner input) {

		int userChoice = 0;

		do {

			System.out.println("To look up a film with it's id, enter 1: ");
			System.out.println("To look up a film with a keyword, enter 2: ");
			System.out.println("To exit the film query app, enter 3: ");
			userChoice = input.nextInt();

			if (userChoice != 1 && userChoice != 2 && userChoice != 3) {
				System.out.println("Invalid entry. Please try again!");
				System.out.println();
			}

			if (userChoice == 1) {
				System.out.println("Please enter a film id for your search: ");
				int filmID = input.nextInt();

				if (db.findFilmById(filmID) == null) {
					System.out.println("No film matching input id.");
					System.out.println();
				} else {
					Film film = db.findFilmById(filmID);
					System.out.println(film.getTitle() + " " + film.getReleaseYear() + " " + film.getRating() + " "
							+ film.getDescription() + " Language: " + film.getLanguage());
					List<Actor> actors = film.getActorsInFilm();
					for (Actor actor : actors) {
						System.out.println(actor);
					}
					System.out.println();
				}
			}
			if (userChoice == 2) {
				System.out.println("Please enter a keyword for your search: ");
				String keyword = input.next();

				List<Film> filmMatch = db.findFilmByKeyword(keyword);
				if (filmMatch.size() == 0) {
					System.out.println("Sorry. No films match your input.");
					System.out.println("You can try again with a different keyword.");
					System.out.println();
				} else {
					
					for (Film film : filmMatch) {
						System.out.println(film.getTitle() + " " + film.getReleaseYear() + " " + film.getRating() + " "
								+ film.getDescription() + " Language: " + film.getLanguage());
						List<Actor> actors = film.getActorsInFilm();
						for (Actor actor : actors) {
							System.out.println(actor);
						}
						System.out.println();
					}
				}
			}

		} while (userChoice != 3);

		if (userChoice == 3) {
			System.out.print("You've chosen to exit. ");
			System.out.println("Thank you for using the Film Query app!");
		} else {
			System.out.println("Please retry with a valid menu selection.");
		}

	}

}
