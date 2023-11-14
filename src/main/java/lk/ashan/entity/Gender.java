package lk.ashan.entity;

public class Gender{

   private int id;
   private String name;

    public Gender(){ }

    public void setId(int id){
        this.id= id;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public  String toString(){return  name;}


}