import persistance.RequestDao;
import persistance.RequestDaoJDBCImpl;
import service.RequestFavoritingService;
import service.SendRequestService;
import ui.PanelManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainUI {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        RequestDao requestDao = new RequestDaoJDBCImpl(connection);
        RequestFavoritingService favorittingService = new RequestFavoritingService(requestDao);

        PanelManager manager = new PanelManager(
                favorittingService,
                new SendRequestService()
        );
        manager.initializeFrame();
    }
}
