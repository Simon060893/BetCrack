function drawTextAlongArc(context, str, centerX, centerY, radius, angle){
    context.save();
    context.translate(centerX, centerY);
    context.rotate(-1 * angle / 2);
    context.rotate(-1 * (angle / str.length) / 2);
    for (var n = 0; n < str.length; n++) {
        context.rotate(angle / str.length);
        context.save();
        context.translate(0, -1 * radius);
        var char = str[n];
        context.fillText(char, 0, 0);
        context.restore();
    }
    context.restore();
}
var canvas = document.getElementById("prompt_name_container");
var ctx = canvas.getContext('2d');
ctx.textAlign = "center";
ctx.font = "28pt Open Sans";
drawTextAlongArc(ctx, "JOIN", 150, 160, 100, 2);


