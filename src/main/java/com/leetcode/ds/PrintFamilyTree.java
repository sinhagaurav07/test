package com.leetcode.ds;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PrintFamilyTree {

    public Map<String, List<Relations>> familyTree = new HashMap<>();

    public static void main(String[] args) {
        PrintFamilyTree obj = new PrintFamilyTree();
        obj.addToFamilyTree("MPSinha","Anupama");obj.addToFamilyTree("MPSinha","Shweta");
        obj.addToFamilyTree("MPSinha","Gaurav");obj.addToFamilyTree("MPSinha","Rishu");
        obj.addToFamilyTree("Anupama","Sejal");obj.addToFamilyTree("Anupama", "Aarav");
        obj.addToFamilyTree("Shweta","Pari");obj.addToFamilyTree("Gaurav","Atharv");
        obj.addToFamilyTree("Gaurav","Sreeja");obj.addToFamilyTree("Rishu","Reyansh");

        obj.printFamily("Gaurav");
    }

    public void addToFamilyTree(String parent, String child){
        Relations relation = new Relations(parent, child);
        List<Relations> children = null;
        if(familyTree.containsKey(parent)){
            children = familyTree.get(parent);

        }else{
            children = new ArrayList<>();
            familyTree.put(parent, children);
        }
        children.add(relation);
    }

    public void printFamily(String parent){

        if(!familyTree.containsKey(parent)){
            System.out.println("No Family Exists");
        }else{
            Stack<Relations> familyStack = new Stack<>();
            System.out.println(parent);
            List<Relations> children= familyTree.get(parent);
            pushToStack(familyStack, children);

            while(!familyStack.isEmpty()){
                Relations rel = familyStack.pop();
                System.out.println(rel.child);
                List<Relations> newChildren= familyTree.get(rel.child);
                if(CollectionUtils.isNotEmpty(newChildren))
                    pushToStack(familyStack, newChildren);
            }
        }
    }

    private void pushToStack(Stack<Relations> familyStack, List<Relations> children) {
        for(Relations r : children){
            familyStack.push(r);
        }
    }

}

class Relations{
    String parent;
    String child;

    public Relations(){

    }
    public Relations(String parent, String child){
        this.parent=parent;
        this.child=child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent(){
        return parent;
    }
    public String getChild(){
        return child;
    }
}