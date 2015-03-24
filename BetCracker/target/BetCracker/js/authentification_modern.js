$(function () {
    $(document).ready(function () {
        createEvents();

    });
});//]]>
function createEvents() {
    var layer = $('#layer'),
        popup = $('#popup'),
        signing = $('#signing'),
        form = popup.html(),
        id = 0,
        captcha,
        refresh,
        sign;
    $('button').click(function () {
        confirmDelete();
        layer.fadeIn();
//            popup.delay(500).fadeIn();
        popup.fadeIn();
    });
    $('#buttonToEnter').click(function () {
        if (refresh == 'refresh') {
            $('#captchaImg').html('<p>you need to refresh me</p>');
            animateDiv();
        } else if (captcha == $('#codeInputCaptcha').val()) {
            var lgn = $('#login').val(),
                psw = $('#pasword1').val();
            if ($.trim(lgn).length > 0 && $.trim(psw).length > 0) {
                $.ajax({
                    url: '/main',
                    type: 'POST',
                    beforeSend: function () {
                        $('.windows8').css('visibility', 'visible');
                        //sign=  $('#signing').html();
                        //$('#signing').html( '<div class="windows8">'+
                        //'<div class="wBall" id="wBall_1">'+
                        //'<div class="wInnerBall"> </div>  </div>'+
                        //'<div class="wBall" id="wBall_2">'+
                        //'<div class="wInnerBall"> </div>  </div>'+
                        //'<div class="wBall" id="wBall_3">'+
                        //'<div class="wInnerBall">  </div>   </div>'+
                        //'<div class="wBall" id="wBall_4">'+
                        //'<div class="wInnerBall">  </div>   </div>'+
                        //'<div class="wBall" id="wBall_5">'+
                        //'<div class="wInnerBall">  </div>  </div>  </div>');
                    },
                    dataType: 'html',
                    cache: false,
                    data: {username: $('#login').val(), pasword: $('#pasword1').val()},
                    success: function (data) {
                        //$('.windows8').css('visibility','hidden');
                        //$('#informer').fadeOut();
                        //$('#layer').fadeOut(500);
                        //$('#signing').fadeOut(500);
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        $('.windows8').css('visibility', 'hidden');
                        $('#signing').html(sign);
                        if (xhr.status == 400) {
                            $('#pasword1').css('box-shadow', '0 0 3px 5px rgba(255, 47, 21,.3)');
                        } else if (xhr.status == 302) {
                            $('#login').css('box-shadow', '0 0 3px 5px rgba(255, 47, 21,.3)');
                        } else {
                            alert(xhr.status);
                        }
                        animateDiv();
                    }
                });
            }
            else {
                animateDiv();
            }
        } else {
            animateDiv();
            $('#codeInputCaptcha').css('box-shadow', '0 0 3px 5px rgba(255, 47, 21,.3)');
        }
    });
    $('#codeInputCaptcha').bind('input', function () {
        $(this).css('box-shadow', '0 0 0 0 rgb(0,0,0)');
    });
    $('#login').bind('input', function () {
        $(this).css('box-shadow', '0 0 0 0 rgb(0,0,0)');
    });
    $('#lgn').bind('input', function () {
        $(this).css('box-shadow', '0 0 0 0 rgb(0,0,0)');
    });
    $('#pasword1').bind('input', function () {
        $(this).css('box-shadow', '0 0 0 0 rgb(0,0,0)');
    });
    $('#psw').bind('input', function () {
        $(this).css('box-shadow', '0 0 0 0 rgb(0,0,0)');
    });
    $('#update').bind('click', function () {
        updateCaptcha(id++);
    });
    $('#entr').bind('click', function () {
        var lgn = $('#lgn').val(),
            psw = $('#psw').val();
        if ($.trim(lgn).length > 0) {
            if ($.trim(psw).length > 0) {
                if (navigator.cookieEnabled) {
                $.ajax({
                    url: '/main',
                    type: 'POST',
                    dataType: 'html',
                    cache: false,
                    data: {entr: lgn, psw: psw},
                    success: function (data) {
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        if (xhr.status == 400) {
                            $('#psw').css('box-shadow', '0 0 3px 5px rgba(240, 0, 0,.9)');
                        } else if (xhr.status == 409) {
                            layer.fadeIn(500);
                            updateCaptcha(id++);
                            signing.fadeIn();
                        } else {
                            alert(xhr.status);
                        }
                    }
                });
            }else{alert('please, enable your cookies');}
            }
            else {
                $('#psw').css('box-shadow', '0 0 3px 5px rgba(240, 0, 0,.9)');
            }
        } else {
            $('#lgn').css('box-shadow', '0 0 3px 5px rgba(240, 0, 0,.9)');
        }
    });
    $('#prompt_name_container').bind('mouseover',function(){
        $('#prompt_name_container').animate({ "left": "+=50px" }, 300);
    });
    $('#prompt_name_container').bind('mouseout',function(){
        $('#prompt_name_container').animate({ "left": "-=50px" }, 300);
    });
    layer.click(function (e) {
        e.preventDefault();
        $('#popup').html('Registratin canceled!').fadeOut(700, function () {
            $('#popup').html(form);
            $('.formError').fadeOut();
        });
        $('#signing').fadeOut(700);
        $('#layer').fadeOut(500);
    });
    function updateCaptcha(id) {
        refresh = '';
        $("#captchaImg").html('<img src="main?entr1=' + id + '" />');
        $.get('main?captch=' + id + '', function (data) {
            captcha = data;
        });
        setTimeout(function () {
            refresh = 'refresh';
        }, 25000);
    }
}
function animateDiv() {
    var c = $('#signing').offset().left;
    $('#signing').animate({left: c - 10}, 100);
    $('#signing').animate({left: c + 10}, 100);
    $('#signing').animate({left: c - 10}, 100);
    $('#signing').animate({left: c + 0.1}, 100);
}
