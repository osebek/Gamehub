package cz.ondra.gamehub.exception;

public class PlayerInputValidationException extends GamehubException {

    public PlayerInputValidationException(String reason) {
        super("Player input is not valid: " + reason);
    }
}
