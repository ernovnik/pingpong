import akka.actor.AbstractActor;

public class Player extends AbstractActor {

    private final String name;

    private int hitsBeforeStop;

    private int hitCounter;

    public Player(String name, int hitsBeforeStop){
        this.name = name;
        this.hitsBeforeStop = hitsBeforeStop;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, m -> {
                    try {
                        Thread.sleep(200);
                        hitCounter++;
                        final int counter = m.getCounter();
                        if(hitCounter <= hitsBeforeStop || hitsBeforeStop < 0) {
                            System.out.println(String.format("%s got message number %d", name, hitCounter));
                            if(hitsBeforeStop > 0 && hitCounter >= hitsBeforeStop) {
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
