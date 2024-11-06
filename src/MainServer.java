import persistance.RequestDao;
import persistance.RequestDaoJDBCImpl;
import service.FavoriteRequestException;
import service.RequestFavoritingService;
import ui.Frame;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainServer {
    public static void main(String[] args) throws URISyntaxException, SQLException, FavoriteRequestException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        RequestDao requestDao = new RequestDaoJDBCImpl(connection);
        RequestFavoritingService favorittingService = new RequestFavoritingService(requestDao);

        favorittingService.getFavorites().stream().forEach(System.out::println);

    }
}
