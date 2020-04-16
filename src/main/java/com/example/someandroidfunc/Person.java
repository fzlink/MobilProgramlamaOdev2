package com.example.someandroidfunc;

import java.util.ArrayList;

public class Person {
    private String userName;
    private String password;
    private int imageID;

    public Person(String userName,String password){
        this.userName = userName;
        this.password = password;
    }
    public Person(){

    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setImageID(int imageID){
        this.imageID = imageID;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public int getImageID(){
        return imageID;
    }

    public static ArrayList<Person> getData() {
        ArrayList<Person> personList = new ArrayList<Person>();
        int personImages[] = {R.drawable.boy, R.drawable.girl, R.drawable.defaultperson,R.drawable.daredevil,R.drawable.batman};
        String[] personUserNames = {"Faz", "Zehra", "SpiderMan","Daredevil","Batman"};

        for (int i = 0; i < personUserNames.length; i++) {
            Person temp = new Person();
            temp.setImageID(personImages[i]);
            temp.setUserName(personUserNames[i]);
            temp.setPassword("1");

            personList.add(temp);

        }
        return personList;
    }

}
