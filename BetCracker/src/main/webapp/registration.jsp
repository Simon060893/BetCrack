<%--
  Created by IntelliJ IDEA.
  User: Катя
  Date: 15.02.2015
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="./css/style.css" type="text/css">
</head>
<body>
<script>
    var f;
    var req = '<%=session.getAttribute("showConfDialog")%>';
    if (req !== 'null') {
        alert(req);
    }

</script>

<form action="/reg" method="Post" name="form">
    <input type="text" name="FIO" class="placeholder" value="FIO"
           onfocus="preparedInput('0');" onblur="resettInput('0')"/><br><br>
    <input type="" name="login" class="placeholder" value="Login"
           onfocus="preparedInput('1');" onblur="resettInput('1')"/><br><br>
    <input type="password" name="psw" class="placeholder" value="1111111111"
           onfocus="preparedInput('2');" onblur="resettInput('2')"/><br><br>
    <input type="password" name="psw_r" class="placeholder" value="1111111111"
           onfocus="preparedInput('3');" onblur="resettInput('3')"/><br><br>
    <input type="email" name="email" class="placeholder" value="email"
           onfocus="preparedInput('4');" onblur="resettInput('4')"/><br><br>
    <input type="text" name="phone_number" class="placeholder" value="380"
           onfocus="preparedInput('5');" onblur="resettInput('5')"/><b id="error"></b><br>
    <input type="date" name="dat" />
    <input type="submit" name="btn" value="sign"/>
</form>


<script type="text/javascript">
    var errorHolder = document.getElementById('error');
    var input;
var i=0;
    function preparedInput() {
        input = document.form.getElementsByTagName("input")[arguments[0]];
        if (input.className == 'placeholder' || input.className == 'error') {
            prepareInput(input);
        }
    }

    function prepareInput(input) {
        input.className = '';
        input.oldValue = input.value;
        input.value = '';
    }

    function resettInput() {
        input = document.getElementsByTagName("input")[arguments[0]];
        if (input.value == '') { // если пустой
            resetInput(input); // заполнить плейсхолдером
        }
        if (arguments[0] == 5) {
            if (isNaN(input.value)) { // введено не число
                // показать ошибку
                input.className = 'error';
                errorHolder.innerHTML = '*'
            } else {
                errorHolder.innerHTML = ''
            }
        }
    }

    function resetInput(input) {
        input.className = 'placeholder';
        input.value = input.oldValue;
    }
</script>

</body>
</html>
