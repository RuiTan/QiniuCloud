
function displayFiles(data){
    for (var i in data){
        var publication = data[i];
        var panel = document.createElement('div');
        panel.setAttribute('class', 'panel panel-info');

    }
}

function getFiles(){
    $.ajax({
        url: 'getSARFiles',
        type: 'get',
        success: function (data) {
            displayFiles(data)
        }
    });
}

getFiles();


function search() {
    var searchContent = document.getElementById("searchContent");
    searchContent = searchContent.value;
    if (searchContent == null || searchContent == "") {
        alert("关键词不能为空！");
    }else {
        $.ajax({
            url: 'getFilesWithFilter',
            type: 'post',
            data: {
                content: searchContent
            },
            success: function (data) {
                displayFiles(data)
            }
        })
    }
}


var uid;
$(function () {
    $("#btn1").on("click", function () {
        console.log($("#f_upload")[0].files.length);
        console.log($("#f_upload")[0].files);
        console.log($("#f_upload")[0]);
        var files = $("#f_upload")[0].files;
        var formData = new FormData();
        for (var i = 0; i < files.length; i++){
            var file = files[i];
            formData.append("file", file);
        }
        console.log(formData);
        console.log(formData.get("file"));
        $.ajax({
            type: "POST",              //因为是传输文件，所以必须是post
            url: 'uploadFiles',         //对应的后台处理类的地址
            data: formData,
            processData: false,
            // contentType: "multipart/form-data;boundary=1;charset=utf-8",
            contentType: false,
            success: function (data) {
                console.log(data);
                window.location.reload(true);
            },
            error: function (data) {
                console.log("fail");
            }
        });
    });

    $("#btn2").on("click", function () {
        $("#f_upload").fileinput('clear').fileinput('unlock');
    });

    $("#f_upload").fileinput({
        language:'zh',//设置文中文
        uploadUrl:'uploadFiles',//图片上传的url，我这里对应的是后台struts配置好的的action方法
        showCaption:true,//显示标题
        showRemove:true, //显示移除按钮
        uploadAsync:true,//默认异步上传
        showUpload:false,//显示上传按钮
        showPreview:true,//是否显示预览
        textEncoding:"UTF-8",//文本编码
        autoReplaceBoolean:false,//选择图片时不清空原图片
    });

    $("#f_upload").on('fileuploaded', function(event, data, previewId, index) {//异步上传成功结果处理
        // var img= JSON.parse(data.response);//接收后台传过来的json数据
        $(event.target)
            .fileinput('clear')
            .fileinput('unlock');
        var result = data.response;
        console.log(result);
    });

});


function changeBucket(bucket) {
    $.ajax({
        url: '/changeBucket',
        type: 'POST',
        data: {
            bucket: bucket
        },
        success: function () {
            window.location.reload(true);
        },
        error: function () {
            alert(" Change Bucket Error!! ");
        }
    })
}