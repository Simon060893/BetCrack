<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.core.controller.ActionsBet" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.core.modelEvents.ASportBetEvent" %>
<%@ page import="com.core.modelEvents.FootballEvent" %>
<%--
  Created by IntelliJ IDEA.
  User: Катя
  Date: 19.02.2015
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <title>Create Event</title>
<script>
    window.onload = function () {
        if ('<%=session.getAttribute("canCreate")%>' !== 'null') {
            document.getElementById("chooseEvents").style.visibility = 'hidden';
            document.getElementById("showTableCrt").style.visibility = 'visible';
//                document.getElementById("crtEv").style.visibility = 'visible';
        }else{
            document.getElementById("chooseEvents").style.visibility = 'visible';
            document.getElementById("showTableCrt").style.visibility = 'hidden';
        }
        var integ=0;
        var integ1=1;
        $(document).ready(function () {
            var input = document.creaEv.getElementsByTagName("input");
            for (var i = 0; i < input.length; i++) {
                if (input[i].type == 'text') {
                    input[i].setAttribute('size', 4);
                    input[i].setAttribute('class', 'placeholder');
                    input[i].setAttribute('value', '0.0');
                }
            }
        });}
    function interval(par,t,timeMytch)
    {
        var sec;
        if(timeMytch == 0 || timeMytch =='0'){
             sec=0;
        }else{
            sec=70;
        }

        var intervalID2;
        var countUp=function(){
            document.getElementById(par).innerHTML=Math.floor(++sec / 60) + ': ' + sec % 60;
            if(intervalID2 == null){
                intervalID2=setInterval(countUp,1000);
            }
        }
        countUp();
        $(document).on('click', 'input', function () {
            if(this.id == t){
                clearInterval(intervalID2);
            }
        });
    }
</script>
    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="http://scriptjava.net/source/scriptjava/scriptjava.js"></script>
    <script src='js/stream-min.js'></script>
    <script type="text/javascript" src="js/autoset.js"> </script>
    <link rel="stylesheet" href="./css/style.css" type="text/css">
</head>
<style>
    .drop:hover{
        background-color: red;
    }
</style>
<body>

<div id="chooseEvents">
    <form method="post" action="reg">
        <input type="submit" value="back" name="back">
    </form>
    <table id="myTblChsEv">
        <th>match</th>
        <th>time</th>
        <th>score</th>
        <th colspan="4">Actions</th>
        <c:forEach items="${list}" var="mtchList">
            <tr>
                <td><a href=<c:url value="/createEvent?matchId=${mtchList.value.getIdRow()}" />/>
                        ${mtchList.value.toString()}: ${mtchList.value.getNameF()} - ${mtchList.value.getNameS()} </td>
                <td id="time${mtchList.value.getIdRow()}">${mtchList.value.getTimeMatch()}</td>
                <td>${mtchList.value.getResultMatch()}</td>
                <td>
                    <input type="button" value="DROP"  class ="drop"  onclick="deleteRowFromList(this,'myTblChsEv')">
                </td>
                <td>
                    <input type="button" value="Start" style="background-color: green"onclick="interval('time'+
                    ${mtchList.value.getIdRow()},'stopTimer'+${mtchList.value.getIdRow()},${mtchList.value.getTimeMatch()})">
                </td>
                <td>
                    <input type="button" value="STOP"  style="background-color: red" onclick="stopEvents(this)">
                </td>
                <td>
                    <input type="button" value="time out"  id="stopTimer${mtchList.value.getIdRow()}" >
                </td>
            </tr>
        </c:forEach>
    </table>
    <form method="POST" name="addNE" action="/createEvent">
        <input type="submit" value="addNEvnt" name="addNEvnt">
    </form>

</div>

