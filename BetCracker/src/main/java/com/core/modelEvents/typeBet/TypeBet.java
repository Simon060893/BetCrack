package com.core.modelEvents.typeBet;

/**
 * Created by Катя on 28.02.2015.
 */
public class TypeBet {
    private String countBet;
    private String type1;
    private String type2;
    private String type3;

    public TypeBet(String []str){
        setData(str);
        countBet = str[0].substring(str[0].indexOf(":")+1, str[0].indexOf("="));
    }

    private void setData(String[] str) {
       for(String s : str){
           checkType(s);
       }
    }

    private void checkType(String s) {
        if(s.substring(4,s.indexOf("_")).equals("Total")){
            if(s.substring(s.indexOf(".")+1,s.indexOf(":")).equals("over")){
                type1=s.split("=")[1];
            }else{
                type2=s.split("=")[1];
            }
        }else{
            if(s.substring(s.indexOf(".")+2,s.indexOf(":")).equals("F")){
                type1=s.split("=")[1];
            }else if(s.substring(s.indexOf(".")+2,s.indexOf(":")).equals("S")){
                type2=s.split("=")[1];
            }else{
                type3=s.split("=")[1];
            }
        }
    }

    public String getCountBet() {
        return countBet;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getType3() {
        return type3;
    }
}
