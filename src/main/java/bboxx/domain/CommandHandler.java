package bboxx.domain;

public interface CommandHandler<Command, Result> {
    Result handle(Command command);
}
