package service;

import domain.Request;
import persistance.PersistanceException;
import persistance.RequestDao;

import java.util.List;

public class RequestFavoritingService {
    private RequestDao requestDao;

    public RequestFavoritingService(RequestDao requestDao) {

        this.requestDao = requestDao;
    }

    public void markAsFavorite(Request request) {
        request.setAsFavorite();
        requestDao.save(request);
    }

    public void removeFromFavorites(Request request) {

    }

    public List<Request> getFavorites() throws FavoriteRequestException {
        try {
            return requestDao.findFavorites();
        } catch (PersistanceException e) {
            throw new FavoriteRequestException(e);
        }
    }
}
