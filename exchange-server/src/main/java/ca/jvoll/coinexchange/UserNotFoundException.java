package ca.jvoll.coinexchange;

class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}