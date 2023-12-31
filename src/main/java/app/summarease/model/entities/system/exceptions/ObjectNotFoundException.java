package app.summarease.model.entities.system.exceptions;

public class ObjectNotFoundException extends RuntimeException{

    @SuppressWarnings("unused")
    public ObjectNotFoundException(String objectName, String id) {
        super("Could not find " + objectName + " with Id: " + id);
    }

    @SuppressWarnings("unused")
    public ObjectNotFoundException(String objectName, Integer id) {
        super("Could not find " + objectName + " with Id: " + id);
    }

    public ObjectNotFoundException(String objectName, Long id) {
        super("Could not find " + objectName + " with Id: " + id);
    }

}