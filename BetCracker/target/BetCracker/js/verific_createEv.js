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
    input1 = document.creaEv.getElementsByTagName("input");
    for (var i = 0; i < input1.length; i++) {
        if (input1[i].name == val.name) {
            input = document.creaEv.getElementsByTagName("input")[i];
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

function loadSubdata(obj) {
    var val = obj.options[obj.selectedIndex].value;
    if (!val) {
        return;
    } else if (val == '0') {
        document.getElementById("hockey").style.visibility = 'hidden';
        document.getElementById("foulsFootball").style.visibility = 'visible';
        document.getElementById("corner").style.visibility = 'visible';
    } else if (val == '2') {
        document.getElementById("hockey").style.visibility = 'visible';
        document.getElementById("foulsFootball").style.visibility = 'hidden';
        document.getElementById("corner").style.visibility = 'hidden';
    } else {

    }
}

function deleteRow(r, nameTable) {
    var i = r.parentNode.parentNode.rowIndex;
    document.getElementById(nameTable).deleteRow(i);
}

function deleteRowFromList(r, nameTable) {
    var i = r.parentNode.parentNode.rowIndex;
    document.getElementById(nameTable).deleteRow(i);
    sendDataToUrl("indexToDrop",i)
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


$(function () {
    $('.button > .hide').click(function () {
        $(this).next('.inner').stop().slideToggle();
    });
});

var countInput=document.creaEv.getElementsByTagName("input").length;
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
    addNewRow(str1, str2,str3, table, i);
}
function addNewRow( str1, str2,str3, table, i) {
    if (str3 !== null) {
        $('#' + table + ' > tbody:last').append(
            '<tr><td> ' + i + table.split("_")[1] + '</td>' +
            '<td><input type="text" size="4" value="0.0" class="placeholder" name="' + table +'.'+ str1+':'+i + '" onkeyup="return proverka(this);" onchange="return proverka(this);"></td>' +
            '<td><input type="text" size="4" value="0.0" class="placeholder"  name="' + table +'.'+ str3+':'+i  + '" onkeyup="return proverka(this);" onchange="return proverka(this);"></td>' +
            '<td><input type="text" size="4" value="0.0" class="placeholder" name="' + table + '.'+ str2+':'+i + '" onkeyup="return proverka(this);"onchange="return proverka(this);"></td>' +
            '<td><input type="button" value="Delete" onclick="deleteRow(this,\'' + table + '\')"></td></tr>'
        );
    } else {
        $('#' + table + ' > tbody:last').append(
            '<tr><td>' + i + '.5</td>' +
            ' <td><input type="text" size="4" value="0.0" class="placeholder"  name="' + table+'.'+  str1+':'+i+'.5' + '" onkeyup="return proverka(this);" onchange="return proverka(this);"></td>' +
            '<td><input type="text" size="4" value="0.0" class="placeholder"  name="' + table +'.'+  str2+':'+i+'.5' + '" onkeyup="return proverka(this);"  onchange="return proverka(this);"></td>' +
            '<td><input type="button" value="Delete" onclick="deleteRow(this,\'' + table + '\')"></td></tr>'
        );
    }
}