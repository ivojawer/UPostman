import domain.Header;
import domain.Parameter;
import domain.Request;
import persistance.KeyValueDao;
import persistance.KeyValueJDBCImplDao;
import persistance.RequestDao;
import persistance.RequestDaoJDBCImpl;
import persistance.mappers.HeaderMapper;
import persistance.mappers.ParameterMapper;
import service.FavoriteRequestException;
import service.RequestFavoritingService;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainServer {
    public static void main(String[] args) throws SQLException, FavoriteRequestException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        KeyValueDao<Header> headerDao = new KeyValueJDBCImplDao<>(connection,"key", "value", "Header", new HeaderMapper());
        KeyValueDao<Parameter> parameterDao = new KeyValueJDBCImplDao<>(connection,"name", "value", "Parameter", new ParameterMapper());
        RequestDao requestDao = new RequestDaoJDBCImpl(connection,headerDao, parameterDao);
        RequestFavoritingService favorittingService = new RequestFavoritingService(requestDao);

        favorittingService.getFavorites().stream().map(Request::getPath).forEach(System.out::println);
    }
}
