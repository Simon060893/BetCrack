function proverka(input) {
    if (input.id == 'FT' || input.id == 'ST') {
    } else {
        input.value = input.value.replace(/[^\d.]/g, '');
    }
}


$(document).ready(function () {
    var input = document.creaEv1.getElementsByTagName("input");
    for (var i = 0; i < input.length; i++) {
        if (input[i].type == 'text') {
            input[i].size = 4;
            input[i].setAttribute('class','placeholder');
            if(input[i].value == null || input[i].value =='null' ){
                input[i].value = '0.0';
            }
        }
    }
});

var i, i1 = i7 = i5 = i8 = 2;
var i2 = i3 = i6 = i4 = 1;
var data = ['', 'FullTotal_Goal', 'HalfTotal_Goal', 'FullNext_Goal',
    'FullNext_Hckey', 'FullNext_Corner', 'FullTotal_Hckey', 'FullTotal_Corner'];
var s = 0;
function sendData(par) {
    var id=1;
    var oldName,oldName1,fullEven='';
    if(par == 'Soccer'){
       id=0;
    }else if(par == 'Hockey'){
        id=2;
    }
            var input1 = document.creaEv1.getElementsByTagName('input');
            for (var i = 0; i < input1.length; i++) {
                if (input1[i].type == 'text' && input1[i].value !== 0 + '.' + 0 && input1[i].value !== '') {
                    if (!(/\d/.test(input1[i].name.substr(0, 6)))) {
                        if (input1[i].name.substr(input1[i].name.indexOf(":")) == oldName &&
                            input1[i].name.substr(0,input1[i].name.indexOf("."))==oldName1) {
                            fullEven = fullEven + ' ' + input1[i].name + '=' + input1[i].value;
                        } else {
                            fullEven = fullEven + ' ' + '@' + input1[i].name + '=' + input1[i].value;
                        }
                        oldName = input1[i].name.substr(input1[i].name.indexOf(":"));
                        oldName1 =  input1[i].name.substr(0,input1[i].name.indexOf("."));
                    }
                }
            }
            document.crtEv.updateEvent.value = id+fullEven;
}
function sendDataToUrl(name, param) {
    var req = new XMLHttpRequest();
    req.open("POST", "/createEvent?" + name + '=' + param, true);
    req.send(null);
    req.onreadystatechange = readystate; // назначим обработчик событию объекта
    function readystate() {
        if (req.readyState == 4) {// если запрос завершен
            if (req.status == 200) { // если он завершен без ошибок
            }
            else {
                alert("Произошла ошибка " + name + param + req.status);
            }
        }
    }
}
function getNotNullData(val) {
    if (val !== null) {
        return val;
    } else {
        return 0, 0;
    }
}
var rowId = 0;