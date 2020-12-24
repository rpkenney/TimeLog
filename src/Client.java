public class Client {

    private String name;

    public Client(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof Client) {
            Client other = (Client)o;
            if(name.equals(other.name)) {   
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}