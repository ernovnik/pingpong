import akka.actor.AbstractActor;

public class Player extends AbstractActor {

    private final String name;

    private final int messsagesBeforeStop;

    private int messageCounter;

    public Player(String name, int messsagesBeforeStop){
        this.name = name;
        this.messsagesBeforeStop = messsagesBeforeStop;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, m -> {
                    try {
                        Thread.sleep(200);
                        messageCounter++;
                        final int counter = m.getCounter();
                        if(messageCounter <= messsagesBeforeStop || messsagesBeforeStop < 0) {
                            System.out.println(String.format("%s got message number %d", name, messageCounter));
                            if(messsagesBeforeStop > 0 && messageCounter >= messsagesBeforeStop) {
                                System.out.println(String.format("%s took the last hit", name));
                                getContext().getSystem().terminate();
                            }
                            else
                                getSender().tell(new Message(m.getText(), counter + 1), getSelf());
                        }
                    } catch (Exception ex) {
                        getSender().tell(
                                new akka.actor.Status.Failure(ex), getSelf());
                        throw ex;
                    }
                }).build();
    }
}
