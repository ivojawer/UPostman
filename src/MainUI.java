import domain.Header;
import domain.Parameter;
import persistance.KeyValueDao;
import persistance.KeyValueJDBCImplDao;
import persistance.RequestDao;
import persistance.RequestDaoJDBCImpl;
import service.RequestFavoritingService;
import service.RequestHistoryException;
import service.RequestHistoryService;
import service.SendRequestService;
import ui.PanelManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainUI {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        KeyValueDao<Header> headerDao = new KeyValueJDBCImplDao<>(connection,"value", "key", "Header");
        KeyValueDao<Parameter> parameterDao = new KeyValueJDBCImplDao<>(connection,"value", "name", "Parameter");
        RequestDao requestDao = new RequestDaoJDBCImpl(connection,headerDao, parameterDao);
        RequestFavoritingService favorittingService = new RequestFavoritingService(requestDao);
        RequestHistoryService historyService = new RequestHistoryService(requestDao);
        PanelManager manager = new PanelManager(
                favorittingService,
                new SendRequestService(historyService)
        );
        manager.initializeFrame();
    }
}
