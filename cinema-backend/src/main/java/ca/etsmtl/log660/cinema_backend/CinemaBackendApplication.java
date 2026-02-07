package ca.etsmtl.log660.cinema_backend;

import ca.etsmtl.log660.cinema_backend.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaBackendApplication {

	public static void main(String[] args) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			System.out.println("✅ Connexion BD réussie!");
			session.close();
		} catch (Exception e) {
			System.err.println("❌ Erreur BD: " + e.getMessage());
			e.printStackTrace();
		}

		SpringApplication.run(CinemaBackendApplication.class, args);
	}
}