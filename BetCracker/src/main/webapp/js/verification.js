$(document).on('focus click', 'input', function () {
    preparedInput(this);
});
$(document).on('blur', 'input', function () {
    resettInput(this);
});
$(document).on('keup', 'input', function () {
    proverka(this);
});
$(document).on('change', 'input', function () {
    proverka(this);
});
var input;
var input1;
function checkIndex(val) {
    input1 = document.creaEv1.getElementsByTagName("input");
    for (var i = 0; i < input1.length; i++) {
        if (input1[i].name == val.name) {
            input = document.creaEv1.getElementsByTagName("input")[i];
            break;
        }
    }
}
function preparedInput(value) {
    checkIndex(value);
    if (input.className == 'placeholder') {
        prepareInput(input);
    }
}

function prepareInput(input) {
    input.className = '';
    input.oldValue = input.value;
    input.value = '';
}

function resettInput(value) {
    checkIndex(value);
    if (input.value == '') { // если пустой
        resetInput(input); // заполнить плейсхолдером
    }
}

function resetInput(input) {
    input.className = 'placeholder';
    input.value = input.oldValue;
}


function deleteRow(r, nameTable) {
    var i = r.parentNode.parentNode.rowIndex;
    document.getElementById(nameTable).deleteRow(i);
}

function deleteRowFromList(r, nameTable) {
    var i = r.parentNode.parentNode.rowIndex;
    document.getElementById(nameTable).deleteRow(i);
    sendDataToUrl("indexToDrop", i)
}
var gr = document.getElementsByName('check');
window.onclick = function () {
    if (gr[0].checked) {
        document.getElementById("formCreateEvebtByDefaulr").style.visibility = "visible";
        document.getElementById("formCreateEvebtByDefaulr").style.height = $('#formCreateEvebt').css('height');
    } else {
        document.getElementById("formCreateEvebtByDefaulr").style.visibility = "hidden";
    }
}

var input1 = document.getElementById("FT");
var input2 = document.getElementById("ST");
input1.oninput = function () {
    document.getElementById('nmF').innerHTML = input.value;
    document.getElementById('nmF1').innerHTML = input.value;
    document.getElementById('nmF2').innerHTML = input.value;
}
input2.oninput = function () {
    document.getElementById('nmS').innerHTML = input2.value;
    document.getElementById('nmS1').innerHTML = input2.value;
    document.getElementById('nmS2').innerHTML = input2.value;
}
function setNameForInput(inp, param, param1, param2) {
    var lengthOfStr = document.getElementById(param).name;
    document.getElementById(param).name = lengthOfStr.substr(0, (lengthOfStr.indexOf(":") + 1)) + inp.value;
    lengthOfStr = document.getElementById(param1).name;
    document.getElementById(param1).name = lengthOfStr.substr(0, (lengthOfStr.indexOf(":") + 1)) + inp.value;
    if (param2 !== null) {
        lengthOfStr = document.getElementById(param2).name;
        document.getElementById(param2).name = lengthOfStr.substr(0, (lengthOfStr.indexOf(":") + 1)) + inp.value;
    }
}

$(function () {
    $('.button > .hide').click(function () {
        $(this).next('.inner').stop().slideToggle();
    });
});

var countInput = document.creaEv.getElementsByTagName("input").length;
function addRow(str1, str2, str3, table) {
    if (table == data[5]) {
        i = i1++;
    } else if (table == data[7]) {
        i = i2++;
    } else if (table == data[1]) {
        i = i3++;
    } else if (table == data[2]) {
        i = i4++;
    } else if (table == data[3]) {
        i = i5++;
    } else if (table == data[6]) {
        i = i6++;
    } else if (table == data[4]) {
        i = i7++;
    } else if (table == data[0]) {
        i = i8++;
    }
    addNewRow(str1, str2, str3, table, i);
}
function addNewRow(str1, str2, str3, table, i) {
    var i = 0;
    var idLabe1 = new Date().getTime() + i++;
    var idLabe2 = new Date().getTime() + i++;
    var idLabe3 = new Date().getTime() + i++;
    var idLabe0 = new Date().getTime() + i++;
    if (str3 !== null) {
        $('#' + table + ' > tbody:last').append(
            '<tr><td ><input type="text" size="4"  value="0.0" class="placeholder" name=' + idLabe0 + '  oninput="setNameForInput(this,' + idLabe1 + ',' + idLabe2 + ',' + idLabe3 + ');"/></td>' +
            '<td><input type="text" size="4"  value="0.0" class="placeholder" id=' + idLabe1 + ' name="' + table + '.' + str1 + ':' + '" ></td>' +
            '<td><input type="text" size="4" value="0.0" class="placeholder" id=' + idLabe3 + ' name="' + table + '.' + str3 + ':' + '" ></td>' +
            '<td><input type="text" size="4" value="0.0" class="placeholder" id=' + idLabe2 + ' name="' + table + '.' + str2 + ':' + '" ></td>' +
            '<td><input type="button" value="Delete" onclick="deleteRow(this,\'' + table + '\')"></td></tr>'
        );
    } else {
        $('#' + table + ' > tbody:last').append(
            '<tr><td><input type="text" size="4"  value="0.0" class="placeholder" name=' + idLabe0 + '   oninput="setNameForInput(this,' + idLabe1 + ',' + idLabe2 + ',' + null + ');"/></td>' +
            ' <td><input type="text" size="4" value="0.0" class="placeholder" id=' + idLabe1 + ' name="' + table + '.' + str1 + ':' + '"></td>' +
            '<td><input type="text" size="4" value="0.0" class="placeholder" id=' + idLabe2 + ' name="' + table + '.' + str2 + ':' + '" ></td>' +
            '<td><input type="button" value="Delete" onclick="deleteRow(this,\'' + table + '\')"></td></tr>'
        );
    }
}

function hideTypeMatch(par) {
    document.getElementById("typeMatch").innerHTML = par;
    if (par == 'Soccer') {
        document.getElementById("hockey").style.visibility = 'hidden';
        document.getElementById("foulsFootball").style.visibility = 'visible';
        document.getElementById("corner").style.visibility = 'visible';
    } else if (par == 'Hockey') {
        document.getElementById("hockey").style.visibility = 'visible';
        document.getElementById("foulsFootball").style.visibility = 'hidden';
        document.getElementById("corner").style.visibility = 'hidden';
    } else {
        document.getElementById("hockey").style.visibility = 'hidden';
        document.getElementById("foulsFootball").style.visibility = 'hidden';
        document.getElementById("corner").style.visibility = 'hidden';
    }
}


