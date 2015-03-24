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

    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="http://scriptjava.net/source/scriptjava/scriptjava.js"></script>
    <script src='js/stream-min.js'></script>
    <script src="js/set_val_updat.js">
    </script>
    <script>
        window.onload = function () {
            if ('<%=request.getSession().getAttribute("isAdmin")%>' !== 'null') {
                document.getElementById("showTableCrt").style.visibility = 'visible';
                document.getElementById('nmF').innerHTML = '<%=session.getAttribute("FullWin:FT")%>';
                document.getElementById('nmF1').innerHTML = '<%=session.getAttribute("FullWin:FT")%>';
                document.getElementById('nmF2').innerHTML = '<%=session.getAttribute("FullWin:FT")%>';
                document.getElementById('nmS').innerHTML = '<%=session.getAttribute("FullWin:ST")%>';
                document.getElementById('nmS1').innerHTML = '<%=session.getAttribute("FullWin:ST")%>';
                document.getElementById('nmS2').innerHTML = '<%=session.getAttribute("FullWin:ST")%>';
                hideTypeMatch('<%=request.getSession().getAttribute("typeMatch")%>');
            } else {
                document.getElementById("showTableCrt").style.visibility = 'hidden';
                window.location.href = '/main';
            }
        }


    </script>
    <link rel="stylesheet" href="./css/style.css" type="text/css">
