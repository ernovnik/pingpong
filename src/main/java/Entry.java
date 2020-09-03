import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Entry {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("ping-pong-system");
        ActorRef player1
                = system.actorOf(Props.create(Player.class, "Player1", 15), "Player1");
        ActorRef player2
                = system.actorOf(Props.create(Player.class,"Player2", -1), "Player2");
        ActorRef referee = system.actorOf(Props.create(Referee.class,player1, player2), "Referee");
        referee.tell(new Start(), ActorRef.noSender());
    }

}
