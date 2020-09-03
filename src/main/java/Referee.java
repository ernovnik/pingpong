import akka.actor.AbstractActor;
import akka.actor.ActorRef;

public class Referee extends AbstractActor {

    private ActorRef player1;
    private ActorRef player2;

    public Referee(ActorRef player1, ActorRef player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(Start.class, m -> {
                    try {
                        player1.tell(new Message("test", 1), player2);
                    } catch (Exception ex) {
                        getSender().tell(
                                new akka.actor.Status.Failure(ex), getSelf());
                        throw ex;
                    }
                }).build();
    }

}
