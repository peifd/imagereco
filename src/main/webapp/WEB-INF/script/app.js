var mainapp = angular.module('mainApp', ['ui.router', 'ui.bootstrap']);
var ICON_UP;
var ICON_HANDLE;
mainapp.controller('mainCtrl', function ($scope, $http) {
    //图片上传
    var $upload = $('#iconUpload');
    $upload.fileinput({
        language: 'zh',
        allowedFileExtensions: ['jpg', 'png', 'jpeg'],
        showUpload: true,
        uploadAsync: true,
        showPreview: false,
        multiple: false,
        fileInputCleared: true,
        maxFileCount: 1,
        msgPlaceholder: "支持jpg、jpeg、png格式、最大200M",
        autoReplace: true,
        uploadUrl: "upload"
    });
    //上传后触发的动作
    $upload.on("fileuploaded", function (event, data, previewId, index) {
        ICON_UP = data.response.filePath;
        showimage(ICON_UP, "showimg");
    });

    /**
     * 图片处理
     * */
    $scope.handle_fn = function () {
        var dataJson = {
            filePath: ICON_UP.substr(ICON_UP.lastIndexOf("/") + 1)
        };
        $.ajax({
            url: "handle",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(dataJson),
            contentType: 'application/json',
            success: function (data) {
                ICON_HANDLE = data.filePath;
                showimage(ICON_HANDLE, "showimg");
            }
        })
    };

    /**
     * 识别区域
     * */
    $scope.recognize_fn = function () {
        var dataJson = {
            filePath: ICON_HANDLE.substr(ICON_HANDLE.lastIndexOf("/") + 1)
        };
        $.ajax({
            url: "recognize",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(dataJson),
            contentType: 'application/json',
            success: function (data) {
                showimage(data.filePath, "showimg");
            }
        })
    };

    //内容识别
    $scope.comment_fn = function () {
        var tabSelect = $('#categoryTab li.active a').attr('href');
        var script;
        var showImage;
        var showContent;
        //获取切换的tab
        switch (tabSelect) {
            case "#tickCode":
                script = "Invoice_code_java.py";
                showImage = "tickCodeImg";
                showContent = "tickCodeText";
                break;
            case "#tickNum":
                script = "Invoice_number_java.py";
                showImage = "tickNumImg";
                showContent = "tickNumText";
                break;
            case "#tickDate":
                script = "Invoice_date_java.py";
                showImage = "tickDateImg";
                showContent = "tickDateText";
                break;
            case "#tickName":
                script = "Invoice_buy_name_java.py";
                showImage = "tickNameImg";
                showContent = "tickNameText";
                break;
            case "#tickIden":
                script = "Invoice_buy_id_java.py";
                showImage = "tickIdenImg";
                showContent = "tickIdenText";
                break;
            case "#tickCount":
                script = "Invoice_value_java.py";
                showImage = "tickCountImg";
                showContent = "tickCountText";
                break;
        }

        console.log("select tab: " + tabSelect);
        console.log("showImageId: " + showImage);
        console.log("showContentId: " + showContent);

        var dataJson = {
            filePath: ICON_HANDLE.substr(ICON_HANDLE.lastIndexOf("/") + 1),
            content: script
        };
        $.ajax({
            url: "content",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(dataJson),
            contentType: 'application/json',
            success: function (data) {
                showimagePart(data.filePath, data.content, showImage, showContent);
            }
        })
    };

    /**
     * 图片显示区域显示图片
     * */
    function showimage(iconPath, imgId) {
        var ul = document.getElementById(imgId);
        $('#' + imgId + '  img').remove();
        var img = document.createElement("img");
        img.src = iconPath;
        img.setAttribute("id", "oringimg");
        img.setAttribute("height", "100%");
        img.setAttribute("width", "100%");
        ul.appendChild(img);
    }

    /**
     * 小框框图片
     * */
    function showimagePart(iconPath, content, imgId, contentId) {
        //图片
        var ul = document.getElementById(imgId);
        $('#' + imgId + '  img').remove();
        var img = document.createElement("img");
        img.src = iconPath;
        img.setAttribute("id", "partimg");
        img.setAttribute("height", "100%");
        img.setAttribute("width", "100%");
        ul.appendChild(img);

        //内容
        var ulc = document.getElementById(contentId);
        $('#' + contentId + '  input').remove();
        var input = document.createElement("input");
        input.setAttribute("value", content);
        input.setAttribute("height", "100%");
        input.setAttribute("width", "100%");
        input.setAttribute("style", "font-size:45px;font-family:inherit;line-height:inherit;background-color:transparent;border:0px;outline:none;cursor:pointer;");
        ulc.appendChild(input);
    }
});

