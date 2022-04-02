package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	private String user = "student";
	private String pass = "student";

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT f.id, f.title, f.description, f.release_year, f.language_id, "
					+ "f.rental_duration, f.rental_rate, f.length, f.replacement_cost, "
					+ "f.rating, f.special_features, a.id, a.first_name, a.last_name "
					+"FROM film_actor fa "
					+ "JOIN actor a ON a.id = fa.actor_id "
					+ "JOIN film f ON f.id = fa.film_id "
					+ "WHERE f.id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, filmId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				film = new Film();
				film.setID(rs.getInt("f.id"));
				film.setTitle(rs.getString("f.title"));
				film.setDescription(rs.getString("f.description"));
				film.setReleaseYear(rs.getInt("f.release_year"));
				film.setLanguageID(rs.getInt("f.language_id"));
				film.setRentalDuration(rs.getInt("f.rental_duration"));
				film.setRentalRate(rs.getInt("f.rental_rate"));
				film.setLength(rs.getInt("f.length"));
				film.setReplacementCost(rs.getDouble("f.replacement_cost"));
				film.setRating(rs.getString("f.rating"));
				film.setSpecialFeatures(rs.getString("f.special_features"));
				film.setActorsInFilm(findActorsByFilmId(filmId));
				
				}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}
	
	

	@Override
	public Actor findActorById(int actorId) {
		Actor actor;
		try {
			actor = new Actor();
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM film WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, actorId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				actor.setID(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT a.id, a.first_name, a.last_name, f.id " + "FROM film_actor fa "
					+ "JOIN actor a ON a.id = fa.actor_id " + "JOIN film f ON f.id = fa.film_id " + "WHERE f.id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, filmId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Actor actor = new Actor();
				actor.setID(rs.getInt("a.id"));
				actor.setFirstName(rs.getString("a.first_name"));
				actor.setLastName(rs.getString("a.last_name"));
				actors.add(actor);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Driver not found.");
			throw new RuntimeException("**Unable to load MySQL Driver class");
		}
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		Film film = null;
		List<Film> filmMatch = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT f.id, f.title, f.description, f.release_year, f.language_id, "
					+ "f.rental_duration, f.rental_rate, f.length, f.replacement_cost, "
					+ "f.rating, f.special_features "
					+"FROM film f "
//					+ "JOIN film f ON f.id = fa.film_id "
					+ "WHERE f.description LIKE ? OR f.title LIKE ?";
					
					
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				film = new Film();
				film.setID(rs.getInt("f.id"));
				film.setTitle(rs.getString("f.title"));
				film.setDescription(rs.getString("f.description"));
				film.setReleaseYear(rs.getInt("f.release_year"));
				film.setLanguageID(rs.getInt("f.language_id"));
				film.setRentalDuration(rs.getInt("f.rental_duration"));
				film.setRentalRate(rs.getInt("f.rental_rate"));
				film.setLength(rs.getInt("f.length"));
				film.setReplacementCost(rs.getDouble("f.replacement_cost"));
				film.setRating(rs.getString("f.rating"));
				film.setSpecialFeatures(rs.getString("f.special_features"));
//				film.setActorsInFilm(findActorsByFilmId(film.getID()));
				
				filmMatch.add(film);
				
				}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filmMatch;
	}
}
