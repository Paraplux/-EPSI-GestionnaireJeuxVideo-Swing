package com.marcbouchez.services;

import com.marcbouchez.entities.Categorie;
import com.marcbouchez.entities.Jeu;
import com.marcbouchez.entities.Plateforme;
import com.marcbouchez.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireJeuxService {

    private static SessionFactory sessionFactory;
    private static Transaction tx;

    private static List<Jeu> tousLesJeux = new ArrayList<>();
    private static List<Plateforme> toutesLesPlateformes = new ArrayList<>();
    private static List<Categorie> toutesLesCategories = new ArrayList<>();

    static {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static void loadData() {
        for (int i = 1; i <= 30; i++) {
            String nomCategorie = "Catégorie " + i;
            Categorie categorie = new Categorie(nomCategorie);
            categorie.setId(i);
            createCategorie(categorie);

            String nomPlateforme = "Plateforme " + i;
            int anneePlateforme = 1990 + i;
            Plateforme plateforme = new Plateforme(nomPlateforme, LocalDate.of(anneePlateforme, 1, 1));
            plateforme.setId(i);
            createPlateforme(plateforme);

            String nomJeu = "Jeu " + i;
            int anneeJeu = 1990 + i;
            Jeu jeu = new Jeu(nomJeu, LocalDate.of(anneeJeu, 1, 1), plateforme, 69.99F);
            jeu.setId(i);
            createJeu(jeu);
        }
    }

    public static void createJeu(Jeu j) {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(j);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public static List<Jeu> getTousLesJeux() {
        tousLesJeux.clear();
        Query<Jeu> query = HibernateUtil.getSessionFactory().openSession().createQuery("FROM Jeu", Jeu.class);
        List<Jeu> resultats = query.list();
        for (Jeu j : resultats) {
            tousLesJeux.add(j);
        }
        return tousLesJeux;
    }

    public static List<Plateforme> getToutesLesPlateformes() {
        toutesLesPlateformes.clear();
        Query<Plateforme> query = HibernateUtil.getSessionFactory().openSession().createQuery("FROM Plateforme", Plateforme.class);
        List<Plateforme> resultats = query.list();
        for (Plateforme p : resultats) {
            toutesLesPlateformes.add(p);
        }
        return toutesLesPlateformes;
    }

    public static void createPlateforme(Plateforme p) {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(p);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public static List<Categorie> getToutesLesCategories() {
        toutesLesCategories.clear();
        Query<Categorie> query = HibernateUtil.getSessionFactory().openSession().createQuery("FROM Categorie", Categorie.class);
        List<Categorie> resultats = query.list();
        for (Categorie c : resultats) {
            toutesLesCategories.add(c);
        }
        return toutesLesCategories;
    }

    public static void createCategorie(Categorie c) {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
}
