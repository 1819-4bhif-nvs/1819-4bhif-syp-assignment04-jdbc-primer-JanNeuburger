package at.htl.musiccollection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.fail;

public class MusicCollectionTest {
    public static final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    public static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db";
    public static final String USER = "app";
    public static final String PASSWORD = "app";
    private static Connection conn;

    @BeforeClass
    public static void initJdbc(){
        try {
            Class.forName(DRIVER_STRING);
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Verbindung zur Datenbank nicht möglich\n" + e.getMessage() + "\n");
            System.exit(1);
        }

        try {
            //CREATE
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE song (" +
                    "ID INT CONSTRAINT song_pk PRIMARY KEY," +
                    "title VARCHAR(30) NOT NULL," +
                    "artist_id INT," +
                    "album_id INT)";
            stmt.execute(sql);
            System.out.println("Tabelle SONG erstellt!");
            sql = "CREATE TABLE artist (" +
                    "ID INT CONSTRAINT artist_pk PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL)";
            stmt.execute(sql);
            System.out.println("Tabelle ARTIST erstellt!");
            sql = "CREATE TABLE album (" +
                    "ID INT CONSTRAINT album_pk PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL)";
            stmt.execute(sql);
            System.out.println("Tabelle ALBUM erstellt!");

            //CONSTRAINTS
            sql = "ALTER TABLE song ADD CONSTRAINT song_artist_fk " +
                    "FOREIGN KEY(artist_id) REFERENCES artist(id) ON DELETE CASCADE";
            stmt.execute(sql);
            sql = "ALTER TABLE song ADD CONSTRAINT song_album_fk " +
                    "FOREIGN KEY(album_id) REFERENCES album(id) ON DELETE CASCADE";
            stmt.execute(sql);

            //INSERTS
            //ARTISTS
            conn.createStatement().execute("INSERT INTO artist (id,name) VALUES (1,'Gorillaz')");
            conn.createStatement().execute("INSERT INTO artist (id,name) VALUES (2,'Queen')");
            conn.createStatement().execute("INSERT INTO artist (id,name) VALUES (3,'Led Zeppelin')");
            conn.createStatement().execute("INSERT INTO artist (id,name) VALUES (4,'Equilibrium')");

            //ALBUMS
            conn.createStatement().execute("INSERT INTO album (id,name) VALUES (1,'The Now Now')");
            conn.createStatement().execute("INSERT INTO album (id,name) VALUES (2,'A Kind Of Magic')");
            conn.createStatement().execute("INSERT INTO album (id,name) VALUES (3,'Mothership')");
            conn.createStatement().execute("INSERT INTO album (id,name) VALUES (4,'Demon Days')");
            conn.createStatement().execute("INSERT INTO album (id,name) VALUES (5,'Erdentempel')");

            //SONGS
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (1,'Humility',1,1)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (2,'Fire Flies',1,1)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (3,'White Light',1,4)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (4,'Feel Good Inc',1,4)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (5,'A Kind Of Magic',2,2)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (6,'One Vision',2,2)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (7,'Immigrant Song',3,3)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (8,'Ramble On',3,3)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (9,'Good Times Bad Times',3,3)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (10,'Wirtshaus Gaudi',4,5)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (11,'Wellengang',4,5)");
            conn.createStatement().execute("INSERT INTO song (id,title,artist_id,album_id) VALUES (12,'Freiflug',4,5)");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @AfterClass
    public static void teardownJdbc(){
        try {
            conn.createStatement().execute("DROP TABLE song");
            System.out.println("Tabelle SONG gelöscht!");
            conn.createStatement().execute("DROP TABLE artist");
            System.out.println("Tabelle ARTIST gelöscht!");
            conn.createStatement().execute("DROP TABLE album");
            System.out.println("Tabelle ALBUM gelöscht!");
        } catch (SQLException e) {
            System.out.println("Eine Tabelle kann nicht gelöscht werden:\n"+e.getMessage());
        }

        try {
            if(conn!=null && !conn.isClosed()){
                conn.close();
                System.out.println("Goodbye!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ddl(){
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE song (" +
                    "ID INT CONSTRAINT song_pk PRIMARY KEY," +
                    "title VARCHAR(30) NOT NULL," +
                    "artist_id INT," +
                    "album_id INT)";
            stmt.execute(sql);
            sql = "CREATE TABLE artist (" +
                    "ID INT CONSTRAINT artist_pk PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL)";
            stmt.execute(sql);
            sql = "CREATE TABLE album (" +
                    "ID INT CONSTRAINT album_pk PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL)";
            stmt.execute(sql);
            sql = "ALTER TABLE song ADD CONSTRAINT song_artist_fk " +
                    "FOREIGN KEY(artist_id) REFERENCES artist(id)";
            stmt.execute(sql);
            sql = "ALTER TABLE song ADD CONSTRAINT song_album_fk " +
                    "FOREIGN KEY(album_id) REFERENCES album(id)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
