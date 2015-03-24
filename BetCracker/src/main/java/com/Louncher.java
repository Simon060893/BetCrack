package com;
import com.registration.server.UserActions;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 * Created by Катя on 29.01.2015.
 */
public class Louncher {
    static Logger logger = Logger.getLogger(Louncher.class);
    public static void main(String[] args) throws Exception {
//        new SaveClassToDb().savePackegeWithClasses(new File("src\\main\\java\\com\\registration\\models"));
//        Usere user = new Usere();
//        user.setLogin("fgdhr1ghF4gfGFd#$");
//        user.setPassword("fgdhF435dfGFd#$");
//        user.setAdmins("false");
//        user.setDatReg(String.valueOf(new Timestamp(10)));
//        user.setEmail("fvfyf@3dsf4.fdg4.com");
//        user.setFio("jklj");
//        user.setIpReg("1254");
//        user.setIdExp(2);
//        user.setPhonenumber("1271534232323");
//        new UserActions().register(user);
//        for(String s:  ActionsSelectFromEAV.getDataForObj(22)){
//            System.out.println(s);
//        }
        UserActions userActions= new UserActions();
        userActions.connect("simon72", "1111");
        System.out.println(userActions.isConect()+"//////////--------------------------------------------");
        System.out.println(userActions.getUser().getFio());
        System.out.println(userActions.getUser().getPassword());
        System.out.println(userActions.getUser().getPhonenumber());
//        System.out.println( ActionsSelectFromEAV.getDataFromParametersBySuperObj(new AttrvaluesPK(
//                ActionsSelectFromEAV.getDataFromAttrtypeByObjtype(ActionsSelectFromEAV.getDataFromObjTypeByName("Usere").get(0)).get(0),
//                ActionsSelectFromEAV.getDataFromSuperobjByName("User1505961970").get(0))).get(0).getValue());

        BasicConfigurator.configure();
        logger.info("Entering application.");
    }
}
