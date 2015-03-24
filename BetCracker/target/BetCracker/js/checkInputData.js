var input;
var input1;
function checkIndex(val) {
    input1 =document.log.getElementsByTagName("input");
    for (var i = 0; i < input1.length; i++) {
        if (input1[i].name == val.name) {
            input = document.log.getElementsByTagName("input")[i];
            break;
        }
    }
}

function preparedInput(value) {
    checkIndex(value);
    if (input.className == 'placeholder' || input.className == 'error') {
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
