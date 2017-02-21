$(document).ready(function () {
    //Toggle fullscreen
    $("#dia_click").add("#timeGapDiagram_click").add("#integration_map_clcik").add("#combine_map_click").add("#ind_Exp_click").add("#ser_add_touch_click").click(function minMax(e) {
      //  e.preventDefault();
        
        var $this = $(this);
    
        if ($this.children('i').hasClass('glyphicon-resize-full'))
        {
            $this.children('i').removeClass('glyphicon-resize-full');
            $this.children('i').addClass('glyphicon-resize-small');
        }
        else if ($this.children('i').hasClass('glyphicon-resize-small'))
        {
            $this.children('i').removeClass('glyphicon-resize-small');
            $this.children('i').addClass('glyphicon-resize-full');
        }
        $(this).closest('.panel').toggleClass('panel-fullscreen');
    });
});
//$("#Maximize").add("#Maximize1").click(function Maximize()
//{
//     var $this = $(this);
//    console.log("Maximize");
//     if ($this.children('i').hasClass('glyphicon-resize-full'))
//        {
//            $this.children('i').removeClass('glyphicon-resize-full');
//            $this.children('i').addClass('glyphicon-resize-small');
//        }
//    $(this).closest('.panel').addClass('panel-fullscreen');
//
//});
//$("#Minimize").add("#Minimize1").click(function Minimize()
//{
//console.log("Minimize");
// var $this = $(this);
//if ($this.children('i').hasClass('glyphicon-resize-small'))
//        {
//            $this.children('i').removeClass('glyphicon-resize-small');
//            $this.children('i').addClass('glyphicon-resize-full');
//        }
// 
//    $(this).closest('.panel-fullscreen').removeClass('panel-fullscreen').addClass('panel panel-default');
//});

