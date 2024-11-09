package service;

import domain.Request;
import persistance.PersistanceException;
import persistance.RequestDao;
import ui.FavoriteRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestFavoritingService {
    private RequestDao requestDao;

    public RequestFavoritingService(RequestDao requestDao) {

        this.requestDao = requestDao;
    }

    public void markAsFavorite(Request request) throws FavoriteRequestException {
        request.setAsFavorite();
        try {
            requestDao.save(request);
        } catch (PersistanceException e) {
            throw new FavoriteRequestException(e);
        }
    }

    public void removeFromFavorites(Request request) {

    }

    public List<Request> getFavorites() throws FavoriteRequestException {
        List<Request> favorites = new ArrayList<>();
        for(int i = 0; i<5; i++){
            Request req =new Request(i, "Request #"+ i+1);
            favorites.add(req);
        }

        return favorites;

//        try {
//            return requestDao.findFavorites();
//        } catch (PersistanceException e) {
//            throw new FavoriteRequestException(e);
//        }
    }
}
