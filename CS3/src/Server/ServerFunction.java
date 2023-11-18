package Server;

import pojo.People;

import java.util.List;
import java.util.Scanner;

/**
 * author:          ndg
 * date:            2023/11/3
 */
public class ServerFunction {
    ServerData serverData = new ServerData();
    Scanner sc = new Scanner(System.in);
    List<People> peopleList ;

    public ServerFunction() {
        peopleList = serverData.readPeopleListFromFile();
    }

    public void conduct(int num){
        if(num == 1){
            show();
        } else if (num == 2) {
            add();
        } else if (num == 4) {
            delete();
        } else if (num == 3) {
            revise();
        }
    }

    public void add(){
        System.out.println("请输入联系人姓名：");
        String name = sc.nextLine();
        System.out.println("请输入联系人地址：");
        String address = sc.nextLine();
        System.out.println("请输入联系人电话：");
        String phone = sc.nextLine();
        People people = new People(name,address,phone);
        peopleList.add(people);
        try{
            serverData.writePeopleListToFile(peopleList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(){
        System.out.println("请输入你要删除的姓名：");
        String name = sc.nextLine();
        People people = null;
        for (People value : peopleList) {
            if (name.equals(value.getName())) {
                people = value;
            }
        }
        if (people == null){
            System.out.println("查无此人!!!");
        }
        peopleList.remove(people);
        serverData.writePeopleListToFile(peopleList);
    }

    public void show(){
        for (People people : peopleList) {
            System.out.println(people);
        }
    }

    public void revise(){
        System.out.println("请输入你要修改的姓名：");
        String name = sc.nextLine();
        People people1 = null;
        for (People value : peopleList) {
            if (name.equals(value.getName())) {
                people1 = value;
            }
        }
        if (people1 == null){
            System.out.println("查无此人!!!");
        }
        peopleList.remove(people1);
        serverData.writePeopleListToFile(peopleList);
        System.out.println("请输入修改后的信息：");
        add();
    }
}
