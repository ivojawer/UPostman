package service;

import domain.Request;
import persistance.PersistanceException;
import persistance.RequestDao;

import java.util.List;

public class RequestFavoritingService {
    private final RequestDao requestDao;

    public RequestFavoritingService(RequestDao requestDao) {

        this.requestDao = requestDao;
    }

    public void addToFavorites(Request request) throws FavoriteRequestException {
        Request favorite = request.copy();
        favorite.setAsFavorite();
        try {
            requestDao.save(favorite);
        } catch (PersistanceException e) {
            throw new FavoriteRequestException("No se pudo guardar la request" + favorite.getURI() + " en favoritos");
        }
    }

    public List<Request> getFavorites() throws FavoriteRequestException {
        try {
            return requestDao.findFavorites();
        } catch (PersistanceException e) {
            throw new FavoriteRequestException("No se pudo obtener las requests favoritas");
        }
    }
}
