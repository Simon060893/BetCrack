<%--
  Created by IntelliJ IDEA.
  User: Катя
  Date: 15.02.2015
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<jsp:forward page="/registration.jsp"/>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <title>MAIN</title>
    <link rel="stylesheet" href="./css/style.css" type="text/css">
    <link rel="stylesheet" href="./css/annimation.css" type="text/css">
    <link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css" media="screen" title="no title" charset="utf-8" />
    <link rel="stylesheet" href="css/template.css" type="text/css" media="screen" title="no title" charset="utf-8" />
    <link rel="stylesheet" href="css/chat.css" type="text/css"   charset="utf-8" />
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.validationEngine.js" type="text/javascript"></script>
    <script>
        var req = '<%=session.getAttribute("showConfDialog")%>';
        <%session.setAttribute("showConfDialog",null);%>
        if (req !== 'null') {
            alert(req);
        }
    </script>
    <script>
        window.onload = function () {
            var inAccess = '<%=session.getAttribute("inAccess")%>';
            var isAdmin = '<%=session.getAttribute("isAdmin")%>';
            if (inAccess !== 'null') {
                document.getElementById("informer").style.visibility = "hidden";
                document.getElementById("body_container").style.visibility = "visible";
                document.getElementById("account").style.visibility = "visible";
                if (isAdmin !== 'yeas') {
                    document.getElementById("bet").style.visibility = "visible";
                    document.getElementById("createEv").style.visibility = "hidden";
                }else{
                    document.getElementById("createEv").style.visibility = "visible";
                    document.getElementById("bet").style.visibility = "hidden";
                }
            } else {
                document.getElementById("body_container").style.visibility = "hidden";
                document.getElementById("informer").style.visibility = "visible";
                document.getElementById("account").style.visibility = "hidden";
            }
        }
        function confirmDelete() {
            if (confirm("Would you wish to be an Admin?")) {
                document.form.admin.value = "yeas";
            }else{
                document.form.admin.value = "no";
            }
        }
    </script>
    <script type="text/javascript" src="js/checkInputData.js"> </script>
    <script language="javascript" src="js/registration_modern.js">  </script>
    <script type="text/javascript" src="js/authentification_modern.js"></script>
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/openChat.js"></script>
</head>
<body>
<div id="main">
    <div id="header">
        <div id="events">
            <a href="">SPORT</a>
            <a href="">LIVE</a>
            <a href="">CASINO</a>
            <a href="">POKER</a>
        </div>
        <div id="informer">
            <form name="log" method="post">
                <input id="lgn" type="text" name="lgn" size="10" class="placeholder" value="login" onfocus="preparedInput(this);"
                       onblur="resettInput(this);" />
                <input id="psw" type="password" name="psw" size="10" class="placeholder" value="password" onfocus="preparedInput(this);"
                       onblur="resettInput(this);" >
                <input id="entr" type="button" name="entr" value="Enter"/>
                <input type="submit" name="rg" value="Sign up" onclick="confirmDelete();"/><br>
                <input type="hidden" name="admin"/>
            </form>
            <button id="redf">Sign UP</button>
        </div>
        <div id="account">
            <i style="color: black">Hello, <b id="playerName" style="color: black"><%=session.getAttribute("playerName")%>
            </b></i>
            <a href="account.jsp">My account</a>
            <a href="replenish.jsp">Put money</a>
            <span style="color: black">my money <%=session.getAttribute("money")%></span>

            <form name="escap" action="/main" method="POST">
                <input action="/main" type="submit" name="esc" value="ESCAPE">
            </form>
        </div>
        <div id="createEv"><a href="/createEvent">Create new Event</a></div>
        <div id="bet">
            <form name="makeBet" method="POST" action="/main">
                <input type="text"name="count" size="10">
            </form>
        </div>
    </div>
    <div id="news">

    </div>
    <div id="content">
        <table>
            <th>match</th>
            <th>time</th>
            <th>score</th>
            <c:forEach items="${listEv}" var="mtch">
                <tr>
                    <td><a href=<c:url value="/main?matchId=${mtch.value.getIdRow()}"/>  ${mtch.value.toString()}:
                    ${mtch.value.getNameF()} - ${mtch.value.getNameS()} </a></td>
                    <td>${mtch.value.getTimeMatch()}</td>
                    <td>${mtch.getResultMatch()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div  id="body_container">
        <canvas id="prompt_name_container" class="box_shadow"></canvas>'

        <script src="js/rotateDiv.js"></script>
        <div id="message_container" class="box_shadow">
            <p class='online_count'>
                <b>0</b> people online right now
            </p>
            <ul id="messages">
            </ul>


            <div id="input_message_container">
                <form id="form_send_message" method="post" action="#">
                    <input type="text" id="input_message"
                           placeholder="Type your message here..." /> <input type="submit"
                                                                             id="btn_send" onclick="send();" value="Send" />
                    <div class="clear"></div>
                </form>
            </div>
            <div>

                <input type="button" onclick="closeSocket();"
                       value="Leave Chat Room" id="btn_close" />
            </div>

        </div>
    </div>
</div>




<div id="layer"></div>
<div id="popup">
    <form id="formID" class="formular" name ="form" method="post" >
        <input  type="hidden" name="admin" value="no"/>
        <label>
            <span >Login :<span id="msgbox" style="display:none"></span></span>
            <input class="validate[required,custom[noSpecialCaracters],length[0,20]] text-input" type="text"
                   id ="username" name="user" required/>
        </label>
        <label>
            <span>Password : </span>
            <input class="validate[required,custom[password]] text-input" type="password" name="password" id="password" required/>
        </label>
        <label>
            <span>First name : </span>
            <input class="validate[required,custom[onlyLetter],length[0,100]] text-input" type="text" name="firstname"required/>
        </label>

        <label>
            <span>Confirm password : </span>
            <input class="validate[required,confirm[password]] text-input" type="password" name="password2" required/>
        </label>
        <label>
            <span>Last name : </span>
            <input class="validate[required,custom[onlyLetter],length[0,100]] text-input" type="text" name="lastname" required/>
        </label>

        <label>
            <span>Telephone : </span>
            <input class="validate[required,custom[telephone]] text-input" type="text" name="telephone" required/>
        </label>
        <label>
            <span>Email address : </span>
            <input class="validate[required,custom[email]] text-input" type="text" name="email" id="email" required/>
        </label>
        <label>
            <span>Adress : </span>
            <input class="validate[required,length[4,50]] text-input" type="text" name="adres" id="adres" required/>
        </label>

        <label>
            <span>City : </span>
            <input class="validate[required,length[4,50]] text-input" type="text" name="city" id="city" required/>
        </label>
        <label>
            <span>Date born : </span>
            <input class=" text-input" type="date" name="dateB" id="dateB" required/>
        </label>
        <label>
            <span>Currency : </span>
            <select name="typeExp" required>
                <option value="" selected="selected">Select Currency</option>

                <option value="EUR">EUR(Europa)</option>
                <option value="USD">USD(US of America)</option>
                <option value="UAN">UAN(Ukraine)</option>
                <option value="RUB">RUB(Russia)</option>

            </select>
        </label>
        <label>
            <span>Country : </span>
            <select name="Country" required>
                <option value="" selected="selected">Select Country</option>

                <option value="United States">United States</option>
                <option value="United Kingdom">United Kingdom</option>

                <option value="Afghanistan">Afghanistan</option>
                <option value="Albania">Albania</option>
                <option value="Algeria">Algeria</option>

                <option value="American Samoa">American Samoa</option>
                <option value="Andorra">Andorra</option>

                <option value="Angola">Angola</option>
                <option value="Anguilla">Anguilla</option>
                <option value="Antarctica">Antarctica</option>

                <option value="Antigua and Barbuda">Antigua and Barbuda</option>
                <option value="Argentina">Argentina</option>
                <option value="Armenia">Armenia</option>

                <option value="Aruba">Aruba</option>
                <option value="Australia">Australia</option>
                <option value="Austria">Austria</option>

                <option value="Azerbaijan">Azerbaijan</option>
                <option value="Bahamas">Bahamas</option>
                <option value="Bahrain">Bahrain</option>

                <option value="Bangladesh">Bangladesh</option>
                <option value="Barbados">Barbados</option>
                <option value="Belarus">Belarus</option>

                <option value="Belgium">Belgium</option>
                <option value="Belize">Belize</option>
                <option value="Benin">Benin</option>

                <option value="Bermuda">Bermuda</option>
                <option value="Bhutan">Bhutan</option>
                <option value="Bolivia">Bolivia</option>

                <option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
                <option value="Botswana">Botswana</option>
                <option value="Bouvet Island">Bouvet Island</option>

                <option value="Brazil">Brazil</option>
                <option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
                <option value="Brunei Darussalam">Brunei Darussalam</option>

                <option value="Bulgaria">Bulgaria</option>
                <option value="Burkina Faso">Burkina Faso</option>

                <option value="Burundi">Burundi</option>
                <option value="Cambodia">Cambodia</option>
                <option value="Cameroon">Cameroon</option>

                <option value="Canada">Canada</option>
                <option value="Cape Verde">Cape Verde</option>
                <option value="Cayman Islands">Cayman Islands</option>

                <option value="Central African Republic">Central African Republic</option>
                <option value="Chad">Chad</option>
                <option value="Chile">Chile</option>

                <option value="China">China</option>
                <option value="Christmas Island">Christmas Island</option>
                <option value="Cocos (Keeling) Islands">Cocos (Keeling) Islands</option>

                <option value="Colombia">Colombia</option>
                <option value="Comoros">Comoros</option>
                <option value="Congo">Congo</option>

                <option value="Congo, The Democratic Republic of The">Congo, The Democratic Republic of The</option>

                <option value="Cook Islands">Cook Islands</option>
                <option value="Costa Rica">Costa Rica</option>

                <option value="Cote D'ivoire">Cote D'ivoire</option>
                <option value="Croatia">Croatia</option>
                <option value="Cuba">Cuba</option>

                <option value="Cyprus">Cyprus</option>
                <option value="Czech Republic">Czech Republic</option>
                <option value="Denmark">Denmark</option>

                <option value="Djibouti">Djibouti</option>
                <option value="Dominica">Dominica</option>
                <option value="Dominican Republic">Dominican Republic</option>

                <option value="Ecuador">Ecuador</option>
                <option value="Egypt">Egypt</option>
                <option value="El Salvador">El Salvador</option>

                <option value="Equatorial Guinea">Equatorial Guinea</option>
                <option value="Eritrea">Eritrea</option>
                <option value="Estonia">Estonia</option>

                <option value="Ethiopia">Ethiopia</option>
                <option value="Falkland Islands (Malvinas)">Falkland Islands (Malvinas)</option>
                <option value="Faroe Islands">Faroe Islands</option>

                <option value="Fiji">Fiji</option>
                <option value="Finland">Finland</option>
                <option value="France">France</option>

                <option value="French Guiana">French Guiana</option>
                <option value="French Polynesia">French Polynesia</option>

                <option value="French Southern Territories">French Southern Territories</option>
                <option value="Gabon">Gabon</option>

                <option value="Gambia">Gambia</option>
                <option value="Georgia">Georgia</option>
                <option value="Germany">Germany</option>

                <option value="Ghana">Ghana</option>
                <option value="Gibraltar">Gibraltar</option>
                <option value="Greece">Greece</option>

                <option value="Greenland">Greenland</option>
                <option value="Grenada">Grenada</option>
                <option value="Guadeloupe">Guadeloupe</option>

                <option value="Guam">Guam</option>
                <option value="Guatemala">Guatemala</option>
                <option value="Guinea">Guinea</option>

                <option value="Guinea-bissau">Guinea-bissau</option>
                <option value="Guyana">Guyana</option>

                <option value="Haiti">Haiti</option>
                <option value="Heard Island and Mcdonald Islands">Heard Island and Mcdonald Islands</option>
                <option value="Holy See (Vatican City State)">Holy See (Vatican City State)</option>

                <option value="Honduras">Honduras</option>
                <option value="Hong Kong">Hong Kong</option>

                <option value="Hungary">Hungary</option>
                <option value="Iceland">Iceland</option>
                <option value="India">India</option>

                <option value="Indonesia">Indonesia</option>
                <option value="Iran, Islamic Republic of">Iran, Islamic Republic of</option>
                <option value="Iraq">Iraq</option>

                <option value="Ireland">Ireland</option>
                <option value="Israel">Israel</option>
                <option value="Italy">Italy</option>

                <option value="Jamaica">Jamaica</option>
                <option value="Japan">Japan</option>
                <option value="Jordan">Jordan</option>

                <option value="Kazakhstan">Kazakhstan</option>
                <option value="Kenya">Kenya</option>
                <option value="Kiribati">Kiribati</option>

                <option value="Korea, Democratic People's Republic of">Korea, Democratic People's Republic of</option>
                <option value="Korea, Republic of">Korea, Republic of</option>

                <option value="Kuwait">Kuwait</option>
                <option value="Kyrgyzstan">Kyrgyzstan</option>
                <option value="Lao People's Democratic Republic">Lao People's Democratic Republic</option>

                <option value="Latvia">Latvia</option>
                <option value="Lebanon">Lebanon</option>
                <option value="Lesotho">Lesotho</option>
                <option value="Liberia">Liberia</option>

                <option value="Libyan Arab Jamahiriya">Libyan Arab Jamahiriya</option>
                <option value="Liechtenstein">Liechtenstein</option>
                <option value="Lithuania">Lithuania</option>

                <option value="Luxembourg">Luxembourg</option>
                <option value="Macao">Macao</option>
                <option value="Macedonia, The Former Yugoslav Republic of">Macedonia, The Former Yugoslav Republic of</option>

                <option value="Madagascar">Madagascar</option>
                <option value="Malawi">Malawi</option>
                <option value="Malaysia">Malaysia</option>
                <option value="Maldives">Maldives</option>

                <option value="Mali">Mali</option>
                <option value="Malta">Malta</option>
                <option value="Marshall Islands">Marshall Islands</option>

                <option value="Martinique">Martinique</option>
                <option value="Mauritania">Mauritania</option>
                <option value="Mauritius">Mauritius</option>
                <option value="Mayotte">Mayotte</option>

                <option value="Mexico">Mexico</option>
                <option value="Micronesia, Federated States of">Micronesia, Federated States of</option>

                <option value="Moldova, Republic of">Moldova, Republic of</option>
                <option value="Monaco">Monaco</option>
                <option value="Mongolia">Mongolia</option>

                <option value="Montserrat">Montserrat</option>
                <option value="Morocco">Morocco</option>
                <option value="Mozambique">Mozambique</option>
                <option value="Myanmar">Myanmar</option>

                <option value="Namibia">Namibia</option>
                <option value="Nauru">Nauru</option>
                <option value="Nepal">Nepal</option>
                <option value="Netherlands">Netherlands</option>

                <option value="Netherlands Antilles">Netherlands Antilles</option>
                <option value="New Caledonia">New Caledonia</option>
                <option value="New Zealand">New Zealand</option>

                <option value="Nicaragua">Nicaragua</option>
                <option value="Niger">Niger</option>
                <option value="Nigeria">Nigeria</option>
                <option value="Niue">Niue</option>

                <option value="Norfolk Island">Norfolk Island</option>
                <option value="Northern Mariana Islands">Northern Mariana Islands</option>
                <option value="Norway">Norway</option>

                <option value="Oman">Oman</option>
                <option value="Pakistan">Pakistan</option>
                <option value="Palau">Palau</option>
                <option value="Palestinian Territory, Occupied">Palestinian Territory, Occupied</option>

                <option value="Panama">Panama</option>
                <option value="Papua New Guinea">Papua New Guinea</option>
                <option value="Paraguay">Paraguay</option>

                <option value="Peru">Peru</option>
                <option value="Philippines">Philippines</option>
                <option value="Pitcairn">Pitcairn</option>
                <option value="Poland">Poland</option>

                <option value="Portugal">Portugal</option>
                <option value="Puerto Rico">Puerto Rico</option>
                <option value="Qatar">Qatar</option>

                <option value="Reunion">Reunion</option>
                <option value="Romania">Romania</option>
                <option value="Russian Federation">Russian Federation</option>

                <option value="Rwanda">Rwanda</option>
                <option value="Saint Helena">Saint Helena</option>
                <option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>

                <option value="Saint Lucia">Saint Lucia</option>
                <option value="Saint Pierre and Miquelon">Saint Pierre and Miquelon</option>

                <option value="Saint Vincent and The Grenadines">Saint Vincent and The Grenadines</option>
                <option value="Samoa">Samoa</option>

                <option value="San Marino">San Marino</option>
                <option value="Sao Tome and Principe">Sao Tome and Principe</option>

                <option value="Saudi Arabia">Saudi Arabia</option>
                <option value="Senegal">Senegal</option>
                <option value="Serbia and Montenegro">Serbia and Montenegro</option>

                <option value="Seychelles">Seychelles</option>
                <option value="Sierra Leone">Sierra Leone</option>
                <option value="Singapore">Singapore</option>

                <option value="Slovakia">Slovakia</option>
                <option value="Slovenia">Slovenia</option>
                <option value="Solomon Islands">Solomon Islands</option>

                <option value="Somalia">Somalia</option>
                <option value="South Africa">South Africa</option>
                <option value="South Georgia and The South Sandwich Islands">South Georgia and The South Sandwich Islands</option>

                <option value="Spain">Spain</option>
                <option value="Sri Lanka">Sri Lanka</option>
                <option value="Sudan">Sudan</option>

                <option value="Suriname">Suriname</option>
                <option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
                <option value="Swaziland">Swaziland</option>

                <option value="Sweden">Sweden</option>
                <option value="Switzerland">Switzerland</option>
                <option value="Syrian Arab Republic">Syrian Arab Republic</option>

                <option value="Taiwan, Province of China">Taiwan, Province of China</option>
                <option value="Tajikistan">Tajikistan</option>

                <option value="Tanzania, United Republic of">Tanzania, United Republic of</option>
                <option value="Thailand">Thailand</option>

                <option value="Timor-leste">Timor-leste</option>
                <option value="Togo">Togo</option>
                <option value="Tokelau">Tokelau</option>

                <option value="Tonga">Tonga</option>
                <option value="Trinidad and Tobago">Trinidad and Tobago</option>
                <option value="Tunisia">Tunisia</option>

                <option value="Turkey">Turkey</option>
                <option value="Turkmenistan">Turkmenistan</option>
                <option value="Turks and Caicos Islands">Turks and Caicos Islands</option>

                <option value="Tuvalu">Tuvalu</option>
                <option value="Uganda">Uganda</option>
                <option value="Ukraine">Ukraine</option>
                <option value="United Arab Emirates">United Arab Emirates</option>

                <option value="United Kingdom">United Kingdom</option>
                <option value="United States">United States</option>
                <option value="United States Minor Outlying Islands">United States Minor Outlying Islands</option>

                <option value="Uruguay">Uruguay</option>
                <option value="Uzbekistan">Uzbekistan</option>
                <option value="Vanuatu">Vanuatu</option>
                <option value="Venezuela">Venezuela</option>

                <option value="Viet Nam">Viet Nam</option>
                <option value="Virgin Islands, British">Virgin Islands, British</option>

                <option value="Virgin Islands, U.S.">Virgin Islands, U.S.</option>
                <option value="Wallis and Futuna">Wallis and Futuna</option>

                <option value="Western Sahara">Western Sahara</option>
                <option value="Yemen">Yemen</option>
                <option value="Zambia">Zambia</option>

                <option value="Zimbabwe">Zimbabwe</option>
            </select>
        </label>
        <fieldset>
            <legend>Conditions</legend>
            <div class="infos">Подтверждая эту форму, вы сообщаете, что согласны на все! Вы уверены?</div>
            <label>
                <span class="checkbox">Я согласен на все : </span>
                <input class="validate[required] checkbox" type="checkbox" name="agree" />
            </label>
        </fieldset>
        <input class="submit" type="submit" value="sigh up"/>
        <hr/>
    </form>
</div>
<div id="signing">
    <form id="from_sign_in" name="from_sign_in" method="post">
        <input type="text" id="login"name="login" placeholder="login" class="from_sign_in_c" required > <br>
        <input type="password" id="pasword1" name="pasword" placeholder="password" class="from_sign_in_c" required> <br>
        <input type="text" id="codeInputCaptcha" name="code" placeholder="enter what you see" class="from_sign_in_c" required> <br>
        <div id="captchaImg"></div>
        <div id="update">
            <img src="img/refresh.png"/>
        </div>
        <input type="button"id="buttonToEnter"name="buttonToEnter" value="sign in" class="from_sign_in_s">
    </form>
</div>

<div class="windows8">
    <div class="wBall" id="wBall_1">
        <div class="wInnerBall"> </div>  </div>
    <div class="wBall" id="wBall_2">
        <div class="wInnerBall"> </div>  </div>
    <div class="wBall" id="wBall_3">
        <div class="wInnerBall">  </div>   </div>
    <div class="wBall" id="wBall_4">
        <div class="wInnerBall">  </div>   </div>
    <div class="wBall" id="wBall_5">
        <div class="wInnerBall">  </div>  </div>  </div>
</body>
</html>
