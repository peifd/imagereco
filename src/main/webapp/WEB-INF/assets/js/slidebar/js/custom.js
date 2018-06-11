jQuery(function ($) {
    //默认全部展开
    $(".sidebar-submenu").slideDown();
    $(".sidebar-menu li").addClass("active");
    $(".sidebar-dropdown > a").click(function () {
        if ($(this).parent().hasClass("active")) {
            $(this).parent().removeClass("active");
            $(this).next(".sidebar-submenu").hide(250);
        } else {
            $(this).parent().addClass("active");
            $(this).next(".sidebar-submenu").show(250);
        }
    });
    $(".page-wrapper").toggleClass("toggled");
    if (!/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
        $(".sidebar-content").mCustomScrollbar({
            axis: "y",
            autoHideScrollbar: true,
            scrollInertia: 300
        });
        $(".sidebar-content").addClass("desktop");
    }
});