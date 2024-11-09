package service;

import domain.Request;
import persistance.PersistanceException;
import persistance.RequestDao;

import java.util.ArrayList;
import java.util.List;

public class RequestHistoryService {
    private RequestDao requestDao;

    public RequestHistoryService(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public List<Request> getHistory(){
        //ToDo
        return new ArrayList<>();
    }

    public void registerHistory(Request request) throws RequestHistoryException {
        try {
            requestDao.save(request);
        } catch (PersistanceException e) {
            throw new RequestHistoryException("Failed to save request");
        }
    }
}
