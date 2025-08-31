package cz.ondra.gamehub.exception;

public class AuditorNotFoundException extends GamehubException {

    public AuditorNotFoundException() {
        super("Auditor not found");
    }
}