<div id="showTableCrt">
    <div id="byDef">
        <div class="slideThree">
            <input type="checkbox" value="None" id="slideThree" name="check"/>
            <label for="slideThree"></label>
        </div>
        <p>Create maych by default</p>
    </div>
    <div id="formCreateEv">
        <div id="crtEv">
            <form name="crtEv" method="post" action="/createEvent">
            <input type="submit" value="createNewEv"  onclick="sendData();">
            <input type="hidden" name="createNewEvent" >
            </form>
        </div>
        <form id="name" name="creaEv">
            <input id="FT" type="text"  name="FullWin:FT" >team/person(1)
            <input name ="Result:F" type="text" >result Match team/person(1)<br>
            <input id="ST" type="text" name="FullWin:ST">team/person(2)
            <input name ="Result:S" type="text">result Match team/person(2)<br>
            <select id="mySelectId" name="mySelect" onchange="loadSubdata(this)">
                <option value="0"> Football</option>
                <option value="1"> Basketball</option>
                <option value="2"> Hockey</option>
            </select><br>

            <div id="formCreateEvebt" >
                <div class="button" id="needBet">

                    <div class="hide">Main bets</div>
                    <div   class="inner"  >

                        <input type="text" name="FullWin:FF" >FullWinFirst<br>
                        <input type="text" name="FullWin:SF">FullWinSecond<br>
                        <input type="text" name="FullWin:NF">FullDraw<br><br>
                        <input class="extrabutton" type="button" id="createExtraGoal"
                               onclick="return addRow('over','under',null,'FullTotal_Goal');" value="add new total">
                        <table id="FullTotal_Goal" style="border: 1px solid black">
                            <tbody>
                            <th>total</th>
                            <th>over</th>
                            <th>under</th>
                            <th></th>
                            <tr>
                                <td>0.5</td>
                                <td><input type="text" name ="FullTotal_Goal.over:0.5"></td>
                                <td><input type="text" name ="FullTotal_Goal.under:0.5" ></td>
                                <td><input type="button" value="Delete" onclick="deleteRow(this,'FullTotal_Goal')">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                            <br>

                        <div class="button">
                            <div class="hide">show half time</div>
                            <div class="inner"  >
                                <input type="text" name ="HalfWin:FH">winFrstHalfTm<br>
                                <input type="text" name ="HalfWin:SH">winScndHalfTm<br>
                                <input type="text" name ="HalfWin:NH">drawHalfTm<br><br>
                                <input class="extrabutton" type="button"
                                       onclick="return addRow('over','under',null,'HalfTotal_Goal');"
                                       value="add new total">
                                <table id="HalfTotal_Goal" style="border: 1px solid black">
                                    <tbody>
                                    <th>total</th>
                                    <th>overTime</th>
                                    <th>underTime</th>
                                    <th></th>
                                    <tr>
                                        <td>0.5</td>
                                        <td><input type="text" name="HalfTotal_Goal.over:0.5"></td>
                                        <td><input type="text" name="HalfTotal_Goal.under:0.5" ></td>
                                        <td><input type="button" value="Delete"
                                                   onclick="deleteRow(this,'HalfTotal_Goal')">
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <br>
                        <input class="extrabutton" type="button" id="createExtraButton"
                               onclick="return addRow('gF','gS','gN','FullNext_Goal');"
                               value="add new goal">
                        <table id="FullNext_Goal" style="border: 1px solid black">
                            <tbody>
                            <th>next goal</th>
                            <th><i><span id="nmF"></span></i></th>
                            <th>no next goal</th>
                            <th><i><span id="nmS"></span></i></th>
                            <th></th>
                            <tr>
                                <td><p class="p1">1goal</p></td>
                                <td><input type="text" name="FullNext_Goal.gF:1"></td>
                                <td><input type="text" name="FullNext_Goal.gN:1"></td>
                                <td><input type="text" name="FullNext_Goal.gS:1"></td>
                                <td><input type="button" value="Delete" onclick="deleteRow(this,'FullNext_Goal')">
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="button" id="corner">
                    <div class="hide">show corners</div>
                    <div name="bal1" class="inner">
                        <input class="extrabutton" type="button" id="createExtraCorner"
                               onclick="return addRow('over','under',null,'FullTotal_Corner');"
                               value="add new corner">
                        <table id="FullTotal_Corner" style="border: 1px solid red">
                            <tbody>
                            <th>total</th>
                            <th>overCornerTotal</th>
                            <th>underCornerTotal</th>
                            <th></th>
                            <tr>
                                <td>0.5</td>
                                <td><input type="text" name="FullTotal_Corner.over:0.5"></td>
                                <td><input type="text" name="FullTotal_Corner.under:0.5"></td>
                                <td><input type="button" value="Delete"
                                           onclick="deleteRow(this,'FullTotal_Corner');">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <input class="extrabutton" type="button"
                               onclick="return addRow('cF','cS','cN','FullNext_Corner');"
                               value="who get next cornet">
                        <table id="FullNext_Corner">
                            <tbody>
                            <th>next corner</th>
                            <th><i><span id="nmF1"></span></i></th>
                            <th>no next corner</th>
                            <th><i><span id="nmS1"></span></i></th>
                            <th></th>
                            <tr>
                                <td><p class="p1">1Corner</p></td>
                                <td><input type="text" name="FullNext_Corner.cF:1"></td>
                                <td><input type="text" name="FullNext_Corner.cN:1"></td>
                                <td><input type="text" name="FullNext_Corner.cS:1"></td>
                                <td><input type="button" value="Delete" onclick="deleteRow(this,'FullNext_Corner');">
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="button" id="foulsFootball">
                    <div class="hide">show football fouls</div>
                    <div name="bal2"class="inner"> football fouls</div>
                </div>
                <div class="button" id="hockey">
                    <div class="hide">show hockey periods</div>
                    <div name="bal3" class="inner">
                        <input class="extrabutton" type="button"
                               onclick="return addRow('over','under',null,'FullTotal_Hckey');"
                               value="add new goal">
                        <table id="FullTotal_Hckey" style="border: 1px solid black">
                            <tbody>
                            <th>total</th>
                            <th>overPrd</th>
                            <th>underPrd</th>
                            <th></th>
                            <tr>
                                <td>0.5</td>
                                <td><input type="text" name="FullTotal_Hckey.over:0.5"></td>
                                <td><input type="text" name="FullTotal_Hckey.under:0.5"></td>
                                <td><input type="button" value="Delete"
                                           onclick="deleteRow(this,'FullTotal_Hckey')">
                            </tr>
                            </tbody>
                        </table>
                        <input class="extrabutton" type="button"
                               onclick="return addRow('hF','hS','hN','FullNext_Hckey');"
                               value="add new goal">
                        <table id="FullNext_Hckey" style="border: 1px solid black">
                            <tbody>
                            <th>periods</th>
                            <th><i><span id="nmF2"></span></i></th>
                            <th>no winner</th>
                            <th><i><span id="nmS2"></span></i></th>
                            <th></th>
                            <tr>
                                <td><p class="p1">1 period</p></td>
                                <td><input type="text" name="FullNext_Hckey.hF:1"></td>
                                <td><input type="text" name="FullNext_Hckey.hN:1" ></td>
                                <td><input type="text" name="FullNext_Hckey.hS:1" ></td>
                                <td><input type="button" value="Delete"
                                           onclick="deleteRow(this,'FullNext_Hckey')">
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </form>
        <div id="formCreateEvebtByDefaulr"></div>
    </div>
</div>


<script src="js/verific_createEv.js"></script>
</body>
</html>