</head>
<body>

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
                <input type="submit" value="updateEv" onclick="sendData('<%=request.getSession().getAttribute("typeMatch")%>');">
                <input type="hidden" name="updateEvent">
            </form>
        </div>
        <form id="name" name="creaEv1">
            <input id="FT" type="text" name="FullWin:FT" value="<%=session.getAttribute("FullWin:FT")%>">team/person(1)
            <input name="Result:F" type="text" value="<%=session.getAttribute("Result:F")%>">result Match team/person(1)<br>
            <input id="ST" type="text" name="FullWin:ST" value="<%=session.getAttribute("FullWin:ST")%>">team/person(2)
            <input name="Result:S" type="text" value="<%=session.getAttribute("Result:S")%>">result Match team/person(2)<br>
            <span id="typeMatch">suka</span>
            <div id="formCreateEvebt">
                <div class="button" id="needBet">

                    <div class="hide">Main bets</div>
                    <div class="inner">

                        <input type="text" name="FullWin:FF" value="<%=session.getAttribute("FullWin:FF")%>">FullWinFirst<br>
                        <input type="text" name="FullWin:SF" value="<%=session.getAttribute("FullWin:SF")%>">FullWinSecond<br>
                        <input type="text" name="FullWin:NF"
                               value="<%=session.getAttribute("FullWin:NF")%>">FullDraw<br><br>
                        <input class="extrabutton" type="button" id="createExtraGoal"
                               onclick="return addRow('over','under',null,'FullTotal_Goal');" value="add new total">
                        <table id="FullTotal_Goal" style="border: 1px solid black">
                            <tbody>
                            <th>total</th>
                            <th>over</th>
                            <th>under</th>
                            <th></th>
                            <c:set var="i"/>
                            <c:forEach items="${FullTotal_Goal}" var="list1">
                                <tr>
                                    <td>${list1.getCountBet()}</td>
                                    <td><input type="text" name="FullTotal_Goal.over:${list1.getCountBet()}"
                                               value="${list1.getType1()}"></td>
                                    <td><input type="text" name="FullTotal_Goal.under:${list1.getCountBet()}"
                                               value="${list1.getType2()}"></td>
                                    <td><input type="button" value="Delete" onclick="deleteRow(this,'FullTotal_Goal')">
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                        <br>

                        <div class="button">
                            <div class="hide">show half time</div>
                            <div class="inner">
                                <input type="text" name="HalfWin:FH" value="<%=session.getAttribute("HalfWin:FH")%>">winFrstHalfTm<br>
                                <input type="text" name="HalfWin:SH" value="<%=session.getAttribute("HalfWin:SH")%>">winScndHalfTm<br>
                                <input type="text" name="HalfWin:NH" value="<%=session.getAttribute("HalfWin:NH")%>">drawHalfTm<br><br>
                                <input class="extrabutton" type="button"
                                       onclick="return addRow('over','under',null,'HalfTotal_Goal');"
                                       value="add new total">
                                <table id="HalfTotal_Goal" style="border: 1px solid black">
                                    <tbody>
                                    <th>total</th>
                                    <th>overTime</th>
                                    <th>underTime</th>
                                    <th></th>
                                    <c:forEach items="${HalfTotal_Goal}" var="list2">
                                        <tr>
                                            <td>${list2.getCountBet()} goal</td>
                                            <td><input type="text" name="HalfTotal_Goal.over:${list2.getCountBet()}"
                                                       value="${list2.getType1()}"></td>
                                            <td><input type="text" name="HalfTotal_Goal.under:${list2.getCountBet()}"
                                                       value="${list2.getType2()}"></td>
                                            <td><input type="button" value="Delete"
                                                       onclick="deleteRow(this,'HalfTotal_Goal')">
                                            </td>
                                        </tr>
                                    </c:forEach>
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
                            <c:forEach items="${FullNext_Goal}" var="list3">
                                <tr>
                                    <td><p class="p1">${list3.getCountBet()} goal</p></td>
                                    <td><input type="text" name="FullNext_Goal.gF:${list3.getCountBet()}"
                                               value="${list3.getType1()}">
                                    </td>
                                    <td><input type="text" name="FullNext_Goal.gN:${list3.getCountBet()}"
                                               value="${list3.getType3()}">
                                    </td>
                                    <td><input type="text" name="FullNext_Goal.gS:${list3.getCountBet()}"
                                               value="${list3.getType2()}">
                                    </td>
                                    <td><input type="button" value="Delete" onclick="deleteRow(this,'FullNext_Goal')">
                                </tr>
                            </c:forEach>
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
                            <c:forEach items="${FullTotal_Corner}" var="list4">
                                <tr>
                                    <td>${list4.getCountBet()}</td>
                                    <td><input type="text" name="FullTotal_Corner.over:${list4.getCountBet()}"
                                               value="${list4.getType1()}"></td>
                                    <td><input type="text" name="FullTotal_Corner.under:${list4.getCountBet()}"
                                               value="${list4.getType2()}"></td>
                                    <td><input type="button" value="Delete"
                                               onclick="deleteRow(this,'FullTotal_Corner');">
                                    </td>
                                </tr>
                            </c:forEach>
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
                            <c:forEach items="${FullNext_Corner}" var="list5">
                                <tr>
                                    <td><p class="p1">${list5.getCountBet()} Corner</p></td>
                                    <td><input type="text" name="FullNext_Corner.cF:${list5.getCountBet()}"
                                               value="${list5.getType1()}">
                                    </td>
                                    <td><input type="text" name="FullNext_Corner.cN:${list5.getCountBet()}"
                                               value="${list5.getType3()}">
                                    </td>
                                    <td><input type="text" name="FullNext_Corner.cS:${list5.getCountBet()}"
                                               value="${list5.getType2()}">
                                    </td>
                                    <td><input type="button" value="Delete"
                                               onclick="deleteRow(this,'FullNext_Corner');">
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="button" id="foulsFootball">
                    <div class="hide">show football fouls</div>
                    <div name="bal2" class="inner"> football fouls</div>
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
                            <c:forEach items="${FullTotal_Hckey}" var="list6">
                                <tr>
                                    <td>${list6.getCountBet()}</td>
                                    <td><input type="text" name="FullTotal_Hckey.over:${list6.getCountBet()}"
                                               value="${list6.getType1()}"></td>
                                    <td><input type="text" name="FullTotal_Hckey.under:${list6.getCountBet()}"
                                               value="${list6.getType2()}"></td>
                                    <td><input type="button" value="Delete"
                                               onclick="deleteRow(this,'FullTotal_Hckey')">
                                </tr>
                            </c:forEach>
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
                            <c:forEach items="${FullNext_Hckey}" var="list7">
                                <tr>
                                    <td><p class="p1">${list7.getCountBet()} period</p></td>
                                    <td><input type="text" name="FullNext_Hckey.hF:${list7.getCountBet()}"
                                               value="${list7.getType1()}"></td>
                                    <td><input type="text" name="FullNext_Hckey.hN:${list7.getCountBet()}"
                                               value="${list7.getType3()}"></td>
                                    <td><input type="text" name="FullNext_Hckey.hS:${list7.getCountBet()}"
                                               value="${list7.getType2()}"></td>
                                    <td><input type="button" value="Delete"
                                               onclick="deleteRow(this,'FullNext_Hckey')">
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </form>
        <div id="formCreateEvebtByDefaulr"></div>
    </div>
</div>

<script src="js/verification.js"></script>
</body>
</html>
