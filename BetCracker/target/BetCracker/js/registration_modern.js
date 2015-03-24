$(document).ready(function()
{
    $("#username").blur(function()
    {
        if($(this).val().length>0){
            $("#msgbox").removeClass().addClass('messagebox').text('Checking...').fadeIn("slow");
            $.ajax({
                url: '/reg',
                type: 'POST',
                beforeSend: function () {
                    $('#msgbox').html('Checking...');
                },
                dataType: 'html',
                data: {delay: $(this).val()},
                success: function (data) {
                    $("#msgbox").fadeTo(200, 0.1, function () {
                        $(this).html('name is available').addClass('messageboxok').fadeTo(900, 1);
                    });
                },
                error: function (xhr, status, error) {
                    $("#msgbox").fadeTo(200, 0.1, function () //начнет появляться сообщение
                    {
                        $(this).html('name has already busy').addClass('messageboxerror').fadeTo(900, 1);
                    });
                }
            });
        }
    });
});