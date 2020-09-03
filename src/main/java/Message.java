public class Message {

    private String text;

    private int counter;

    public Message(String text, int counter){
        this.text = text;
        this.counter = counter;
    }

    public String getText(){
        return text;
    }

    public int getCounter() {
        return counter;
    }
}
